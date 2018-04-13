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
import zhuozhuo.com.zhuo.bean.StoryVideoBean;

/**
 * Created by Administrator on 2017/11/8.
 */

public class StoryListAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<StoryVideoBean.DataBean>  data;
    Context context;
    int index=0;

    public StoryListAdapter (List<StoryVideoBean.DataBean>  data, Context context){
        this.data=data;
        inflater=LayoutInflater.from(context);
        this.context=context;
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
        ViewHold viewHold=null;
        if (view==null){
            viewHold=new ViewHold();
            view=inflater.inflate(R.layout.story_item_layout,viewGroup,false);
            viewHold.story_iv= (ImageView) view.findViewById(R.id.story_iv);
            viewHold.Story_tv= (TextView) view.findViewById(R.id.story_tv);
            view.setTag(viewHold);
        }else {
            viewHold= (ViewHold) view.getTag();
        }
        Glide.with(context).load(data.get(i).getVideo_thumb_link()).into(viewHold.story_iv);
        viewHold.Story_tv.setText(data.get(i).getShare_title());
        return view;
    }



    protected class ViewHold{
        ImageView story_iv;
        TextView Story_tv;
    }

}
