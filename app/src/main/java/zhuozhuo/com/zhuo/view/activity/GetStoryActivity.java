package zhuozhuo.com.zhuo.view.activity;

import android.content.Intent;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hyphenate.easeui.widget.EaseTitleBar;
import java.util.ArrayList;
import java.util.List;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.adapter.StoryListAdapter;
import zhuozhuo.com.zhuo.bean.StoryVideoBean;
import zhuozhuo.com.zhuo.presentermodel.GetStoryPresentModel;
import zhuozhuo.com.zhuo.view.GetStoryView;

public class GetStoryActivity extends BaseActivity implements GetStoryView, AdapterView.OnItemClickListener {

    private PullToRefreshListView pullToRefreshListView;

    private int index = 0;
    private List<StoryVideoBean.DataBean> dataList = new ArrayList<>();
    private ILoadingLayout endLabels;
    private EaseTitleBar titlebar;
    private StoryListAdapter adapter;
    private boolean isBottom = false;

    private GetStoryPresentModel storyPresentModel;
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
        storyPresentModel = new GetStoryPresentModel(this);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_add);
        endLabels = pullToRefreshListView.getLoadingLayoutProxy(
                false, true);

        adapter = new StoryListAdapter(dataList, this);
        pullToRefreshListView.setAdapter(adapter);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setOnItemClickListener(this);

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        storyPresentModel.getStory(index);
    }

    private void addmore() {
        index += 10;
        storyPresentModel.getStory(index);
    }

    private void refresh() {
        isBottom=false;
        index = 0;
        dataList.clear();
        storyPresentModel.getStory(index);
    }


    protected void initTitleBar() {
        titlebar = (EaseTitleBar) findViewById(R.id.add_titlebar);
        titlebar.setTitle("个人故事视频列表");
        titlebar.setLeftImageResource(R.drawable.ease_mm_title_back);
        titlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    public void refreshList(List<StoryVideoBean.DataBean> list) {
        if (list==null||list.size()==0) {
            isBottom=true;
            pullToRefreshListView.onRefreshComplete();
        } else {
            for (int i = 0; i < list.size(); i++) {
                dataList.add(list.get(i));
            }
            adapter.notifyDataSetChanged();
            pullToRefreshListView.onRefreshComplete();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, MineVideoPlayActivity.class);
        intent.putExtra("id", dataList.get(i-1).getShare_id());
        intent.putExtra("url", dataList.get(i-1).getVideo_link());
        intent.putExtra("zan", dataList.get(i-1).getZan());
        intent.putExtra("title",dataList.get(i-1).getShare_title());
        intent.putExtra("photo",dataList.get(i-1).getVideo_thumb_link());
        startActivityForResult(intent,001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refresh();
    }


}
