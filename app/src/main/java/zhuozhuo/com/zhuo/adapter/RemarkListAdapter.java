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
import zhuozhuo.com.zhuo.bean.RemarkBean;

/**
 * Created by Administrator on 2017/11/9.
 */

public class RemarkListAdapter extends BaseAdapter {
    List<RemarkBean.DataBean>  list;
    LayoutInflater inflater;
    Context context;
    public RemarkListAdapter(List<RemarkBean.DataBean>  list, Context context) {
        this.list=list;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHold viewHold=null;
        if (view==null){
            viewHold=new ViewHold();
            view=inflater.inflate(R.layout.remark_item_layout,viewGroup,false);
            viewHold.iv= (ImageView) view.findViewById(R.id.remark_iv);
            viewHold.tv1= (TextView) view.findViewById(R.id.remark_tv1);
            viewHold.tv2= (TextView) view.findViewById(R.id.remark_tv2);
            viewHold.tv3= (TextView) view.findViewById(R.id.remark_tv3);
            viewHold.tv4= (TextView) view.findViewById(R.id.remark_tv4);
            viewHold.tv5= (TextView) view.findViewById(R.id.remark_tv5);
            view.setTag(viewHold);
        }else {
            viewHold= (ViewHold) view.getTag();
        }
        String[] strings=list.get(i).getCreated().split(" ");
        Glide.with(context).load(list.get(i).getPhoto_link()).into(viewHold.iv);

        viewHold.tv1.setText(list.get(i).getNick_name());
        viewHold.tv2.setText(strings[0]);
        viewHold.tv5.setText(strings[1]);
        viewHold.tv3.setText("打分："+list.get(i).getScore());
        switch (list.get(i).getCard()){
            case "0":
                viewHold.tv4.setText("好人卡");
                break;
            case "1":
                viewHold.tv4.setText("闺蜜卡");
                break;
            case "2":
                viewHold.tv4.setText("普通卡");
                break;
            case "3":
                viewHold.tv4.setText("好友卡");
                break;
            case "4":
                viewHold.tv4.setText("贱人卡");
                break;
            case "5":
                viewHold.tv4.setText("恋爱卡");
                break;
        }
        return view;
    }

    protected class ViewHold{
        ImageView iv;
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;
    }
}
