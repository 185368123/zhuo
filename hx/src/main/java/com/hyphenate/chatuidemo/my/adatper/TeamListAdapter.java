package com.hyphenate.chatuidemo.my.adatper;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.my.bean.CupTeamBean;
import com.hyphenate.chatuidemo.my.presenter.InviteMemberPresenter;

import java.util.List;

import li.com.base.basesinglebean.SingleBeans;
import li.com.base.baseuntils.TimeUtils;


/**
 * Created by Administrator on 2017/10/30.
 */

public class TeamListAdapter extends BaseAdapter {
    private List<CupTeamBean> list;
    private LayoutInflater inflater;
    private Context context;
    private InviteMemberPresenter mPresenter;


    public TeamListAdapter(List<CupTeamBean> data, Context context, InviteMemberPresenter mPresenter) {
        this.context=context;
        this.list = data;
        inflater=LayoutInflater.from(context);
        this.mPresenter=mPresenter;
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
            view=inflater.inflate(R.layout.team_item_layout,viewGroup,false);
            viewHolder.tv1= (TextView) view.findViewById(R.id.tv_teamname);
            viewHolder.tv2= (TextView) view.findViewById(R.id.tv_creattime);
            viewHolder.bt_sq= (Button) view.findViewById(R.id.button_sq);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }

        viewHolder.tv1.setText("队伍名称："+list.get(i).getGroup_name());
        //viewHolder.tv2.setText("创建时间："+ TimeUtils.timedate(list.get(i).getCreate_time()));
        viewHolder.tv2.setText("队伍人数："+ list.get(i).getMember_ids());
        viewHolder.tv2.setTextColor(context.getResources().getColor(R.color.green));
        if (list.get(i).getMember_ids().equals(list.get(i).getMaxuser())){
            viewHolder.bt_sq.setVisibility(View.GONE);
        }else {
            if (SingleBeans.getInstance().isTeam()){
                viewHolder.bt_sq.setVisibility(View.GONE);
            }else {
                viewHolder.bt_sq.setVisibility(View.VISIBLE);
                viewHolder.bt_sq.setClickable(true);
                viewHolder.bt_sq.setText("申请加入");
                viewHolder.bt_sq.setBackground(ContextCompat.getDrawable(context,R.drawable.invite_button_white_shape));
            }
        }

        viewHolder.bt_sq.setTag(i);

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
        TextView tv1;
        TextView tv2;
        Button bt_sq;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
}
