package com.hyphenate.chatuidemo.provider;

/**
 * 获取 PreferenceManager 对象中保存的用户信息
 */
public class UserInfoProvider {

    /**
     * 获取 UserId
     */
    public static String getUserID() {
        return PreferenceManager.getPreferenceManager().getUSER_ID();
    }

    public static void setUserId(String userId) {
        PreferenceManager.getPreferenceManager().setUSER_ID(userId);
    }
    /**
     * 获取 session_id
     */
    public static String getSessionId() {
        return PreferenceManager.getPreferenceManager().getSessionId();
    }

    public static void setSessionId(String SessionId) {
        PreferenceManager.getPreferenceManager().setSessionId(SessionId);
    }
    /**
     * 获取 昵称
     */
    public static String getNickName() {
        return PreferenceManager.getPreferenceManager().getNickName();
    }

    public static void setNickName(String nickName) {
        PreferenceManager.getPreferenceManager().setNickName(nickName);
    }
    /**
     * 获取Token
     */
    public static String getToken() {
        return PreferenceManager.getPreferenceManager().getToken();
    }

    public static void setToken(String token) {
        PreferenceManager.getPreferenceManager().setToken(token);
    }
    /**
     * 获取 账号
     */
    public static String getAccount() {
        return PreferenceManager.getPreferenceManager().getAccount();
    }

    public static void setAccount(String account) {
        PreferenceManager.getPreferenceManager().setAccount(account);
    }
    /**
     * 获取 用户头像连接
     */
    public static String getPhotoLink() {
        return PreferenceManager.getPreferenceManager().getPhotoLink();
    }

    public static void setPhotoLink(String photoLink) {
        PreferenceManager.getPreferenceManager().setPhotoLink(photoLink);
    }
    /**
     * 获取 用户性别
     */
    public static String getSex() {
        return PreferenceManager.getPreferenceManager().getSex();
    }

    public static void setSex(String sex) {
        PreferenceManager.getPreferenceManager().setSex(sex);
    }

    /**
     * 用户手机
     */
    public static String getPhone() {
        return PreferenceManager.getPreferenceManager().getPhone();
    }

    public static void setPhone(String Phone) {
        PreferenceManager.getPreferenceManager().setPhone(Phone);
    }
    /**
     * 用户注册方式
     */
    public static String getRegMode() {
        return PreferenceManager.getPreferenceManager().getRegMode();
    }

    public static void setRegMode(String regMode) {
        PreferenceManager.getPreferenceManager().setRegMode(regMode);
    }
    /**
     * 获取用户经验值
     */
    public static String getExp() {
        return PreferenceManager.getPreferenceManager().getExp();
    }

    public static void setExp(String Exp) {
        PreferenceManager.getPreferenceManager().setExp(Exp);
    }



    /**
     * 获取匹配条件
     */
    public static int getMatchCondition() {
        return PreferenceManager.getPreferenceManager().getMatchCondition();
    }

    public static void setMatchCondition(int id) {
        PreferenceManager.getPreferenceManager().setMatchCondition(id);
    }
    /**
     * 获取用户爱好
     */
    public static String getHobby() {
        return PreferenceManager.getPreferenceManager().getHobby();
    }

    public static void setHobby(String hobby) {
        PreferenceManager.getPreferenceManager().setHobby(hobby);
    }
    /**
     * 获取用户所在学院
     */
    public static String getLocation() {
        return PreferenceManager.getPreferenceManager().getLocation();
    }

    public static void setLocation(String location) {
        PreferenceManager.getPreferenceManager().setLocation(location);
    }


    public static String getStatus() {
        return PreferenceManager.getPreferenceManager().getMatchStatus();
    }

    public static void setStatus(String id) {
        PreferenceManager.getPreferenceManager().setMatchStatus(id);
    }


    public static String getUnreadShare() {
        return PreferenceManager.getPreferenceManager().getUnreadShare();
    }

    public static void setUnreadShare(String id) {
        PreferenceManager.getPreferenceManager().setUnreadShare(id);
    }
    public static String getFinish() {
        return PreferenceManager.getPreferenceManager().getFinish();
    }

    public static void setFinish(String id) {
        PreferenceManager.getPreferenceManager().setFinish(id);
    }
    public static String getTime() {
        return PreferenceManager.getPreferenceManager().getTime();
    }

    public static void setTime(String id) {
        PreferenceManager.getPreferenceManager().setTime(id);
    }


    public static boolean getHelloFlag() {
        return PreferenceManager.getPreferenceManager().getHelloFlag();
    }

    public static void setHelloFlag(boolean id) {
        PreferenceManager.getPreferenceManager().setHelloFlag(id);
    }


}
