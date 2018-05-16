package com.hyphenate.chatuidemo.my.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/10.
 */

public class TagBean {

    /**
     * error_code : 0
     * msg : 请填写标签！
     * label_error : 1
     * data : [{"label_name":"啄啄标签一","label_content":"内容描述","user_label":"1","id":"9","status":"0","number":"1"}]
     */

    private int error_code;
    private String msg;
    private int label_error=0;
    private List<DataBean> data;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getLabel_error() {
        return label_error;
    }

    public void setLabel_error(int label_error) {
        this.label_error = label_error;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * label_name : 啄啄标签一
         * label_content : 内容描述
         * user_label : 1
         * id : 9
         * status : 0
         * number : 1
         */

        private String label_name;
        private String label_content;
        private String user_label;
        private String idd;
        private String status;
        private String number;

        public String getLabel_name() {
            return label_name;
        }

        public void setLabel_name(String label_name) {
            this.label_name = label_name;
        }

        public String getLabel_content() {
            return label_content;
        }

        public void setLabel_content(String label_content) {
            this.label_content = label_content;
        }

        public String getUser_label() {
            return user_label;
        }

        public void setUser_label(String user_label) {
            this.user_label = user_label;
        }

        public String getIdd() {
            return idd;
        }

        public void setIdd(String idd) {
            this.idd = idd;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
