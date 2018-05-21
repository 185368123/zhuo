package com.hyphenate.chatuidemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.hyphenate.chatuidemo.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;


/**
 * Created by Administrator on 2018/5/9.
 */

public class SimpleVideo_ extends StandardGSYVideoPlayer {

    /**
     * 1.5.0开始加入，如果需要不同布局区分功能，需要重载
     */
    public SimpleVideo_(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public SimpleVideo_(Context context) {
        super(context);
    }

    public SimpleVideo_(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    //这个必须配置最上面的构造才能生效
    @Override
    public int getLayoutId() {
        return R.layout.video_simple;
    }

    @Override
    protected void updateStartImage() {
        if (mIfCurrentIsFullscreen) {
            if(mStartButton instanceof ImageView) {
                ImageView imageView = (ImageView) mStartButton;
                if (mCurrentState == CURRENT_STATE_PLAYING) {
                    imageView.setImageResource(com.hyphenate.chatuidemo.R.drawable.video_click_pause_selector);
                } else if (mCurrentState == CURRENT_STATE_ERROR) {
                    imageView.setImageResource(com.hyphenate.chatuidemo.R.drawable.video_click_play_selector);
                } else {
                    imageView.setImageResource(com.hyphenate.chatuidemo.R.drawable.video_click_play_selector);
                }
            }
        } else {
            super.updateStartImage();
        }
    }
}
