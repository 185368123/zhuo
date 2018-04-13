package zhuozhuo.com.zhuo.presentermodel;

import com.hyphenate.chatuidemo.provider.UserInfoProvider;
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

public class AcceptPresentModel extends BaseImportPresenter {


    public AcceptPresentModel() {

    }

    public void accept(String from_user_id,String team_id) {
        HashMap map = new HashMap();
        map.put("token", UserInfoProvider.getToken());
        map.put("from_user_id", from_user_id);
        map.put("team_id", team_id);
        // 获取 Post 请求的 RequestCall
        RequestCall requestCall = MyOkHttpClient.getPostRequest(UrlConstant.Accept_URL, map, UserInfoProvider.getSessionId());

        // 发送登录请求
        RequestUtils.sendPostRequest(requestCall, this);
    }

    @Override
    public void onVerifySucceed(String strResponse, JSONObject jsonResponse, String errMsg, int id) {

    }
}
