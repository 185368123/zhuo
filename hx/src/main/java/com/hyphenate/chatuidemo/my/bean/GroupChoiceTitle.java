package com.hyphenate.chatuidemo.my.bean;

/**
 * Created by Administrator on 2017/12/9.
 */

public class GroupChoiceTitle {

    public static GroupChoiceTitle choiceTitle;

    private GroupChoiceTitle() {
    }

    public static GroupChoiceTitle getGroupChoiceTitle() {
        if (choiceTitle == null) {
            synchronized (GroupChoiceTitle.class) {
                if (choiceTitle == null) {
                    choiceTitle = new GroupChoiceTitle();

                }
            }
        }
        return choiceTitle;
    }

    private String title;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
