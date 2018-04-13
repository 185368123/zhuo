package zhuozhuo.com.zhuo.http;

import org.json.JSONObject;

/**
 * author：kang
 * time:  2017/1/6
 *
 * 验证响应数据包合法性的回调接口
 */
public interface OkHttpVerifyResponse {

    /**
     * 请求成功，并且返回的响应数据包 合法 ，
     * 并且返回的 errcode 是成功状态(000) 时调用 -- 此方法在 UI 线程中调用
     * @param strResponse 响应数据包，字符串格式
     * @param jsonResponse 响应数据包，Json 格式
     * @param errMsg 错误信息
     * @param id 请求 id
     */
    void onVerifySucceed(String strResponse, JSONObject jsonResponse, String errMsg, int id);

    /**
     * 请求成功，并且返回的响应数据包 不合法 时调用 -- 此方法在 UI 线程中调用（子类复写）
     * @param strResponse 响应数据包，字符串格式
     * @param jsonResponse 响应数据包，Json 格式
     * @param errMsg 错误信息
     * @param id 请求 id
     * 该响应数据包不合法，不需要进行解析
     */
    void onVerifyFailure(String strResponse, JSONObject jsonResponse, String errMsg, int id);

    /**
     * 请求成功，并且返回的 errCode 错误 时调用(errcode 不是 000 ) -- 此方法在 UI 线程中调用（子类复写）
     * @param strResponse 响应数据包，字符串格式
     * @param jsonResponse 响应数据包，Json 格式
     * @param errMsg 错误信息
     * @param id 请求 id
     * 该响应数据包的 errCode 错误，不需要进行解析
     */
    void onErrorCode(String strResponse, JSONObject jsonResponse, String errMsg, String errCode, int id);
}
