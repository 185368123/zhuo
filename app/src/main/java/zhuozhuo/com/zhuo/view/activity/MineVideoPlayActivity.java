package zhuozhuo.com.zhuo.view.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.hyphenate.chatuidemo.my.Untils.IShareVideo;
import com.hyphenate.chatuidemo.my.Untils.ShareVideo;
import com.hyphenate.chatuidemo.my.okhttp.ToastUtils;
import com.hyphenate.easeui.provider.UserInfoProvider;
import com.hyphenate.chatuidemo.widget.BottomDialog;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.presentermodel.CommentPresentModel;
import zhuozhuo.com.zhuo.presentermodel.DelViedeoPresentModel;
import zhuozhuo.com.zhuo.presentermodel.LovePresentModel;
import zhuozhuo.com.zhuo.view.Video.MineLayoutVideo;
import zhuozhuo.com.zhuo.view.Video.SampleListener;
import com.hyphenate.chatuidemo.my.EmptyView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import zhuozhuo.com.zhuo.widget.CommentPopupWindow;

public class MineVideoPlayActivity extends BaseActivity implements IShareVideo, EmptyView, View.OnClickListener {
    private String mPlayUrl;

    private String id = null;

    private String zanString = "";
    private String zans[] = {};
    private int zannum = 0;

    private boolean iszan = false;
    private boolean ismyself = false;
    private Button button_zan;
    private Button button_commnet;
    private TextView tv_zannum;
    private ImageView iv_share;
    private ImageView iv_go;
    private MineLayoutVideo mineLayoutVideo;
    private String title;
    private String photo;
    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;
    private ImageView imageView;
    private ImageView iv_back;
    private ImageView iv_love_num;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_video_play;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

        mPlayUrl = getIntent().getStringExtra("url");
        title=getIntent().getStringExtra("title");
        photo=getIntent().getStringExtra("photo");

        if (getIntent().getStringExtra("id") != null) {
            id = getIntent().getStringExtra("id");
        }
        tv_zannum = (TextView) findViewById(R.id.zan_num);
        button_zan = (Button) findViewById(R.id.zan_button);
        button_commnet = (Button) findViewById(R.id.comment_button);
        mineLayoutVideo = (MineLayoutVideo) findViewById(R.id.mine_video);
        iv_love_num = (ImageView) findViewById(R.id.iv_love_num);

        iv_back = (ImageView) findViewById(R.id.back);
        iv_back.setVisibility(View.VISIBLE);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        iv_go = (ImageView) findViewById(R.id.iv_go);
        button_zan.setOnClickListener(this);
        button_commnet.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        iv_go.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_love_num.setOnClickListener(this);


