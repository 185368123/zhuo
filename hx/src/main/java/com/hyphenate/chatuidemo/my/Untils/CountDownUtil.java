package com.hyphenate.chatuidemo.my.Untils;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.TextView;

/**
 * <p>
 * 自定义倒计时
 */
public class CountDownUtil extends CountDownTimer {

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

    /**
     * @param textView          显示时间的控件
     * @param millisInFuture    倒计时多少秒
     * @param countDownInterval 每次跳多少秒
     */
    public CountDownUtil(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
    }

    /**
     * @param textView       显示时间的控件
     * @param millisInFuture 倒计时多少秒
     */
    public CountDownUtil(TextView textView, long millisInFuture) {
        super(millisInFuture, 1000);
        this.textView = textView;
    }

    /**
     * @param textView       显示时间的控件
     * @param millisInFuture 倒计时多少秒
     * @param showText       计时过程中要显示的文字
     * @param finishText     计时完成后，按钮上要显示的文字
     */
    public CountDownUtil(TextView textView, long millisInFuture, String showText, String finishText) {
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
    public CountDownUtil(TextView textView, int textSize, long millisInFuture) {
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
    public CountDownUtil(TextView textView, int textSize, long millisInFuture, long countDownInterval) {
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
