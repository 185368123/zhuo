package zhuozhuo.com.zhuo.presentermodel;

import com.hyphenate.easeui.provider.UserInfoProvider;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONObject;

import java.util.HashMap;

import com.hyphenate.chatuidemo.my.api.UrlConstant;
import zhuozhuo.com.zhuo.http.MyOkHttpClient;
import zhuozhuo.com.zhuo.presenter.BaseImportPresenter;
import zhuozhuo.com.zhuo.util.RequestUtils;
import com.hyphenate.chatuidemo.my.EmptyView;

/**
 * Created by Administrator on 2017/11/15.
 */

public class LovePresentModel extends BaseImportPresenter {
    EmptyView emptyView;

    public LovePresentModel(EmptyView emptyView) {
        this.emptyView = emptyView;
    }

    ;

    public void sendLove(String share_id) {
        HashMap map = new HashMap();
        map.put("share_id", share_id);
        map.put("token", UserInfoProvider.getToken());
        // 获取 Post 请求的 RequestCall
        RequestCall requestCall = MyOkHttpClient.getPostRequest(UrlConstant.Zan_URL, map);

        // 发送登录请求
        RequestUtils.sendPostRequest(requestCall, this);
    }

    @Override
    public void onVerifySucceed(String strResponse, JSONObject jsonResponse, String errMsg, int id) {
        emptyView.emptyBack();
    }
}
