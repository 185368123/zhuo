package com.hyphenate.chatuidemo.my.easytagdragview.bean;

public class SimpleTitleTip implements Tip {
    private int id;
    private String tip;
    private String  id_member;
    private boolean clickable=true;
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getId_member() {
        return id_member;
    }

    public void setId_member(String id_member) {
        this.id_member = id_member;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public boolean isClickable() {
        return clickable;
    }

    @Override
    public String toString() {
        return "SimpleTitleTip{" +
                "id=" + id +
                ", tip='" + tip + '\'' +
                ", id_member='" + id_member + '\'' +
                '}';
    }
}
