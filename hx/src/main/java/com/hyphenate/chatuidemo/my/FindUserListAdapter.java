package com.hyphenate.chatuidemo.my;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.chatuidemo.R;
import com.hyphenate.easeui.widget.EaseAlertDialog;

import java.util.List;

/**
 * Created by Administrator on 2017/11/4.
 */

public class FindUserListAdapter extends BaseAdapter implements View.OnClickListener {
    Context context;
    List<UserListBean>  data;
    private ProgressDialog progressDialog;
    String user_id;
    Activity activity;

    public FindUserListAdapter(Context context, List<UserListBean> data, Activity activity) {
        this.context = context;
        this.data = data;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHold viewHold=null;
        if (view==null){
            viewHold=new ViewHold();
            view= LayoutInflater.from(context).inflate(R.layout.add_list_layout,viewGroup,false);
            viewHold.button= (Button) view.findViewById(R.id.indicator);
            viewHold.imageView= (ImageView) view.findViewById(R.id.avatar);
            viewHold.textView= (TextView) view.findViewById(R.id.name);
            view.setTag(viewHold);
        }else {
            viewHold= (ViewHold) view.getTag();
        }
        user_id=data.get(i).getUser_id();
        viewHold.textView.setText(data.get(i).getNick_name());
        viewHold.button.setOnClickListener(this);
        Glide.with(context).load(data.get(i).getPhoto_link()).into(viewHold.imageView);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(EMClient.getInstance().getCurrentUser().equals(user_id)){
            new EaseAlertDialog(context, R.string.not_add_myself).show();
            return;
        }

        if(DemoHelper.getInstance().getContactList().containsKey(user_id)){
            //let the user know the contact already in your contact list
            if(EMClient.getInstance().contactManager().getBlackListUsernames().contains(user_id)){
                new EaseAlertDialog(context, R.string.user_already_in_contactlist).show();
                return;
            }
            new EaseAlertDialog(context, R.string.This_user_is_already_your_friend).show();
            return;
        }

        progressDialog = new ProgressDialog(context);
        String stri = context.getResources().getString(R.string.Is_sending_a_request);
        progressDialog.setMessage(stri);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        new Thread(new Runnable() {
            public void run() {
                try {
                    //demo use a hardcode reason here, you need let user to input if you like
                    String s = context.getResources().getString(R.string.Add_a_friend);
                    EMClient.getInstance().contactManager().addContact(user_id, s);
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s1 = context.getResources().getString(R.string.send_successful);
                            Toast.makeText(context.getApplicationContext(), s1, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (final Exception e) {
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s2 = context.getResources().getString(R.string.Request_add_buddy_failure);
                            Toast.makeText(context.getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }

    class ViewHold{
        ImageView imageView;
        TextView textView;
        Button button;
    }
}
