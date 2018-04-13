package zhuozhuo.com.zhuo.util;

import android.support.annotation.StringRes;
import android.text.TextUtils;

import zhuozhuo.com.zhuo.MainApplication;

/**
 * author：kangjia
 * time:  2017/3/7
 */
public class StringUtils {

    /**
     * 将字符串转换成 int 类型 默认值返回 0
     */
    public static int stringToInt(String intString) {
        if (TextUtils.isEmpty(intString)) {
            return 0;
        } else {
            intString = intString.trim();
            if (intString.matches("^\\d+$")) {
                return Integer.parseInt(intString);
            } else {
                return 0;
            }
        }
    }

    /**
     * 将字符串转换成 int 类型 默认值返回 0
     */
    public static long stringToLong(String longString) {
        if (TextUtils.isEmpty(longString)) {
            return 0;
        } else {
            longString = longString.trim();
            if (longString.matches("\\d+")) {
                return Long.parseLong(longString);
            } else {
                return 0;
            }
        }
    }

    /**
     * 将字符串转换成 int 类型 默认值返回 0
     */
    public static double stringToDouble(String doubleString) {
        if (TextUtils.isEmpty(doubleString)) {
            return 0;
        } else {
            doubleString = doubleString.trim();
            if (doubleString.matches("\\d+") || doubleString.matches("\\d+\\.\\d+")) {
                return Double.parseDouble(doubleString);
            } else {
                return 0;
            }
        }
    }

    /**
     * 将字符串转换成 int 类型 默认值返回 0
     */
    public static float stringToFloat(String floatString) {
        if (TextUtils.isEmpty(floatString)) {
            return 0;
        } else {
            floatString = floatString.trim();
            if (floatString.matches("\\d+") || floatString.matches("\\d+\\.\\d+")) {
                return Float.parseFloat(floatString);
            } else {
                return 0;
            }
        }
    }

    /**
     * 在小于 10 的数字前面加 0
     */
    public static String getValue(int date) {
        return date < 10 ? "0" + date : String.valueOf(date);
    }

    public static String getString(@StringRes int stringResId) {
        return MainApplication.getInstance().getString(stringResId);
    }

    /**
     * 获取字符串，如果为空，返回 0
     */
    public static String getEmptyZero(String text) {
        if (TextUtils.isEmpty(text)) {
            return "0";
        } else {
            return text;
        }
    }
}
