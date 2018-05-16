package com.hyphenate.easeui;

import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */

public class TeamUnreadBean {

    /**
     * error_code : 0
     * msg : 获取成功
     * count : 16
     * line_id : 2529
     * group_id : 47365598806017
     * data : [{"id":"2560","line_id":"2526","user_id":"76","group_id":"47358756847617","group_name":"我的","nick_user_id":"32","nick_name":"波结衣","status":"0","type":"2","create_time":"1524569543","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/321523605352436.jpg"},{"id":"2554","line_id":"2522","user_id":"76","group_id":"47272990670849","group_name":"糖糖","nick_user_id":"92","nick_name":"糖糖","status":"0","type":"2","create_time":"1524548966","photo_link":"http://zhuozhuotech.xin/UserIcon/9220180408113028.png"},{"id":"2553","line_id":"2522","user_id":"76","group_id":"47272990670849","group_name":"糖糖","nick_user_id":"92","nick_name":"糖糖","status":"0","type":"2","create_time":"1524548956","photo_link":"http://zhuozhuotech.xin/UserIcon/9220180408113028.png"},{"id":"2552","line_id":"2520","user_id":"76","group_id":"47259918073857","group_name":"458","nick_user_id":"32","nick_name":"波结衣","status":"0","type":"1","create_time":"1524548185","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/321523605352436.jpg"}]
     */

    private int error_code;
    private String msg;
    private String count;
    private String line_id;
    private String group_id;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2560
         * line_id : 2526
         * user_id : 76
         * group_id : 47358756847617
         * group_name : 我的
         * nick_user_id : 32
         * nick_name : 波结衣
         * status : 0
         * type : 2
         * create_time : 1524569543
         * photo_link : http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/321523605352436.jpg
         */

        private String id;
        private String line_id;
        private String user_id;
        private String group_id;
        private String group_name;
        private String nick_user_id;
        private String nick_name;
        private String status;
        private String type;
        private String create_time;
        private String photo_link;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLine_id() {
            return line_id;
        }

        public void setLine_id(String line_id) {
            this.line_id = line_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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

        public String getNick_user_id() {
            return nick_user_id;
        }

        public void setNick_user_id(String nick_user_id) {
            this.nick_user_id = nick_user_id;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPhoto_link() {
            return photo_link;
        }

        public void setPhoto_link(String photo_link) {
            this.photo_link = photo_link;
        }
    }
}
