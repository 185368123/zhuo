package li.com.base.baserx;

import java.io.Serializable;

/**
 * des:封装服务器返回数据
 * Created by xsf
 * on 2016.09.9:47
 */
public class BaseRespose<T> implements Serializable {

    public String error_code;
    public String msg;

    public T data;

    public boolean success() {
        return "0".equals(error_code);
    }

    public boolean remind() {
        return "9999".equals(error_code);
    }

    public boolean relogin() {
        return "1000".equals(error_code) || "1001".equals(error_code);
    }

    public boolean recall() {
        return "8888".equals(error_code);
    }

    public void setCode(String error_code) {
        this.error_code = error_code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return error_code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "BaseRespose{" +
                "code='" + error_code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
