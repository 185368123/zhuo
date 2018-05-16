package com.hyphenate.chatuidemo.my;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.hyphenate.chatuidemo.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * Created by shuyu on 2016/12/23.
 * CustomGSYVideoPlayer是试验中，建议使用的时候使用StandardGSYVideoPlayer
 */
public class LayoutVideo extends StandardGSYVideoPlayer {

    /**
     * 1.5.0开始加入，如果需要不同布局区分功能，需要重载
     */
    public LayoutVideo(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public LayoutVideo(Context context) {
        super(context);
    }

    public LayoutVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    //这个必须配置最上面的构造才能生效
    @Override
    public int getLayoutId() {
        return R.layout.mine_video_normal1;
    }

    @Override
    protected void updateStartImage() {
        if (mIfCurrentIsFullscreen) {
            if(mStartButton instanceof ImageView) {
                ImageView imageView = (ImageView) mStartButton;
                if (mCurrentState == CURRENT_STATE_PLAYING) {
                    imageView.setImageResource(R.drawable.video_click_pause_selector);
                } else if (mCurrentState == CURRENT_STATE_ERROR) {
                    imageView.setImageResource(R.drawable.video_click_play_selector);
                } else {
                    imageView.setImageResource(R.drawable.video_click_play_selector);
                }
            }
        } else {
            super.updateStartImage();
        }
    }



}
