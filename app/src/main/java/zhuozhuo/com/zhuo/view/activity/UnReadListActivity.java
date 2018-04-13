package zhuozhuo.com.zhuo.view.activity;

import android.content.Intent;
import android.os.Bundle;
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
import zhuozhuo.com.zhuo.adapter.UnReadAdapter;
import zhuozhuo.com.zhuo.bean.UnReadBean;
import zhuozhuo.com.zhuo.presenter.UnReadPresent;
import zhuozhuo.com.zhuo.presentermodel.SetReadPresentModel;
import zhuozhuo.com.zhuo.view.UnReadView;

public class UnReadListActivity extends BaseActivity implements UnReadView {

    private EaseTitleBar titleBar_unread;
    private PullToRefreshListView lv_unread;
    private int index = 0;
    private List<UnReadBean.DataBean> data;
    private UnReadAdapter adapter;
    private ILoadingLayout endLabels;
    private boolean isBottom = false;
    private UnReadPresent present;
    @Override
    public int getLayoutId() {
        return R.layout.activity_un_read_list;
    }

    @Override
    public void initPresenter() {

    }
    @Override
    public void initView() {
        lv_unread = (PullToRefreshListView) findViewById(R.id.lv_unread);
        titleBar_unread = (EaseTitleBar) findViewById(R.id.unread_titlebar);
        data = new ArrayList<>();
        adapter = new UnReadAdapter(data, this);
        lv_unread.setAdapter(adapter);
        endLabels = lv_unread.getLoadingLayoutProxy(false, true);
        present = new UnReadPresent(this);
        present.getUnread(index);

        lv_unread.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        lv_unread.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                i-=1;
                new SetReadPresentModel().setRead(data.get(i).getShare_id());
                if (data.get(i).getType().equals("blog")){
                    Intent intent=new Intent(mContext,ArticleDetaileActivity.class);
                    intent.putExtra("id",data.get(i).getShare_id());
                    startActivityForResult(intent,111);
                }else if (data.get(i).getType().equals("share_story")){
                    Intent intent=new Intent(mContext,ShareVideoPlayActivity.class);
                    intent.putExtra("url",data.get(i).getVideo_link());
                    intent.putExtra("title",data.get(i).getShare_title());
                    intent.putExtra("id",data.get(i).getShare_id());
                    intent.putExtra("zan",data.get(i).getZan());
                    intent.putExtra("photo","");
                    startActivityForResult(intent,111);
                }
            }
        });
        initTitleBar();
    }
    private void addmore() {
        index += 10;
       present.getUnread(index);
    }
    private void refresh() {
        isBottom = false;
        index=0;
        data.clear();
        present.getUnread(index);
    }

    protected void initTitleBar() {
        titleBar_unread.setTitle("未读的回复");
        titleBar_unread.setLeftImageResource(R.drawable.ease_mm_title_back);
        titleBar_unread.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onSucess(List<UnReadBean.DataBean> data) {
        if(data==null||data.size()==0){
            isBottom=true;
            this.data.addAll(new ArrayList<UnReadBean.DataBean>());
            adapter.notifyDataSetChanged();
            lv_unread.onRefreshComplete();
        }else {
            this.data.addAll(data);
            adapter.notifyDataSetChanged();
            lv_unread.onRefreshComplete();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refresh();
    }

}
