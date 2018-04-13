package zhuozhuo.com.zhuo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;

import li.com.base.basesinglebean.SingleBeans;
import zhuozhuo.com.zhuo.R;

import com.hyphenate.chatuidemo.my.EmptyView;
import com.hyphenate.chatuidemo.my.bean.UserOnlineBean;
import zhuozhuo.com.zhuo.presenter.InvitePresenter;
import zhuozhuo.com.zhuo.presentermodel.SetCaptainPresentModel;
import zhuozhuo.com.zhuo.widget.CircleImageView;

/**
 * Created by Administrator on 2017/10/30.
 */

public class InviteListAdapter extends BaseAdapter {
    private List<UserOnlineBean> list;
    private LayoutInflater inflater;
    private Context context;
    private InvitePresenter mPresenter;

    public InviteListAdapter(List<UserOnlineBean> list, Context context, InvitePresenter mPresenter) {
        this.context=context;
        this.list = list;
        inflater=LayoutInflater.from(context);
        this.mPresenter=mPresenter;
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
        ViewHolder viewHolder=null;
        if (view == null) {
          viewHolder=new ViewHolder();
            view=inflater.inflate(R.layout.invite_item_layout,viewGroup,false);
            viewHolder.iv= (CircleImageView) view.findViewById(R.id.iv_userpic);
            viewHolder.tv1= (TextView) view.findViewById(R.id.tv_username);
            viewHolder.tv2= (TextView) view.findViewById(R.id.tv_exp);
            viewHolder.tv3= (TextView) view.findViewById(R.id.tv_normol);
            viewHolder.button= (Button) view.findViewById(R.id.button_yq);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        Glide.with(context).load(list.get(i).getPhoto_link()).into(viewHolder.iv);
        viewHolder.tv1.setText(list.get(i).getNick_name());
        viewHolder.tv2.setText(list.get(i).getExp());
        if (list.get(i).getOnline_status()==1){
            if (list.get(i).getGroup_match_status().equals("0")){
                viewHolder.tv3.setText("在线");
                viewHolder.tv3.setTextColor(Color.GREEN);
                viewHolder.button.setVisibility(View.VISIBLE);
                viewHolder.button.setClickable(true);
                viewHolder.button.setText("邀请");
                viewHolder.button.setBackground(ContextCompat.getDrawable(context,R.drawable.invite_button_white_shape));
                viewHolder.button.setTag(i);
                viewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        int i= (int) view.getTag();
                        Button button= (Button) view;
                        button.setText("已邀请");
                        button.setBackground(ContextCompat.getDrawable(context,R.drawable.invite_button_gray_shape));
                        button.setClickable(false);
                        if (SingleBeans.getInstance().getGroupStatusBeans().getGroup_match_status().equals("4")){
                            mPresenter.invite(SingleBeans.getInstance().getGroupStatusBeans().getTeam_id(),list.get(i).getUser_id());
                        }else {
                            new SetCaptainPresentModel(new EmptyView() {
                                @Override
                                public void emptyBack() {
                                    mPresenter.invite(SingleBeans.getInstance().getGroupStatusBeans().getTeam_id(),list.get((Integer) view.getTag()).getUser_id());
                                }
                            }).setCaptain();
                        }
                    }
                });
            }else {
                viewHolder.tv3.setText("组队中");
                viewHolder.tv3.setTextColor(Color.YELLOW);
                viewHolder.button.setVisibility(View.VISIBLE);
                viewHolder.button.setClickable(false);
                viewHolder.button.setText("邀请");
                viewHolder.button.setBackground(ContextCompat.getDrawable(context,R.drawable.invite_button_gray_shape));
            }

        }else {
            viewHolder.tv3.setText("离线");
            viewHolder.tv3.setTextColor(Color.GRAY);
            viewHolder.button.setVisibility(View.GONE);
        }
        return view;
    }

    public class ViewHolder {
        CircleImageView iv;
        TextView tv1;
        TextView tv2;
        TextView tv3;
        Button button;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
}
