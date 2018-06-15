package li.com.base.basesinglebean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/12.
 */

public class SuggestFriendBean {

    /**
     * error_code : 0
     * msg : 获取成功!
     * user_num : 23443
     * data : [{"user_id":"214","nick_name":"????????????","sex":"2","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/BBS/20171013110807.png","location":"深职,创新创业学院,大一"},{"user_id":"270","nick_name":"糖糖糖","sex":"1","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/BBS/20171013110807.png","location":"深职,创新创业学院,大一"}]
     */

    private int error_code;
    private String msg;
    private int user_num;
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

    public int getUser_num() {
        return user_num;
    }

    public void setUser_num(int user_num) {
        this.user_num = user_num;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_id : 214
         * nick_name : ????????????
         * sex : 2
         * photo_link : http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/BBS/20171013110807.png
         * location : 深职,创新创业学院,大一
         */

        private String user_id;
        private String nick_name;
        private String sex;
        private String photo_link;
        private String location;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPhoto_link() {
            return photo_link;
        }

        public void setPhoto_link(String photo_link) {
            this.photo_link = photo_link;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
