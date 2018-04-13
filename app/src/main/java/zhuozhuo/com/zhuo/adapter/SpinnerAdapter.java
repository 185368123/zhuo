package zhuozhuo.com.zhuo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zhuozhuo.com.zhuo.R;


/**
 * Created by Administrator on 2017/11/28.
 */

public class SpinnerAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<String> list=new ArrayList<>();
    public SpinnerAdapter(Context context,List<String>  list){
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.list=list;
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
        view=inflater.inflate(R.layout.spinner_layout,viewGroup,false);
        TextView textView= (TextView) view.findViewById(R.id.tv_spinner);
        textView.setText(list.get(i));
        return view;
    }

}
