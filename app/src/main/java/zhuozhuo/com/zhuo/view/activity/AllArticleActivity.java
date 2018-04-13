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
import com.hyphenate.easeui.widget.EaseTitleBar;
import java.util.ArrayList;
import java.util.List;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.adapter.ArticleListAdapter;
import com.hyphenate.chatuidemo.my.bean.AllArticleBean;
import zhuozhuo.com.zhuo.contract.AllArticleActivityConstract;
import zhuozhuo.com.zhuo.model.AllArticleActivityModel;
import zhuozhuo.com.zhuo.presenter.AllArticleActivityPresenter;

public class AllArticleActivity extends BaseActivity<AllArticleActivityModel, AllArticleActivityPresenter> implements  AllArticleActivityConstract.View {

    private ArticleListAdapter listAdapter;
    private List<AllArticleBean> been = new ArrayList<>();
    private PullToRefreshListView listView;
    private int index = 0;
    private EaseTitleBar mytitlebar;
    private ILoadingLayout endLabels;
    private boolean isBottom = false;
    private Button button;

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
        initTitleBar();
        listView = (PullToRefreshListView) findViewById(R.id.lv_article);
        button = (Button) findViewById(R.id.button_article);
        button.setVisibility(View.VISIBLE);
        endLabels = listView.getLoadingLayoutProxy(
                false, true);
        mPresenter.getAllArticle(String.valueOf(index));
        listAdapter = new ArticleListAdapter(been, this);

        listView.setAdapter(listAdapter);
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, ArticleDetaileActivity.class);
                intent.putExtra("id", been.get(i - 1).getShare_id());
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_mine = new Intent(mContext, MineArticleActivity.class);
                startActivity(intent_mine);
            }
        });
    }

    private void addmore() {
        index += 10;
        mPresenter.getAllArticle(String.valueOf(index));
    }

    private void refresh() {
        isBottom = false;
        index = 0;
        been.clear();
        mPresenter.getAllArticle(String.valueOf(index));
    }

    protected void initTitleBar() {
        mytitlebar = (EaseTitleBar) findViewById(R.id.article_titlebar);
        mytitlebar.setTitle("论坛");
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
    public void returnAllArticle(List<AllArticleBean> allArticleBeans) {
        if (allArticleBeans == null || allArticleBeans.size() == 0) {
            isBottom = true;
        } else {
            for (int i = 0; i < allArticleBeans.size(); i++) {
                been.add(allArticleBeans.get(i));
            }
            listAdapter.notifyDataSetChanged();
        }
        listView.onRefreshComplete();
    }
}
