package zhuozhuo.com.zhuo.presenter;


import android.widget.EditText;
import android.widget.ImageView;

import com.hyphenate.chatuidemo.my.model.InitializationModel;
import com.hyphenate.chatuidemo.my.presenter.InitializationPresenter;
import com.hyphenate.chatuidemo.provider.PreferenceManager;
import com.hyphenate.chatuidemo.provider.UserInfoProvider;
import java.util.List;
import li.com.base.baserx.RxSubscriber;
import zhuozhuo.com.zhuo.base.ClearTextClickListener;
import com.hyphenate.chatuidemo.my.bean.LoginBean;
import zhuozhuo.com.zhuo.contract.LoginConstract;
import zhuozhuo.com.zhuo.util.ImportCheckUtils;
import zhuozhuo.com.zhuo.util.MainUtils;

/**
 * Created by Administrator on 2017/10/17.
 * 处理登录业务逻辑(逻辑层)
 */

public class LoginPresenter extends LoginConstract.Presenter {


    @Override
    public void returnLoginBean(EditText etUserName, EditText etPassword) {

        String userName = MainUtils.getText(etUserName); // 用户名
        String password = MainUtils.getText(etPassword); // 密码

        // 判断 用户名(手机号码) 是否合法
        if (!ImportCheckUtils.checkPhoneNumber(etUserName)) {
            return;
        }
        // 判断 密码 是否合法
        if (!ImportCheckUtils.checkPassword(etPassword)) {
            return;
        }
         mView.showLoading("正在登陆......");

        mModel.returnLoginBean(userName, password).subscribe(new RxSubscriber<List<LoginBean>>(mContext, false) {
            @Override
            protected void _onNext(List<LoginBean> loginBeen) {

                if (loginBeen != null && loginBeen.size() > 0) {
                    // TODO: 要将用户数据保存到数据库
                    for (LoginBean userInfo : loginBeen) {
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
                        UserInfoProvider.setLocation(userInfo.getLocation());
                        UserInfoProvider.setHobby(userInfo.getAccount());
                        PreferenceManager.getPreferenceManager().setIsFirstStart(false);
                        InitializationPresenter initializationPresenter=new InitializationPresenter();
                        InitializationModel initializationModel=new InitializationModel();
                        initializationPresenter.setVM(initializationModel,null);
                        initializationPresenter.getSingleStatus();
                    }
                }
                mView.returnLoginBean(loginBeen);
            }

            @Override
            protected void _onError(String message) {

            }
        });
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
