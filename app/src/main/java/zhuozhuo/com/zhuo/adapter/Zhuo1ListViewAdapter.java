package zhuozhuo.com.zhuo.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import li.com.base.basesinglebean.SingleChooseBean;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.view.MatchInterface;

/**
 * Created by Administrator on 2017/11/8.
 */

public class Zhuo1ListViewAdapter extends BaseAdapter implements View.OnClickListener {
    List<SingleChooseBean> data;
    Context context;
    LayoutInflater inflater;
    int[] picId;
MatchInterface matchInterface;
   String index;

    public Zhuo1ListViewAdapter(List<SingleChooseBean> data, Context context, int[] picId, MatchInterface matchInterface) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.picId = picId;
       this.matchInterface=matchInterface;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHold viewHold = null;
        if (view == null) {
            view = inflater.inflate(R.layout.zhuo1item_layout, viewGroup, false);
            viewHold = new ViewHold();
            viewHold.tv1 = (TextView) view.findViewById(R.id.zhuo1_tv1);
            viewHold.tv2 = (TextView) view.findViewById(R.id.zhuo1_tv2);
            viewHold.button = (Button) view.findViewById(R.id.zhuo1_button);
            view.setTag(viewHold);
        } else {
            viewHold = (ViewHold) view.getTag();
        }
        viewHold.tv1.setText(data.get(i).getChoice_name());
        viewHold.tv2.setText(data.get(i).getChoice_title());
        viewHold.button.setBackground(ContextCompat.getDrawable(context,picId[i%4]));
        viewHold.button.setOnClickListener(this);
        viewHold.button.setTag(data.get(i).getChoice_id());//选项ID跟名字拼接保存在Tag中，用逗号隔开
        return view;
    }

    @Override
    public void onClick(View view) {
        matchInterface.doClick((String) view.getTag());
    }

    protected class ViewHold {
        TextView tv1;
        TextView tv2;
        Button button;
    }
}
