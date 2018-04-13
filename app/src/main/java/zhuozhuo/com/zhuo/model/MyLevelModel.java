package zhuozhuo.com.zhuo.model;

import com.hyphenate.chatuidemo.provider.UserInfoProvider;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.HashMap;

import com.hyphenate.chatuidemo.my.api.UrlConstant;
import zhuozhuo.com.zhuo.http.MyOkHttpClient;
import zhuozhuo.com.zhuo.http.OkHttpResponseHandler;
import zhuozhuo.com.zhuo.util.RequestUtils;

/**
 * Created by Administrator on 2017/10/30.
 */

public class MyLevelModel {

    /**
     * 请求回调
     */
    private OkHttpResponseHandler responseHandler;

    public MyLevelModel(OkHttpResponseHandler responseHandler){
        this.responseHandler=responseHandler;
    }

    public void getTotalRate(int index,String user_ids) {
        HashMap map=new HashMap();
        map.put("token", UserInfoProvider.getToken());
        map.put("index",String.valueOf(index));
        map.put("length", "10");
        map.put("user_ids", user_ids);
        // 获取 Post 请求的 RequestCall
        RequestCall requestCall= MyOkHttpClient.getPostRequest(UrlConstant.GetTotalRate_URL,map);

        // 发送登录请求
        RequestUtils.sendPostRequest(requestCall, responseHandler);
    }
}
