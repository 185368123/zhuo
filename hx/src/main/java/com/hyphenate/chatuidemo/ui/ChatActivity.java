package com.hyphenate.chatuidemo.ui;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;

import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.runtimepermissions.PermissionsManager;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;

/**
 *
 *
 */
public class ChatActivity extends BaseActivity {

    public final int CHAT=10010;
    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    String toChatUsername;
    private Bundle bundle;
    private String hundred_id="";
    private String userName="";
    private String finish="";


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_chat);
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        activityInstance = this;
        bundle = new Bundle();
        //get user id or group id
        toChatUsername = getIntent().getExtras().getString("userId");
           if (getIntent().getStringExtra("hundred_id")!=null){
               hundred_id = getIntent().getStringExtra("hundred_id");

           }
        if (getIntent().getStringExtra("userName")!=null){
            userName = getIntent().getStringExtra("userName");

        }
        if (getIntent().getStringExtra("finish")!=null){
            finish = getIntent().getStringExtra("finish");

        }
        //use EaseChatFratFragment
        chatFragment = new ChatFragment();
        bundle.putString("hundred_id", hundred_id);
        bundle.putString("userName", userName);
        bundle.putString("finish", finish);
        bundle.putString(EaseConstant.EXTRA_USER_ID, toChatUsername);
        bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE,getIntent().getIntExtra(EaseConstant.EXTRA_CHAT_TYPE,EaseConstant.CHATTYPE_SINGLE));
        //pass parameters to chat fragment
        chatFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();

        setResult(CHAT,null);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }


    public String getToChatUsername() {
        return toChatUsername;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

}
