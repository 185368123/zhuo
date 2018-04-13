package zhuozhuo.com.zhuo.bean.viewholdbean;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/12/7.
 */

public class Zhuo2ViewHold {
    TextView tv1;
    TextView tv2;
    RadioGroup radioGroup;
    ImageView iv;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    LinearLayout ll;

    int ivVisibility= View.GONE;
    int llVisibility=View.GONE;

    int radioButtonIndex=0;


    public Zhuo2ViewHold(TextView tv1, TextView tv2, RadioGroup radioGroup, ImageView iv, RadioButton radioButton1, RadioButton radioButton2, RadioButton radioButton3,LinearLayout ll) {
        this.tv1 = tv1;
        this.tv2 = tv2;
        this.radioGroup = radioGroup;
        this.iv = iv;
        this.radioButton1 = radioButton1;
        this.radioButton2 = radioButton2;
        this.radioButton3 = radioButton3;
        this.ll=ll;
    }

    public TextView getTv1() {
        return tv1;
    }

    public TextView getTv2() {
        return tv2;
    }

    public RadioGroup getRadioGroup() {
        return radioGroup;
    }

    public ImageView getIv() {
        return iv;
    }

    public RadioButton getRadioButton1() {
        return radioButton1;
    }

    public RadioButton getRadioButton2() {
        return radioButton2;
    }

    public RadioButton getRadioButton3() {
        return radioButton3;
    }

    public LinearLayout getLl() {
        return ll;
    }

    public int getIvVisibility() {
        return ivVisibility;
    }

    public int getLlVisibility() {
        return llVisibility;
    }

    public int getRadioButtonIndex() {
        return radioButtonIndex;
    }

    public void setRadioButtonIndex(int radioButtonIndex) {
        this.radioButtonIndex = radioButtonIndex;
    }

    public void setIvVisibility(int ivVisibility) {
        this.ivVisibility = ivVisibility;
    }

    public void setLlVisibility(int llVisibility) {
        this.llVisibility = llVisibility;
    }
}
