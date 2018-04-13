package zhuozhuo.com.zhuo.view.activity;

import android.content.Intent;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hyphenate.chatuidemo.my.bean.MineArticleBean;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.util.ArrayList;
import java.util.List;

import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.adapter.MineArticleListAdapter;
import zhuozhuo.com.zhuo.contract.MineArticleConstract;
import zhuozhuo.com.zhuo.model.MineArticleActivityModel;
import zhuozhuo.com.zhuo.presenter.MineArticleActivityPresenter;


/**
 * Created by Administrator on 2017/11/9.
 */

public class MineArticleActivity extends BaseActivity<MineArticleActivityModel, MineArticleActivityPresenter> implements MineArticleConstract.View {
    private PullToRefreshListView listView;
    private int index = 0;
    private EaseTitleBar mytitlebar;
    private ILoadingLayout endLabels;
    private boolean isBottom = false;
    private Button button;
    private List<MineArticleBean> data = new ArrayList<>();
    private MineArticleListAdapter mineArticleListAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_article;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(mModel, this);
    }

    @Override
    public void initView() {
        listView = (PullToRefreshListView) findViewById(R.id.lv_article);
        mytitlebar = (EaseTitleBar) findViewById(R.id.article_titlebar);
        initTitleBar();
        button = (Button) findViewById(R.id.button_article);
        button.setVisibility(View.GONE);
        endLabels = listView.getLoadingLayoutProxy(
                false, true);
        mPresenter.getMyArticle(String.valueOf(index));
        mineArticleListAdapter = new MineArticleListAdapter(data, this);
        listView.setAdapter(mineArticleListAdapter);

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, ArticleDetaileActivity.class);
                intent.putExtra("id", data.get(i - 1).getShare_id());
                startActivity(intent);
            }
        });
    }

    private void addmore() {
        index += 10;
        mPresenter.getMyArticle(String.valueOf(index));
    }

    private void refresh() {
        isBottom = false;
        index = 0;
        data.clear();
        mPresenter.getMyArticle(String.valueOf(index));
    }


    protected void initTitleBar() {
        mytitlebar.setTitle("我的帖子");
        mytitlebar.setLeftImageResource(R.drawable.ease_mm_title_back);
        mytitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mytitlebar.setRightImageResource(R.drawable.writearticlenormal);
        mytitlebar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(WriteArticleActivity.class);
            }
        });
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
    public void returnMyArticlr(List<MineArticleBean> mineArticleBeans) {
        if (mineArticleBeans == null || mineArticleBeans.size() == 0) {
            isBottom = true;
            listView.onRefreshComplete();
        } else {
            for (int i = 0; i < mineArticleBeans.size(); i++) {
                data.add(mineArticleBeans.get(i));
            }
            mineArticleListAdapter.notifyDataSetChanged();
            listView.onRefreshComplete();
        }
    }
}
