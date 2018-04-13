package com.hyphenate.chatuidemo.my.okhttp;

/**
 * author：kang
 * time:  2017/3/20
 *
 * 数据包接口，包括 请求数据包，和 响应数据包
 */
public interface DataPackage {

    // ============ 数据包字段 ============
    /** 时间毫秒值 */
    String timestamp = "timestamp";

    /** 用户令牌 */
    String userToken = "userToken";

    /** 时间毫秒值 */
    String getTimestamp();
    void setTimestamp(String timestamp);
}
