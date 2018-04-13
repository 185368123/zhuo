package zhuozhuo.com.zhuo.http;


import okhttp3.Call;

/**
 * author：kangjia
 * time:  2016-10-20
 *
 * 请求回调
 */
public interface OkHttpResponseHandler {

    /**
     * 请求成功 -- 此方法在 UI 线程中调用
     * @param response 响应数据包
     * @param id 请求 id
     */
    void onSucceed(String response, int id);

    /**
     * 请求失败 -- 此方法在 UI 线程中调用
     * @param call 请求包
     * @param e 异常信息
     * @param id 请求 id
     */
    void onFailure(Call call, Exception e, int id);
}
