package com.hyphenate.chatuidemo.my.bean;

/**
 * Created by Administrator on 2017/10/30.
 */

public class UserOnlineBean {

        /**
         * user_id : 1
         * group_match_status : 0
         * online_status : 0
         * exp : 13300
         * photo_link : https://wx.qlogo.cn/mmopen/vi_32/5icsJibet91SiadmNp95pSQpxr0MAhV38023eRuALNruqRkvqtQ6B6yyvlsmtem3vcLVyibHrg6yibylzFYTtrQgExg/0
         * nick_name : Roger
         */

        private String user_id;
        private String group_match_status;
        private int online_status;
        private String exp;
        private String photo_link;
        private String nick_name;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getGroup_match_status() {
            return group_match_status;
        }

        public void setGroup_match_status(String group_match_status) {
            this.group_match_status = group_match_status;
        }

        public int getOnline_status() {
            return online_status;
        }

        public void setOnline_status(int online_status) {
            this.online_status = online_status;
        }

        public String getExp() {
            return exp;
        }

        public void setExp(String exp) {
            this.exp = exp;
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
}
