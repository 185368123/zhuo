package zhuozhuo.com.zhuo.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import java.util.ArrayList;
import java.util.List;
import li.com.base.base.BaseFragment;
import li.com.base.baserx.RxManager;
import li.com.base.basesinglebean.SingleBeans;
import li.com.base.basesinglebean.SingleChooseBean;
import li.com.base.basewidget.LoadingTip;
import rx.functions.Action1;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.adapter.Zhuo3ListViewAdapter;
import com.hyphenate.chatuidemo.my.bean.VideoBean;
import zhuozhuo.com.zhuo.contract.Zhuo3FragmentConstract;
import zhuozhuo.com.zhuo.model.Zhuo3FragmentModel;
import zhuozhuo.com.zhuo.presenter.Zhuo3FragmentPresenter;
import zhuozhuo.com.zhuo.view.activity.UnReadListActivity;

/**
 * Created by Administrator on 2017/1/16.
 */
public class Zhuo3Fragment extends BaseFragment<Zhuo3FragmentPresenter,Zhuo3FragmentModel> implements View.OnClickListener, Zhuo3FragmentConstract.View {

    private ILoadingLayout endLabels;
    private boolean isBottom = false;
    private int index = 0;
    private String choice_id ="";
    private PullToRefreshListView lv;
    private Zhuo3ListViewAdapter adapter;
    private List<VideoBean> videoList;
    private FrameLayout frameLayout;
    private TextView tv_unread;
    private TabLayout tablayout;
    private LoadingTip loadingTip;
    private List<SingleChooseBean> data;

    @Override
    protected int getLayoutResource() {
        return R.layout.zhuo3_layout;
    }

    @Override
    public void initPresenter() {
      mPresenter.setVM(mModel,this);
    }

    @Override
    protected void initView(View view) {
        loadingTip = view.findViewById(R.id.loadedTip_zhuo3);
        lv = (PullToRefreshListView) view.findViewById(R.id.lv_zhuo3);
        frameLayout = (FrameLayout) view.findViewById(R.id.zhuo3_frame);
        tv_unread = (TextView) view.findViewById(R.id.tv_unreadsharenum);
        tablayout = (TabLayout) view.findViewById(R.id.tab_layout);
        endLabels = lv.getLoadingLayoutProxy(false, true);
        frameLayout.setOnClickListener(this);

        videoList = new ArrayList<>();
        adapter = new Zhuo3ListViewAdapter(videoList, this.getContext(),getActivity());
        lv.setAdapter(adapter);
        tv_unread = (TextView) view.findViewById(R.id.tv_unreadsharenum);
        int num= SingleBeans.getInstance().getUnReadBean().getComNum();
        if (num > 0) {
            tv_unread.setVisibility(View.VISIBLE);
            tv_unread.setText(num + "");
        } else {
            tv_unread.setVisibility(View.GONE);
        }

        initTablayout();
        initRxBus();
        mPresenter.getShareVideo(index,choice_id);
    }

    private void initRxBus() {
        mRxManager=new RxManager();
        mRxManager.on("unread", new Action1<String>() {
            @Override
            public void call(String s) {
                    int num= SingleBeans.getInstance().getUnReadBean().getComNum();
                    if (num > 0) {
                        tv_unread.setVisibility(View.VISIBLE);
                        tv_unread.setText(num + "");
                    } else {
                        tv_unread.setVisibility(View.GONE);
                    }
            }
        });
    }

    private void initTablayout() {
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tablayout.setBackgroundColor(Color.WHITE);//设置背景颜色
        tablayout.setTabTextColors(Color.BLACK, Color.parseColor("#f76243"));//设置字体选中前的颜色
        tablayout.setSelectedTabIndicatorColor(Color.parseColor("#f76243"));
        tablayout.addTab(tablayout.newTab().setText("全部"));
        tablayout.getTabAt(0).setTag("");
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                choice_id=(String) tab.getTag();
                refresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        data = SingleBeans.getInstance().getSingleChooseBeans();
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i).getValue().split(",").length>2){
                tablayout.addTab(tablayout.newTab().setText(data.get(i).getChoice_name()));
                tablayout.getTabAt(i+1).setTag(data.get(i).getChoice_id());
            }
        }
        tablayout.addTab(tablayout.newTab().setText("其他"));
        tablayout.getTabAt(tablayout.getTabCount()-1).setTag("0");
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
                endLabels.setRefreshingLabel("正在刷新...");// 刷新时
                endLabels.setReleaseLabel("松开立即刷新...");// 下来达到一定距离时，显示的提示
                String label = DateUtils.formatDateTime(
                        getContext(),
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
                    addMore();
                } else {
                    endLabels.setPullLabel("上拉加载更多...");// 刚下拉时，显示的提示
                    endLabels.setRefreshingLabel("正在加载...");// 刷新时
                    endLabels.setReleaseLabel("松开立刻加载更多...");// 下来达到一定距离时，显示的提示
                    addMore();//加载更多
                }
            }
        });
    }

    private void addMore() {
        index += 10;
        mPresenter.getShareVideo(index,choice_id);
    }

    private void refresh() {
        isBottom = false;
        index = 0;
        videoList.clear();
        mPresenter.getShareVideo(index,choice_id);
    }

    @Override
    public void onClick(View view) {
        startActivity(UnReadListActivity.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoading(String title) {
        loadingTip.setLoadingTip(LoadingTip.LoadStatus.loading);
    }

    @Override
    public void stopLoading() {
        loadingTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg) {
        loadingTip.setLoadingTip(LoadingTip.LoadStatus.error);
        loadingTip.setTips(msg);
        lv.onRefreshComplete();
    }

    @Override
    public void returnVideoList(List<VideoBean> list) {
        if (list == null || list.size() == 0) {
            isBottom = true;
        } else {
            videoList.addAll(list);
        }
        adapter.notifyDataSetChanged();
        lv.onRefreshComplete();
    }
}
