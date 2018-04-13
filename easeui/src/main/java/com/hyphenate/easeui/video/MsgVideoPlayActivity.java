package com.hyphenate.easeui.video;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hyphenate.easeui.R;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;

public class MsgVideoPlayActivity extends AppCompatActivity {
    private String mPlayUrl;
    private LayoutVideo video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_video_play);
        mPlayUrl = getIntent().getStringExtra("url");
        video = (LayoutVideo) findViewById(R.id.msg_video);
        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption
                .setShowFullAnimation(true)
                .setUrl(mPlayUrl)  //播放地址
                .setVideoTitle("")  //设置标题
                .setCacheWithPlay(true) //是否边下边播
                .build(video);
        video.getStartButton().performClick();
        video.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
