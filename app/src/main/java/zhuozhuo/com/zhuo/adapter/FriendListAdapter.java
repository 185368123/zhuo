package zhuozhuo.com.zhuo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import zhuozhuo.com.zhuo.R;

/**
 * Created by Administrator on 2017/10/16.
 */

public class FriendListAdapter extends BaseAdapter {


    private final LayoutInflater inflater;

    public FriendListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.friendlist_iter_layout,viewGroup,false);
        ImageView imageView= (ImageView) view.findViewById(R.id.friend_image);
        TextView textView= (TextView) view.findViewById(R.id.friend_text);
        switch (i) {
            case 0:
                imageView.setImageResource(R.drawable.message);
                textView.setText("申请与通知");
                break;
            case 1:
                imageView.setImageResource(R.drawable.addfriend);
                textView.setText("添加好友");
                break;
        }
        return view;
    }
}
