package zhuozhuo.com.zhuo.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */

public class CommentBean {

    /**
     * data : [{"comment_id":"166","comment":"，，，","created":"2017-11-07 14:36:29","user_id":"8","to_id":"0","from_nick_name":"啄啄阿卡丽","from_photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20170926195437.png","to_nick_name":null}]
     * msg : 返回所有的留言信息
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
         * comment_id : 166
         * comment : ，，，
         * created : 2017-11-07 14:36:29
         * user_id : 8
         * to_id : 0
         * from_nick_name : 啄啄阿卡丽
         * from_photo_link : http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20170926195437.png
         * to_nick_name : null
         */

        private String comment_id;
        private String comment;
        private String created;
        private String user_id;
        private String to_id;
        private String from_nick_name;
        private String from_photo_link;
        private Object to_nick_name;

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getTo_id() {
            return to_id;
        }

        public void setTo_id(String to_id) {
            this.to_id = to_id;
        }

        public String getFrom_nick_name() {
            return from_nick_name;
        }

        public void setFrom_nick_name(String from_nick_name) {
            this.from_nick_name = from_nick_name;
        }

        public String getFrom_photo_link() {
            return from_photo_link;
        }

        public void setFrom_photo_link(String from_photo_link) {
            this.from_photo_link = from_photo_link;
        }

        public Object getTo_nick_name() {
            return to_nick_name;
        }

        public void setTo_nick_name(Object to_nick_name) {
            this.to_nick_name = to_nick_name;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "comment_id='" + comment_id + '\'' +
                    ", comment='" + comment + '\'' +
                    ", created='" + created + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", to_id='" + to_id + '\'' +
                    ", from_nick_name='" + from_nick_name + '\'' +
                    ", from_photo_link='" + from_photo_link + '\'' +
                    ", to_nick_name=" + to_nick_name +
                    '}';
        }
    }
}
