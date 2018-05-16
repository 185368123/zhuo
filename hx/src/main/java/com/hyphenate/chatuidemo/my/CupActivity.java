package com.hyphenate.chatuidemo.my;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.my.adatper.CupViewPageAdatper;
import com.hyphenate.chatuidemo.ui.BaseActivity;
import com.hyphenate.easeui.widget.EaseTitleBar;

import li.com.base.baseuntils.LogUtils;

/**
 * Created by Administrator on 2018/4/25.
 */

public class CupActivity extends BaseActivity {

    private TabLayout tl;
    private ViewPager vp;
    private CupViewPageAdatper adatper;
    private  EaseTitleBar titlebar;
    private String hunderd_id;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_cup_layout);

        hunderd_id = getIntent().getStringExtra("hunderd_id");

        tl = (TabLayout)findViewById(R.id.tl_cup);
        vp = (ViewPager) findViewById(R.id.vp_cup);

        //绑定tabLayout和viewPager
        tl.setupWithViewPager(vp);

        adatper = new CupViewPageAdatper(getSupportFragmentManager(), hunderd_id);
        vp.setAdapter(adatper);


        titlebar = (EaseTitleBar) findViewById(R.id.cup_titlebar);
        titlebar.setTitle("积分表&对战表");
        titlebar.setLeftImageResource(R.drawable.ease_mm_title_back);
        titlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
