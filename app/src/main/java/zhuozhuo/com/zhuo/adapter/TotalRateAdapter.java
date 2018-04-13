package zhuozhuo.com.zhuo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.bean.TotalRateBean;

/**
 * Created by Administrator on 2018/1/22.
 */

public class TotalRateAdapter extends BaseAdapter {
    List<TotalRateBean.DataBean> dataBeen;
    Context context;
    LayoutInflater inflater;
    public TotalRateAdapter(Context context, List<TotalRateBean.DataBean> dataBeen) {
        this.dataBeen=dataBeen;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return dataBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold=null;
        if (convertView==null){
            viewHold=new ViewHold();
            convertView=inflater.inflate(R.layout.rate_item_layout,parent,false);
            viewHold.tv1= (TextView) convertView.findViewById(R.id.rate_item_tv1);
            viewHold.tv2= (TextView) convertView.findViewById(R.id.rate_item_tv2);
            convertView.setTag(viewHold);
        }else {
            viewHold= (ViewHold) convertView.getTag();
        }
        viewHold.tv1.setText(dataBeen.get(position).getNick_name());
        viewHold.tv2.setText(dataBeen.get(position).getHundred_level().trim().toUpperCase()+"级-第"+dataBeen.get(position).getHundred_rate()+"名");
        return convertView;
    }
    class  ViewHold{
        TextView tv1;
        TextView tv2;
    }
}
