package zhuozhuo.com.zhuo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import zhuozhuo.com.zhuo.R;
import com.hyphenate.chatuidemo.my.bean.GroupChoicesBean;
import zhuozhuo.com.zhuo.bean.viewholdbean.Zhuo2ViewHold;
import zhuozhuo.com.zhuo.util.Zhuo2ViewHoldUtil;
import zhuozhuo.com.zhuo.view.MatchInterface;

/**
 * Created by Administrator on 2017/12/7.
 */

public class Zhuo2ListAdapter extends BaseAdapter implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    List<GroupChoicesBean> dataBeen;
    LayoutInflater inflater;
    Context context;
    MatchInterface matchInterface;
    public Zhuo2ListAdapter(List<GroupChoicesBean> dataBeen,Context context,MatchInterface matchInterface){
        this.dataBeen=dataBeen;
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.matchInterface=matchInterface;
    }
    @Override
    public int getCount() {
        return dataBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return dataBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.zhuo2fragment_item_layout,viewGroup,false);
        TextView tv1= (TextView) view.findViewById(R.id.tv1_fragment2);
        TextView tv2=(TextView) view.findViewById(R.id.tv2_fragment2);
        RadioGroup radioGroup= (RadioGroup) view.findViewById(R.id.group_fragment2);
        radioGroup.setTag(i);
        ImageView iv= (ImageView) view.findViewById(R.id.iv_fragment2);
        iv.setTag(i);
        iv.setOnClickListener(new View.OnClickListener() {//Go图标被点击，回调Fragment中的方法，传入Go图标所被点击的RadioGroup的下标
            @Override
            public void onClick(View view) {
                matchInterface.doClick(String.valueOf((int) view.getTag()));
            }
        });
        RadioButton radioButton1= (RadioButton) view.findViewById(R.id.groupbutton1_fragment2);
        RadioButton radioButton2= (RadioButton) view.findViewById(R.id.groupbutton2_fragment2);
        RadioButton radioButton3= (RadioButton) view.findViewById(R.id.groupbutton3_fragment2);
        LinearLayout ll= (LinearLayout) view.findViewById(R.id.ll_fragment2);
        Zhuo2ViewHoldUtil.getZhuo2ViewHoldUtil().addViewHold(new Zhuo2ViewHold(tv1,tv2,radioGroup,iv,radioButton1,radioButton2,radioButton3,ll));
        tv1.setText(dataBeen.get(i).getChoice_name());
        tv1.setTag(i);
        tv2.setText(dataBeen.get(i).getDesc());
        String[] values=dataBeen.get(i).getValue().split(",");
        radioButton1.setText(values[0]);
        radioButton2.setText(values[1]);
        radioButton3.setText(values[2]);
        tv1.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {//当主题栏被点击，如果选项栏显示则设置隐藏，隐藏则设置为显示
        int i= (int) view.getTag();
        if (Zhuo2ViewHoldUtil.getZhuo2ViewHoldUtil().getViewHold(i).getLl().getVisibility()==View.VISIBLE){//当前选项栏显示
            Zhuo2ViewHoldUtil.getZhuo2ViewHoldUtil().getViewHold(i).getLl().setVisibility(View.GONE);
            Zhuo2ViewHoldUtil.getZhuo2ViewHoldUtil().getViewHold(i).setLlVisibility(View.GONE);
            Zhuo2ViewHoldUtil.getZhuo2ViewHoldUtil().getViewHold(i).getIv().setVisibility(View.GONE);
        }else {//当前选项栏隐藏，改变状态为显示，判断隐藏前的GO图标是否为显示状态，恢复以前的状态
            Zhuo2ViewHoldUtil.getZhuo2ViewHoldUtil().getViewHold(i).getLl().setVisibility(View.VISIBLE);
            Zhuo2ViewHoldUtil.getZhuo2ViewHoldUtil().getViewHold(i).setLlVisibility(View.VISIBLE);
            if (Zhuo2ViewHoldUtil.getZhuo2ViewHoldUtil().getViewHold(i).getIvVisibility()==View.GONE){
                Zhuo2ViewHoldUtil.getZhuo2ViewHoldUtil().getViewHold(i).getIv().setVisibility(View.GONE);
            }else {
                Zhuo2ViewHoldUtil.getZhuo2ViewHoldUtil().getViewHold(i).getIv().setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {//当RadioGroup中的RadioButton被选中事件
        List<Zhuo2ViewHold> list1;
        int index= (int) radioGroup.getTag();//获得选中radioGroup的下标
        list1 = Zhuo2ViewHoldUtil.getZhuo2ViewHoldUtil().getViewHoldList();
        for (int j = 0; j < list1.size(); j++) {//遍历保存控件的集合
            if (j==index){//集合保存的下标等于控件选中的下标，改变选中控件的状态
              if ( list1.get(j).getRadioButton1().getId()==i){//保存选中第几个radioButton
                  list1.get(j).setRadioButtonIndex(1);
              } else if ( list1.get(j).getRadioButton2().getId()==i){//保存选中第几个radioButton
                    list1.get(j).setRadioButtonIndex(2);
                } else if ( list1.get(j).getRadioButton3().getId()==i){//保存选中第几个radioButton
                    list1.get(j).setRadioButtonIndex(3);
                }
                list1.get(j).getIv().setVisibility(View.VISIBLE);//选中的这个RadioGroup的Go图标设置显示
                list1.get(j).setIvVisibility(View.VISIBLE);//保存选中的这个RadioGroup的Go图标的状态
            }else {
                list1.get(j).getRadioGroup().setOnCheckedChangeListener(null);//清空没有选中的RadioGroup的监听事件
                list1.get(j).getRadioGroup().clearCheck();//清空没有选中的RadioGroup的所有RadioButton的选中状态
                list1.get(j).getRadioGroup().setOnCheckedChangeListener(this);//设置没有选中的RadioGroup的监听事件
                list1.get(j).getIv().setVisibility(View.GONE);//未选中的这个RadioGroup的Go图标设置隐藏
                list1.get(j).setIvVisibility(View.GONE);//保存未选中的这个RadioGroup的Go图标的状态
            }
        }
    }
}
