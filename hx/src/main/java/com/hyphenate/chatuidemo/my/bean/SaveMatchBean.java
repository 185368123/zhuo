package com.hyphenate.chatuidemo.my.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/29.
 */

public class SaveMatchBean {

    /**
     * you_user_id : 3
     * nick_name : 花落、莫相離
     * photo_link : http://wx.qlogo.cn/mmopen/vi_32/tsw3h9icPSV6heLgiaYTd6WTpwHicf743D5vbDqia67fye0mQAbpbeC3KxmCRYaN4ibNzIQk23ye7uqeutKSB5icSFGA/132
     * sex : 2
     * status : 0
     */

    private String you_user_id;
    private String nick_name;
    private String photo_link;
    private String sex;
    private String status;
    private String content;

    public String getYou_user_id() {
        return you_user_id;
    }

    public void setYou_user_id(String you_user_id) {
        this.you_user_id = you_user_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPhoto_link() {
        return photo_link;
    }

    public void setPhoto_link(String photo_link) {
        this.photo_link = photo_link;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
