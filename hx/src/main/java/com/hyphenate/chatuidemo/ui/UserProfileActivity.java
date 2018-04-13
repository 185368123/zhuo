package com.hyphenate.chatuidemo.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.DBOpenHelp;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.UserMsgDBHelp;
import com.hyphenate.chatuidemo.my.bean.UserDB;

import java.util.List;

public class UserProfileActivity extends BaseActivity {
    private ImageView headAvatar;
    private TextView tvNickName;
    private TextView tv_card1;
    private TextView tv_card2;
    private TextView tv_card3;
    private TextView tv_card4;
    private TextView tv_card5;
    private TextView tv_card6;
    private TextView tv_card7;
    private TextView tv_card8;
    private TextView tv_card9;
    private TextView tv_college;
    private TextView tv_hobby;
    private TextView tv_sex;
    protected String username;
    private Button bt_add_friend;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_user_profile);
        initView();
        username = getIntent().getStringExtra("username");
        setView(username);
        UserMsgDBHelp.getUserMsgDBHelp().updateMsg(username);
    }

    private void setView(final String username) {
        UserDB userDB = UserMsgDBHelp.getUserMsgDBHelp().searchByUserId(username);
        if (userDB!=null) {
            Glide.with(this).load(userDB.getPhoto_link()).into(headAvatar);
            tvNickName.setText(userDB.getNick_name());
            if (userDB.getSex().equals("1")) {
                tv_sex.setText("男");
            } else if (userDB.getSex().equals("2")) {
                tv_sex.setText("女");
            }
            if (userDB.getLocation()==null||userDB.getLocation().equals("")){
                tv_college.setText("无");
            }else {
                tv_college.setText(userDB.getLocation());
            }
            if (userDB.getAccount()==null||userDB.getAccount().equals("")){
                tv_hobby.setText("无");
            }else {
                tv_hobby.setText(userDB.getAccount());
            }

            List<String> card=userDB.getCard();
            tv_card1.setText(card.get(0%card.size()));
            tv_card2.setText(card.get(1%card.size()));
            tv_card3.setText(card.get(2%card.size()));
            tv_card4.setText(card.get(3%card.size()));
            tv_card5.setText(card.get(4%card.size()));
            tv_card6.setText(card.get(5%card.size()));
            tv_card7.setText(card.get(6%card.size()));
            tv_card8.setText(card.get(7%card.size()));
            tv_card9.setText(card.get(8%card.size()));
            bt_add_friend.setOnClickListener(new View.OnClickListener() {//添加好友
                @Override
                public void onClick(View view) {
                    progressDialog = new ProgressDialog(UserProfileActivity.this);
                    String stri = getResources().getString(R.string.Is_sending_a_request);
                    progressDialog.setMessage(stri);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                //demo use a hardcode reason here, you need let user to input if you like
                                String s =getResources().getString(R.string.Add_a_friend);
                                EMClient.getInstance().contactManager().addContact(username, s);
                                DBOpenHelp.getDBOpenHelp().selectByUserId(username);
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        progressDialog.dismiss();
                                        String s1 = getResources().getString(R.string.send_successful);
                                        Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
                                    }
                                });
                            } catch (final Exception e) {
                               runOnUiThread(new Runnable() {
                                    public void run() {
                                        progressDialog.dismiss();
                                        String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                                        Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }).start();
                }
            });
            if (!DemoHelper.getInstance().getContactList().containsKey(username)) {
                bt_add_friend.setVisibility(View.VISIBLE);
            }else {
                bt_add_friend.setVisibility(View.GONE);
            }
        }

    }

    private void initView() {
        headAvatar = (ImageView) findViewById(R.id.user_head_avatar);
        tvNickName = (TextView) findViewById(R.id.user_nickname);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_college= (TextView)findViewById(R.id.tv_college);
        tv_hobby= (TextView)findViewById(R.id.tv_hobby);
        tv_card1 = (TextView) findViewById(R.id.tv_card1);
        tv_card2 = (TextView) findViewById(R.id.tv_card2);
        tv_card3 = (TextView) findViewById(R.id.tv_card3);
        tv_card4 = (TextView) findViewById(R.id.tv_card4);
        tv_card5 = (TextView) findViewById(R.id.tv_card5);
        tv_card6 = (TextView) findViewById(R.id.tv_card6);
        tv_card7 = (TextView) findViewById(R.id.tv_card7);
        tv_card8 = (TextView) findViewById(R.id.tv_card8);
        tv_card9 = (TextView) findViewById(R.id.tv_card9);
        bt_add_friend = (Button) findViewById(R.id.bt_add_friend);
    }


}

	

	
	




