package com.hyphenate.chatuidemo.my.bean;

/**
 * Created by Administrator on 2017/12/7.
 */

public class GroupChoicesBean {
        /**
         * choice_id : 10
         * choice_name : 篮球赛
         * exp : 0
         * type : group
         * value : 宵夜,饮料,无筹码
         * desc : 选择
         筹码:
         * choice_title : 约好时间地点，然后商量好筹码金额就可以开打啦！
         */

        private String choice_id;
        private String choice_name;
        private String exp;
        private String type;
        private String value;
        private String desc;
        private String choice_title;

        public String getChoice_id() {
            return choice_id;
        }

        public void setChoice_id(String choice_id) {
            this.choice_id = choice_id;
        }

        public String getChoice_name() {
            return choice_name;
        }

        public void setChoice_name(String choice_name) {
            this.choice_name = choice_name;
        }

        public String getExp() {
            return exp;
        }

        public void setExp(String exp) {
            this.exp = exp;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getChoice_title() {
            return choice_title;
        }

        public void setChoice_title(String choice_title) {
            this.choice_title = choice_title;
        }
}
