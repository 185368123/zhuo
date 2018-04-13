package zhuozhuo.com.zhuo.constants;

/**
 * author：kang
 * time:  2017/3/20
 *
 * 请求数据包
 */
public interface RequestPackage extends DataPackage{

    // ============ 请求包字段 ============
    /** App 类型 */
    String apptype = "apptype";
    /** 项目版本号 */
    String ver = "ver";
    /** 用户唯一 Id */
    String userid = "userID";
    /** 用户名 */
    String username = "userName";

    /** json 数组数据 */
    String data = "data";

    String getApptype();
    void setApptype(String appType);

    /** 项目版本号 */
    String getVer();
    void setVer(String ver);
}
