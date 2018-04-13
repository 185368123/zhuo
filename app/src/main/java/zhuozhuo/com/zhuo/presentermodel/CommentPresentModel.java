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

public class CommentPresentModel extends BaseImportPresenter{
    public CommentPresentModel() {

    }
    public void comment(String share_id,String to_id,String parent_id,String comment) {
        HashMap map = new HashMap();
        map.put("token", UserInfoProvider.getToken());
        map.put("share_id", share_id);
        map.put("to_id", to_id);
        map.put("parent_id", parent_id);
        map.put("comment", comment);
        // 获取 Post 请求的 RequestCall
        RequestCall requestCall = MyOkHttpClient.getPostRequest(UrlConstant.Comment_URL, map, UserInfoProvider.getSessionId());

        // 发送登录请求
        RequestUtils.sendPostRequest(requestCall, this);
    }
    public void comment(String share_id,String comment) {
        HashMap map = new HashMap();
        map.put("token", UserInfoProvider.getToken());
        map.put("share_id", share_id);
        map.put("comment", comment);
        // 获取 Post 请求的 RequestCall
        RequestCall requestCall = MyOkHttpClient.getPostRequest(UrlConstant.Comment_URL, map, UserInfoProvider.getSessionId());

        // 发送登录请求
        RequestUtils.sendPostRequest(requestCall, this);
    }

    @Override
    public void onVerifySucceed(String strResponse, JSONObject jsonResponse, String errMsg, int id) {

    }
}
