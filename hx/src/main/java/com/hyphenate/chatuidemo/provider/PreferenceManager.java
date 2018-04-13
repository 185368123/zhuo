package com.hyphenate.chatuidemo.provider;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.hyphenate.chatuidemo.DemoApplication;



/**
 * 保存用户信息
 */
public class PreferenceManager {

    public static final String PREFERENCE_NAME = "Application";

    private static PreferenceManager preferenceManager;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    // ========================= 启动界面 =========================
    /**
     * 启动界面 - 是否第一次启动程序
     */
    private static final String IS_FIRST_START = "is_first_start";

    // ========================= 用户信息 =========================
    /**
     * 用户的实际ID
     */
    private static final String USER_ID="user_id";
    /**
     * 服务器返回session_id
     */
    private static final String SESSION_ID="session_id";
    /**
     * 用户令牌
     */
    private static final String TOKEN="token";

    /**
     * 用户账号
     */
    private static final String ACCOUNT="account";
    /**
     * 用户昵称
     */
    private static final String NICK_NAME="nick_name";
    /**
     * 用户头像连接
     */
    private static final String PHOTO_LINK="photo_link";
    /**
     * 用户性别，男为1，女为2
     */
    private static final String SEX="sex";
    /**
     * 用户手机
     */
    private static final String PHOTO="phone";
    /**
     * 指用户是用哪种方式注册，微信注册为0，手机注册为1
     */
    private static final String REG_MODE="reg_mode";
    /**
     *
     */
    private static final String WACHAT_ID="wechat_id";
    /**
     * 目前用户所积累的经验值
     */
    private static final String EXP="exp";
    /**
     * 用户所在校区
     */
    private static final String Location="location";
    /**
     * 用户的喜好
     */
    private static final String Hobby="hobby";

    /**
     * 用户的状态,无匹配为0，等待中为1，已匹配为2
     */
    private static final String Status="match_status";

    /**
     * 论坛未读数量
     */
    private static final String UnreadShare="unread_share_count";

    /**
     * 组队评价未读数量
     */
    private static final String UnreadGroup="unread_group_count";

    /**
     * 0为未结束，1为已结束
     */
    private static final String Finish="finish";

    /**
     * 匹配时间戳
     */
    private static final String Time="time";
    /**
     * 是否发送Hello
     */
    private static final String HelloFlag="helloflag";

    /**
     * 匹配条件：1男或2女或0不限
     */
    private static final String MatchCondition="matchcondition";




    // ============================================================
    private PreferenceManager() {
    }

