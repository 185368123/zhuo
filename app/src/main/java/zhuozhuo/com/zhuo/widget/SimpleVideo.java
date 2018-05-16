package zhuozhuo.com.zhuo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import zhuozhuo.com.zhuo.R;

/**
 * Created by Administrator on 2018/5/9.
 */

public class SimpleVideo extends StandardGSYVideoPlayer {

    /**
     * 1.5.0开始加入，如果需要不同布局区分功能，需要重载
     */
    public SimpleVideo(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public SimpleVideo(Context context) {
        super(context);
    }

    public SimpleVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    //这个必须配置最上面的构造才能生效
    @Override
    public int getLayoutId() {
        return R.layout.mine_video_simple;
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
