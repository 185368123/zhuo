package li.com.base.baseapp;


import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDex;

import org.litepal.LitePalApplication;

/**
 * APPLICATION
 */
public class BaseApplication extends LitePalApplication {

    public static Context applicationContext;
    public static BaseApplication baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        applicationContext = this;
    }

    public static Context getAppContext() {
        return baseApplication;
    }

    public static Resources getAppResources() {
        return baseApplication.getResources();
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 分包
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
