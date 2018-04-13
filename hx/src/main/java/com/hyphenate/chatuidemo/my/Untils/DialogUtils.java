package com.hyphenate.chatuidemo.my.Untils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;
import android.view.WindowManager;

import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.my.okhttp.IDialogView;


/**
 * author：kang
 * time:  2016-12-2
 * <p>
 * 显示进度对话框
 */
public class DialogUtils {

    /**
     * 对话框样式
     */
    public static final int DIALOG_STYLE = R.style.progress_dialog_style1;
    /**
     * 对话框显示时 Activity 是透明的
     */
    public static final int DIALOG_STYLE_NO_FOCUS = R.style.progress_dialog_style3;

    /**
     * 进度对话框
     */
    private ProgressDialog dialog;
    private Activity activity;

    public DialogUtils( Activity activity) {
        this.activity = activity;
    }

    /**
     * 显示对话框
     *
     * @param message  对话框上的提示内容
     * @param isCancel 点击对话框外面，是否取消 Dialog，true - 会取消，false - 不会取消
     * @param isFocus  对话框显示时，是否有焦点，true - 有焦点，false - 没有焦点
     * @param style    Dialog 样式,DIALOG_STYLE or DIALOG_STYLE_NO_FOCUS
     */
    public void showWaitDialog(String message, boolean isCancel, boolean isFocus, int style) {
        if (activity == null) return;
        dialog = new ProgressDialog(activity, style);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // 对话框上的提示内容
        dialog.setMessage(message);
        // 点击对话框外面，是否取消 Dialog
        dialog.setCancelable(isCancel);
        if (!isFocus) {
            //使 dialog 失去焦点
            Window window = dialog.getWindow();
            if (window != null) {
                window.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            }
            dialog.setCanceledOnTouchOutside(true);
        }
        dialog.show();
    }

    /**
     * 显示对话框
     *
     * @param message  对话框上的提示内容
     * @param isCancel 点击对话框外面，是否取消 Dialog，true - 会取消，false - 不会取消
     */
    public void showWaitDialog(String message, boolean isCancel) {
        showWaitDialog(message, isCancel, true, DIALOG_STYLE);
    }

    /**
     * 显示对话框 - 请稍后...
     */
    public void showWaitDialog() {
        this.showWaitDialog(getWaitMessage(), true, true, DIALOG_STYLE);
    }

    /**
     * @param message 对话框的提示信息
     */
    public void showWaitDialog(String message) {
        this.showWaitDialog(message,
                true, true, DIALOG_STYLE);
    }

    /**
     * @param isCancel 点击对话框外面，是否取消 Dialog，true - 会取消，false - 不会取消
     */
    public void showWaitDialog(boolean isCancel) {
        this.showWaitDialog(getWaitMessage(), isCancel, true, DIALOG_STYLE);
    }

    /**
     * 显示对话框,点击对话框外面不会取消
     */
    public void showWaitDialogNoCancel() {
        this.showWaitDialog(getWaitMessage(), false, true, DIALOG_STYLE);
    }

    /**
     * 显示对话框,对话框没有焦点,点击对话框外面时，
     * 可以在 Activity onTouchEvent(MotionEvent event)
     * 方法中监听 ACTION_DOWN 事件关闭对话框
     */
    public void showWaitDialogNoFocus() {
        this.showWaitDialog(getWaitMessage(), true, false, DIALOG_STYLE_NO_FOCUS);
    }

    /**
     * 显示对话框 - 正在加载...
     */
    public void showLoadDialog() {
        this.showWaitDialog(getLoadMessage(), true, true, DIALOG_STYLE);
    }

    /**
     * 关闭对话框
     */
    public void closeDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
            dialog = null;
        }
    }

    /**
     * @return 正在加载...
     */
    public static String getLoadMessage() {
        return StringUtils.getString(R.string.dialog_msg_load);
    }

    /**
     * @return 请稍后...
     */
    public static String getWaitMessage() {
        return StringUtils.getString(R.string.dialog_msg_wait);
    }

    /**
     * 正在加载...
     */
    public static void showLoadDialog(IDialogView dialogView) {
        if (dialogView != null) {
            dialogView.showWaitDialog(DialogUtils.getLoadMessage());
        }
    }

    /**
     * 请稍后...
     */
    public static void showWaitDialog(IDialogView dialogView) {
        if (dialogView != null) {
            dialogView.showWaitDialog(DialogUtils.getWaitMessage());
        }
    }
}
