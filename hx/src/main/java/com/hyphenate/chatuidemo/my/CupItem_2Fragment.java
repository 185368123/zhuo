package com.hyphenate.chatuidemo.my;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.my.adatper.CupItemFragment_1ListAdapter;
import com.hyphenate.chatuidemo.my.adatper.CupItemFragment_2ListAdapter;
import com.hyphenate.chatuidemo.my.bean.IntegralBean;
import com.hyphenate.chatuidemo.my.bean.TeamScoreBean;
import com.hyphenate.chatuidemo.my.constract.GetIntegralConstract;
import com.hyphenate.chatuidemo.my.model.GetIntegralModel;
import com.hyphenate.chatuidemo.my.presenter.GetIntegralPresenter;

import java.util.ArrayList;
import java.util.List;

import li.com.base.base.BaseFragment;
import li.com.base.baseapp.BaseApplication;

/**
 * Created by Administrator on 2018/4/25.
 */

public class CupItem_2Fragment extends BaseFragment<GetIntegralPresenter, GetIntegralModel> implements GetIntegralConstract.View {

    private CupItemFragment_2ListAdapter adapter;
    private PullToRefreshListView prlv;
    private ILoadingLayout endLabels;
    private List<TeamScoreBean> list;
    private String hunderd_id;
    @Override
    protected int getLayoutResource() {
        return R.layout.cup_fragment_2_layout;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(mModel, this);
    }

    @Override
    protected void initView(View view) {
        hunderd_id = getArguments().getString("hunderd_id");
        mPresenter.getIntegral(hunderd_id);
        prlv = (PullToRefreshListView) view.findViewById(R.id.cup_2_prlv);
        list = new ArrayList<>();
        adapter = new CupItemFragment_2ListAdapter(getContext(), list);
        prlv.setAdapter(adapter);
        endLabels = prlv.getLoadingLayoutProxy(
                false, true);
        prlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                endLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
                endLabels.setRefreshingLabel("正在刷新...");// 刷新时
                endLabels.setReleaseLabel("松开立即刷新...");// 下来达到一定距离时，显示的提示
                String label = DateUtils.formatDateTime(
                        BaseApplication.getAppContext(),
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
    }

    private void refresh() {
        list.clear();
        mPresenter.getIntegral(hunderd_id);
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
    public void returnIntegral(List<TeamScoreBean> teamScoreBeans) {
        list.addAll(teamScoreBeans);
        adapter.notifyDataSetChanged();
        prlv.onRefreshComplete();
    }
}
