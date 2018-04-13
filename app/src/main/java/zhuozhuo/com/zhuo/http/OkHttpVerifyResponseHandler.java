package zhuozhuo.com.zhuo.http;

import org.json.JSONObject;

import li.com.base.baseuntils.LogUtils;
import okhttp3.Call;
import zhuozhuo.com.zhuo.constants.Constant;
import zhuozhuo.com.zhuo.util.ResponseUtils;
import zhuozhuo.com.zhuo.util.ToastUtils;
import zhuozhuo.com.zhuo.view.IDialogView;

/**
 * <p>
 * OkHttpVerifyResponse 接口的基类。对请求成功，或请求失败的包装。(装饰设计模式)
 * 请求回调，检查返回的响应数据包是否是合法的响应数据
 * 主要检查 packmd5，timestamp 字段的合法性
 */
public abstract class OkHttpVerifyResponseHandler implements OkHttpVerifyResponse, OkHttpResponseHandler {

    /**
     * 返回的 errcode 有错误时，是否提示 Toast
     */
    private boolean isShowToast = true;

    private IDialogView iDialogView;

    /**
     * 请求回调
     */
    private OkHttpVerifyResponse verifyResponse;

    public OkHttpVerifyResponseHandler() {
    }

    public OkHttpVerifyResponseHandler(IDialogView iDialogView) {
        this.iDialogView = iDialogView;
    }

    public OkHttpVerifyResponseHandler(boolean isShowToast) {
        this(isShowToast, null);
    }

    public OkHttpVerifyResponseHandler(OkHttpVerifyResponse verifyResponse) {
        this(true, verifyResponse);
    }

    public OkHttpVerifyResponseHandler(boolean isShowToast, OkHttpVerifyResponse verifyResponse) {
        this.isShowToast = isShowToast;
        this.verifyResponse = verifyResponse;
    }

    /**
     * 请求成功 -- 此方法在 UI 线程中调用 (请求成功包装)
     *
     * @param response 响应数据包
     * @param id       请求 id
     */
    @Override
    public void onSucceed(String response, int id) {
       JSONObject obj = ResponseUtils.stringToJsonObject(response);
        String errCode = ResponseUtils.getErrCode(obj); // 错误码
        String errMsg = ResponseUtils.getErrMsg(obj); // 错误信息
        closeWaitDialog(); // 关闭对话框
        if (Constant.RESPONSE_SUCCEED.equals(errCode)||Constant.RESPONSE_SUCCEED_.equals(errCode)) { // 请求成功
            // 执行 onVerifySucceed 方法
            LogUtils.logd("返回数据:"+response);
            executeOnVerifySucceed(response, obj, null, id);

        }
        else {
            // 执行 onErrorCode 方法
            executeOnErrorCode(response, obj, errMsg, errCode, id);
        }
    }
    /**
     * 执行 onVerifySucceed 方法
     */
    protected void executeOnVerifySucceed(String response, JSONObject obj, String errMsg, int id) {
        if (verifyResponse != null) {
            verifyResponse.onVerifySucceed(response, obj, errMsg, id);
        } else {
            // 请求成功，并且返回的响应数据包 合法 ，
            // 并且返回的 errcode 是成功状态 时调用 -- 此方法在 UI 线程中调用
            // 调用 OkHttpVerifyResponse 接口中的方法
            onVerifySucceed(response, obj, errMsg, id);
        }
    }

    /**
     * 执行 onVerifyFailure 方法
     */
    private void executeOnVerifyFailure(String response, JSONObject obj, String errMsg, int id) {
        if (verifyResponse != null) {
            verifyResponse.onVerifyFailure(response, obj, errMsg, id);
        } else {
            // 请求成功，并且返回的响应数据包 不合法 时调用 -- 此方法在 UI 线程中调用
            onVerifyFailure(response, obj, errMsg, id);
        }
    }

    /**
     * 执行 onErrorCode 方法
     */
    protected void executeOnErrorCode(String response, JSONObject obj, String errMsg, String errCode, int id) {
        LogUtils.logd("OkHttpVerifyResponseHandler - executeOnErrorCode - response：" + response);
        if (isShowToast) {
            ToastUtils.showToast(errMsg);
        }
        if (verifyResponse != null) {
            verifyResponse.onErrorCode(response, obj, errMsg, errCode, id);
        } else {
            // 请求成功，并且返回的 errCode 错误 时调用(errcode 不是 000 )
            onErrorCode(response, obj, errMsg, errCode, id);
        }
    }

    /**
     * 请求成功，并且返回的响应数据包 不合法 时调用 -- 此方法在 UI 线程中调用（子类复写）
     *
     * @param strResponse  响应数据包，字符串格式
     * @param jsonResponse 响应数据包，Json 格式
     * @param errMsg       错误信息
     * @param id           请求 id
     *                     该响应数据包不合法，不需要进行解析
     */
    @Override
    public void onVerifyFailure(String strResponse, JSONObject jsonResponse, String errMsg, int id) {
        if (isShowToast) {
            ToastUtils.showResponseError();
        }
    }

    /**
     * 请求成功，并且返回的 errCode 错误 时调用(errcode 不是 000 ) -- 此方法在 UI 线程中调用（子类复写）
     *
     * @param strResponse  响应数据包，字符串格式
     * @param jsonResponse 响应数据包，Json 格式
     * @param errMsg       错误信息
     * @param id           请求 id
     *                     该响应数据包的 errCode 错误，不需要进行解析
     */
    @Override
    public void onErrorCode(String strResponse, JSONObject jsonResponse, String errMsg, String errCode, int id) {

    }

    /**
     * 请求失败 -- 此方法在 UI 线程中调用(请求失败包装)（子类复写）
     *
     * @param call 请求包
     * @param e    异常信息
     * @param id   请求 id
     */
    public void onFailure(Call call, Exception e, int id) {
        LogUtils.logd("OkHttpVerifyResponseHandler - onFailure - Exception：" + e.getMessage());
        e.printStackTrace();
        closeWaitDialog(); // 关闭对话框
        if (isShowToast) {
           // ToastUtils.showTheNetworkIsBusy();
        }
    }

    public boolean isShowToast() {
        return isShowToast;
    }

    public void setShowToast(boolean isShowToast) {
        this.isShowToast = isShowToast;
    }

    /**
     * 关闭对话框
     */
    protected void closeWaitDialog() {
        if (iDialogView != null) {
            iDialogView.closeWaitDialog();
        }
    }
}
