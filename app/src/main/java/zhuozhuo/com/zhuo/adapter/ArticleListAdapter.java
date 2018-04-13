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
import com.hyphenate.chatuidemo.my.bean.AllArticleBean;

/**
 * Created by Administrator on 2017/11/9.
 */

public class ArticleListAdapter extends BaseAdapter {
    List<AllArticleBean> data;
    Context context;
    LayoutInflater inflater;

    public ArticleListAdapter(List<AllArticleBean> data, Context context) {
        this.data = data;
        this.context = context;
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
            view = inflater.inflate(R.layout.article_item_layout, viewGroup, false);
            viewHold = new ViewHold();
            viewHold.article_iv = (ImageView) view.findViewById(R.id.article_iv);
            viewHold.article_tv = (TextView) view.findViewById(R.id.article_tv);
            view.setTag(viewHold);
        } else {
            viewHold = (ViewHold) view.getTag();
        }
        Glide.with(context).load(data.get(i).getPhoto_link()).into(viewHold.article_iv);
        viewHold.article_tv.setText(data.get(i).getShare_title());
        return view;
    }

    class ViewHold {
        ImageView article_iv;
        TextView article_tv;
    }
}
