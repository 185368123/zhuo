package com.hyphenate.chatuidemo.my.Untils;

import android.util.Log;

import com.google.gson.Gson;
import com.hyphenate.chatuidemo.Constant;
import com.hyphenate.chatuidemo.my.IAddContactView;
import com.hyphenate.chatuidemo.my.UserListBean;
import com.hyphenate.chatuidemo.my.api.UrlConstant;
import com.hyphenate.chatuidemo.my.okhttp.MyOkHttpClient;
import com.hyphenate.chatuidemo.my.okhttp.OkHttpVerifyResponseHandler;
import com.hyphenate.chatuidemo.my.okhttp.RequestUtils;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/11/4.
 */

public class SearchUser extends OkHttpVerifyResponseHandler {
    IAddContactView contactView;

    public SearchUser(IAddContactView view) {
        contactView = view;
    }

    public void search(String keyword, int index) {
        HashMap map = new HashMap();
        map.put("index", index + "");
        map.put("keyword", keyword);
        // 获取 Post 请求的 RequestCall
        RequestCall requestCall = MyOkHttpClient.getPostRequest(UrlConstant.SearchUser, map);

        // 发送登录请求
        RequestUtils.sendPostRequest(requestCall, this);
    }

    @Override
    public void onVerifySucceed(String strResponse, JSONObject jsonResponse, String errMsg, int id) {
        try{
            UserListBean listBean = new Gson().fromJson(strResponse, UserListBean.class);
            List<UserListBean.DataBean> list = listBean.getData();
            contactView.setList(list);
        }catch (Exception e){
            contactView.setList(null);
        }


    }
}
