package zhuozhuo.com.zhuo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import zhuozhuo.com.zhuo.R;

/**
 * Created by li on 2017/2/23.
 */

public class LocationRecyclerViewAdapter extends RecyclerView.Adapter<LocationRecyclerViewAdapter.MyViewHoder> {


    private List<Integer> photo;
    private List<String> name;
    private List<Boolean> flags;
    private final LayoutInflater inflater;
    public interface OnItemOnClickListener{
        void onItemOnClick(View view,int pos);
    }
    private OnItemOnClickListener mOnItemOnClickListener;
    public LocationRecyclerViewAdapter(Context context,List<Integer> photo,List<String> name,List<Boolean> flags,OnItemOnClickListener mOnItemOnClickListener) {
       this.photo=photo;
        this.name=name;
        inflater = LayoutInflater.from(context);
        this.mOnItemOnClickListener=mOnItemOnClickListener;
        this.flags=flags;
    }

    @Override
    public MyViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHoder viewHoder=new MyViewHoder(inflater.inflate(R.layout.location_recycle_item_layout,null));
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(final MyViewHoder holder, final int position) {
        holder.setI(R.id.recycle_iv,photo.get(position));
        holder.setT(R.id.recycle_tv,name.get(position));
        if (flags.get(position)){
            holder.setTC(R.id.recycle_tv);
        }else {
            holder.setTC(R.id.recycle_tv,"");
        }

        if (mOnItemOnClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.setTC(R.id.recycle_tv);
                    mOnItemOnClickListener.onItemOnClick(holder.itemView,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        View view;
        public MyViewHoder(View itemView) {
            super(itemView);
            view=itemView;
        }
        public void setI(int id,int drawble){
            ImageView iv= (ImageView) view.findViewById(id);
            Glide.with(view.getContext()).load(drawble).into(iv);
        }
        public void setT(int id,String url){
            TextView tv= (TextView) view.findViewById(id);
            tv.setText(url);
        }
        public void setTC(int id){
            TextView tv= (TextView) view.findViewById(id);
            tv.setTextColor(Color.parseColor("#ffffff"));
            tv.setBackgroundColor(Color.parseColor("#f76243"));
        }
        public void setTC(int id,String s){
            TextView tv= (TextView) view.findViewById(id);
            tv.setTextColor(Color.parseColor("#000000"));
            tv.setBackgroundColor(Color.parseColor("#ffffff"));
        }

    }
}
