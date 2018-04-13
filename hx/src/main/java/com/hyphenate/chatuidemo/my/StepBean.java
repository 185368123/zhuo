package com.hyphenate.chatuidemo.my;

/**
 * Created by Administrator on 2017/11/2.
 */

public class StepBean {

    public static StepBean stepBean;

    private String[] title;

    private String[] detail;

    private StepBean() {

    }


    public static StepBean getStepBean() {
        if (stepBean == null) {
            synchronized (StepBean.class) {
                if (stepBean == null) {
                    stepBean = new StepBean();
                }
            }
        }
        return stepBean;
    }

    public String[] getTitle() {
        return title;
    }

    public String[] getDetail() {
        return detail;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public void setDetail(String[] detail) {
        this.detail = detail;
    }
}
