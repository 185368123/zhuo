package com.hyphenate.chatuidemo.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.ui.BaseActivity;

/**
 * Created by Administrator on 2018/5/30.
 */

public class RemarkActivity extends BaseActivity {

    private EditText et;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_remark_layout);
        et = (EditText) findViewById(R.id.et_remark);
    }

    public void savePreson(View view){
        setResult(RESULT_OK,new Intent().putExtra("com",et.getText().toString()));
        finish();
    }
    @Override
    public void onBackPressed() {

    }
}
