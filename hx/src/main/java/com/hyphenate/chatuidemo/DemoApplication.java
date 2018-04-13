
package com.hyphenate.chatuidemo;

import li.com.base.baseapp.BaseApplication;

public class DemoApplication extends BaseApplication {


	private static DemoApplication instance;
	// login user name
	public final String PREF_USERNAME = "username";
	/**
	 * nickname for current user, the nickname instead of ID be shown when user receive notification from APNs
	 */
	public static String currentUserNick = "";

	@Override
	public void onCreate() {
		super.onCreate();
        instance = this;
	}

	public static DemoApplication getInstance() {
		return instance;
	}

}
