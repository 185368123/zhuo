package zhuozhuo.com.zhuo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;

import zhuozhuo.com.zhuo.R;
import com.hyphenate.chatuidemo.my.bean.VideoBean;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import zhuozhuo.com.zhuo.view.activity.ShareVideoPlayActivity;

/*
 * Created by Administrator on 2017/10/23.
 */

public class Zhuo3ListViewAdapter extends BaseAdapter {
    private List<VideoBean> list;
    private Context context;
    private LayoutInflater inflater;
    private Activity activity;
    private int index;

    public Zhuo3ListViewAdapter(List<VideoBean> been, Context context, Activity activity) {
        inflater = LayoutInflater.from(context);
        list = been;
        this.context = context;
        this.activity=activity;
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
    public View getView( int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.zhuo3_item_layout, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.iv = (ImageView) view.findViewById(R.id.iv_item);
            viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_title);
            viewHolder.tv_detail = (TextView) view.findViewById(R.id.tv_detail);
            viewHolder.tv_jubao= (TextView)view.findViewById(R.id.tv_jubao);
            viewHolder.tv_love= (TextView)view.findViewById(R.id.tv_love);
            viewHolder.frameLayout = (FrameLayout) view.findViewById(R.id.video_play);
            viewHolder.iv_weixin=(ImageView)view.findViewById(R.id.iv_share);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
       }
        Glide.with(context).load(list.get(i).getVideo_thumb_link()).into(viewHolder.iv);
        viewHolder.tv_title.setText(list.get(i).getShare_title());
        viewHolder.tv_detail.setText(list.get(i).getShare_brief()+"&"+list.get(i).getNick_name());
        viewHolder.frameLayout.setTag(list.get(i).getVideo_link());
        viewHolder.tv_love.setText((list.get(i).getComment_count()+""));
        viewHolder.tv_jubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 举报信息
            }
        });
        viewHolder.iv_weixin.setTag(i);
        viewHolder.iv_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag= (int) v.getTag();
                UMImage image = new UMImage(context, list.get(tag).getPhoto_link());//网络图片
                UMWeb web = new UMWeb("http://www.zhuozhuotech.xin/playvideo/pvideo?video="+list.get(tag).getShare_id());
                web.setTitle(list.get(tag).getShare_title());//标题
                web.setThumb(image);  //缩略图
                web.setDescription("快来围观");//描述
                new ShareAction(activity)
                        .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                        .withMedia(web)
                        .share();
            }
        });

        viewHolder.frameLayout.setTag(i);
        viewHolder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index= (int) view.getTag();
                Intent intent = new Intent(context,ShareVideoPlayActivity.class);
                try {
                    intent.putExtra("id", list.get(index).getShare_id());
                    intent.putExtra("url", list.get(index).getVideo_link());
                    intent.putExtra("zan",list.get(index).getZan());
                    intent.putExtra("title",list.get(index).getShare_title());
                    intent.putExtra("photo",list.get(index).getVideo_thumb_link());
                    context.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    class ViewHolder {
        ImageView iv;//视频大图显示
        TextView tv_title;//视频标题
        TextView tv_detail;//视频简介
        FrameLayout frameLayout;//视频播放
        TextView tv_love;//评论数量
        TextView tv_jubao;//举报
        ImageView iv_weixin;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
