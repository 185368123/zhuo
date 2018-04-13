package com.hyphenate.chatuidemo.my.bean;

/**
 * Created by Administrator on 2017/10/18.
 */

public class LoginBean {

        /**
         * user_id : 7
         * session_id : gml38gei57bqt3ucac1hjdme9iukh09u
         * token : bc5bad69e25257cebf605f8188ac38de
         * account : 无
         * sex : 1
         * photo_link : http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20170926100108.png
         * nick_name : 啊了巴
         * phone : 13888888888
         * wechat_id :
         * reg_mode : 1
         * exp : 8100
         * location : 无
         */

        private String user_id;
        private String session_id;
        private String token;
        private String account;
        private String sex;
        private String photo_link;
        private String nick_name;
        private String phone;
        private String wechat_id;
        private String reg_mode;
        private String exp;
        private String location;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getSession_id() {
            return session_id;
        }

        public void setSession_id(String session_id) {
            this.session_id = session_id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
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

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getWechat_id() {
            return wechat_id;
        }

        public void setWechat_id(String wechat_id) {
            this.wechat_id = wechat_id;
        }

        public String getReg_mode() {
            return reg_mode;
        }

        public void setReg_mode(String reg_mode) {
            this.reg_mode = reg_mode;
        }

        public String getExp() {
            return exp;
        }

        public void setExp(String exp) {
            this.exp = exp;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "user_id='" + user_id + '\'' +
                    ", session_id='" + session_id + '\'' +
                    ", token='" + token + '\'' +
                    ", account='" + account + '\'' +
                    ", sex='" + sex + '\'' +
                    ", photo_link='" + photo_link + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    ", phone='" + phone + '\'' +
                    ", wechat_id='" + wechat_id + '\'' +
                    ", reg_mode='" + reg_mode + '\'' +
                    ", exp='" + exp + '\'' +
                    ", location='" + location + '\'' +
                    '}';
        }

}
