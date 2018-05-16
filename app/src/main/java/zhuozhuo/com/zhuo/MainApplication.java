package zhuozhuo.com.zhuo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import com.hyphenate.chatuidemo.DemoApplication;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/10/16.
 */

public class MainApplication extends DemoApplication {

    /**
     * 存放 Activity 的集合
     */
    private ArrayList<Activity> listActivity = new ArrayList<>();


    private static MainApplication application;

    public static Context applicationContext;

    private static final String rsa="MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCc5re0mnAdt6pkSjLGeIqouXmYq1fBRuzyHtJtoM/bFDnZpRSyY4n0HtHzjf4/jRiBt+VwHM2vYKMw9dxdrNFZvpWCK12N6mWOoL3AS+rLfLLJF0zO+yW/oW+svebWQXKJXv7Fg7Cd/v0aTvzakraydvgwmBknZg3veas0HBwCw6pwSwzKhcnYlkmb9PWaufe0MC3lMqHNVStHeHU2eGOvrEah03K0KXl1v7BxceBynNnOGIkOJ8O27uUm1yvgMkC/5bbmrlSPORtVTBDIYucRgTcNKqjK99BEfZ60R+lng4WHeUU5tkaFBVK7/x/XyVYpTi9MTHgSjwR2p6dKwDMlAgMBAAECggEAA3Y7u2pk6uLwiYHxmNt0A8ED49B0ATm1ZKo1bmHL9EN/+0NfL4hutMoxS7RoVbKhRctu7ohUKXhyYVQrZo23mMlRtyuJxuXTE9CMH0ANkCvSLgmrki2MaawAMMmH3FZZVpqX+HetX+8OWpQ/k/sEuO9TDo9sjqf7yrreginUnGL3+aExaWA29nEqK7vcE3StYgN4XsEuccforL8qwvGNQc3ZO0WFe23OlWe9WEiabzGt6a/LfvAWPZJ5lfDJErAu58bTgzf1Xgf43mttsIXOAMHZiL+YZcmzQEYWfYq8sJlgJvAb06EM1FMfJOrefBky+OcuLay3QfbTBB6KEIUHSQKBgQDJiUWpmUoMGnEB8zscrck6QvHc8B8MdvT49NVmxkMmU5Y6wlPRvuGE9JtLYobYa+yQ6xBjav4ZfZ0LwFeDOyyGrHEdNUE8wCN71vVuhyV9usZ0DlKhOxVo6W0WtQm65cKd1w2idMq/hQmQ5gj6dbj0gpFCrlHXNyZitZiOaqhTmwKBgQDHTX9cXTSZgqM5VjhJpO3CRvT0JgTakRcs5UEQMGsGPLs370yB/CJrnWlOv0Jcr+s4W3iJARFg/Vp/CU9TDOZJIaqL4EIlRAxB8myRRIQibFrBHoLY7sK052W6sjCvgnM6fQhbpUBs6J9vrqgACImkM0fi59LOabwFcA2l90TgPwKBgQCBqe0E+hfXA6gTfh9ZC0K9VlbwNBl11o80IamvDv4jCYJNWbu95daIySuk68YgNgelEWeg3zSOx++joONtDkROfWHlTd8kK+ZohduwegkYBHQsNQ7apv3WWuCRzHZvJeX50WP3UrcjU3/dFvNN2THfNqYoxajoE5aByZ5Weq7D4wKBgQCn1jWI/NzM4dy+7kLckc2/q62g5Vt1DHZt0momTf/y8rXe9uCBHmmGIruKGzqxf1wgy7cH+SQ/f1v0W0y37ccIkxdiT+qkI1gHOTYAZ6i2rHUI6L7cHMVPPwbGYETrqHmNKboeI13L2WJa1MSZspbm9mB1TzCRry0tjx2PRFp2VQKBgQCYUA6TspPiGtR2/5Qm6PNCnLITXV+vs5uNrA8hGBth+guwT/goY9H+PWn6M0LcYjsj0qpe3lobfTs1n+9cHzG4Ie3jIQSr67N0o3C9bIeqQZ2HgqQOHyi0sR6HiOMerU1V21yz2ACFmnrGRgZXk1KvpMTfsSPcQcK2PvL2PlBMCg==";

    @Override
    public void onCreate() {
        super.onCreate();
        //initHotfix();
        this.initInstance();
        initUCrash();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    private void initUCrash() {
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:友盟 app key
         * 参数3:友盟 channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         */
        UMConfigure.init(this,"59e9c302734be4474800016a","unknown", UMConfigure.DEVICE_TYPE_PHONE, "");
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true);
        MobclickAgent.setDebugMode(true);
        MobclickAgent.setCatchUncaughtExceptions(true); //是否需要错误统计功能
    }


    private void initInstance() {
        application = this;
        applicationContext = this;
    }


    public static MainApplication getInstance() {
        return application;
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    /**
     * 添加界面到 Activity 集合中
     */
    public void addActivity(Activity activity) {
        if (activity == null) return;
        if (!listActivity.contains(activity)) {
            listActivity.add(activity);
        }
    }

    /**
     * 关闭集合中的所有 Activity
     */
    public void finishAllActivity() {
        for (Activity activity : listActivity) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 清空集合中的 Activity
     */
    public void clearActivity() {
        if (listActivity != null) {
            listActivity.clear();
        }
    }

    public String getVison(){
        try {
            return this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "1.0.0";
        }
    }

    {
        PlatformConfig.setWeixin("wxbe8df2ef27d991e9","9a6673f57176eaa3da518db6e6234cda");
    }
}