    public static PreferenceManager getPreferenceManager() {
        if (preferenceManager == null) {
            synchronized (PreferenceManager.class) {
                if (preferenceManager == null) {
                    preferenceManager = new PreferenceManager();
                    sharedPreferences = DemoApplication.getInstance().getSharedPreferences(
                            PreferenceManager.PREFERENCE_NAME, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                }
            }
        }
        return preferenceManager;
    }

    /**
     * 判断程序是否没有存储用户信息
     */
    public boolean isFirstStart() {
        return sharedPreferences.getBoolean(PreferenceManager.IS_FIRST_START, true);
    }

    /**
     * 设置程序清空存储用的户信息
     */
    public void setIsFirstStart(boolean isFirstStart) {
        editor.putBoolean(PreferenceManager.IS_FIRST_START, isFirstStart);
        editor.commit();
    }

    /**
     * 从 SharedPreferences 对象中获取用户的实际ID
     */
    public String getUSER_ID() {
        return sharedPreferences.getString(PreferenceManager.USER_ID, "");
    }

    /**
     * 将用户的实际ID保存到 SharedPreferences 对象中
     */
    public void setUSER_ID(String userid) {
        editor.putString(PreferenceManager.USER_ID, userid);
        editor.commit();
    }
    /**
     * 从 SharedPreferences 对象中获取服务器返回session_id
     */

    public String getSessionId() {
        return sharedPreferences.getString(PreferenceManager.SESSION_ID, "");
    }
    /**
     * 将服务器返回session_id保存到 SharedPreferences 对象中
     */
    public void setSessionId(String sessionId) {
        editor.putString(PreferenceManager.SESSION_ID, sessionId);
        editor.commit();
    }


    /**
     * 保存昵称
     */
    public void setNickName(String nickname) {
        editor.putString(PreferenceManager.NICK_NAME, nickname);
        editor.commit();
    }

    /**
     * 获取昵称
     */
    public String getNickName() {
        return sharedPreferences.getString(PreferenceManager.NICK_NAME, "");
    }


    /**
     * 保存用户令牌
     */
    public void setToken(String userToken) {
        if (TextUtils.isEmpty(userToken)) {
            return;
        }
        editor.putString(PreferenceManager.TOKEN, userToken);
        editor.commit();
    }

    /**
     * 获取用户令牌
     */
    public String getToken() {
        return sharedPreferences.getString(PreferenceManager.TOKEN, "");
    }

    /**
     * 账号
     */
    public String getAccount() {
        return sharedPreferences.getString(PreferenceManager.ACCOUNT, "");
    }

    public void setAccount(String account) {
        if (TextUtils.isEmpty(account)) return;
        editor.putString(PreferenceManager.ACCOUNT, account);
        editor.commit();
    }


    /**
     * 用户头像连接
     */
    public String getPhotoLink() {
        return sharedPreferences.getString(PreferenceManager.PHOTO_LINK, "");
    }

    public void setPhotoLink(String photoLink) {
        if (TextUtils.isEmpty(photoLink)) return;
        editor.putString(PreferenceManager.PHOTO_LINK, photoLink);
        editor.commit();
    }

    /**
     * 用户性别
     */
    public String getSex() {
        return sharedPreferences.getString(PreferenceManager.SEX, "");
    }

    public void setSex(String sex) {
        if (TextUtils.isEmpty(sex)) return;
        editor.putString(PreferenceManager.SEX, sex);
        editor.commit();
    }

    /**
     * 用户手机
     */
    public String getPhone() {
        return sharedPreferences.getString(PreferenceManager.PHOTO, "");
    }

    public void setPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            editor.putString(PreferenceManager.PHOTO, "未绑定");
        }else {
            editor.putString(PreferenceManager.PHOTO, phone);
        }
        editor.commit();
    }

    /**
     * 用户注册方式
     */
    public String getRegMode() {
        return sharedPreferences.getString(PreferenceManager.REG_MODE, "");
    }

    public void setRegMode(String regMode) {
        if (TextUtils.isEmpty(regMode)) return;
        editor.putString(PreferenceManager.REG_MODE, regMode);
        editor.commit();
    }

    /**
     * 用户爱好
     */
    public String getHobby() {
        return sharedPreferences.getString(PreferenceManager.Hobby, "");
    }

    public void setHobby(String hobby) {
        if (TextUtils.isEmpty(hobby)) {
            editor.putString(PreferenceManager.Hobby, "未设置");
        }else {
            editor.putString(PreferenceManager.Hobby, hobby);
        }
        editor.commit();
    }
    /**
     * 用户所学院
     */
    public String getLocation() {
        return sharedPreferences.getString(PreferenceManager.Location, "");
    }

    public void setLocation(String location) {
        if (TextUtils.isEmpty(location)) {
            editor.putString(PreferenceManager.Location, "未设置");
        }else {
            editor.putString(PreferenceManager.Location, location);
        }
        editor.commit();
    }
    /**
     * 用户经验值
     */
    public String getExp() {
        return sharedPreferences.getString(PreferenceManager.EXP, "");
    }

    public void setExp(String exp) {
        if (TextUtils.isEmpty(exp)) return;
        editor.putString(PreferenceManager.EXP, exp);
        editor.commit();
    }


    /**
     * 匹配用户的ID
     */
    public String getMatchStatus() {
        return sharedPreferences.getString(PreferenceManager.Status, "");
    }

    public void setMatchStatus(String status) {
        if (TextUtils.isEmpty(status)) return;
        editor.putString(PreferenceManager.Status, status);
        editor.commit();
    }

    /**
     * 论坛未读数量
     */
    public String getUnreadShare() {
        return sharedPreferences.getString(PreferenceManager.UnreadShare, "");
    }

    public void setUnreadShare(String id) {
        if (TextUtils.isEmpty(id)) return;
        editor.putString(PreferenceManager.UnreadShare, id);
        editor.commit();
    }
    /**
     * 组队评价未读数量
     */
    public String getUnreadGroup() {
        return sharedPreferences.getString(PreferenceManager.UnreadGroup, "");
    }

    public void setUnreadGroup(String id) {
        if (TextUtils.isEmpty(id)) return;
        editor.putString(PreferenceManager.UnreadGroup, id);
        editor.commit();
    }
    /**
     * 0为未结束，1为已结束
     */
    public String getFinish() {
        return sharedPreferences.getString(PreferenceManager.Finish, "");
    }

    public void setFinish(String id) {
        if (TextUtils.isEmpty(id)) return;
        editor.putString(PreferenceManager.Finish, id);
        editor.commit();
    }
    /**
     * 匹配时间戳
     */
    public String getTime() {
        return sharedPreferences.getString(PreferenceManager.Time, "");
    }

    public void setTime(String id) {
        if (TextUtils.isEmpty(id)) return;
        editor.putString(PreferenceManager.Time, id);
        editor.commit();
    }
    /**
     * 匹配性别优先条件
     */
    public int getMatchCondition() {
        return sharedPreferences.getInt(PreferenceManager.MatchCondition, 0);
    }

    public void setMatchCondition(int id) {
        editor.putInt(PreferenceManager.MatchCondition, id);
        editor.commit();
    }


    public boolean getHelloFlag() {
        return sharedPreferences.getBoolean(PreferenceManager.HelloFlag,false);
    }

    public void setHelloFlag(boolean helloFlag) {
        editor.putBoolean(PreferenceManager.HelloFlag,helloFlag);
        editor.commit();
    }


}

