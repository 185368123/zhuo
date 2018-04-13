package zhuozhuo.com.zhuo.presenter;

import android.widget.EditText;
import android.widget.ImageView;


import com.hyphenate.EMCallBack;
import com.hyphenate.chatuidemo.DemoHelper;

import org.json.JSONObject;

import zhuozhuo.com.zhuo.base.ClearTextClickListener;
import zhuozhuo.com.zhuo.constants.Constant;
import zhuozhuo.com.zhuo.http.OkHttpVerifyResponseHandler;
import zhuozhuo.com.zhuo.view.IPasswordImageView;
import zhuozhuo.com.zhuo.view.IPhoneNumberImageView;
import zhuozhuo.com.zhuo.view.IVerifyCodeImageView;

/**
 * 输入内容的业务逻辑
 */
public abstract class BaseImportPresenter extends OkHttpVerifyResponseHandler implements IImportPresenter {

    /**
     * 给在 EditText 右边的图片添加监听器，点击图片清空 EditText 中的内容
     *
     * @param phoneNumberImageView 手机号码输入框右边的图片
     * @param etPhoneNumber        手机号码输入框
     */
    public void addClearPhoneNumberEditListener(IPhoneNumberImageView phoneNumberImageView, EditText etPhoneNumber) {
        if (phoneNumberImageView != null && etPhoneNumber != null) {
            // 清除手机号码输入框
            ImageTextWatcher clearPhoneNumber = new ImageTextWatcher(etPhoneNumber,
                    getPhoneNumberImageView(phoneNumberImageView, etPhoneNumber));
            etPhoneNumber.addTextChangedListener(clearPhoneNumber);
        }
    }

    /**
     * 给在 EditText 右边的图片添加监听器，点击图片清空 EditText 中的内容
     *
     * @param verifyCodeImageView 验证码 输入框右边的图片
     * @param etVerifyCode        验证码 输入框
     */
    public void addClearVerifyCodeEditListener(IVerifyCodeImageView verifyCodeImageView, EditText etVerifyCode) {
        if (verifyCodeImageView != null && etVerifyCode != null) {
            // 清除验证码输入框
            ImageTextWatcher clearVerifyCode = new ImageTextWatcher(etVerifyCode,
                    getVerifyCodeImageView(verifyCodeImageView, etVerifyCode));
            etVerifyCode.addTextChangedListener(clearVerifyCode);
        }
    }

    /**
     * 给在 EditText 右边的图片添加监听器，点击图片清空 EditText 中的内容
     *
     * @param passwordImageView 密码 输入框右边的图片
     * @param etPassword        密码 输入框
     */
    public void addClearPasswordEditListener(IPasswordImageView passwordImageView, EditText etPassword) {
        if (passwordImageView != null && etPassword != null) {
            // 清除密码输入框
            ImageTextWatcher clearPassword = new ImageTextWatcher(etPassword,
                    getPasswordImageView(passwordImageView, etPassword));
            etPassword.addTextChangedListener(clearPassword);
        }
    }

    /**
     * 获取手机号码输入框右边的 ImageView
     */
    @Override
    public ImageView getPhoneNumberImageView(IPhoneNumberImageView phoneNumberImageView, EditText etPhoneNumber) {
        if (phoneNumberImageView == null) return null;
        ImageView imageView = phoneNumberImageView.getPhoneNumberImageView();
        setImageClickListener(imageView, etPhoneNumber);
        return imageView;
    }

    /**
     * 获取验证码输入框右边的 ImageView
     */
    @Override
    public ImageView getVerifyCodeImageView(IVerifyCodeImageView verifyCodeImageView, EditText etVerifyCode) {
        if (verifyCodeImageView == null) return null;
        ImageView imageView = verifyCodeImageView.getVerifyCodeImageView();
        setImageClickListener(imageView, etVerifyCode);
        return imageView;
    }

    @Override
    public ImageView getPasswordImageView(IPasswordImageView passwordImageView, EditText etPassword) {
        if (passwordImageView == null) return null;
        ImageView imageView = passwordImageView.getPasswordImageView();
        setImageClickListener(imageView, etPassword);
        return imageView;
    }

    /**
     * 为图片添加监听器
     */
    protected void setImageClickListener(ImageView imageView, EditText editText) {
        if (imageView != null && editText != null) {
            imageView.setOnClickListener(new ClearTextClickListener(editText));
        }
    }

    /**
     * 子类复写该方法，关闭显示的等待对话框
     */
    @Override
    protected void closeWaitDialog() {

    }

    @Override
    public void onErrorCode(String strResponse, JSONObject jsonResponse, String errMsg, String errCode, int id) {
        if(Constant.RESPONSE_MEGUNUSE.equals(errCode)){
            DemoHelper.getInstance().logout(true,new EMCallBack() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onProgress(int progress, String status) {

                }

                @Override
                public void onError(int code, String message) {

                }
            });
        }
    }
}
