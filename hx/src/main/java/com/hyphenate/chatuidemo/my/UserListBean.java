package com.hyphenate.chatuidemo.my;

import java.util.List;

/**
 * Created by Administrator on 2017/11/4.
 */

public class UserListBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "user_id='" + user_id + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    ", photo_link='" + photo_link + '\'' +
                    ", account='" + account + '\'' +
                    ", sex='" + sex + '\'' +
                    ", location='" + location + '\'' +
                    ", exp='" + exp + '\'' +
                    '}';
        }

        /**
         * user_id : 1
         * nick_name : Roger
         * photo_link : https://wx.qlogo.cn/mmopen/vi_32/5icsJibet91SiadmNp95pSQpxr0MAhV38023eRuALNruqRkvqtQ6B6yyvlsmtem3vcLVyibHrg6yibylzFYTtrQgExg/0
         * account : 501
         * sex : 1
         * location : 深圳
         * exp : 25050
         */

        private String user_id;
        private String nick_name;
        private String photo_link;
        private String account;
        private String sex;
        private String location;
        private String exp;

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

        public String getPhoto_link() {
            return photo_link;
        }

        public void setPhoto_link(String photo_link) {
            this.photo_link = photo_link;
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

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getExp() {
            return exp;
        }

        public void setExp(String exp) {
            this.exp = exp;
        }
}
