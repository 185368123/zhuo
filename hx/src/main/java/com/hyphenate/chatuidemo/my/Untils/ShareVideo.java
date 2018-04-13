package com.hyphenate.chatuidemo.my.Untils;

import com.hyphenate.chatuidemo.Constant;
import com.hyphenate.chatuidemo.my.api.UrlConstant;
import com.hyphenate.chatuidemo.my.okhttp.MyOkHttpClient;
import com.hyphenate.chatuidemo.my.okhttp.OkHttpVerifyResponseHandler;
import com.hyphenate.chatuidemo.my.okhttp.RequestUtils;
import com.hyphenate.chatuidemo.provider.UserInfoProvider;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/9.
 */

public class ShareVideo extends OkHttpVerifyResponseHandler {
    IShareVideo shareVideo;

    public ShareVideo(IShareVideo shareVideo) {
        this.shareVideo = shareVideo;
    }

    public void shareVideo(String video_link) {
        HashMap map = new HashMap();
        map.put("token", UserInfoProvider.getToken());
        map.put("video_link", video_link);
        // 获取 Post 请求的 RequestCall
        RequestCall requestCall = MyOkHttpClient.getPostRequest(UrlConstant.ShareVideo, map);

        // 发送登录请求
        RequestUtils.sendPostRequest(requestCall, this);
    }

    @Override
    public void onVerifySucceed(String strResponse, JSONObject jsonResponse, String errMsg, int id) {
          shareVideo.shareSucess();
    }
}
