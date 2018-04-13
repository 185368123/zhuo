package zhuozhuo.com.zhuo.presenter;

import com.hyphenate.chatuidemo.my.model.InitializationModel;
import com.hyphenate.chatuidemo.my.presenter.InitializationPresenter;
import com.hyphenate.chatuidemo.provider.PreferenceManager;
import com.hyphenate.chatuidemo.provider.UserInfoProvider;
import java.util.List;
import java.util.Map;
import li.com.base.baserx.RxSubscriber;
import com.hyphenate.chatuidemo.my.bean.LoginBean;
import zhuozhuo.com.zhuo.contract.WeiXinLoginConstract;


/**
 * Created by Administrator on 2017/10/20.
 */

public class WeiXinLoginPresenter extends WeiXinLoginConstract.Presenter {
    @Override
    public void login(Map<String, String> data) {
        String wechat_id = data.get("unionid");
        String nick_name = data.get("screen_name");
        String photo_link = data.get("profile_image_url");
        String location = data.get("province");
        String sex = "1";

        if (data.get("gender").equals("男")) {
            sex = "1";
        } else if (data.get("gender").equals("男")) {
            sex = "2";
        }
        mView.showLoading("正在登陆......");
        mModel.login(wechat_id, nick_name, photo_link, sex, location).subscribe(new RxSubscriber<List<LoginBean>>(mContext,false) {
            @Override
            protected void _onNext(List<LoginBean> loginBeen) {
                if (loginBeen != null && loginBeen.size() > 0) {
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
                        UserInfoProvider.setLocation(userInfo.getLocation());
                        UserInfoProvider.setHobby(userInfo.getAccount());
                        PreferenceManager.getPreferenceManager().setIsFirstStart(false);
                        InitializationPresenter initializationPresenter=new InitializationPresenter();
                        InitializationModel initializationModel=new InitializationModel();
                        initializationPresenter.setVM(initializationModel,null);
                        initializationPresenter.getSingleStatus();
                    }
                    mView.stopLoading();
                    mView.returnLoginBean(loginBeen);
                }
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
