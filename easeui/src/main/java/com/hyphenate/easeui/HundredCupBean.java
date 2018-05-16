package com.hyphenate.easeui;

import java.util.List;

/**
 * Created by Administrator on 2018/4/21.
 */

public class HundredCupBean {


    /**
     * error_code : 0
     * msg : 获取成功
     * hundred_id : 2530
     * group_id : 47078866747393
     * group_name : 网吧杯
     * conditions : 网吧杯,网吧吃鸡,
     * finish : 1
     * is_close : 0
     * line_id : 2518
     * line_match : 0
     * data : [{"nick_name":"糖糖","photo_link":"http://img1.imgtn.bdimg.com/it/u=1435593655,1758121938&fm=27&gp=0.jpg","user_id":"76","group_name":"刺激","line_id":"2518","group_id":"47078866747393"}]
     */

    private int error_code;
    private String msg;
    private String hundred_id;
    private String group_id;
    private String group_name;
    private String conditions;
    private String finish;
    private String is_close;
    private String line_id;
    private String line_match;
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

    public String getHundred_id() {
        return hundred_id;
    }

    public void setHundred_id(String hundred_id) {
        this.hundred_id = hundred_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getIs_close() {
        return is_close;
    }

    public void setIs_close(String is_close) {
        this.is_close = is_close;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getLine_match() {
        return line_match;
    }

    public void setLine_match(String line_match) {
        this.line_match = line_match;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * nick_name : 糖糖
         * photo_link : http://img1.imgtn.bdimg.com/it/u=1435593655,1758121938&fm=27&gp=0.jpg
         * user_id : 76
         * group_name : 刺激
         * line_id : 2518
         * group_id : 47078866747393
         */

        private String nick_name;
        private String photo_link;
        private String user_id;
        private String group_name;
        private String line_id;
        private String group_id;

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

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public String getLine_id() {
            return line_id;
        }

        public void setLine_id(String line_id) {
            this.line_id = line_id;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }
    }
}
