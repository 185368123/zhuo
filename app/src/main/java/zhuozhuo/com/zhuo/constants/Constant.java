package zhuozhuo.com.zhuo.constants;

/**
 * 常量
 */
public interface Constant {


    /**
     * 服务器返回响应数据成功
     */
    String RESPONSE_SUCCEED = "0";
    /**
     * 服务器返回响应数据成功,而且要求回调
     */
    String RESPONSE_SUCCEED_ = "8888";

    /**
     * 服务器返回响应数据成功,但TOKEN过期
     */
    String RESPONSE_MEGUNUSE = "1001";

    /**
     * 密码的最小长度
     */
    int PASSWORD_MIN_LENGTH = 6;


    /**
     * 日期长度
     */
    int DATE_LENGTH = 10;

    /**
     * 单人匹配
     */
    String single="单人匹配";


    String FRAGMENT_NAME="name";

    String FRAGMENT_TITLE="title";

    String FRAGMENT_IMAGE="image";

    String FRAGMENT_BEAN="bean";

    String MATCH_BEGIN="begin_match";

    String MATCH_STOP="stop_match";

    String MATCH_BEGIN_="begin_match_";

    String MATCH_STOP_="stop_match_";

    String MATCH_BEGIN_1="match_begin_1";


}
