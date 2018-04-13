package zhuozhuo.com.zhuo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.bean.CommentBean;
import zhuozhuo.com.zhuo.widget.CircleImageView;

/**
 * Created by Administrator on 2017/11/8.
 */

public class CommentListAdapter extends BaseAdapter {

    List<CommentBean.DataBean> data;
    Context context;
    LayoutInflater inflater;

    public CommentListAdapter(Context context, List<CommentBean.DataBean> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHold viewHold = null;
        if (view == null) {
            view = inflater.inflate(R.layout.comment_item_layout, viewGroup, false);
            viewHold = new ViewHold();
            viewHold.iv = (CircleImageView) view.findViewById(R.id.comment_iv);
            viewHold.tv1 = (TextView) view.findViewById(R.id.commnet_tv1);
            viewHold.tv2 = (TextView) view.findViewById(R.id.commnet_tv2);
            viewHold.tv3 = (TextView) view.findViewById(R.id.commnet_tv3);
            viewHold.tv4 = (TextView) view.findViewById(R.id.commnet_tv4);
            viewHold.tv5 = (TextView) view.findViewById(R.id.commnet_tv5);
            view.setTag(viewHold);
        } else {
            viewHold = (ViewHold) view.getTag();
        }
        Glide.with(context).load(data.get(i).getFrom_photo_link()).into(viewHold.iv);
        viewHold.tv1.setText(data.get(i).getFrom_nick_name());
        viewHold.tv2.setText(data.get(i).getComment());
        viewHold.tv3.setText(data.get(i).getCreated());
        if (data.get(i).getTo_nick_name()!=null){
            viewHold.tv4.setVisibility(View.VISIBLE);
            viewHold.tv5.setVisibility(View.VISIBLE);
            viewHold.tv4.setText(data.get(i).getFrom_nick_name());
            viewHold.tv5.setText("回复"+data.get(i).getTo_nick_name().toString()+":");
        }else {
            viewHold.tv4.setVisibility(View.GONE);
            viewHold.tv5.setVisibility(View.GONE);
        }

        return view;
    }


    class ViewHold {
        CircleImageView iv;
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;
    }
}
