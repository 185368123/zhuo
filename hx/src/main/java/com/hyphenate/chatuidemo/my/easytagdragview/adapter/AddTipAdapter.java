package com.hyphenate.chatuidemo.my.easytagdragview.adapter;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.my.easytagdragview.bean.SimpleTitleTip;
import com.hyphenate.chatuidemo.my.easytagdragview.bean.Tip;

import java.util.List;

/**
 * Created by Administrator on 2016/5/27 0027.
 */
public class AddTipAdapter extends BaseAdapter {

    private List<Tip> tips;

    public AddTipAdapter() {
    }

    @Override
    public int getCount() {
        if(tips ==null){
            return 0;
        }
        return tips.size();
    }

    @Override
    public Object getItem(int position) {
        return tips.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.view_add_item,null);
        ((TextView)view.findViewById(R.id.tagview_title)).setText((((SimpleTitleTip)(tips.get(position))).getTip()));
        if((((SimpleTitleTip)(tips.get(position))).isClickable())){
            view.findViewById(R.id.tagview_background).setBackground(ContextCompat.getDrawable(parent.getContext(), R.drawable.tag_add_bg));
        }else {
            view.findViewById(R.id.tagview_background).setBackground(ContextCompat.getDrawable(parent.getContext(), R.drawable.tag_add_bg_));
        }
        return view;
    }

    public List<Tip> getData() {
        return tips;
    }

    public void setData(List<Tip> iDragEntities) {
        this.tips = iDragEntities;
    }
    public void refreshData(){
        notifyDataSetChanged();
    }
}
