package com.hyphenate.chatuidemo.my.bean;

/**
 * Created by Administrator on 2018/4/25.
 */

public class TeamScoreBean {

    String type;
    String typeName;
    String groupName;
    String teamName ;
    String teamVictory ;
    String teamFiled ;
    String teamScore ;
    private boolean isMyTeam;

    public void setType(String type) {
        this.type = type;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTeamVictory(String teamVictory) {
        this.teamVictory = teamVictory;
    }

    public void setTeamFiled(String teamFiled) {
        this.teamFiled = teamFiled;
    }

    public void setTeamScore(String teamScore) {
        this.teamScore = teamScore;
    }

    public String getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamVictory() {
        return teamVictory;
    }

    public String getTeamFiled() {
        return teamFiled;
    }

    public String getTeamScore() {
        return teamScore;
    }
    public void setMyTeam(boolean myTeam) {
        isMyTeam = myTeam;
    }

    public boolean isMyTeam() {
        return isMyTeam;
    }
    @Override
    public String toString() {
        return "TeamScoreBean{" +
                "type='" + type + '\'' +
                ", typeName='" + typeName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", teamName='" + teamName + '\'' +
                ", teamVictory='" + teamVictory + '\'' +
                ", teamFiled='" + teamFiled + '\'' +
                ", teamScore='" + teamScore + '\'' +
                '}';
    }
}
