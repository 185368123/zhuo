package zhuozhuo.com.zhuo.constants;

/**
 *
 * 响应数据包接口
 */
public interface ResponsePackage extends DataPackage{

    /** 错误码 */
    String errcode = "error_code";
    /** 错误信息 */
    String errmsg = "msg";

    /** 错误码 */
    String getErrcode();
    void setErrcode(String errCode);

    String getErrmsg();
    void setErrmsg(String errMsg);
}
