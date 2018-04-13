package zhuozhuo.com.zhuo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.bean.UnReadBean;

/**
 * Created by Administrator on 2017/11/30.
 */

public class UnReadAdapter extends BaseAdapter {
    private List<UnReadBean.DataBean> dataBeen;
    private Context context;
    private LayoutInflater inflater;
    public UnReadAdapter(List<UnReadBean.DataBean> dataBeen, Context context){
        this.dataBeen=dataBeen;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return dataBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return dataBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHold viewHold;
        if (view==null){
            viewHold=new ViewHold();
            view=inflater.inflate(R.layout.unread_item_layout,viewGroup,false);
            viewHold.iv= (ImageView) view.findViewById(R.id.iv_unread);
            viewHold.tv1= (TextView) view.findViewById(R.id.tv_unread1);
            viewHold.tv2= (TextView) view.findViewById(R.id.tv_unread2);
            viewHold.tv3= (TextView) view.findViewById(R.id.tv_unread3);
            view.setTag(viewHold);
        }else {
            viewHold= (ViewHold) view.getTag();
        }
        Glide.with(context).load(dataBeen.get(i).getPhoto_link()).into(viewHold.iv);
        viewHold.tv1.setText(dataBeen.get(i).getShare_title());
        viewHold.tv2.setText(dataBeen.get(i).getLast_comment());
        viewHold.tv3.setText(dataBeen.get(i).getUnread_count()+"");
        return view;
    }
    class ViewHold{
        ImageView iv;
        TextView tv1;
        TextView tv2;
        TextView tv3;
    }
}
