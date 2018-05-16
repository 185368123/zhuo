package com.hyphenate.easeui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.HundredCupBean;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.provider.UserInfoProvider;

import java.util.ArrayList;
import java.util.List;

import li.com.base.baseuntils.LogUtils;
import li.com.base.baseuntils.ToastUitl;

/**
 * Created by Administrator on 2018/4/20.
 */

public class TeamRecycleView extends RecyclerView {

    private StaggeredGridLayoutManager glm;
    private MyAdapter adapter;
    private List<HundredCupBean.DataBean> list=new ArrayList<>();

    public TeamRecycleView(Context context) {
       this(context,null);
    }

    public TeamRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TeamRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //创建布局管理器
        glm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        adapter=new MyAdapter(context,list);
        this.setLayoutManager(glm);
        this.setAdapter(adapter);
    }

    public  void setOnItemClickListener_(MyAdapter.OnItemClick clickListener){
        adapter.setItemClickListener(clickListener);
    }

    public void upadteList(List<HundredCupBean.DataBean> list_){
        adapter.update(list_);
    }



    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.TeamViewHolder> implements OnClickListener, OnLongClickListener {
        private Context mContext;
        private List<HundredCupBean.DataBean> data;
        private OnItemClick onItemClick;

        public interface OnItemClick{

            void goTeamChat(HundredCupBean.DataBean dataBean);

            void  inviteMember();

            void  onLongClick(String type,int index);

        };
        public void update(List<HundredCupBean.DataBean> data) {
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
        }
        public  void setItemClickListener(OnItemClick clickListener){
            this.onItemClick=clickListener;
        }
        public MyAdapter(Context context, List<HundredCupBean.DataBean> data) {
            mContext = context;
            this.data = data;
        }


        @Override
        public TeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_item_layout, parent, false);
            TeamViewHolder viewHolder = new TeamViewHolder(view);
            return viewHolder;

        }

        @Override
        public void onBindViewHolder(TeamViewHolder holder, int position) {
            if (position<data.size()){
                holder.tv1.setVisibility(VISIBLE);
                holder.tv2.setVisibility(GONE);
                holder.tv1.setText(data.get(position).getNick_name());
                Glide.with(mContext).load(data.get(position).getPhoto_link()).into(holder.iv);

            }else {
                 holder.tv1.setVisibility(GONE);
                 holder.tv2.setVisibility(GONE);
                 Glide.with(mContext).load(R.drawable.invite_member).into(holder.iv);
            }
            holder.fl.setTag(position);
            holder.fl.setOnClickListener(this);
            holder.fl.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClick!=null){
                if ((int)v.getTag()<data.size()){
                    onItemClick.goTeamChat(data.get((Integer) v.getTag()));
                }else {
                    onItemClick.inviteMember();
                }
            }

        }
        @Override
        public boolean onLongClick(View v) {
            if ((int)v.getTag()<data.size()){
                if (data.get(0).getUser_id().equals(UserInfoProvider.getUserID())) {
                    if ((int)v.getTag()==0){
                        //ToastUitl.showLong("我是队长的点击我的长按事件");
                        onItemClick.onLongClick("1",(int)v.getTag());
                    }else {
                       // ToastUitl.showLong("我是队长的点击其他人的长按事件");
                        onItemClick.onLongClick("3",(int)v.getTag());
                    }
                }else {
                    if (data.get((int)v.getTag()).getUser_id().equals(UserInfoProvider.getUserID())){
                        //ToastUitl.showLong("我是队员的点击我的长按事件");
                        onItemClick.onLongClick("2",(int)v.getTag());
                    }else {
                        //ToastUitl.showLong("我是队员的点击其他人的长按事件");
                        //onItemClick.onLongClick("4",(int)v.getTag());
                    }
                }
            }
            return false;
        }
        @Override
        public int getItemCount() {
            return 5;
        }

        public static class TeamViewHolder extends RecyclerView.ViewHolder {

            private ImageView iv;
            private TextView tv1;
            private  TextView tv2;
            private FrameLayout fl;

            public TeamViewHolder(View itemView) {
                super(itemView);
                iv = itemView.findViewById(R.id.iv_rv_item);
                tv1 = itemView.findViewById(R.id.tv_name_r);
                tv2= itemView.findViewById(R.id.tv_state_r);
                fl=itemView.findViewById(R.id.fl_rv_item);
            }
        }
    }
}
