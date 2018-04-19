package zhuozhuo.com.zhuo.view.activity;

import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hyphenate.chatuidemo.provider.UserInfoProvider;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import java.util.ArrayList;
import java.util.List;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.adapter.CommentListAdapter;
import zhuozhuo.com.zhuo.bean.CommentBean;
import zhuozhuo.com.zhuo.presentermodel.CommentPresentModel;
import zhuozhuo.com.zhuo.presentermodel.GetCommentListPresentModel;
import zhuozhuo.com.zhuo.presentermodel.LovePresentModel;
import zhuozhuo.com.zhuo.view.FullScreenView;
import zhuozhuo.com.zhuo.view.Video.LandLayoutVideo;
import com.hyphenate.chatuidemo.my.EmptyView;
import zhuozhuo.com.zhuo.widget.CommentPopupWindow;

public class ShareVideoPlayActivity extends BaseActivity implements CommentPopupWindow.LiveCommentSendClick, EmptyView, AdapterView.OnItemClickListener, FullScreenView, View.OnClickListener {

    private LandLayoutVideo detailPlayer;
    private GetCommentListPresentModel getList;//获取评论数据层
    private int index = 0;
    private String id = null;

    private CommentPresentModel commentPresentModel;//评论数据层
    private LovePresentModel lovePresentModel;//点赞数据层
    private boolean linear = false;//评论栏显示
    private PullToRefreshListView refreshListView;
    private List<CommentBean.DataBean> list = new ArrayList<>();

    private String zan[];
    private String zanName;
    private CommentPopupWindow popupWindow;//评论输入弹出框

    private CommentListAdapter adapter;
    private TextView tv_zan;
    private LinearLayout zan_ll;
    private boolean iszan = false;
    private boolean haszan = false;

    private boolean isPlay;
    private boolean isPause;

    private String mPlayUrl;
    private String title;
    private String photo;
    private StringBuffer buffer;
    private TextView tv_remark; //评论按钮
    private ImageView iv_zan;
    private LinearLayout zan_linear;

