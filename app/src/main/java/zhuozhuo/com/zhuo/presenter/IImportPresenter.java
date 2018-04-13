package zhuozhuo.com.zhuo.presenter;

import android.widget.EditText;
import android.widget.ImageView;

import zhuozhuo.com.zhuo.view.IPasswordImageView;
import zhuozhuo.com.zhuo.view.IPhoneNumberImageView;
import zhuozhuo.com.zhuo.view.IVerifyCodeImageView;

/**
 * author：kang
 * time: 2017/3/17
 *
 * 输入内容的业务逻辑
 */
public interface IImportPresenter {

    /** 获取手机号码输入框右边的 ImageView */
    ImageView getPhoneNumberImageView(IPhoneNumberImageView phoneNumberImageView, EditText etPhoneNumber);

    /** 获取验证码输入框右边的 ImageView */
    ImageView getVerifyCodeImageView(IVerifyCodeImageView verifyCodeImageView, EditText etVerifyCode);

    /** 获取密码输入框右边的 ImageView */
    ImageView getPasswordImageView(IPasswordImageView passwordImageView, EditText etPassword);
}
