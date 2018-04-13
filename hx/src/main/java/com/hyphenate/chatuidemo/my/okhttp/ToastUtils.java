package com.hyphenate.chatuidemo.my.okhttp;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chatuidemo.DemoApplication;
import com.hyphenate.chatuidemo.R;

public class ToastUtils {

    // 网络繁忙，请稍后重试
    private static final String networkIsBusy = StringUtils.getString(R.string.main_text19);
    // 加载数据失败，请检查网络后重试
    private static final String loadDataFailure = StringUtils.getString(R.string.main_text20);
    // 响应数据包错误，请稍后重试
    private static final String response_error = StringUtils.getString(R.string.main_text21);

    public static void showTheNetworkIsBusy() {
        Toast.makeText(DemoApplication.getInstance(),
                networkIsBusy, Toast.LENGTH_LONG).show();
    }

    public static void showLoadTheDataFailure() {
        Toast.makeText(DemoApplication.getInstance(),
                loadDataFailure, Toast.LENGTH_LONG).show();
    }

    public static void showResponseError() {
        Toast.makeText(DemoApplication.getInstance(),
                response_error, Toast.LENGTH_LONG).show();
    }

    public static void showCommitSucceed() {
        ToastUtils.showToast(R.string.main_text61);
    }

    public static void showToast(String toast) {
        if (TextUtils.isEmpty(toast)) return;
        Toast.makeText(DemoApplication.getInstance(), toast, Toast.LENGTH_LONG).show();
    }



    public static void showToast(TextView textView) {
        if (textView == null) return;
        ToastUtils.showToast(MainUtils.getHint(textView));
    }

    public static void showCenterToast(CharSequence message) {
        if (message == null) return;
        if (!ImportUtils.textIsEmpty(message.toString())) {
            Toast mToast = Toast.makeText(DemoApplication.getInstance(), message, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setText(message);
            mToast.show();
        }
    }
    public static void showToastDebug(String toast) {

    }
    public static void showToast(int stringId) {
        Toast.makeText(DemoApplication.getInstance(), StringUtils.getString(stringId),
                Toast.LENGTH_LONG).show();
    }

}
