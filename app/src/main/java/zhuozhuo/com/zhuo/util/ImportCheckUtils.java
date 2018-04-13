package zhuozhuo.com.zhuo.util;

import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.EditText;

import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.constants.Constant;


/**
 * author：kang
 * time: 2017/3/13
 * <p>
 * 检查用户输入的内容是否合法
 */
public class ImportCheckUtils {

    /**
     * 判断输入的 手机号码 是否合法
     *
     * @param etPhoneNumber 手机号码 输入框
     * @return false - 不合法，true - 合法
     */
    public static boolean checkPhoneNumber(EditText etPhoneNumber) {
        if (etPhoneNumber == null) return false;
        String phoneNumber = etPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            // 手机号码 为空
            ToastUtils.showToast(MainUtils.getHint(etPhoneNumber));
            return false;
        }
        if (!ImportUtils.isPhoneNumber(phoneNumber)) {
            // 手机号码格式错误
            etPhoneNumber.setText(null);
            ToastUtils.showToast(R.string.login_text7);
            return false;
        }
        return true;
    }

    /**
     * 判断输入的 手机号码 是否合法
     *
     * @param etPhoneNumber 手机号码 输入框
     * @return false - 不合法，true - 合法
     */
    public static boolean checkPhoneNumberInput(EditText etPhoneNumber) {
        return ImportCheckUtils.checkPhoneNumber(etPhoneNumber, R.string.add_contacts_text12);
    }

    /**
     * 判断输入的 手机号码 是否合法
     *
     * @param etPhoneNumber 手机号码 输入框
     * @return false - 不合法，true - 合法
     */
    public static boolean checkPhoneNumber(EditText etPhoneNumber, @StringRes int hintRes) {
        if (etPhoneNumber == null) return false;
        String phoneNumber = MainUtils.getText(etPhoneNumber);
        if (TextUtils.isEmpty(phoneNumber)) {
            // 手机号码 为空
            ToastUtils.showToast(hintRes);
            return false;
        }
        if (!ImportUtils.isPhoneNumber(phoneNumber)) {
            // 手机号码格式错误
            etPhoneNumber.setText(null);
            ToastUtils.showToast(R.string.login_text7);
            return false;
        }
        return true;
    }




    /**
     * 判断输入的 密码 是否合法
     *
     * @param etPassword 密码 输入框
     * @return false - 不合法，true - 合法
     */
    public static boolean checkPassword(EditText etPassword) {
        if (etPassword == null) return false;
        String password = MainUtils.getText(etPassword);
        if (TextUtils.isEmpty(password)) {
            // 密码为空
            ToastUtils.showToast(etPassword);
            return false;
        }
        // 判断 密码 的长度是否少于 6 位
        if (password.length() < Constant.PASSWORD_MIN_LENGTH) {
            ToastUtils.showToast(R.string.register_text10);
            return false;
        }
        return true;
    }

    /**
     * 判断 EditText 的内容是否为空
     *
     * @return 如果为空，返回 true，否则返回 false
     */
    public static boolean checkEditTextImport(EditText... editText) {
        for (EditText edit : editText) {
            String text = MainUtils.getText(edit);
            if (TextUtils.isEmpty(text)) {
                ToastUtils.showToast(edit);
                return true;
            }
        }
        return false;
    }
}
