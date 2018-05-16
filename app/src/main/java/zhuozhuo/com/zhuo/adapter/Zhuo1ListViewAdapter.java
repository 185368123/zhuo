package zhuozhuo.com.zhuo.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.hyphenate.chatuidemo.my.bean.HundredBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import li.com.base.basesinglebean.SingleChooseBean;
import li.com.base.timecountdown.CountdownView;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.view.MatchInterface;

/**
 * Created by Administrator on 2017/11/8.
 */

public class Zhuo1ListViewAdapter extends BaseAdapter implements View.OnClickListener {
    List<SingleChooseBean> data1;
    List<HundredBean> data2;
    Context context;
    LayoutInflater inflater;
    int[] picId;
    MatchInterface matchInterface;


    public Zhuo1ListViewAdapter(List<SingleChooseBean> data1, List<HundredBean> data2, Context context, int[] picId, MatchInterface matchInterface) {
        this.data1 = data1;
        this.data2 = data2;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.picId = picId;
        this.matchInterface = matchInterface;
    }

    @Override
    public int getCount() {
        return data2.size()+data1.size();
    }

    @Override
    public Object getItem(int i) {
        if (i<data2.size()){
            return data2.get(i);
        }else {
            return data1.get(i);
        }

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void upadteHundread(List<HundredBean> data2){
        this.data2.clear();
        this.data2.addAll(data2);
        notifyDataSetChanged();
    }

    public void upadteChoose(List<SingleChooseBean> data1){
        this.data1.clear();
        this.data1.addAll(data1);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
            if (i<data2.size()){
                TextView tv_1;
                TextView tv_2;
                TextView tv_3;
                CountdownView countdownView;
                Button button_;
                view=inflater.inflate(R.layout.zhuo1fragment_item_layout_,viewGroup,false);
                tv_1=view.findViewById(R.id.group_tv1);
                tv_2=view.findViewById(R.id.group_tv2);
                tv_3=view.findViewById(R.id.group_tv3);
                button_=view.findViewById(R.id.group_bt1);
                countdownView=view.findViewById(R.id.timecountdown);

                if (data2.get(i).getIs_close().equals("1")){
                    tv_2.setVisibility(View.GONE);
                    tv_3.setVisibility(View.VISIBLE);
                    countdownView.setVisibility(View.GONE);
                    button_.setVisibility(View.GONE);
                }else {
                    tv_2.setVisibility(View.VISIBLE);
                    tv_3.setVisibility(View.GONE);
                    countdownView.setVisibility(View.VISIBLE);
                    button_.setVisibility(View.VISIBLE);
                }
                button_.setText(data2.get(i).getIs_member().equals("0")?"加入":"已加入");
                button_.setTag(data2.get(i).getIs_member()+","+data2.get(i).getGroup_id()+","+data2.get(i).getHundred_id()+","+data2.get(i).getGroup_name()+","+data2.get(i).getFinish());
                button_.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        matchInterface.doHundreadClick((String) v.getTag());
                    }
                });
                tv_1.setText(data2.get(i).getGroup_name());
                tv_2.setText((data2.get(i).getMember_count() - 2)+ "/" + (data2.get(i).getMaxuser()-2));
                countdownView.start(getTime(data2.get(i).getEnd_time()));
            }else {
                TextView tv1;
                TextView tv2;
                Button button;
                view = inflater.inflate(R.layout.zhuo1item_layout, viewGroup, false);
                tv1 = (TextView) view.findViewById(R.id.zhuo1_tv1);
                tv2 = (TextView) view.findViewById(R.id.zhuo1_tv2);
                button = (Button) view.findViewById(R.id.zhuo1_button);
                tv1.setText(data1.get(i-data2.size()).getChoice_name());
                tv2.setText(data1.get(i-data2.size()).getChoice_title());
                tv1.setSelected(true);
                tv2.setSelected(true);
                button.setBackground(ContextCompat.getDrawable(context, picId[(i-data2.size()) % 4]));
                button.setOnClickListener(this);
                button.setTag(data1.get(i-data2.size()).getChoice_id());//选项ID跟名字拼接保存在Tag中，用逗号隔开
            }
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

        TextView tv_1;
        TextView tv_2;
        TextView tv_3;
        CountdownView countdownView;
        Button button_;

    }

    /**
     * 掉此方法输入所要转换的时间输入例如（"2014-06-14 16:09:00"）返回倒计时差
     *
     * @param time
     * @return
     */
    public long getTime(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.CHINA);
        Date date;
        String times = null;
        long time_ = 0;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
            long timeend = Integer.valueOf(times);
            long timenow = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
            time_ = timeend - timenow;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time_*1000;
    }
}
