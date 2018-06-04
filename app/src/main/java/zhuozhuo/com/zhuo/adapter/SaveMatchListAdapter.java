package zhuozhuo.com.zhuo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chatuidemo.my.bean.SaveMatchBean;

import java.util.List;

import li.com.base.basesinglebean.SingleBeans;
import zhuozhuo.com.zhuo.R;

/**
 * Created by Administrator on 2018/5/30.
 */

public class SaveMatchListAdapter extends BaseAdapter {
    List<SaveMatchBean> data;
    Context context;
    BTOnClickLiseten onClickLiseten;
    private final List<String> friends;

    public interface BTOnClickLiseten{

       public void onClick(String id);

        public void onLongClick(String id);
    }
    public SaveMatchListAdapter(Context context, List<SaveMatchBean> data,BTOnClickLiseten onClickLiseten) {
        this.data=data;
        this.context=context;
        this.onClickLiseten=onClickLiseten;
        friends = SingleBeans.getInstance().getFriends();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHold viewHold=null;
        //if (view == null){
            viewHold=new ViewHold();
            view= LayoutInflater.from(context).inflate(R.layout.save_match_item_layout,parent,false);
            viewHold.bt_friend=view.findViewById(R.id.bt_save_friend);
            viewHold.iv_img=view.findViewById(R.id.iv_save_img);
            viewHold.tv_com=view.findViewById(R.id.tv_save_com);
            viewHold.tv_name=view.findViewById(R.id.tv_save_name);
            viewHold.tv_flag=view.findViewById(R.id.tv_save_flag);
            viewHold.ll=view.findViewById(R.id.ll_save_item);
            view.setTag(viewHold);
    /*    }else {
            viewHold= (ViewHold) view.getTag();
        }*/

        SaveMatchBean matchBean=data.get(position);
        Glide.with(context).load(matchBean.getPhoto_link()).into(viewHold.iv_img);
        viewHold.tv_name.setText(matchBean.getNick_name());
        viewHold.tv_com.setText(matchBean.getContent());
        viewHold.bt_friend.setTag(matchBean.getYou_user_id());
        viewHold.ll.setTag(matchBean.getYou_user_id());
        if (matchBean.getStatus().equals("1")){
            viewHold.tv_flag.setVisibility(View.VISIBLE);
        }else
            viewHold.tv_flag.setVisibility(View.GONE);


        for (int i = 0; i < friends.size(); i++) {
            if (data.get(position).getYou_user_id().equals(friends.get(i))){
                viewHold.bt_friend.setVisibility(View.GONE);
                break;
            }
        }
        viewHold.bt_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickLiseten!=null){
                    onClickLiseten.onClick((String) v.getTag());
                }
            }
        });

        viewHold.ll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onClickLiseten!=null){
                    onClickLiseten.onLongClick((String) v.getTag());
                }
                return false;
            }
        });
        return view;
    }

    class ViewHold{
        LinearLayout ll;
        TextView tv_name;
        TextView tv_com;
        ImageView iv_img;
        Button bt_friend;
        TextView tv_flag;
    }

}
