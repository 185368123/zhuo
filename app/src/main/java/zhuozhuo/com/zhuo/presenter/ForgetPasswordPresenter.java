package zhuozhuo.com.zhuo.presenter;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.hyphenate.easeui.provider.PreferenceManager;
import com.hyphenate.easeui.provider.UserInfoProvider;
import java.util.List;
import li.com.base.baserx.RxSubscriber;
import zhuozhuo.com.zhuo.base.ClearTextClickListener;
import com.hyphenate.chatuidemo.my.bean.LoginBean;
import zhuozhuo.com.zhuo.contract.ForgetPasswordConstract;
import zhuozhuo.com.zhuo.model.GetAnthCoeModel;
import zhuozhuo.com.zhuo.util.ImportCheckUtils;
import zhuozhuo.com.zhuo.util.MainUtils;
import zhuozhuo.com.zhuo.util.ToastUtils;

/**
 * 忘记密码   提交 的业务逻辑层
 * Created by Administrator on 2017/10/19.
 */

public class ForgetPasswordPresenter extends ForgetPasswordConstract.Presenter {

    @Override
    public void changePassword(EditText phone, EditText code, EditText newPassword) {
        String userName = MainUtils.getText(phone); // 用户名
        String password = MainUtils.getText(newPassword); // 密码
        String verifyCode = MainUtils.getText(code); // 验证码
        // 判断 用户名(手机号码) 是否合法
        if (!ImportCheckUtils.checkPhoneNumber(phone)) {
            return;
        }
        // 判断 密码 是否合法
        if (!ImportCheckUtils.checkPassword(newPassword)) {
            return;
        }
        if (TextUtils.isEmpty(verifyCode)) {
            // 验证码 为空
            ToastUtils.showToast(code);
            return;
        }
        mModel.changePassword(userName, verifyCode, password).subscribe(new RxSubscriber<List<LoginBean>>(mContext, false) {
            @Override
            protected void _onNext(List<LoginBean> loginBeen) {
                if (loginBeen != null && loginBeen.size() > 0) {
                    // TODO: 要将用户数据保存到数据库
                    for (LoginBean userInfo : loginBeen) {
                        if (userInfo == null) {
                            return;
                        }
                        // 保存用户信息
                        UserInfoProvider.setUserId(userInfo.getUser_id());
                        UserInfoProvider.setSessionId(userInfo.getSession_id());
                        UserInfoProvider.setNickName(userInfo.getNick_name());
                        UserInfoProvider.setToken(userInfo.getToken());
                        UserInfoProvider.setAccount(userInfo.getAccount());
                        UserInfoProvider.setPhotoLink(userInfo.getPhoto_link());
                        UserInfoProvider.setSex(userInfo.getSex());
                        UserInfoProvider.setPhone(userInfo.getPhone());
                        UserInfoProvider.setRegMode(userInfo.getReg_mode());
                        UserInfoProvider.setExp(userInfo.getExp());
                        UserInfoProvider.setUserVideo(userInfo.getUser_video());
                        PreferenceManager.getPreferenceManager().setIsFirstStart(false);
                    }
                }
            }
            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void getAnthCode(EditText etPhoneNumber, Button btGteVerifyCode) {
        GetAnthCoeModel anthCoeModel=new GetAnthCoeModel();
        GetAuthCodePresenter authCodePresenter=new GetAuthCodePresenter();
        authCodePresenter.setVM(anthCoeModel,null);
        authCodePresenter.mContext=mContext;
        authCodePresenter.getAnthCode(etPhoneNumber,btGteVerifyCode,"reset");
    }

    @Override
    public void addClearEditListener(ImageView image, EditText editText) {
        if (image != null && editText != null) {
            image.setOnClickListener(new ClearTextClickListener(editText));
        }
        // 清除手机号码输入框
        ImageTextWatcher clearPhoneNumber = new ImageTextWatcher(editText,image);
        editText.addTextChangedListener(clearPhoneNumber);
    }
}
