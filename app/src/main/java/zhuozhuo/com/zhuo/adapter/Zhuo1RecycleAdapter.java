package zhuozhuo.com.zhuo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.hyphenate.chatuidemo.UserMsgDBHelp;
import com.hyphenate.chatuidemo.my.bean.UserDB;
import com.hyphenate.easeui.events.RxBusConstants;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import li.com.base.baserx.RxManager;
import li.com.base.basesinglebean.MatchPersonBean;
import zhuozhuo.com.zhuo.R;


/**
 * Created by Administrator on 2018/3/28.
 */

public class Zhuo1RecycleAdapter extends RecyclerView.Adapter<Zhuo1RecycleAdapter.MyViewHolder> {

    private Context mContext;
    private List<MatchPersonBean> data;
    private OnItemClick onItemClick;

    public interface OnItemClick{
        void onItemOnClick(int pos);
    };

    public Zhuo1RecycleAdapter(Context context, List<MatchPersonBean> data,OnItemClick onItemClick) {
        mContext = context;
        this.data = data;
        this.onItemClick=onItemClick;
    }

    public void update(List<MatchPersonBean> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_item_layout, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        setUserImage(position,holder.iv);
        holder.tv1.setText(data.get(position).getNick_name());
        if (data.get(position).getFinish().equals("1")){
            holder.tv2.setVisibility(View.VISIBLE);
            holder.tv2.setText("已完成");
        }else if (data.get(position).getIs_status().equals("1")||data.get(position).getStatus().equals("1")){
            holder.tv2.setVisibility(View.VISIBLE);
            holder.tv2.setText("评价");
        }else {
            holder.tv2.setVisibility(View.GONE);
        }

        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemOnClick(position);
            }
        });

    }

    private void setUserImage(final int position, final ImageView iv) {
        UserDB userDB = UserMsgDBHelp.getUserMsgDBHelp().searchByUserId(data.get(position).getR_user_id());
        if (userDB != null) {
            String imageUrl = userDB.getPhoto_link();
            Glide.with(mContext).load(imageUrl).into(iv);
        } else {
            Glide.with(mContext).load(R.drawable.ease_default_avatar).into(iv);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                   new RxManager().post(RxBusConstants.UPDATE_ALL_MATCH,"");
                }
            },5000);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private  TextView tv1;
        private  TextView tv2;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_rv_item);
            tv1 = itemView.findViewById(R.id.tv_name_r);
            tv2= itemView.findViewById(R.id.tv_state_r);
        }
    }
}
