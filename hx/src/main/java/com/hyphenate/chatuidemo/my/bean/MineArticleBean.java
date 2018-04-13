package com.hyphenate.chatuidemo.my.bean;

/**
 * Created by Administrator on 2017/11/9.
 */

public class MineArticleBean {

        /**
         * share_id : 788
         * share_title : 测试
         * share_brief : 这是测试
         * created : 2017-11-09 18:03:21
         * comment_count : 4
         */

        private String share_id;
        private String share_title;
        private String share_brief;
        private String created;
        private String comment_count;

        public String getShare_id() {
            return share_id;
        }

        public void setShare_id(String share_id) {
            this.share_id = share_id;
        }

        public String getShare_title() {
            return share_title;
        }

        public void setShare_title(String share_title) {
            this.share_title = share_title;
        }

        public String getShare_brief() {
            return share_brief;
        }

        public void setShare_brief(String share_brief) {
            this.share_brief = share_brief;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getComment_count() {
            return comment_count;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }
}