        //判断是否有赞，有则显示
        if (!getIntent().getStringExtra("zan").equals("")) {
            Log.e("赞", getIntent().getStringExtra("zan"));
            zanString = getIntent().getStringExtra("zan");
            zans = zanString.split(",");
            zannum = zans.length;
            tv_zannum.setText(zans.length + "");
            for (int i = 0; i < zans.length; i++) {
                if (zans[i].equals(UserInfoProvider.getNickName())) {
                    if (zans.length == 1) {
                        ismyself = true;
                        zannum -= 1;
                    }
                    iszan = true;
                    button_zan.setBackgroundResource(R.drawable.qqlove);
                } else {

                }
            }
        }
        //增加封面
        imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this).load(photo).into(imageView);
        resolveNormalVideoUI();

        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, mineLayoutVideo);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption.setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setSeekRatio(1)
                .setUrl(mPlayUrl)
                .setCacheWithPlay(false)
                .setVideoTitle(title)
                .setStandardVideoAllCallBack(new SampleListener() {
                    @Override
                    public void onPrepared(String url, Object... objects) {//加载成功，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
                        Debuger.printfError("***** onPrepared **** " + objects[0]);
                        Debuger.printfError("***** onPrepared **** " + objects[1]);
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        //orientationUtils.setEnable(true);
                        isPlay = true;
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        Debuger.printfError("***** onEnterFullscreen **** " + objects[0]);//title
                        Debuger.printfError("***** onEnterFullscreen **** " + objects[1]);//当前全屏player
                    }

                    @Override
                    public void onAutoComplete(String url, Object... objects) {
                        super.onAutoComplete(url, objects);
                    }

                    @Override
                    public void onClickStartError(String url, Object... objects) {
                        super.onClickStartError(url, objects);
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[0]);//title
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[1]);//当前非全屏player
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }
                }).build(mineLayoutVideo);
        setResult(1, null);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_share://右上角菜单按钮
                final BottomDialog dialog = BottomDialog.newInstance("",new String[]{"分享到微信","分享到啄啄","删除"});
                dialog.show(getSupportFragmentManager(),"dialog");
                dialog.setListener(new BottomDialog.OnClickListener() {
                    @Override
                    public void click(int position) {
                        switch (position){
                            case 0:
                                String url="http://www.zhuozhuotech.xin/playvideo/pvideo?video="+id;

                                UMImage image = new UMImage(MineVideoPlayActivity.this, photo);//网络图片
                                UMWeb web = new UMWeb(url);
                                web.setTitle(title);//标题
                                web.setThumb(image);  //缩略图
                                web.setDescription("快来围观");//描述
                                new ShareAction(MineVideoPlayActivity.this)
                                        .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                                        .withMedia(web)
                                        .share();
                                dialog.dismiss();
                                break;
                            case 1:
                                new ShareVideo(MineVideoPlayActivity.this).shareVideo(mPlayUrl);
                                dialog.dismiss();
                                break;
                            case 2:
                                new DelViedeoPresentModel(new EmptyView() {
                                    @Override
                                    public void emptyBack() {
                                        ToastUtils.showToast("删除成功");
                                        finish();
                                    }
                                }).del(mPlayUrl);
                                dialog.dismiss();
                                break;
                        }
                    }
                });
                break;
            case R.id.zan_button://点赞或者取消赞
                new LovePresentModel(this).sendLove(id);
                break;
            case R.id.comment_button://评论按钮
                new CommentPopupWindow(this, new CommentPopupWindow.LiveCommentSendClick() {
                    @Override
                    public void onSendClick(View view, boolean isBull, String comment) {
                        new CommentPresentModel().comment(id, comment);
                    }
                }).showReveal();
                break;
            case R.id.iv_go://去另一个播放界面
                Intent intent = new Intent(this, ShareVideoPlayActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("url", mPlayUrl);
                intent.putExtra("zan", zanString);
                intent.putExtra("title","");
                intent.putExtra("photo","123456");
                startActivity(intent);
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.iv_love_num:
                Intent intent_ = new Intent(this, ShareVideoPlayActivity.class);
                intent_.putExtra("id", id);
                intent_.putExtra("url", mPlayUrl);
                intent_.putExtra("zan", zanString);
                intent_.putExtra("title","");
                intent_.putExtra("photo","123456");
                startActivity(intent_);
                break;
        }

    }

    @Override
    public void shareSucess() {
        ToastUtils.showToast("分享成功");
    }

    @Override
    public void emptyBack() {//点赞或取消点赞成功后回调
        if (iszan) {
            if (ismyself) {
                tv_zannum.setText("");
            } else {
                tv_zannum.setText(zannum + "");
            }
            button_zan.setBackgroundResource(R.drawable.qqunlove);
            iszan = false;
        } else {
            button_zan.setBackgroundResource(R.drawable.qqlove);
            tv_zannum.setText(zannum + 1 + "");
            iszan = true;
        }
    }


    @Override
    public void onBackPressed() {

        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }

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
        //GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            mineLayoutVideo.onConfigurationChanged(this, newConfig, orientationUtils);
        }
    }


    private void resolveNormalVideoUI() {
        //增加title
        mineLayoutVideo.getTitleTextView().setVisibility(View.GONE);
        mineLayoutVideo.getBackButton().setVisibility(View.GONE);
    }

    private GSYVideoPlayer getCurPlay() {
        if (mineLayoutVideo.getFullWindowPlayer() != null) {
            return  mineLayoutVideo.getFullWindowPlayer();
        }
        return mineLayoutVideo;
    }
}
