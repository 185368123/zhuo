package com.hyphenate.chatuidemo.my;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.my.adatper.InviteMemberListAdapter;
import com.hyphenate.chatuidemo.my.bean.CupMemberBean;
import com.hyphenate.chatuidemo.my.bean.CupTeamBean;
import com.hyphenate.chatuidemo.my.constract.InviteMemberConstract;
import com.hyphenate.chatuidemo.my.model.InviteMemberModel;
import com.hyphenate.chatuidemo.my.presenter.InviteMemberPresenter;
import com.hyphenate.chatuidemo.ui.BaseActivity;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

import li.com.base.basesinglebean.SingleBeans;
import li.com.base.baseuntils.LogUtils;
import li.com.base.baseuntils.StringUtils;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class InviteMemberActivity extends BaseActivity implements InviteMemberConstract.View, View.OnClickListener {

    private PullToRefreshListView pullListView;
    private int page = 0;
    private int pagesize=10;
    private ILoadingLayout endLabels;
    private boolean isBottom = false;
    private InviteMemberPresenter mPresenter;
    private InviteMemberModel mModel;
    private List<CupMemberBean> list=new ArrayList<>();
    private InviteMemberListAdapter adapter;
    private Button button_search;
    private Button button_team;
    private ImageView iv_back;
    private EditText et;
    private List<String> friends;
    private String friendString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_member);
        friends = SingleBeans.getInstance().getFriends();
        friendString = StringUtils.arryToString(friends);
        mPresenter = new InviteMemberPresenter();
        mModel = new InviteMemberModel();
        mPresenter.setVM(mModel, this);

        pullListView = (PullToRefreshListView) findViewById(R.id.lv_invite_member);
        adapter = new InviteMemberListAdapter(list,this,mPresenter,getIntent().getStringExtra("line_id"));
        pullListView.setAdapter(adapter);

        endLabels = pullListView.getLoadingLayoutProxy(
                false, true);
        pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                endLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
                endLabels.setRefreshingLabel("正在刷新...");// 刷新时
                endLabels.setReleaseLabel("松开立即刷新...");// 下来达到一定距离时，显示的提示
                String label = DateUtils.formatDateTime(
                        getApplicationContext(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 显示最后更新的时间
                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);

                refresh();//刷新列表
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (isBottom) {
                    endLabels.setPullLabel("全部加载完毕...");// 刚下拉时，显示的提示
                    endLabels.setRefreshingLabel("全部加载完毕...");// 刷新时
                    endLabels.setReleaseLabel("全部加载完毕...");// 下来达到一定距离时，显示的提示
                    addmore();
                } else {
                    endLabels.setPullLabel("上拉加载更多...");// 刚下拉时，显示的提示
                    endLabels.setRefreshingLabel("正在加载...");// 刷新时
                    endLabels.setReleaseLabel("松开立刻加载更多...");// 下来达到一定距离时，显示的提示
                    addmore();//加载更多
                }

            }
        });
        button_search = (Button) findViewById(R.id.bt_searchmember);
        button_team= (Button) findViewById(R.id.team);
        iv_back = (ImageView) findViewById(R.id.cupMemberBack);
        et = (EditText) findViewById(R.id.edit_name);
        button_search.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        button_team.setOnClickListener(this);
        rx.Observable.create(new rx.Observable.OnSubscribe<List<String>>() {
            private List<String> usernames;
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                try {
                    usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                subscriber.onNext(usernames);
            }
        }).subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.logd("获取聊天好友成功！");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<String> strings) {
                        SingleBeans.getInstance().setFriends(strings);
                        mPresenter.getFriends(StringUtils.arryToString(strings),page+"",pagesize+"",et.getText().toString());
                        for (int i = 0; i < strings.size(); i++) {
                            DemoHelper.getInstance().saveContact(new EaseUser(strings.get(i)));
                        }
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                    }
                });

    }


    private void addmore() {
        page ++;
        //mPresenter.getAllMember(page+"",pagesize+"",et.getText().toString());
        mPresenter.getFriends(friendString,page+"",pagesize+"",et.getText().toString());
    }

    private void refresh() {
        isBottom = false;
        page = 1;
        list.clear();
        //mPresenter.getAllMember(page+"",pagesize+"",et.getText().toString());
        mPresenter.getFriends(friendString,page+"",pagesize+"",et.getText().toString());
    }



    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void returnAllMember(List<CupMemberBean> data) {
        list.addAll(data);
        adapter.notifyDataSetChanged();
        pullListView.onRefreshComplete();
    }

    @Override
    public void returnFriendsr(List<CupMemberBean> data) {
        list.addAll(data);
        adapter.notifyDataSetChanged();
        pullListView.onRefreshComplete();
    }

    @Override
    public void returnTeam(List<CupTeamBean> cupTeamBeans) {

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bt_searchmember) {
            list.clear();
            mPresenter.getAllMember(page+"",pagesize+"",et.getText().toString());
        }else if (i==R.id.cupMemberBack){
            finish();
        }else if (i==R.id.team){
            Intent intent=new Intent(this,TeamActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
