package zhuozhuo.com.zhuo.model;

import com.hyphenate.chatuidemo.provider.UserInfoProvider;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.HashMap;

import com.hyphenate.chatuidemo.my.api.UrlConstant;
import zhuozhuo.com.zhuo.http.MyOkHttpClient;
import zhuozhuo.com.zhuo.http.OkHttpResponseHandler;
import zhuozhuo.com.zhuo.util.RequestUtils;

/**
 * Created by Administrator on 2017/11/20.
 */

public class WriteArticleModel {
    /**
     * 请求回调
     */
    private OkHttpResponseHandler responseHandler;


    public WriteArticleModel(OkHttpResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }


    public void write(String share_title, String content) {
        HashMap map=new HashMap();
        map.put("share_title", share_title);
        map.put("content", content);
        map.put("token", UserInfoProvider.getToken());
        // 获取 Post 请求的 RequestCall
        RequestCall requestCall= MyOkHttpClient.getPostRequest(UrlConstant.WriteArticle_URL,map, UserInfoProvider.getSessionId());

        // 发送登录请求
        RequestUtils.sendPostRequest(requestCall, responseHandler);
    }
}
