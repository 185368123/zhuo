package zhuozhuo.com.zhuo.util;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import zhuozhuo.com.zhuo.constants.RequestPackage;
import zhuozhuo.com.zhuo.constants.ResponsePackage;

/**
 *
 * 处理响应数据包的工具类
 */
public class ResponseUtils {

    /** 响应数据包超时时间 2 分钟（120000 毫秒） 5 分钟 （300000 毫秒） */
    private static final int RESPONSE_TIME_OUT = 1000 * 60 * 2;

    /** 从 JSONObject 中获取字符串 */
    public static String getString(JSONObject response,String key){
        try {
            if (response != null && key != null){
                if (response.has(key)){
                    return response.getString(key);
                }else {
                    return "";
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    /** 获取 ErrCode */
    public static String getErrCode(JSONObject response){
        return ResponseUtils.getString(response, ResponsePackage.errcode);
    }

    /** 获取 errmsg */
    public static String getErrMsg(JSONObject response){
        return ResponseUtils.getString(response, ResponsePackage.errmsg);
    }

    /** 将 字符串转换成 JSONObject */
    public static JSONObject stringToJsonObject(String response){
        try {
            if (TextUtils.isEmpty(response)){
                return new JSONObject();
            }else {
                return new JSONObject(response);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    /**
     * 判断 timestamp 字段是否合法
     * @param response 响应数据包
     * @return true - 超时，false - 没超时
     */
    public static boolean verifyTimeStamp(JSONObject response){
        if (response == null) return false;
        String obj = response.toString();
        if (TextUtils.isEmpty(obj)) return false;
        // 响应时间
        String responseTimestamp = ResponseUtils.getString(response,ResponsePackage.timestamp);
        long responseTime = StringUtils.stringToLong(responseTimestamp);
        // 接收响应时的时间
        long currentTime = System.currentTimeMillis();

        if ((currentTime - responseTime) > RESPONSE_TIME_OUT){
            // 判断响应数据包的时间是否超时
            return true;
        }else {
            return false;
        }
    }

}
