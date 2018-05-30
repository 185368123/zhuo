package zhuozhuo.com.zhuo.adapter;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;

import li.com.base.baseuntils.ToastUitl;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.view.activity.VideoSelectActivity;

/**
 * 本地视频列表
 */
public class VideoSelectAdapter extends CursorAdapter {
    VideoSelectActivity activity;

    MediaMetadataRetriever retriever;

    public VideoSelectAdapter(Context context, Cursor c) {
        super(context, c);
        retriever=new MediaMetadataRetriever();
    }

    public VideoSelectAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public VideoSelectAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    public void setMediaSelectVideoActivity(VideoSelectActivity activity) {
        this.activity = activity;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        View content = View.inflate(context, R.layout.item_video_select, null);
        holder.content=content;
        holder.pic = (ImageView) content.findViewById(R.id.iv_media_video);
        holder.dur = (TextView) content.findViewById(R.id.tv_duration);
        content.setTag(holder);
        return content;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final ViewHolder holder = (ViewHolder) view.getTag();
        final Uri uri = getUri(cursor);
        final String path = cursor.getString(cursor
                .getColumnIndex(MediaStore.Video.Media.DATA));
        if(TextUtils.isEmpty(path)||!new File(path).exists()){
            return;
        }
        try{
            retriever.setDataSource(path);
        }catch (Exception e){
            e.printStackTrace();
            view.setOnClickListener(null);
            return;
        }

        String duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        if(TextUtils.isEmpty(duration)||"null".equals(duration)){
            return;
        }
        int dur = Integer.parseInt(duration);
        String time =formatMillisec(dur);
        if (dur/1000<4||dur/1000>17){
            view.setOnClickListener(null);
            holder.dur.setText("(时间不符)"+time);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        ToastUitl.showLong("视频时间不符，请选择4-17秒的视频");
                    }
                }
            });
        }else {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onSelect(path,uri.toString());
                    }
                }
            });
            holder.dur.setText(time);
        }
        Glide.with(context)
                .load(uri)
                .placeholder(R.mipmap.editor_img_def_video)
                .error(R.mipmap.editor_img_def_video)
                .crossFade()
                .into(holder.pic);
    }

    public Uri getUri(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
        return Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    class ViewHolder {
        View content;
        ImageView pic;
        TextView dur;
    }

    public void setOnSelectChangedListener(OnVideoSelectListener listener) {
        this.listener = listener;
    }

    OnVideoSelectListener listener;

    public interface OnVideoSelectListener {
        void onSelect(String path, String cover);
    }


    /**
     * 00:00:00 时分秒
     * @param millisec
     * @return
     */
    public static String formatMillisec(int millisec){
        int sec=millisec/1000;
        int min=sec/60;
        min=min%60;
        sec=sec%60;
        String t="";
        t=min>=10?t+min:t+"0"+min+":";
        t=sec>=10?t+sec:t+"0"+sec;
        return t;
    }
}
