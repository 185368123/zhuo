package com.hyphenate.chatuidemo.my;

import android.os.Bundle;
import android.view.View;

import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.ui.BaseActivity;
import com.hyphenate.chatuidemo.widget.SimpleVideo_;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;

public class SimpleVideoActivity extends BaseActivity {

    private SimpleVideo_ sv;
    private String url;
    private GSYVideoOptionBuilder gsyVideoOption;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_video);
        sv = (SimpleVideo_) findViewById(R.id.sv_user_video);
        sv.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        url = getIntent().getStringExtra("url");
        if (url!=null){
            gsyVideoOption = new GSYVideoOptionBuilder();
            gsyVideoOption.setUrl(url);
            gsyVideoOption.build(sv);
            sv.getStartButton().performClick();
        }
    }
}
