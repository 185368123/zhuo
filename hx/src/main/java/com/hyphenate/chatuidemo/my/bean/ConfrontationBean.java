package com.hyphenate.chatuidemo.my.bean;

/**
 * Created by Administrator on 2018/4/25.
 */

public class ConfrontationBean {

    String type;
    String date;
    String time;
    String describe;
    String team1Name;
    String team2Name;
    String team1Victory ;
    String team2Victory ;
    boolean isMyTeam=false;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public void setTeam1Name(String team1Name) {
        this.team1Name = team1Name;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public void setTeam2Name(String team2Name) {
        this.team2Name = team2Name;
    }

    public String getTeam1Victory() {
        return team1Victory;
    }

    public void setTeam1Victory(String team1Victory) {
        this.team1Victory = team1Victory;
    }

    public String getTeam2Victory() {
        return team2Victory;
    }

    public void setTeam2Victory(String team2Victory) {
        this.team2Victory = team2Victory;
    }

    public void setMyTeam(boolean myTeam) {
        isMyTeam = myTeam;
    }

    public boolean isMyTeam() {
        return isMyTeam;
    }
}
