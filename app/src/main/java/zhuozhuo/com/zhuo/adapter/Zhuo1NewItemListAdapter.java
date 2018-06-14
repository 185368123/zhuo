package zhuozhuo.com.zhuo.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import li.com.base.baserx.RxManager;
import li.com.base.basesinglebean.SingleBeans;
import li.com.base.basesinglebean.SingleChooseBean;
import li.com.base.baseuntils.LogUtils;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.constants.Constant;

/**
 * Created by Administrator on 2018/6/7.
 */

public class Zhuo1NewItemListAdapter extends BaseAdapter {

    Context context;
    List<SingleChooseBean> list;
    private ImageView iv_go;
    private TextView tv_title;
    private TextView tv_name;
    private ImageView iv_wait;
    private ImageView iv_cancle;
    private ImageOnClick imageOnClick;
    private RxManager rxManager=new RxManager();
    private List<ViewSave>  viewSaveList=new ArrayList<>();
    private ImageView iv_zhuo1_item_2;
    private AnimationDrawable drawable;


    public interface ImageOnClick {
        void onImageWaitClick(int posion);

        void onImageCancleClick(int posion);
    }

    public Zhuo1NewItemListAdapter(Context context, List<SingleChooseBean> list, ImageOnClick imageOnClick) {
        this.context = context;
        this.list = list;
        this.imageOnClick = imageOnClick;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewSave viewSave=new ViewSave();
        convertView = LayoutInflater.from(context).inflate(R.layout.zhuo1_item_list_item_layout, parent, false);
        tv_name = convertView.findViewById(R.id.tv_name_list_item);
        tv_title = convertView.findViewById(R.id.tv_title_list_item);
        iv_go = convertView.findViewById(R.id.iv_zhuo1_item);
        iv_cancle = convertView.findViewById(R.id.cancle_iv_zhuo1_item);
        iv_wait = convertView.findViewById(R.id.iv_wait_zhuo1_item);
        iv_zhuo1_item_2 = convertView.findViewById(R.id.iv_zhuo1_item_2);
        drawable = (AnimationDrawable) iv_zhuo1_item_2.getDrawable();
        drawable.start();
        viewSave.iv_go=iv_go;
        viewSave.drawable = (AnimationDrawable) iv_wait.getDrawable();
        viewSave.ll = convertView.findViewById(R.id.wait_zhuo1_item);
        viewSave.chronometer = convertView.findViewById(R.id.time_zhuo1_item);
        viewSave.position=position;
        viewSaveList.add(viewSave);

        LogUtils.logd(list.get(position).getChoice_id()+"匹配状态："+list.get(position).getMatch_status());
        if (list.get(position).getMatch_status().equals("1")&&list.get(position).getStatus().equals("66")){
            ViewSave save= viewSaveList.get(position);
            rxManager.post(Constant.MATCH_BEGIN,save);
            startWaitView(save);
        }
        tv_name.setText(list.get(position).getChoice_name().replace("/n","\n"));
        tv_title.setText("(挂机模式)");
        switch (position) {
            case 0:
                Glide.with(context).load(R.mipmap.zhuo1_item_1).into(iv_go);
                break;
            case 1:
                Glide.with(context).load(R.mipmap.zhuo1_item_2).into(iv_go);
                break;
            case 2:
                Glide.with(context).load(R.mipmap.zhuo1_item_3).into(iv_go);
                break;
            case 3:
                Glide.with(context).load(R.mipmap.zhuo1_item_4).into(iv_go);
                break;
        }
        iv_go.setTag(viewSave);
        iv_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageOnClick != null) {
                    ViewSave save= (ViewSave) v.getTag();
                    SingleBeans.getInstance().setStatu("66");
                    SingleBeans.getInstance().setMatch_type(save.position==0 ? "1" : "2");
                    SingleBeans.getInstance().setChoose_id(list.get(save.position).getChoice_id());
                    imageOnClick.onImageWaitClick(save.position);
                    rxManager.post(Constant.MATCH_BEGIN,save);
                    startWaitView(save);
                }
            }
        });
        iv_cancle.setTag(viewSave);
        iv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageOnClick != null) {
                    ViewSave save= (ViewSave) v.getTag();
                    imageOnClick.onImageCancleClick(save.position);
                    rxManager.post(Constant.MATCH_STOP,save);
                    stopWaitView(save);
                }
            }
        });
        return convertView;
    }

    public void startWaitView(ViewSave viewSave) {
        viewSave.ll.setVisibility(View.VISIBLE);
        viewSave.drawable.start();
        viewSave.chronometer.setBase(SystemClock.elapsedRealtime());
        viewSave.chronometer.start();
    }

    public void stopWaitView(ViewSave viewSave) {
        viewSave.ll.setVisibility(View.GONE);
        viewSave.drawable.stop();
        viewSave.chronometer.stop();
    }


    public List<ViewSave> getViewSaveList(){
        return viewSaveList;
    }

    public void clearViewSaveList(){
        viewSaveList.clear();
    }

    public class ViewSave{
        public int position;
        public LinearLayout ll;
        public ImageView iv_go;
        public Chronometer chronometer;
        public AnimationDrawable drawable;
    }
}
