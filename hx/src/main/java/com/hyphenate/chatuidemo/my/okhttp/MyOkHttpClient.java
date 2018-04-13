package com.hyphenate.chatuidemo.my.okhttp;


import android.util.Log;

import com.hyphenate.chatuidemo.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.Map;

import okhttp3.MediaType;

/**
 * 发送 OkHttp 请求
 */
public class MyOkHttpClient {

    /**
     * MediaType
     */
    public static final MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");

    /**
     * 获取 post 请求的 RequestCall
     *
     * @param commitUrl 请求提交地址
     * @param params    请求参数
     */
    public static RequestCall getPostRequest(String commitUrl, Map<String, String> params) {
        return getPostStringRequest(commitUrl, params);
    }

    /**
     * 获取 post 请求的 RequestCall
     *
     * @param commitUrl 请求提交地址
     * @param params    请求参数
     */

    public static RequestCall getPostRequest(String commitUrl,Map<String, String> params,String cookie) {
        String url = Constant.Base_URL + commitUrl;
        String strParams = RequestUtils.getStrParameter(params);
        //Log.e("url",url+""+strParams);
        return OkHttpUtils.postString().content(strParams).addHeader("Cookie","zhuozhuo_session="+cookie).url(url).tag(null).mediaType(mediaType).build();
    }


    /**
     * 获取 post 请求的 RequestCall
     *
     * @param commitUrl 请求提交地址
     * @param params    请求参数
     */
    public static RequestCall getPostStringRequest(String commitUrl, Map<String, String> params) {
        return getPostStringRequest(commitUrl, null, params);
    }

    /**
     * 获取 post 请求的 RequestCall
     *
     * @param params 请求参数
     */
    public static RequestCall getPostStringRequest(String url, String params) {
        //Log.e("url",url+""+params);
        return getPostStringRequest(url, null, params);
    }

    /**
     * 获取 post 请求的 RequestCall
     *
     * @param params 请求参数
     */
    public static RequestCall getPostStringRequest(String url, Object tag, String params) {
       // Log.e("url",url+""+params);
        return OkHttpUtils.postString().content(params).url(url).tag(tag).mediaType(mediaType).build();
    }

    /**
     * 获取 post 请求的 RequestCall
     *
     * @param commitUrl 请求提交地址
     * @param tag       请求 tag，用于取消请求
     * @param params    请求参数
     */
    public static RequestCall getPostStringRequest(String commitUrl, Object tag, Map<String, String> params) {
        String url = Constant.Base_URL + commitUrl;

        String strParams = RequestUtils.getStrParameter(params);
        //Log.e("url",url+""+strParams);
        return OkHttpUtils.postString().content(strParams).url(url).tag(tag).mediaType(mediaType).build();
    }

}
