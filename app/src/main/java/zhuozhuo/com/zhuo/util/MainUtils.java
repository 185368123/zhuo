package zhuozhuo.com.zhuo.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.ColorRes;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import zhuozhuo.com.zhuo.MainApplication;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.constants.Constant;
import zhuozhuo.com.zhuo.view.BaseTextWatcher;

/**
 * author：kang
 * time:  2017/3/7
 */
public class MainUtils {

    /**
     * 隐藏键盘
     */
    public static void concealKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) MainApplication.getInstance()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 显示键盘
     */
    public static void showKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) MainApplication.getInstance()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    /**
     * 查看当前线程的 名字 和 Id
     */
    public static void seeCurrentThread() {
        seeCurrentThread("MainUtils - ");
    }

    /**
     * 查看当前线程的 名字 和 Id
     */
    public static void seeCurrentThread(String tag) {
        Thread thread = Thread.currentThread();
        li.com.base.baseuntils.LogUtils.logd(tag + "seeCurrentThread - Name：" + thread.getName() + "  Id：" + thread.getId());
    }

    /**
     * 判断网络是否可用
     */
    public static boolean isNetWorkEnable() {
        ConnectivityManager manager = (ConnectivityManager) MainApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    /**
     * 获取文本
     */
    public static String getText(TextView textView) {
        if (textView != null) {
            return textView.getText().toString();
        } else {
            return "";
        }
    }

    /**
     * 获取文本,如果为空，返回 0
     */
    public static String getTextZero(TextView textView) {
        if (textView != null) {
            return textView.getText().toString();
        } else {
            return "0";
        }
    }

    public static void setText(TextView textView, String text) {
        if (!ImportUtils.textIsEmpty(text)) {
            textView.setText(text);
        } else {
            textView.setText("");
        }
    }

    public static void setText(EditText editText, String text) {
        if (!ImportUtils.textIsEmpty(text)) {
            editText.setText(text);
        } else {
            editText.setText("");
        }
    }

    /**
     * 将传进来的年，月，日，变成日期格式：yyyy-MM-dd
     */
    public static String getDate(int year, int month, int day) {
        return getDate("-", year, month, day);
    }

    /**
     * 将传进来的年，月，日，变成日期格式：yyyy-MM-dd
     *
     * @param decollator 分割符,日期的分割符 如 2017-04-20，“-” 就是分割符
     */
    public static String getDate(String decollator, int year, int month, int day) {
        String strYear = String.valueOf(year);
        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(strYear)) {
            if (strYear.length() < 4) {
                strYear = "20" + strYear;
            }
            builder.append(strYear).append(decollator)
                    .append(StringUtils.getValue(month)).append(decollator)
                    .append(StringUtils.getValue(day));
            return builder.toString();
        } else {
            return "";
        }
    }

    /**
     * 获取当前日期
     */
    public static String getCurrentDate() {
        return MainUtils.getDateFormat().format(new Date());
    }

    public static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    public static String getPointDate() {
        return new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(new Date());
    }

    public static SimpleDateFormat getTimeFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    public static String getTimeFormatStr() {
        return getTimeFormat().format(new Date());
    }

    public static String getDateFileName() {
        try {
            return new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置 EditText 只能输入两位小数
     */
    public static void setEditTextPoint(final EditText... editText) {
        for (EditText edit : editText) {
            setEditTextPoint(edit);
        }
    }

    /**
     * 设置 EditText 只能输入两位小数
     */
    public static void setEditTextPoint(final EditText editText) {
        if (editText == null) return;
        editText.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }
        });
    }


    /**
     * 获取文本
     */
    public static String getHint(TextView textView) {
        if (textView != null) {
            return textView.getHint().toString();
        } else {
            return "";
        }
    }

    /**
     * 保留两位小数 原封不动
     */
    public static String retainTwoDecimal(double doubleValue) {
        DecimalFormat formater = new DecimalFormat("0.00");
        formater.setMaximumFractionDigits(2);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(doubleValue);
    }

    /**
     * 安装 Apk 文件
     */
    public static void installApk(Activity activity, String apkPath) {
        File apkFile = new File(apkPath);
        // 如果文件不存在 取消安装
        if (!apkFile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.fromFile(apkFile);
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }

    /**
     * 获取当前时间的秒数
     */
    public static long getTimeSecond() {
        return (System.currentTimeMillis() / 1000);
    }


    /**
     * 调用系统短信界面 发送短信
     */
    public static void sendSMS(Activity activity, String phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            phoneNumber = phoneNumber.trim();
            // 判断是否是数字
            if (phoneNumber.matches("\\d+")) {// && phoneNumber.length() == GlobalConstant.PHONE_NUMBER_LENGTH
                Uri smsToUri = Uri.parse("smsto:" + phoneNumber);
                Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                //intent.putExtra("sms_body", smsBody);  // 短信内容
                activity.startActivity(intent);
            } else {
                ToastUtils.showToast(R.string.main_text22);
            }
        } else {
            ToastUtils.showToast(R.string.main_text22);
        }
    }

    public static void assertObjectNotNull(Object... o) {
        for (Object object : o) {
            assertObjectNotNull(object);
        }
    }

    public static void assertObjectNotNull(Object o) {
        if (o == null) {
            throw new NullPointerException("Object == null");
        }
    }

    /**
     * 将 TextView 显示当前日期，格式：yyyy-MM-dd
     */
    public static void setCurrentDate(TextView... textViews) {
        if (textViews == null) return;
        String currentDate = MainUtils.getCurrentDate();
        for (TextView textView : textViews) {
            textView.setText(currentDate);
        }
    }

    /**
     * 获取 ListView 分割线的 Drawable
     *
     * @return GradientDrawable
     */
    public static GradientDrawable getListDividerDrawable(@ColorRes int colorId) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);//形状
        //gd.setStroke(1, resources.getColor(R.color.txt_white));//描边
        gd.setColor(MainApplication.getInstance().getResources().getColor(colorId));//颜色
        //gd.setCornerRadius(DimensUtils.dp2px(10));//圆角
        return gd;
    }

    /**
     * 获取 ListView 分割线的 Drawable，
     * 分割线的颜色是界面的背景颜色
     */
    public static GradientDrawable getActBgListDivider() {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);//形状
        //gd.setStroke(1, resources.getColor(R.color.txt_white));//描边
        gd.setColor(Color.parseColor("#f3f3f3"));//颜色
        //gd.setCornerRadius(DimensUtils.dp2px(10));//圆角
        return gd;
    }

    /**
     * 将日期格式 yyyy-MM-dd HH:mm:ss 变成日期格式 yyyy-MM-dd
     *
     * @param date 日期，格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getDate(String date) {
        if (!TextUtils.isEmpty(date)) {
            if (date.length() > Constant.DATE_LENGTH) {
                date = date.substring(0, Constant.DATE_LENGTH);
                return date;
            } else {
                return date;
            }
        }
        return "";
    }

    public static void seeResponse(String method, String response) {
        li.com.base.baseuntils.LogUtils.logd(method + " - strResponse - " + response);
    }

    /**
     * 获取指定年后的时间
     *
     * @return 返回格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getAddYearDate(int year) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return df.format(calendar.getTime());
    }

    /**
     * 获取指定 小时 后的时间
     *
     * @return 返回格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getAddHourDate(int hour) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        return df.format(calendar.getTime());
    }

    /**
     * 获取指定月后的时间
     *
     * @return 返回格式 yyyy-MM-dd
     */
    public static String getAddMonthDate(int month) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return df.format(calendar.getTime());
    }

    /**
     * 生成 随机数
     *
     * @param count 位数
     */
    public static String getRandomNumber(int count) {
        if (count <= 0) return "";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(String.valueOf((int) (Math.random() * 10)));
        }
        return builder.toString();
    }

    /**
     * 生成随机字符串，不长于 32 位
     */
    public static String getRandomString() {
        try {
            String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
            if (TextUtils.isEmpty(uuid)) {
                return MainUtils.getRandomNumber(16);
            }
            if (uuid.length() > 32) {
                return uuid.substring(uuid.length() / 2);
            } else {
                return uuid;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MainUtils.getRandomNumber(16);
    }

    /**
     * 将ip的整数形式转换成ip形式
     */
    public static String intToIp(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * 获取 链接 流量网络时的 ip 地址
     */
    public static String getNetworkIPAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取 链接 Wifi 网络时的 ip 地址
     */
    public static String getWifiIPAddress() {
        try {
            //获取wifi服务
            WifiManager wifiManager = (WifiManager) MainApplication.getInstance().
                    getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if (wifiInfo != null) {
                    int ipAddress = wifiInfo.getIpAddress();
                    return intToIp(ipAddress);
                } else {
                    return "";
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取 手机 ip 地址
     */
    public static String getIPAddress() {
        String ipAddress = getWifiIPAddress();
        if (!"0.0.0.0".equals(ipAddress) && !TextUtils.isEmpty(ipAddress)) {
            // IpV4
            return ipAddress;
        } else {
            // IpV6
            return getNetworkIPAddress();
        }
    }

    public static boolean isEmpty(List<String> list) {
        if (list == null || list.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
