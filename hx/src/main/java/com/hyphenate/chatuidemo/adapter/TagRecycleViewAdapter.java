package com.hyphenate.chatuidemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.my.Untils.CountDownUtil;
import com.hyphenate.chatuidemo.my.bean.TagBean;

import java.util.ArrayList;
import java.util.List;

import li.com.base.baseuntils.LogUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Created by Administrator on 2018/5/11.
 */

public class TagRecycleViewAdapter extends RecyclerView.Adapter<TagRecycleViewAdapter.TagViewHolder> {

    private List<TagBean.DataBean> dataBeans;
    private Context mContext;
    private List<TextView>  list_tv=new ArrayList<>();


    public TagRecycleViewAdapter(List<TagBean.DataBean> dataBeans, Context mContext) {
        this.dataBeans = dataBeans;
        this.mContext = mContext;
    }

    @Override
    public TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tag_rv_item_layout, parent, false);
        TagViewHolder viewHolder = new TagViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TagViewHolder holder, final int position) {
        holder.tv.setTag(position);
        holder.tv.setText((position+1) + "");
        list_tv.add(holder.tv);
    }


    public void openTextView(int position){
        LogUtils.logd("position"+position+"----"+dataBeans.get(position).getLabel_name());
        if (position<list_tv.size()){
            CountDownUtil countDownUtil= new CountDownUtil(list_tv.get(position),2*1000,dataBeans.get(position).getLabel_name(),(position+1)+"");
            countDownUtil.setSetClick(true);
            countDownUtil.start();
        }
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    public static class TagViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        FrameLayout fl;

        public TagViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_tag_item);
            fl = (FrameLayout) itemView.findViewById(R.id.fl_tag);
        }
    }
}
