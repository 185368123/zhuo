package com.hyphenate.chatuidemo.my;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.my.constract.ShareVideoConstract;
import com.hyphenate.chatuidemo.my.model.ShareVideoModel;
import com.hyphenate.chatuidemo.my.okhttp.ToastUtils;
import com.hyphenate.chatuidemo.my.presenter.ShareVideoPresenter;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

public class VideoPlayActivity extends AppCompatActivity implements View.OnClickListener, ShareVideoConstract.View {

    private LayoutVideo layoutVideo;
    private String mPlayUrl;
    private Button button_save;
    private Button button_share;
    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;
    private ShareVideoPresenter mPresenter;
    private ShareVideoModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        mPresenter = new ShareVideoPresenter();
        mModel = new ShareVideoModel();
        mPresenter.setVM(mModel,this);
        setResult(RESULT_OK);
        mPlayUrl = getIntent().getStringExtra("url");

        layoutVideo = (LayoutVideo) findViewById(R.id.videoPlay);
        button_save = (Button) findViewById(R.id.button_save);
        button_share = (Button) findViewById(R.id.button_share);

        button_save.setOnClickListener(this);
        button_share.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i ==R.id.button_save) {
            finish();
        } else if (i == R.id.button_share) {
            //分享到啄啄
            mPresenter.shareVideo(mPlayUrl);

        }
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
    public void shareSudcess() {
        finish();
        ToastUtils.showToast("视频分享成功");
    }
}
