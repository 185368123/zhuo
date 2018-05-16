

package com.hyphenate.chatuidemo.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.umeng.analytics.MobclickAgent;



@SuppressLint("Registered")
public class BaseActivity extends EaseBaseActivity {
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);//友盟统计
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);//友盟统计
    }


}
