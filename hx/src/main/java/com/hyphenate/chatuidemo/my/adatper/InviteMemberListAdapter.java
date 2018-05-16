package com.hyphenate.chatuidemo.my.adatper;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.my.bean.CupMemberBean;
import com.hyphenate.chatuidemo.my.presenter.InviteMemberPresenter;

import java.util.List;

import li.com.base.basesinglebean.SingleBeans;


/**
 * Created by Administrator on 2017/10/30.
 */

public class InviteMemberListAdapter extends BaseAdapter {
    private List<CupMemberBean> list;
    private LayoutInflater inflater;
    private Context context;
    private InviteMemberPresenter mPresenter;
    private String line_id;

    public InviteMemberListAdapter(List<CupMemberBean> list, Context context, InviteMemberPresenter mPresenter,String line_id) {
        this.context=context;
        this.list = list;
        inflater=LayoutInflater.from(context);
        this.mPresenter=mPresenter;
        this.line_id=line_id;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (view == null) {
          viewHolder=new ViewHolder();
            view=inflater.inflate(R.layout.invite_member_item_layout,viewGroup,false);
            viewHolder.iv= (ImageView) view.findViewById(R.id.iv_userpic);
            viewHolder.tv1= (TextView) view.findViewById(R.id.tv_username);
            viewHolder.tv2= (TextView) view.findViewById(R.id.tv_normol);
            viewHolder.button= (Button) view.findViewById(R.id.button_yq);
            viewHolder.bt_sq= (Button) view.findViewById(R.id.button_sq);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        Glide.with(context).load(list.get(i).getPhoto_link()).into(viewHolder.iv);
        viewHolder.tv1.setText(list.get(i).getNick_name());

        if (list.get(i).getGroup_name()==null||list.get(i).getGroup_name().equals("")){
            viewHolder.bt_sq.setVisibility(View.GONE);
            viewHolder.tv2.setText("所属队伍：无");
            if (SingleBeans.getInstance().isTeam()){
                viewHolder.button.setVisibility(View.VISIBLE);
                viewHolder.bt_sq.setVisibility(View.GONE);
            }else {
                viewHolder.button.setVisibility(View.GONE);
                viewHolder.bt_sq.setVisibility(View.GONE);
            }
        }else {
            if (SingleBeans.getInstance().isTeam()){
                viewHolder.button.setVisibility(View.GONE);
                viewHolder.bt_sq.setVisibility(View.GONE);
            }else {
                viewHolder.bt_sq.setVisibility(View.VISIBLE);
                viewHolder.bt_sq.setClickable(true);
                viewHolder.bt_sq.setText("申请加入");
                viewHolder.bt_sq.setBackground(ContextCompat.getDrawable(context,R.drawable.invite_button_white_shape));
            }
            viewHolder.tv2.setText("所属队伍："+list.get(i).getGroup_name());
        }
        viewHolder.button.setClickable(true);
        viewHolder.button.setText("邀请入队");
        viewHolder.button.setBackground(ContextCompat.getDrawable(context,R.drawable.invite_button_white_shape));


        viewHolder.button.setTag(i);
        viewHolder.bt_sq.setTag(i);
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int j= (int) v.getTag();
                mPresenter.inviteMember(line_id,list.get(j).getUser_id());
                Button button= (Button) v;
                button.setText("已邀请");
                button.setBackground(ContextCompat.getDrawable(context,R.drawable.invite_button_gray_shape));
                button.setClickable(false);
            }
        });
        viewHolder.bt_sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i= (int) v.getTag();
                mPresenter.applyTeam(list.get(i).getLine_id());
                Button button= (Button) v;
                button.setText("已申请");
                button.setBackground(ContextCompat.getDrawable(context,R.drawable.invite_button_gray_shape));
                button.setClickable(false);
            }
        });
        return view;
    }

    public class ViewHolder {
        ImageView iv;
        TextView tv1;
        TextView tv2;
        Button button;
        Button bt_sq;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
}
