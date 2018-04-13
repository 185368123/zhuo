package com.hyphenate.chatuidemo.my.okhttp;

/**
 * 要显示对话框 的界面接口
 */
public interface IDialogView {

    /**
     * 显示 等待对话框
     */
    void showWaitDialog(String message);

    /**
     * 显示对话框
     *
     * @param message  提示信息
     * @param isCancel 点击外面，是否销毁
     */
    void showWaitDialog(String message, boolean isCancel);

    /**
     * 关闭 等待对话框
     */
    void closeWaitDialog();
}
