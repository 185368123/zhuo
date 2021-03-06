package com.hyphenate.chatuidemo.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.DBOpenHelp;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.UserMsgDBHelp;
import com.hyphenate.chatuidemo.my.SimpleVideoActivity;
import com.hyphenate.chatuidemo.my.bean.JoinGroupBean;
import com.hyphenate.chatuidemo.my.bean.UserDB;
import com.hyphenate.chatuidemo.my.bean.UserMsgBean;
import com.hyphenate.chatuidemo.my.constract.GetUserMsgConstract;
import com.hyphenate.chatuidemo.my.constract.UserProfileConstract;
import com.hyphenate.chatuidemo.my.model.UserProfileModel;
import com.hyphenate.chatuidemo.my.presenter.UserProfilePresenter;

import java.util.List;

import li.com.base.baseuntils.LogUtils;
import li.com.base.baseuntils.ToastUitl;

public class UserProfileActivity extends BaseActivity implements View.OnClickListener, UserProfileConstract.View {

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
    private Button bt_join_group;
    private ProgressDialog progressDialog;
    private LinearLayout ll_video;
    private UserDB userDB;
    private UserProfilePresenter mPresenter;
    private UserProfileModel mModel;
    String userVideo;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_user_profile);
        initView();
        username = getIntent().getStringExtra("username");
        setView(username);
        UserMsgDBHelp.getUserMsgDBHelp().updateMsg(username, new GetUserMsgConstract.View() {
            @Override
            public void returnUserMsg(UserMsgBean userMsgBean) {
                setView(username);
            }

            @Override
            public void showLoading(String title) {

            }

            @Override
            public void stopLoading() {

            }

            @Override
            public void showErrorTip(String msg) {

            }
        });
    }

    private void setView(final String username) {
        userDB = UserMsgDBHelp.getUserMsgDBHelp().searchByUserId(username);
        if (userDB != null) {
            Glide.with(this).load(userDB.getPhoto_link()).into(headAvatar);
            tvNickName.setText(userDB.getNick_name());
            userVideo=userDB.getUser_video();
            if (userDB.getSex().equals("1")) {
                tv_sex.setText("男");
            } else if (userDB.getSex().equals("2")) {
                tv_sex.setText("女");
            }
            if (userDB.getLocation() == null || userDB.getLocation().equals("")) {
                tv_college.setText("无");
            } else {
                tv_college.setText(userDB.getLocation());
            }
            if (userDB.getAccount() == null || userDB.getAccount().equals("")) {
                tv_hobby.setText("无");
            } else {
                tv_hobby.setText(userDB.getAccount());
            }

            List<String> card = userDB.getCard();
            tv_card1.setText(card.get(0 % card.size()));
            tv_card2.setText(card.get(1 % card.size()));
            tv_card3.setText(card.get(2 % card.size()));
            tv_card4.setText(card.get(3 % card.size()));
            tv_card5.setText(card.get(4 % card.size()));
            tv_card6.setText(card.get(5 % card.size()));
            tv_card7.setText(card.get(6 % card.size()));
            tv_card8.setText(card.get(7 % card.size()));
            tv_card9.setText(card.get(8 % card.size()));
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
                                String s = getResources().getString(R.string.Add_a_friend);
                                EMClient.getInstance().contactManager().addContact(username, s);
                                DBOpenHelp.getDBOpenHelp().selectByUserId(username);
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        bt_add_friend.setVisibility(View.GONE);
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
            } else {
                bt_add_friend.setVisibility(View.GONE);
            }
        }
         bt_join_group.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (userDB== null) {
                     UserMsgDBHelp.getUserMsgDBHelp().updateMsg(username, new GetUserMsgConstract.View() {
                         @Override
                         public void returnUserMsg(UserMsgBean userMsgBean) {
                             mPresenter.joinUserGroup(userMsgBean.getNick_name(),username);
                         }

                         @Override
                         public void showLoading(String title) {

                         }

                         @Override
                         public void stopLoading() {

                         }

                         @Override
                         public void showErrorTip(String msg) {

                         }
                     });
                 }else {
                     mPresenter.joinUserGroup(userDB.getNick_name(),userDB.getUser_id());
                 }
             }
         });
    }

    private void initView() {
        headAvatar = (ImageView) findViewById(R.id.user_head_avatar);
        tvNickName = (TextView) findViewById(R.id.user_nickname);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_college = (TextView) findViewById(R.id.tv_college);
        tv_hobby = (TextView) findViewById(R.id.tv_hobby);
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
        bt_join_group = (Button) findViewById(R.id.bt_join_group);
        ll_video = (LinearLayout) findViewById(R.id.ll_video);

        ll_video.setOnClickListener(this);

        mPresenter = new UserProfilePresenter();
        mModel = new UserProfileModel();
        mPresenter.setVM(mModel,this);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ll_video) {
            try {
                if (userVideo != null) {
                    Intent intent = new Intent(this, SimpleVideoActivity.class);
                    intent.putExtra("url", userVideo);
                    startActivity(intent);
                } else {
                    ToastUitl.showLong("对方暂未设置视频，请稍后再试!");
                }
            }catch (NullPointerException e){
                ToastUitl.showLong("对方暂未设置视频，请稍后再试!");
            }

        }
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void joinUserGroupSucess(JoinGroupBean joinGroupBean) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("userId",joinGroupBean.getGroup_id() );
        intent.putExtra("hundred_id", "");
        intent.putExtra("userName", joinGroupBean.getGroup_name());
        intent.putExtra(com.hyphenate.chatuidemo.Constant.EXTRA_CHAT_TYPE, com.hyphenate.chatuidemo.Constant.CHATTYPE_GROUP);
        startActivity(intent);
    }
}

	

	
	




