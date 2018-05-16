package com.hyphenate.chatuidemo.my.adatper;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.my.bean.ConfrontationBean;
import com.hyphenate.chatuidemo.my.bean.TeamScoreBean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */

public class CupItemFragment_2ListAdapter extends BaseAdapter {

    private Context context;
    private List<TeamScoreBean> data;
    private LayoutInflater inflater;

    public CupItemFragment_2ListAdapter(Context context, List<TeamScoreBean> data) {
        this.context=context;
        this.data=data;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(data.get(position).getType())-1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodle viewHodle=null;
        if (convertView==null){
            viewHodle=new ViewHodle();
            if (data.get(position).getType().equals("1")){
              convertView=inflater.inflate(R.layout.cup_fragment_2_listitem_layout_1, parent,false);
              viewHodle.tv_typeName= (TextView) convertView.findViewById(R.id.tv_race_type_cupfragment2);
            }else if (data.get(position).getType().equals("2")){
                convertView=inflater.inflate(R.layout.cup_fragment_2_listitem_layout_2, parent,false);
                viewHodle.tv_groupName= (TextView) convertView.findViewById(R.id.tv_group_cupfragment2);
            }else if (data.get(position).getType().equals("3")){
                convertView=inflater.inflate(R.layout.cup_fragment_2_listitem_layout_3, parent,false);
                viewHodle.ll=(LinearLayout) convertView.findViewById(R.id.ll_fragment_2_item);
                viewHodle.tv_teamName= (TextView) convertView.findViewById(R.id.tv_group_cupfragment2);
                viewHodle.tv_teamVictory= (TextView) convertView.findViewById(R.id.tv_sheng_cupfragment2);
                viewHodle.tv_teamFiled= (TextView) convertView.findViewById(R.id.tv_fu_cupfragment2);
                viewHodle.tv_teamScore= (TextView) convertView.findViewById(R.id.tv_jf_cupfragment2);
            }else if (data.get(position).getType().equals("4")){
                convertView=inflater.inflate(R.layout.cup_fragment_2_listitem_layout_4, parent,false);
            }
            convertView.setTag(viewHodle);
        }else {
            viewHodle= (ViewHodle) convertView.getTag();
        }

        if (data.get(position).getType().equals("1")){
            viewHodle.tv_typeName.setText(data.get(position).getTypeName());
        }else if (data.get(position).getType().equals("2")){
            viewHodle.tv_groupName.setText(data.get(position).getGroupName());
        }else if (data.get(position).getType().equals("3")){
            if (data.get(position).isMyTeam()){
                viewHodle.ll.setBackgroundColor(ContextCompat.getColor(context,R.color.main_color));
                viewHodle.tv_teamName.setTextColor(Color.WHITE);
                viewHodle.tv_teamVictory.setTextColor(Color.WHITE);
                viewHodle.tv_teamFiled.setTextColor(Color.WHITE);
                viewHodle.tv_teamScore.setTextColor(Color.WHITE);
            }else {
                viewHodle.ll.setBackgroundColor(Color.WHITE);
                viewHodle.tv_teamName.setTextColor(Color.BLACK);
                viewHodle.tv_teamVictory.setTextColor(Color.BLACK);
                viewHodle.tv_teamFiled.setTextColor(Color.BLACK);
                viewHodle.tv_teamScore.setTextColor(Color.BLACK);
            }
            viewHodle.tv_teamName.setText(data.get(position).getTeamName());
            viewHodle.tv_teamVictory.setText(data.get(position).getTeamVictory());
            viewHodle.tv_teamFiled.setText(data.get(position).getTeamFiled());
            viewHodle.tv_teamScore.setText(data.get(position).getTeamScore());
        }else {

        }
        return convertView;
    }

    public class ViewHodle{
        TextView tv_typeName;
        TextView tv_groupName;
        TextView tv_teamName;
        TextView tv_teamVictory;
        TextView tv_teamFiled;
        TextView tv_teamScore;
        LinearLayout ll;
    }
}
