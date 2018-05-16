package com.hyphenate.chatuidemo.my.adatper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.TeamUnreadBean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */

public class UnreadListAdapter extends BaseAdapter {
    protected onButtonClick click;
    List<TeamUnreadBean.DataBean> unreadBeans;
    Context context;

    public void setOnButtonClick(onButtonClick click){
        this.click=click;
    }

    public interface onButtonClick{
        public void receiveInvite(String line_id,String group_id ,String nick_user_id);

        public void refuseInvite(String line_id,String group_id ,String nick_user_id);

        public void receiveApply(String line_id,String you_user_id);

        public void refuseApply(String line_id,String you_user_id);
    }


    public UnreadListAdapter(Context context,List<TeamUnreadBean.DataBean> unreadBeans) {
        this.context=context;
        this.unreadBeans=unreadBeans;
    }

    @Override
    public int getCount() {
        return unreadBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return unreadBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String line_id=unreadBeans.get(position).getLine_id();
        final String group_id=unreadBeans.get(position).getGroup_id();
        final String nick_user_id=unreadBeans.get(position).getNick_user_id();
        convertView= LayoutInflater.from(context).inflate(com.hyphenate.easeui.R.layout.team_unread_item_layout, parent, false);
        ImageView iv_avatar=(ImageView)convertView.findViewById(com.hyphenate.easeui.R.id.iv_avatar);
        TextView tv_name=(TextView)convertView.findViewById(com.hyphenate.easeui.R.id.unread_name);
        TextView tv_reason=(TextView)convertView.findViewById(com.hyphenate.easeui.R.id.tv_reason);
        Button bt_agree=(Button)convertView.findViewById(com.hyphenate.easeui.R.id.bt_agree);
        Button  bt_refuse=(Button)convertView.findViewById(com.hyphenate.easeui.R.id.bt_refuse);
        Glide.with(context).load(unreadBeans.get(position).getPhoto_link()).into(iv_avatar);
        tv_name.setText(nick_user_id);
        bt_agree.setTag(position);
        bt_refuse.setTag(position);
        if (unreadBeans.get(position).getType().equals("1")){
            tv_reason.setText(nick_user_id+"邀请你加入他（她）的战队");
            bt_agree.setText("接受");
            bt_agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i= (int) v.getTag();
                    if (click!=null){
                        click.receiveInvite(line_id,group_id,nick_user_id);
                    }
                }
            });
            bt_refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (click!=null){
                        click.refuseInvite(line_id,group_id,nick_user_id);
                    }
                }
            });
        }else if (unreadBeans.get(position).getType().equals("2")){
            tv_reason.setText(nick_user_id+"申请加入你的战队");
            bt_agree.setText("同意");
            bt_agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i= (int) v.getTag();
                    if (click!=null){
                        click.receiveApply(line_id,nick_user_id);
                    }
                }
            });
            bt_refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click.refuseApply(line_id,nick_user_id);
                }
            });
        }
        return convertView;
    }
}
