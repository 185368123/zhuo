package com.hyphenate.chatuidemo.my;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.my.adatper.TeamListAdapter;
import com.hyphenate.chatuidemo.my.bean.CupMemberBean;
import com.hyphenate.chatuidemo.my.bean.CupTeamBean;
import com.hyphenate.chatuidemo.my.constract.InviteMemberConstract;
import com.hyphenate.chatuidemo.my.model.InviteMemberModel;
import com.hyphenate.chatuidemo.my.presenter.InviteMemberPresenter;
import com.hyphenate.chatuidemo.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/4/24.
 */

public class TeamActivity extends BaseActivity implements InviteMemberConstract.View {

    private ILoadingLayout endLabels;
    private PullToRefreshListView pullView;
    private InviteMemberPresenter inviteMemberPresenter;
    private InviteMemberModel inviteMemberModel;
    private List<CupTeamBean> cupTeamBeans=new ArrayList<>();
    private TeamListAdapter adapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_team);
        inviteMemberPresenter = new InviteMemberPresenter();
        inviteMemberModel = new InviteMemberModel();
        inviteMemberPresenter.setVM(inviteMemberModel,this);

        adapter = new TeamListAdapter(cupTeamBeans,this,inviteMemberPresenter);
        pullView=(PullToRefreshListView)findViewById(R.id.lv_apply_team);
        pullView.setAdapter(adapter);

        endLabels = pullView.getLoadingLayoutProxy(
                false, true);
        pullView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
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
        });

        inviteMemberPresenter.getTeam();
    }

    private void refresh() {
       cupTeamBeans.clear();
        inviteMemberPresenter.getTeam();
    }



    public void teamBack(View view){
        finish();
    }

    public void tofriends(View view){
        Intent intent=new Intent(this,InviteMemberActivity.class);
        startActivity(intent);
        finish();
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

    }

    @Override
    public void returnFriendsr(List<CupMemberBean> data) {

    }

    @Override
    public void returnTeam(List<CupTeamBean> teamBeans) {
       cupTeamBeans.addAll(teamBeans);
        adapter.notifyDataSetChanged();
        pullView.onRefreshComplete();

    }
}
