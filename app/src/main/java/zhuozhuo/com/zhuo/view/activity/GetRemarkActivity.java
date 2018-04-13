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
import zhuozhuo.com.zhuo.adapter.RemarkListAdapter;
import zhuozhuo.com.zhuo.bean.RemarkBean;
import zhuozhuo.com.zhuo.presentermodel.GetRemarkPersenterModel;
import zhuozhuo.com.zhuo.view.RemarkView;

/**
 * Created by Administrator on 2017/11/9.
 */

public class GetRemarkActivity extends BaseActivity implements RemarkView {
    private PullToRefreshListView refreshListView;

    private int index = 0;
    private ILoadingLayout endLabels;
    private EaseTitleBar titlebar;
    private GetRemarkPersenterModel getRemarkPersenterModel;
    private List<RemarkBean.DataBean> data=new ArrayList<>();
    private RemarkListAdapter listAdapter;
    private boolean isBottom=false;
    @Override
    public int getLayoutId() {
        return R.layout.activity_add;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initTitleBar();
        refreshListView = (PullToRefreshListView) findViewById(R.id.lv_add);
        endLabels = refreshListView.getLoadingLayoutProxy(
                true, true);

        listAdapter=new RemarkListAdapter(data,this);
        refreshListView.setAdapter(listAdapter);

        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
                if (isBottom){
                    endLabels.setPullLabel("全部加载完毕...");// 刚下拉时，显示的提示
                    endLabels.setRefreshingLabel("全部加载完毕...");// 刷新时
                    endLabels.setReleaseLabel("全部加载完毕...");// 下来达到一定距离时，显示的提示
                    addmore();
                }else {
                    endLabels.setPullLabel("上拉加载更多...");// 刚下拉时，显示的提示
                    endLabels.setRefreshingLabel("正在加载...");// 刷新时
                    endLabels.setReleaseLabel("松开立刻加载更多...");// 下来达到一定距离时，显示的提示
                    addmore();//加载更多
                }
            }
        });
        getRemarkPersenterModel = new GetRemarkPersenterModel(this);
        getRemarkPersenterModel.getRemarkList(index);
    }

    private void addmore() {
        index += 10;
       getRemarkPersenterModel.getRemarkList(index);
    }

    private void refresh() {
        isBottom=false;
        index = 0;
        data.clear();
        getRemarkPersenterModel.getRemarkList(index);
    }

    protected void initTitleBar() {
        titlebar = (EaseTitleBar) findViewById(R.id.add_titlebar);
        titlebar.setTitle("我的评价列表");
        titlebar.setLeftImageResource(R.drawable.ease_mm_title_back);
        titlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void changeRemark(List<RemarkBean.DataBean> list) {
        if (list==null||list.size()==0){
           isBottom=true;
        }else {
            for (int i = 0; i < list.size(); i++) {
                data.add(list.get(i));
            }
            listAdapter.notifyDataSetChanged();
        }
        refreshListView.onRefreshComplete();
    }


}
