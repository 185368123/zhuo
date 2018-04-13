package zhuozhuo.com.zhuo.util;

import android.text.TextUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;
import java.nio.charset.Charset;
import java.util.Map;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import zhuozhuo.com.zhuo.http.OkHttpResponseHandler;


/**
 * 请求包 工具类
 */
public class RequestUtils {

    /**
     * 获取请求参数
     * 格式：key=value&key=value
     */
    public static String getStrParameter(Map<String, String> map) {
        StringBuilder parameter = getParameter(map);
        if (!TextUtils.isEmpty(parameter)) {
            return parameter.toString();
        } else {
            return "";
        }
    }

    /**
     * 获取请求参数
     * 格式：key=value&key=value
     */
    public static StringBuilder getParameter(Map<String, String> map) {
        return getParameter(map, true, false);
    }

    /**
     * 获取请求参数
     * 格式：key=value&key=value
     *
     * @param emptyValueIsAppend 如果 Value 为空(null)，是否拼接空字符串
     * @param lastIsAdd          拼接最后一个 Value 时，是否拼接 "&" 符号
     *                           true - 拼接，false - 不拼接
     */
    public static StringBuilder getParameter(Map<String, String> map, boolean emptyValueIsAppend,
                                             boolean lastIsAdd) {
        if (map == null) return null;
        StringBuilder builder = new StringBuilder();
        int count = 0;
        for (String key : map.keySet()) {
            String value = map.get(key);
            ++count;
            if (TextUtils.isEmpty(value)) {
                if (emptyValueIsAppend) {
                    if ((count == map.size()) && !lastIsAdd) {
                        // 值为空，传空字符串
                        builder.append(key).append("=").append("");
                    } else {
                        // 值为空，传空字符串
                        builder.append(key).append("=").append("").append("&");
                    }
                }
            } else {
                if ((count == map.size()) && !lastIsAdd) {
                    // 值为空，传空字符串
                    builder.append(key).append("=").append(value);
                } else {
                    builder.append(key).append("=").append(value).append("&");
                }
            }
        }
        return builder;
    }

    /**
     * 发送 Post 请求
     *
     * @param requestCall     发送请求对象
     * @param responseHandler 请求回调
     */
    public static void sendPostRequest(RequestCall requestCall, final OkHttpResponseHandler responseHandler) {
        if (requestCall == null || responseHandler == null) return;
        requestCall.execute(new StringCallback() {
            @Override
            public void onResponse(String response, int id) {
                // 请求发送成功
                responseHandler.onSucceed(response, id);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                // 请求失败
                responseHandler.onFailure(call, e, id);
            }
        });
    }

    /**
     * 设置字符编码
     */
    public static void setCharset(Request request) {
        if (request != null) {
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    li.com.base.baseuntils.LogUtils.logd("setCharset - 设置字符编码 - utf-8 - MediaType：" + mediaType.charset());
                    // 设置字符编码 - utf-8
                    mediaType.charset(Charset.forName("UTF-8"));
                    li.com.base.baseuntils.LogUtils.logd("setCharset - 设置字符编码 - utf-8 - MediaType：" + mediaType.charset());
                }
            }
        }
    }

}
