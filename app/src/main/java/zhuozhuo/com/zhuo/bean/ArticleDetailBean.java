package zhuozhuo.com.zhuo.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/20.
 */

public class ArticleDetailBean {

    /**
     * data : [{"share_id":"1296","share_title":"123123","content":"<p>1356480<\/p><img src=\"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/bbs/420171117180923.png\"><p>3456786<\/p><img src=\"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/bbs/420171117180932.png\">","created":"2017-11-17 18:09:29","nick_name":"大灰机","account":"324","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20170925172946.png","comment_count":"0"}]
     * msg : 文章信息返回
     * error_code : 0
     */

    private String msg;
    private int error_code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * share_id : 1296
         * share_title : 123123
         * content : <p>1356480</p><img src="http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/bbs/420171117180923.png"><p>3456786</p><img src="http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/bbs/420171117180932.png">
         * created : 2017-11-17 18:09:29
         * nick_name : 大灰机
         * account : 324
         * photo_link : http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20170925172946.png
         * comment_count : 0
         */

        private String share_id;
        private String share_title;
        private String content;
        private String created;
        private String nick_name;
        private String account;
        private String photo_link;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPhoto_link() {
            return photo_link;
        }

        public void setPhoto_link(String photo_link) {
            this.photo_link = photo_link;
        }

        public String getComment_count() {
            return comment_count;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }
    }
}
