package zhuozhuo.com.zhuo.util;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 *
 * 判断输入的内容是否合法
 */
public class ImportUtils {

    /** 手机号码 - 正则表达式 */
    private static final String PHONE = "^1[3,4,5,6,7,8]\\d{9}$";

    /** 邮箱地址 - 正则表达式 */
    private static final Pattern EMAIL = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");

    /** 身份证号码 - 正则表达式 */
    private static final String ID_CARD_NO = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";

    /** 银行卡号 - 正则表达式 */
    private static final String CARD_NO= "^\\d{16}|\\d{19}$";

    /** 六位验证码 - 正则表达式 */
    private static final String FOUR_CODE = "^\\d{6}$";
    /** 数字 - 正则表达式 */
    public static final String NUMBER = "^\\d+$";

    /** 字母 a-z A-Z - 正则表达式 */
    public static final String LETTER = "^[a-zA-Z]+$";

    /** 判断房号输入 - 正则表达式 */
    public static final String ROOM_NUMBER = "^[a-zA-Z0-9;]+$";

    /** 判断是否为手机号码 */
    public static boolean isPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return false;
        return phoneNumber.matches(ImportUtils.PHONE);
    }

    /** 判断是不是邮箱地址 */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return ImportUtils.EMAIL.matcher(email).matches();
    }

    /** 身份证号码 */
    public static boolean isIdCardNo(String idCardNo) {
        if (idCardNo == null) return false;
        return idCardNo.matches(ImportUtils.ID_CARD_NO);
    }

    /** 银行卡号 */
    public static boolean isBankCardNo(String cardNo){
        if (cardNo == null) return false;
        return cardNo.matches(ImportUtils.CARD_NO);
    }

    public static boolean textIsEmpty(String text){
        if (TextUtils.isEmpty(text) || "null".equals(text)){
            return true;
        }else {
            return false;
        }
    }

    /** 判断 传入的时间是否小于当前时间
     * @param date 输入的时间,格式：yyyy-MM-dd
     *             小于 - true，大于 - false
     * */
    public static boolean isLessThanCurrentDate(String date){
        try{
            SimpleDateFormat format = MainUtils.getDateFormat();
            Date inputDate = format.parse(date);
            Date currentDate = format.parse(MainUtils.getCurrentDate());
            // 判断输入的时间是否小于当前时间
            if (inputDate.getTime() < currentDate.getTime()){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
