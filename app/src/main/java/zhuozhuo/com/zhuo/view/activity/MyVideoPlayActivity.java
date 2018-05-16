package zhuozhuo.com.zhuo.view.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chatuidemo.my.LayoutVideo;
import com.hyphenate.chatuidemo.my.SampleListener;
import com.hyphenate.easeui.provider.UserInfoProvider;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.widget.SimpleVideo;

public class MyVideoPlayActivity extends BaseActivity {
    private SimpleVideo layoutVideo;
    private String mPlayUrl;
    private boolean isPlay;
    private OrientationUtils orientationUtils;
    private TextView tv;
    private ImageView iv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_video_play;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

        tv = findViewById(R.id.tv_my_video);
        iv = findViewById(R.id.iv_my_video);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建构建器
                AlertDialog.Builder builder = new AlertDialog.Builder(MyVideoPlayActivity.this);
                // 设置参数
                builder.setMessage("你还没有录制视频，是否现在去录制？")
                        .setNeutralButton("暂不设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("录制视频", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(RecordVideoActivity.class);
                        finish();
                    }
                }).setPositiveButton("选择视频", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(VideoSelectActivity.class);
                        finish();
                    }
                });
                builder.create().show();
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mPlayUrl = UserInfoProvider.getUserVideo();

        layoutVideo = (SimpleVideo) findViewById(R.id.lv_my_video);

        resolveNormalVideoUI();
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, layoutVideo);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption.setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setSeekRatio(1)
                .setUrl(mPlayUrl)
                .setCacheWithPlay(false)
                .setVideoTitle("")
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
                })
                .setLockClickListener(new LockClickListener() {
                    @Override
                    public void onClick(View view, boolean lock) {
                        if (orientationUtils != null) {
                            //配合下方的onConfigurationChanged
                            orientationUtils.setEnable(!lock);
                        }
                    }
                }).build(layoutVideo);
        layoutVideo.getStartButton().performClick();
    }

    private void resolveNormalVideoUI() {
        //增加title
        layoutVideo.getTitleTextView().setVisibility(View.GONE);
        layoutVideo.getBackButton().setVisibility(View.GONE);
    }

    private GSYVideoPlayer getCurPlay() {
        if (layoutVideo.getFullWindowPlayer() != null) {
            return  layoutVideo.getFullWindowPlayer();
        }
        return layoutVideo;
    }

    protected void initTitleBar() {

    }
    @Override
    protected void onPause() {
        getCurPlay().onVideoPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        getCurPlay().onVideoResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            getCurPlay().release();
        }
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

}
