package zhuozhuo.com.zhuo.view.activity;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.util.ArrayList;
import java.util.List;

import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.adapter.TotalRateAdapter;
import zhuozhuo.com.zhuo.bean.TotalRateBean;
import zhuozhuo.com.zhuo.presenter.MyLevelPresent;
import zhuozhuo.com.zhuo.view.TotalRateView;

public class TotalRateActivity extends BaseActivity implements TotalRateView {
    private int index = 0;
    private boolean isBottom = false;
    private PullToRefreshListView lv;
    private ILoadingLayout endLabels;
    private MyLevelPresent levelPresent;
    private TotalRateAdapter adapter;
    private List<TotalRateBean.DataBean> data;
    private EaseTitleBar titlebar;
    @Override
    public int getLayoutId() {
        return R.layout.activity_total_rate;
    }

    @Override
    public void initPresenter() {

    }
    @Override
    public void initView() {
        initTitleBar();
        lv = (PullToRefreshListView) findViewById(R.id.lv_rate);
        endLabels = lv.getLoadingLayoutProxy(false, true);

        levelPresent = new MyLevelPresent(this);
        levelPresent.getTotalRate(index, getIntent().getStringExtra("useId"));

        data = new ArrayList<>();
        adapter = new TotalRateAdapter(this, data);
        lv.setAdapter(adapter);

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

    }

    protected void initTitleBar() {
        titlebar = (EaseTitleBar) findViewById(R.id.rate_titlebar);
        titlebar.setTitle(getIntent().getStringExtra("title"));
        titlebar.setLeftImageResource(R.drawable.ease_mm_title_back);
        titlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void addmore() {
        index += 10;
        levelPresent.getTotalRate(index, getIntent().getStringExtra("useId"));
    }

    private void refresh() {
        isBottom=false;
        index = 0;
        data.clear();
        levelPresent.getTotalRate(index, getIntent().getStringExtra("useId"));
    }

    //Present层方法=============================================================
    @Override
    public void refreshListView(List<TotalRateBean.DataBean> list) {
        if (list==null||list.size()==0){
           lv.onRefreshComplete();
            isBottom=true;
        }else {
            data.addAll(list);
            adapter.notifyDataSetChanged();
            lv.onRefreshComplete();
        }
    }


}
