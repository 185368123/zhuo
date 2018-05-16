package zhuozhuo.com.zhuo.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.util.ArrayList;
import java.util.List;

import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.adapter.CommentListAdapter;
import zhuozhuo.com.zhuo.bean.ArticleDetailBean;
import zhuozhuo.com.zhuo.bean.CommentBean;
import zhuozhuo.com.zhuo.presentermodel.CommentPresentModel;
import zhuozhuo.com.zhuo.presentermodel.GetArticleDetailePresentModel;
import zhuozhuo.com.zhuo.presentermodel.GetCommentListPresentModel;
import zhuozhuo.com.zhuo.view.ArticleDetaileView;
import zhuozhuo.com.zhuo.view.FullScreenView;
import zhuozhuo.com.zhuo.widget.CircleImageView;
import zhuozhuo.com.zhuo.widget.CommentPopupWindow;

public class ArticleDetaileActivity extends BaseActivity implements ArticleDetaileView, AdapterView.OnItemClickListener, FullScreenView {
    private GetArticleDetailePresentModel detailePresentModel;
    private EaseTitleBar titleBar;
    private CircleImageView iv;
    private TextView tv_3;
    private TextView tv_2;
    private TextView tv_1;
    private WebView vb;
    private PullToRefreshListView refreshListView;
    private List<CommentBean.DataBean> list = new ArrayList<>();
    private CommentListAdapter adapter;
    private CommentPresentModel commentPresentModel;//评论数据层
    private GetCommentListPresentModel getList;//获取评论数据层
    private String id;
    private int index = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_article_detaile;
    }

    @Override
    public void initPresenter() {

    }


    @Override
    public void initView() {
        initTitleBar();
        detailePresentModel = new GetArticleDetailePresentModel(this);
        id = getIntent().getStringExtra("id");
        detailePresentModel.getArticleDetaile(getIntent().getStringExtra("id"));

        tv_1 = (TextView) findViewById(R.id.tv_article1);
        tv_2 = (TextView) findViewById(R.id.tv_article2);
        tv_3 = (TextView) findViewById(R.id.tv_article3);

        iv = (CircleImageView) findViewById(R.id.iv_article);
        //vb = (WebView) findView(R.id.wv_article);
        vb = new WebView(this);
        vb.setMinimumHeight(500);
        refreshListView = (PullToRefreshListView) findViewById(R.id.lv_article);
        refreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
        adapter = new CommentListAdapter(this, list);
        refreshListView.setAdapter(adapter);
        ListView listView = refreshListView.getRefreshableView();
        listView.addHeaderView(vb);
        refreshListView.setOnItemClickListener(this);
        commentPresentModel = new CommentPresentModel();
        getList = new GetCommentListPresentModel(this);
        getList.getList(index, id);

    }


    protected void initTitleBar() {
        titleBar = (EaseTitleBar) findViewById(R.id.articledetaile_titlebar);
        titleBar.setTitle("详情");
        titleBar.setLeftImageResource(R.drawable.ease_mm_title_back);
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        titleBar.setRightImageResource(R.drawable.remark);
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommentPopupWindow((FragmentActivity) mContext, new CommentPopupWindow.LiveCommentSendClick() {
                    @Override
                    public void onSendClick(View view, boolean isBull, String comment) {
                        commentPresentModel.comment(id, comment);
                    }
                }).showReveal();
            }
        });
    }

    @Override
    public void showArticleDeatile(List<ArticleDetailBean.DataBean> list) {
        if (list.size() > 0) {
            ArticleDetailBean.DataBean bean = list.get(0);
            Glide.with(this).load(bean.getPhoto_link()).into(iv);
            tv_1.setText(bean.getShare_title());
            tv_2.setText(bean.getNick_name());
            tv_3.setText(bean.getCreated());
            String html = "<html><body>" + bean.getContent() + "</body></html>";
            vb.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        new CommentPopupWindow(this, new CommentPopupWindow.LiveCommentSendClick() {
            @Override
            public void onSendClick(View view, boolean isBull, String comment) {
                commentPresentModel.comment(id, list.get(i - 2).getUser_id(), list.get(i - 2).getComment_id(), comment);
            }
        }).showReveal();
    }

    @Override
    public void changeList(List<CommentBean.DataBean> data) {
        if (data != null) {
            list.clear();
            list.addAll(data);

        }
        adapter.notifyDataSetChanged();
        refreshListView.onRefreshComplete();
    }

}
