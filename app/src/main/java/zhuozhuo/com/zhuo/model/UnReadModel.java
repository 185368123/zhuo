package zhuozhuo.com.zhuo.model;

import com.zhy.http.okhttp.request.RequestCall;

import java.util.HashMap;

import com.hyphenate.chatuidemo.my.api.UrlConstant;
import zhuozhuo.com.zhuo.http.MyOkHttpClient;
import zhuozhuo.com.zhuo.http.OkHttpResponseHandler;
import zhuozhuo.com.zhuo.util.RequestUtils;

/**
 * Created by Administrator on 2017/10/21.
 */

public class UnReadModel {
    /**
     * 请求回调
     */
    private OkHttpResponseHandler responseHandler;
    public UnReadModel(OkHttpResponseHandler responseHandler){
        this.responseHandler=responseHandler;

    }

    public void getUnread(String index,String token) {
        HashMap map=new HashMap();
        map.put("token", token);
        map.put("index", index);
        // 获取 Post 请求的 RequestCall
        RequestCall requestCall= MyOkHttpClient.getPostRequest(UrlConstant.GetUnRead_URL,map);

        // 发送登录请求
        RequestUtils.sendPostRequest(requestCall, responseHandler);
    }
}
