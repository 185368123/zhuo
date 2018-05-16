package zhuozhuo.com.zhuo.presentermodel;

import com.hyphenate.easeui.provider.UserInfoProvider;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONObject;

import java.util.HashMap;

import com.hyphenate.chatuidemo.my.api.UrlConstant;
import zhuozhuo.com.zhuo.http.MyOkHttpClient;
import zhuozhuo.com.zhuo.presenter.BaseImportPresenter;
import zhuozhuo.com.zhuo.util.RequestUtils;

/**
 * Created by Administrator on 2017/11/15.
 */

public class LogOutPresentModel extends BaseImportPresenter {


    public LogOutPresentModel() {

    }

    public void logout() {
        HashMap map = new HashMap();
        map.put("token", UserInfoProvider.getToken());
        // 获取 Post 请求的 RequestCall
        RequestCall requestCall = MyOkHttpClient.getPostRequest(UrlConstant.LogOut_URL, map, UserInfoProvider.getSessionId());

        // 发送登录请求
        RequestUtils.sendPostRequest(requestCall, this);
    }

    @Override
    public void onVerifySucceed(String strResponse, JSONObject jsonResponse, String errMsg, int id) {

    }
}
