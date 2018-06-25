package com.hyphenate.chatuidemo.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.ui.BaseActivity;

import li.com.base.baserx.RxManager;
import li.com.base.basesinglebean.SingleBeans;

/**
 * Created by Administrator on 2018/5/30.
 */

public class SetCitykActivity extends BaseActivity implements View.OnClickListener {


    private TextView tv_start;
    private TextView tv_end_1;
    private TextView tv_end_2;
    private TextView tv_end_3;
    private ImageView iv_delete1;
    private ImageView iv_delete2;
    private ImageView iv_delete3;
    private ImageView iv_delete4;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_setcity_layout);
        tv_start = (TextView) findViewById(R.id.tv_start);
        tv_end_1 = (TextView) findViewById(R.id.tv_end_1);
        tv_end_2 = (TextView) findViewById(R.id.tv_end_2);
        tv_end_3 = (TextView) findViewById(R.id.tv_end_3);
        iv_delete1 = (ImageView) findViewById(R.id.tv_start_delete);
        iv_delete2 = (ImageView) findViewById(R.id.tv_end_1_delete);
        iv_delete3 = (ImageView) findViewById(R.id.tv_end_2_delete);
        iv_delete4 = (ImageView) findViewById(R.id.tv_end_3_delete);

        refshView();

        tv_start.setOnClickListener(this);
        tv_end_1.setOnClickListener(this);
        tv_end_2.setOnClickListener(this);
        tv_end_3.setOnClickListener(this);
        iv_delete1.setOnClickListener(this);
        iv_delete2.setOnClickListener(this);
        iv_delete3.setOnClickListener(this);
        iv_delete4.setOnClickListener(this);
    }


    public void refshView(){
        tv_start.setText(SingleBeans.getInstance().getLocation());
        tv_end_1.setText(SingleBeans.getInstance().getCity1Name());
        tv_end_2.setText(SingleBeans.getInstance().getCity2Name());
        tv_end_3.setText(SingleBeans.getInstance().getCity3Name());
        if (SingleBeans.getInstance().getLocation().equals("")){
            iv_delete1.setVisibility(View.INVISIBLE);
            tv_start.setClickable(true);
        }else {
            iv_delete1.setVisibility(View.VISIBLE);
            tv_start.setClickable(false);
        }
        if (SingleBeans.getInstance().getCity1Name().equals("")){
            iv_delete2.setVisibility(View.INVISIBLE);
            tv_end_1.setClickable(true);
        }else {
            iv_delete2.setVisibility(View.VISIBLE);
            tv_end_1.setClickable(false);
        }
        if (SingleBeans.getInstance().getCity2Name().equals("")){
            iv_delete3.setVisibility(View.INVISIBLE);
            tv_end_2.setClickable(true);
        }else {
            iv_delete3.setVisibility(View.VISIBLE);
            tv_end_2.setClickable(false);
        }
        if (SingleBeans.getInstance().getCity3Name().equals("")){
            iv_delete4.setVisibility(View.INVISIBLE);
            tv_end_3.setClickable(true);
        }else {
            iv_delete4.setVisibility(View.VISIBLE);
            tv_end_3.setClickable(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refshView();

    }

    public void back_(View view) {
        new RxManager().post("ToItemFragment1", "");
        finish();
    }

    public void bt_go(View view) {
        if (SingleBeans.getInstance().getStatu().equals("0")){
            new RxManager().post("match_begin_1", "");
        }else if (SingleBeans.getInstance().getStatu().equals("66")){
            new RxManager().post("match_begin_2", "");
        }
        finish();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_start) {
            Intent intent=new Intent(this,CityChooseActivity.class);
            intent.putExtra("where","start");
            startActivityForResult(intent,9981);
        } else if (i == R.id.tv_end_1) {
            Intent intent=new Intent(this,CityChooseActivity.class);
            intent.putExtra("where","end_1");
            startActivityForResult(intent,9981);
        } else if (i == R.id.tv_end_2) {
            Intent intent=new Intent(this,CityChooseActivity.class);
            intent.putExtra("where","end_2");
            startActivityForResult(intent,9981);
        } else if (i == R.id.tv_end_3) {
            Intent intent=new Intent(this,CityChooseActivity.class);
            intent.putExtra("where","end_3");
            startActivityForResult(intent,9981);
        }else if (i==R.id.tv_start_delete){
            SingleBeans.getInstance().setLocation("");
            SingleBeans.getInstance().setLocation_id("");
            refshView();
        }else if (i==R.id.tv_end_1_delete){
            SingleBeans.getInstance().setCity1Name("");
            SingleBeans.getInstance().setCity1("");
            refshView();
        } else if (i==R.id.tv_end_2_delete){
            SingleBeans.getInstance().setCity2Name("");
            SingleBeans.getInstance().setCity2("");
            refshView();
        } else if (i==R.id.tv_end_3_delete){
            SingleBeans.getInstance().setCity3Name("");
            SingleBeans.getInstance().setCity3("");
            refshView();
        }
    }
}
