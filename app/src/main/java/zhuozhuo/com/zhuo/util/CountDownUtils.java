package zhuozhuo.com.zhuo.util;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.TextView;
import com.hyphenate.chatuidemo.my.EmptyView;
import zhuozhuo.com.zhuo.R;

/**
 * <p>
 * 自定义倒计时
 */
public class CountDownUtils extends CountDownTimer {

    private TextView textView;

    /**
     * 默认的字体大小
     */
    private int textSize = 12;

    /**
     * 是否设置字体大小
     */
    private boolean isSetSize;

    /**
     * 是否设置按钮在计时时不可点击
     */
    private boolean isSetClick;

    /**
     * 计时过程中要显示的文字
     */
    private String showText;
    /**
     * 计时完成后，按钮上要显示的文字
     */
    private String finishText;
    private EmptyView emptyView;




    /**
     * @param textView          显示时间的控件
     * @param millisInFuture    倒计时多少秒
     * @param countDownInterval 每次跳多少秒
     */
    public CountDownUtils(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
    }

    public void cancle(){
        super.cancel();
    }
    /**
     * @param textView       显示时间的控件
     * @param millisInFuture 倒计时多少秒
     */
    public CountDownUtils(TextView textView, long millisInFuture, EmptyView emptyView) {
        super(millisInFuture, 1000);
        this.textView = textView;
        this.emptyView= emptyView;
    }
    /**
     * @param textView       显示时间的控件
     * @param millisInFuture 倒计时多少秒
     */
    public CountDownUtils(TextView textView, long millisInFuture, EmptyView emptyView,String finishText) {
        super(millisInFuture, 1000);
        this.textView = textView;
        this.finishText = finishText;
        this.emptyView = emptyView;

    }

    /**
     * @param textView       显示时间的控件
     * @param millisInFuture 倒计时多少秒
     * @param showText       计时过程中要显示的文字
     * @param finishText     计时完成后，按钮上要显示的文字
     */
    public CountDownUtils(TextView textView, long millisInFuture, String showText, String finishText) {
        super(millisInFuture, 1000);
        this.textView = textView;
        this.showText = showText;
        this.finishText = finishText;
    }

    /**
     * @param textView       显示时间的控件
     * @param textSize       计时完成以后 TextView 的字体大小 单位 sp
     * @param millisInFuture 倒计时多少秒
     */
    public CountDownUtils(TextView textView, int textSize, long millisInFuture) {
        super(millisInFuture, 1000);
        this.textView = textView;
        this.textSize = textSize;
    }

    /**
     * @param textView          显示时间的控件
     * @param textSize          计时完成以后 TextView 的字体大小 单位 sp
     * @param millisInFuture    倒计时多少秒
     * @param countDownInterval 每次跳多少秒
     */
    public CountDownUtils(TextView textView, int textSize, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        this.textSize = textSize;
    }

    /**
     * 计时过程中的回调
     */
    @Override
    public void onTick(long millisUntilFinished) {
        this.setTextViewState(false, textSize); // 设置按钮不能点击
        if (!TextUtils.isEmpty(showText)) {
            textView.setText((millisUntilFinished / 1000) + " " + showText);
        } else {
            textView.setText((millisUntilFinished / 1000)+"");
        }

    }

    /**
     * 计时完成
     */
    @Override
    public void onFinish() {
        this.setTextViewState(true, textSize); // 按钮设置成能点击
        if (!TextUtils.isEmpty(finishText)) {
            textView.setText(finishText);
        }
        li.com.base.baseuntils.LogUtils.logd(CountDownUtils.class.getSimpleName());
        if (emptyView!=null){
            li.com.base.baseuntils.LogUtils.logd(CountDownUtils.class.getSimpleName()+"doemptyView");
            emptyView.emptyBack();
        }
    }

    public void deletCallBack(){//删除函数回调
        emptyView=null;
    }

    private void setTextViewState(boolean isClickable, int textSize) {
        if (isSetClick()) {
            textView.setClickable(isClickable); // 设置按钮是否能点击
        }
        if (isSetSize()) {
            textView.setTextSize(textSize);
        }
    }

    /**
     * 获取获取验证码的计时器,启动计时器
     *
     * @param btGteVerifyCode 获取验证码按钮
     * @return CountDownUtils
     */
    public static CountDownUtils startAuthCodeCountDown(TextView btGteVerifyCode) {
        if (btGteVerifyCode == null) return null;
        CountDownUtils countDownUtils = new CountDownUtils(
                btGteVerifyCode, 60 * 1000, StringUtils.getString(R.string.personal_text15),
                StringUtils.getString(R.string.register_text3));
        countDownUtils.setSetClick(true);
        countDownUtils.start();
        return countDownUtils;
    }

    /**
     * 获取获取验证码的计时器,启动计时器
     */
    public void stopAuthCodeCountDown(TextView tvGteVerifyCode) {
        this.cancel();
        if (tvGteVerifyCode != null) {
            tvGteVerifyCode.setText(finishText);
        }
    }

    public boolean isSetSize() {
        return isSetSize;
    }

    public void setSetSize(boolean setSize) {
        isSetSize = setSize;
    }

    public boolean isSetClick() {
        return isSetClick;
    }

    public void setSetClick(boolean setClick) {
        isSetClick = setClick;
    }

    public String getShowText() {
        return showText;
    }

    public void setShowText(String showText) {
        this.showText = showText;
    }

    public String getFinishText() {
        return finishText;
    }

    public void setFinishText(String finishText) {
        this.finishText = finishText;
    }
}
