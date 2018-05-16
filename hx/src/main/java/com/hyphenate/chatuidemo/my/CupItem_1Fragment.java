package com.hyphenate.chatuidemo.my;



import android.text.format.DateUtils;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.my.adatper.CupItemFragment_1ListAdapter;
import com.hyphenate.chatuidemo.my.bean.ConfrontationBean;
import com.hyphenate.chatuidemo.my.constract.GetRaceConstract;
import com.hyphenate.chatuidemo.my.model.GetRaceModel;
import com.hyphenate.chatuidemo.my.presenter.GetRacePresenter;

import java.util.ArrayList;
import java.util.List;

import li.com.base.base.BaseFragment;
import li.com.base.baseapp.BaseApplication;
import li.com.base.baseuntils.LogUtils;

/**
 * Created by Administrator on 2018/4/25.
 */

public class CupItem_1Fragment extends BaseFragment<GetRacePresenter,GetRaceModel> implements GetRaceConstract.View {

    private CupItemFragment_1ListAdapter adapter;
    private PullToRefreshListView prlv;
    private ILoadingLayout endLabels;
    private List<ConfrontationBean> list;
    private String hunderd_id;

    @Override
    protected int getLayoutResource() {
        return R.layout.cup_fragment_1_layout;
    }

    @Override
    public void initPresenter() {
           mPresenter.setVM(mModel,this);
    }

    @Override
    protected void initView(View view) {
        hunderd_id = getArguments().getString("hunderd_id");
        mPresenter.getRace(hunderd_id);
        prlv = (PullToRefreshListView)view.findViewById(R.id.cup_1_prlv);
        list = new ArrayList<>();
        adapter = new CupItemFragment_1ListAdapter(getContext(), list);
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
        mPresenter.getRace(hunderd_id);
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
    public void returnRace(List<ConfrontationBean> list_) {
        list.addAll(list_);
        adapter.notifyDataSetChanged();
        prlv.onRefreshComplete();
    }
}
