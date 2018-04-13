package com.hyphenate.chatuidemo.my.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/27.
 */

public class UserMsgBean {

        /**
         * account : 5566
         * nick_name : 苍井空
         * sex : 2
         * photo_link : http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/471521798026072.jpg
         * location : 深职,数字创意与动画学院,大三
         * exp : 50
         * match_count : 5
         * group_match_count : 0
         * phone_type : 0
         * hundred_rate : 0
         * hundred_level : c
         * card : ["0","0","2","0","0","1","0","0","1"]
         */

        private String account;
        private String nick_name;
        private String sex;
        private String photo_link;
        private String location;
        private String exp;
        private String match_count;
        private String group_match_count;
        private String phone_type;
        private String hundred_rate;
        private String hundred_level;
        private List<String> card;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
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

        public String getExp() {
            return exp;
        }

        public void setExp(String exp) {
            this.exp = exp;
        }

        public String getMatch_count() {
            return match_count;
        }

        public void setMatch_count(String match_count) {
            this.match_count = match_count;
        }

        public String getGroup_match_count() {
            return group_match_count;
        }

        public void setGroup_match_count(String group_match_count) {
            this.group_match_count = group_match_count;
        }

        public String getPhone_type() {
            return phone_type;
        }

        public void setPhone_type(String phone_type) {
            this.phone_type = phone_type;
        }

        public String getHundred_rate() {
            return hundred_rate;
        }

        public void setHundred_rate(String hundred_rate) {
            this.hundred_rate = hundred_rate;
        }

        public String getHundred_level() {
            return hundred_level;
        }

        public void setHundred_level(String hundred_level) {
            this.hundred_level = hundred_level;
        }

        public List<String> getCard() {
            return card;
        }

        public void setCard(List<String> card) {
            this.card = card;
        }
}