    @Override
    public int getLayoutId() {
        return R.layout.activity_share_video_play;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        popupWindow = new CommentPopupWindow(this, this);
        detailPlayer = (LandLayoutVideo) findViewById(R.id.detail_player);
        refreshListView = (PullToRefreshListView) findViewById(R.id.lv_comment);
        getList = new GetCommentListPresentModel(this);
        tv_zan = (TextView) findViewById(R.id.tv_zan);
        zan_ll = (LinearLayout) findViewById(R.id.zan_ll);
        tv_remark = (TextView) findViewById(R.id.tv_comment_button);
        iv_zan = (ImageView) findViewById(R.id.iv_zan);
        zan_linear = (LinearLayout) findViewById(R.id.zan_linear);

        lovePresentModel = new LovePresentModel(this);
        commentPresentModel = new CommentPresentModel();

        zan_linear.setOnClickListener(this);
        tv_remark.setOnClickListener(this);

        adapter = new CommentListAdapter(this, list);
        refreshListView.setOnItemClickListener(this);
        refreshListView.setAdapter(adapter);
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
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
        });

        mPlayUrl = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        photo = getIntent().getStringExtra("photo");
        //根据传入ID去获取视频评论详情
        if (getIntent().getStringExtra("id") != null) {
            id = getIntent().getStringExtra("id");
            getList.getList(index, id);
        }
        buffer = new StringBuffer();
        //判断是否有赞，有则显示
        if (getIntent().getStringExtra("zan")!=null||getIntent().getStringExtra("zan")!="") {
            haszan = true;
            zan_ll.setVisibility(View.VISIBLE);
            tv_zan.setText(getIntent().getStringExtra("zan"));
            zanName = getIntent().getStringExtra("zan");
            zan = zanName.split(",");
            for (int i = 0; i < zan.length; i++) {
                if (zan[i].equals(UserInfoProvider.getNickName())) {
                    iszan = true;
                    iv_zan.setBackground(ContextCompat.getDrawable(this,R.drawable.qqlove));
                } else {
                    if (i == zan.length - 1) {
                        buffer.append(zan[i]);
                    } else {
                        buffer.append(zan[i] + ",");
                    }
                }
            }
            if (zan.length==1&&zan[0].equals("")){
                zan_ll.setVisibility(View.GONE);
            }
            zanName = buffer.toString();
            buffer.delete(0, buffer.length());
        } else {
            haszan = false;
            zan_ll.setVisibility(View.GONE);
        }
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this).load(photo).into(imageView);
        resolveNormalVideoUI();


        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption
                .setShowFullAnimation(true)
                .setThumbImageView(imageView)//封面图
                .setUrl(mPlayUrl)  //播放地址
                .setVideoTitle(title)  //设置标题
                .setCacheWithPlay(true) //是否边下边播
                .build(detailPlayer);
        detailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                detailPlayer.startWindowFullscreen(ShareVideoPlayActivity.this, true, true);
            }
        });
       detailPlayer.getStartButton().performClick();
       detailPlayer.getFullscreenButton().performClick();
    }

    private void refresh() {
        index = 0;
        list.clear();
        getList.getList(index, id);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_comment_button://写评论
                popupWindow.showReveal();
                break;
            case R.id.zan_linear://点赞按钮
                lovePresentModel.sendLove(id);
                break;
        }
    }

    @Override
    public void onSendClick(View view, boolean isBull, String comment) {
        commentPresentModel.comment(id, comment);
    }

    @Override
    public void emptyBack() {
        if (iszan) {
            if (haszan) {
                if (zanName.length() == 0) {//赞的人只有我自己
                    tv_zan.setText("");
                    zan_ll.setVisibility(View.GONE);
                } else
                    tv_zan.setText(zanName);
            } else {
                tv_zan.setText("");
                zan_ll.setVisibility(View.GONE);
            }
            iv_zan.setBackground(ContextCompat.getDrawable(this,R.drawable.qqunlove));;
            iszan = false;

        } else {
            if (haszan) {//存在赞
                if (zanName.length() == 0) {//赞的人只有我自己
                    tv_zan.setText(UserInfoProvider.getNickName());
                    zan_ll.setVisibility(View.VISIBLE);
                } else {//有其他人点赞
                    tv_zan.setText(UserInfoProvider.getNickName() + "," + zanName);
                }
            } else {//没有赞
                tv_zan.setText(UserInfoProvider.getNickName());
                zan_ll.setVisibility(View.VISIBLE);
            }
            iszan = true;
            iv_zan.setBackground(ContextCompat.getDrawable(this,R.drawable.qqlove));;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        new CommentPopupWindow(this, new CommentPopupWindow.LiveCommentSendClick() {
            @Override
            public void onSendClick(View view, boolean isBull, String comment) {
                commentPresentModel.comment(id, list.get(i - 1).getUser_id(), list.get(i - 1).getComment_id(), comment);
            }
        }).showReveal();
    }


    @Override
    public void onBackPressed() {
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        getCurPlay().onVideoPause();
        super.onPause();
        isPause = true;
    }


    @Override
    protected void onResume() {
        getCurPlay().onVideoResume();
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            getCurPlay().release();
        }
    }

    private void resolveNormalVideoUI() {
        detailPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private GSYVideoPlayer getCurPlay() {
        if (detailPlayer.getFullWindowPlayer() != null) {
            return detailPlayer.getFullWindowPlayer();
        }
        return detailPlayer;
    }

    //当评论加载完毕回调
    @Override
    public void changeList(List<CommentBean.DataBean> data) {
        for (int i = 0; i < data.size(); i++) {
            list.add(data.get(i));
        }
        adapter.notifyDataSetChanged();
        refreshListView.onRefreshComplete();
    }



}
