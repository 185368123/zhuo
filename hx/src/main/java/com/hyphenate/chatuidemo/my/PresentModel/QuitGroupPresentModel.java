package com.hyphenate.chatuidemo.my.PresentModel;

import com.hyphenate.chatuidemo.my.EmptyView;
import com.hyphenate.chatuidemo.my.api.UrlConstant;
import com.hyphenate.chatuidemo.my.okhttp.MyOkHttpClient;
import com.hyphenate.chatuidemo.my.okhttp.OkHttpVerifyResponseHandler;
import com.hyphenate.chatuidemo.my.okhttp.RequestUtils;
import com.hyphenate.chatuidemo.provider.UserInfoProvider;
import com.zhy.http.okhttp.request.RequestCall;
import org.json.JSONObject;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/15.
 */

public class QuitGroupPresentModel extends OkHttpVerifyResponseHandler {
    private EmptyView emptyView;

    public QuitGroupPresentModel(EmptyView emptyView) {
        this.emptyView = emptyView;
    }

    public void quitGroup(String group_id) {

        HashMap map = new HashMap();
        map.put("token", UserInfoProvider.getToken());
        map.put("group_id", group_id);
        // 获取 Post 请求的 RequestCall
        RequestCall requestCall = MyOkHttpClient.getPostRequest(UrlConstant.QuitHundredGroup_URL, map);

        // 发送登录请求
        RequestUtils.sendPostRequest(requestCall, this);
    }

    @Override
    public void onVerifySucceed(String strResponse, JSONObject jsonResponse, String errMsg, int id) {
        emptyView.emptyBack();
    }
}
