package com.hyphenate.easeui;

import android.app.Application;

/**
 * Created by Administrator on 2017/10/31.
 */

public class MainApplication extends Application {

    public static MainApplication instance;

    @Override
    public void onCreate() {
        //MultiDex.install(this);
        super.onCreate();
// ============== fabric start
//		Fabric.with(this, new Crashlytics());
// ============== fabric end
        instance = this;

    }


    public static MainApplication getInstance() {
        return instance;
    }
}
