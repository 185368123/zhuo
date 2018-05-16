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

import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */

public class CupItemFragment_1ListAdapter extends BaseAdapter {

    private Context context;
    private List<ConfrontationBean> data;
    private LayoutInflater inflater;

    public CupItemFragment_1ListAdapter(Context context, List<ConfrontationBean> data) {
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
        return 3;
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
              convertView=inflater.inflate(R.layout.cup_fragment_1_listitem_layout_1, parent,false);
              viewHodle.tv_date= (TextView) convertView.findViewById(R.id.tv_date_cupfragment1);
            }else if (data.get(position).getType().equals("3")){
                convertView=inflater.inflate(R.layout.cup_fragment_1_listitem_layout_3, parent,false);
                viewHodle.tv_groupName= (TextView) convertView.findViewById(R.id.tv_group_cupfragment2);
            }else {
                convertView=inflater.inflate(R.layout.cup_fragment_1_listitem_layout_2, parent,false);
                viewHodle.tv_time= (TextView) convertView.findViewById(R.id.tv_time_cupfragment1);
                viewHodle.tv_describe= (TextView) convertView.findViewById(R.id.tv_describe_cupfragment1);
                viewHodle.tv_team1Name= (TextView) convertView.findViewById(R.id.tv_team1Name_cupfragment1);
                viewHodle.tv_team2Name= (TextView) convertView.findViewById(R.id.tv_team2Name_cupfragment1);
                viewHodle.tv_team1Victory= (TextView) convertView.findViewById(R.id.tv_team1Victory_cupfragment1);
                viewHodle.tv_team2Victory= (TextView) convertView.findViewById(R.id.tv_team2Victory_cupfragment1);
                viewHodle.ll=(LinearLayout) convertView.findViewById(R.id.ll_fragment_1_item);
            }
            convertView.setTag(viewHodle);
        }else {
            viewHodle= (ViewHodle) convertView.getTag();
        }
        if (data.get(position).getType().equals("1")){
            viewHodle.tv_date.setText(data.get(position).getDate());
        }else if (data.get(position).getType().equals("3")){
            viewHodle.tv_groupName.setText(data.get(position).getDate());
        }else {
            if (data.get(position).isMyTeam()){
                viewHodle.ll.setBackgroundColor(ContextCompat.getColor(context,R.color.main_color));
                viewHodle.tv_time.setTextColor(Color.WHITE);
                viewHodle.tv_describe.setTextColor(Color.WHITE);
                viewHodle.tv_team1Name.setTextColor(Color.WHITE);
                viewHodle.tv_team2Name.setTextColor(Color.WHITE);
                viewHodle.tv_team1Victory.setTextColor(Color.WHITE);
                viewHodle.tv_team2Victory.setTextColor(Color.WHITE);
            }else {
                viewHodle.ll.setBackgroundColor(Color.WHITE);
                viewHodle.tv_time.setTextColor(Color.BLACK);
                viewHodle.tv_describe.setTextColor(Color.BLACK);
                viewHodle.tv_team1Name.setTextColor(Color.BLACK);
                viewHodle.tv_team2Name.setTextColor(Color.BLACK);
                viewHodle.tv_team1Victory.setTextColor(Color.BLACK);
                viewHodle.tv_team2Victory.setTextColor(Color.BLACK);
            }
            viewHodle.tv_time.setText(data.get(position).getTime());
            viewHodle.tv_describe.setText(data.get(position).getDescribe());
            viewHodle.tv_team1Name.setText(data.get(position).getTeam1Name());
            viewHodle.tv_team2Name.setText(data.get(position).getTeam2Name());
            viewHodle.tv_team1Victory.setText(data.get(position).getTeam1Victory());
            viewHodle.tv_team2Victory.setText(data.get(position).getTeam2Victory());
        }
        return convertView;
    }

    public class ViewHodle{
        TextView tv_date;
        TextView tv_time;
        TextView tv_describe;
        TextView tv_team1Name;
        TextView tv_team2Name;
        TextView tv_team1Victory ;
        TextView tv_team2Victory ;
        TextView tv_groupName;
        LinearLayout ll;
    }
}
