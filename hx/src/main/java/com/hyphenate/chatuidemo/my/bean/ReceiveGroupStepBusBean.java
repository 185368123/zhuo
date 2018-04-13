package com.hyphenate.chatuidemo.my.bean;

/**
 * Created by Administrator on 2018/1/8.
 */

public class ReceiveGroupStepBusBean {

    /**
     * method : groupstep
     * conditions : 任务简介1,任务简介2,任务简介3,任务简介4,任务简介5,任务简介6,任务简介7
     * conditions_num : 0
     */

    private String method;
    private String conditions;
    private String conditions_num;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getConditions_num() {
        return conditions_num;
    }

    public void setConditions_num(String conditions_num) {
        this.conditions_num = conditions_num;
    }
}
