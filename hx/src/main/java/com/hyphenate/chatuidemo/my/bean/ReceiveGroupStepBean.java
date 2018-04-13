package com.hyphenate.chatuidemo.my.bean;

/**
 * Created by Administrator on 2017/12/11.
 */

public class ReceiveGroupStepBean {

    /**
     * method : groupstep
     * error_code : 0
     * group_name : 篮球赛
     * group_id : 37453135151105
     * group_match_status : 2
     * choice_name : 篮球赛
     * choice_desc : 约好时间地点，然后商量好筹码金额就可以开打啦！
     * choice_number_name : 饮料
     * choice_money : 0
     * money : 1
     */

    private String method;
    private int error_code;
    private String group_name;
    private String group_id;
    private String group_match_status;
    private String choice_name;
    private String choice_desc;
    private String choice_number_name;
    private String choice_money;
    private int money;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_match_status() {
        if (group_match_status==null){
            return "0";
        }
        return group_match_status;
    }

    public void setGroup_match_status(String group_match_status) {
        this.group_match_status = group_match_status;
    }

    public String getChoice_name() {
        return choice_name;
    }

    public void setChoice_name(String choice_name) {
        this.choice_name = choice_name;
    }

    public String getChoice_desc() {
        return choice_desc;
    }

    public void setChoice_desc(String choice_desc) {
        this.choice_desc = choice_desc;
    }

    public String getChoice_number_name() {
        return choice_number_name;
    }

    public void setChoice_number_name(String choice_number_name) {
        this.choice_number_name = choice_number_name;
    }

    public String getChoice_money() {
        return choice_money;
    }

    public void setChoice_money(String choice_money) {
        this.choice_money = choice_money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
