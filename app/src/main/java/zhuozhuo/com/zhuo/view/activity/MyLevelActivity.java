package zhuozhuo.com.zhuo.view.activity;

import android.content.Intent;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.my.bean.UserDB;
import com.hyphenate.chatuidemo.my.model.GetUserMsgModel;
import com.hyphenate.chatuidemo.my.presenter.GetUserMsgPresenter;
import com.hyphenate.chatuidemo.provider.UserInfoProvider;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.hyphenate.exceptions.HyphenateException;

import org.litepal.crud.DataSupport;

import java.util.List;

import rx.Subscriber;
import rx.schedulers.Schedulers;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.presenter.GetHistoryPresent;

public class MyLevelActivity extends BaseActivity implements View.OnClickListener {
    private EaseTitleBar titlebar;
    private TextView tv1;
    private TextView tv2;
    private GetHistoryPresent getHistoryPresent;
    private PullToRefreshListView lv;
    private ILoadingLayout endLabels;
    private LinearLayout ll1;
    private LinearLayout ll2;
    private int index = 0;
    private boolean isBottom = false;
    private String friendIds="";
    @Override
    public int getLayoutId() {
        return R.layout.activity_my_level;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initTitleBar();
        ll1 = (LinearLayout) findViewById(R.id.level_linearlayout1);
        ll2 = (LinearLayout) findViewById(R.id.level_linearlayout2);
        tv1 = (TextView) findViewById(R.id.level_tv1);
        tv2 = (TextView) findViewById(R.id.level_tv2);
        lv= (PullToRefreshListView) findViewById(R.id.lv_level);
        endLabels = lv.getLoadingLayoutProxy(false, true);

        ll1.setOnClickListener(this);
        ll2.setOnClickListener(this);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
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


        List<UserDB> userDBList = DataSupport.select().where("user_id = ?", UserInfoProvider.getUserID()).find(UserDB.class);
        if (!(userDBList.size() > 0)) {
            GetUserMsgPresenter getUserMsgPresenter=new GetUserMsgPresenter();
            getUserMsgPresenter.setVM(new GetUserMsgModel(),null);
            getUserMsgPresenter.getUserMsg(UserInfoProvider.getUserID());
        }else {
            tv1.setText(userDBList.get(0).getMatch_count());
        }
        tv2.setText(UserInfoProvider.getExp());



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
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onNext(List<String> strings) {
                        StringBuffer sb=new StringBuffer();
                        for (int i = 0; i < strings.size(); i++) {
                            if (i==0){
                                sb.append(strings.get(i));
                            }else {
                                sb.append(","+strings.get(i));
                            }
                        }
                        if (sb.length()>0){
                            friendIds=sb.toString();
                        }else {
                            friendIds=",";
                        }

                    }
                });



        getHistoryPresent = new GetHistoryPresent();
        getHistoryPresent.getHistoryRate(0);


    }


    private void addmore() {
        index += 10;
        getHistoryPresent.getHistoryRate(index);
    }

    private void refresh() {
        isBottom=false;
        index = 0;
        //dataList.clear();
        getHistoryPresent.getHistoryRate(index);
    }
    protected void initTitleBar() {
        titlebar = (EaseTitleBar) findViewById(R.id.level_titlebar);
        titlebar.setTitle("我的等级");
        titlebar.setLeftImageResource(R.drawable.ease_mm_title_back);
        titlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.level_linearlayout1:
                Intent intent1=new Intent(this,TotalRateActivity.class);
                intent1.putExtra("useId",friendIds);
                intent1.putExtra("title","好友排名");
                startActivity(intent1);
                break;
            case R.id.level_linearlayout2:
                Intent intent2=new Intent(this,TotalRateActivity.class);
                intent2.putExtra("useId","");
                intent2.putExtra("title","总排名");
                startActivity(intent2);
                break;
        }
    }


}
