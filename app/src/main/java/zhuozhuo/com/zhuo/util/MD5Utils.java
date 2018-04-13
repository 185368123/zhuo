package zhuozhuo.com.zhuo.util;

import android.text.TextUtils;

import java.security.MessageDigest;

public class MD5Utils {

    private static final String MD5_END = "zhuoZhuoKeJi*";

    public static String getMd5(String inStr) {
        try {
            if (TextUtils.isEmpty(inStr)) {
                return "";
            }
            char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
            byte[] btInput = inStr.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getAddmd5(String string) {
        if (string == null) return "";
        // 值连接加密
        StringBuilder builder = new StringBuilder();
        builder.append(string);
        builder.append(MD5_END);
        return MD5Utils.getMd5(builder.toString());
    }

}
