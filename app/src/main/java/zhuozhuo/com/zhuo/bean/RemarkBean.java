package zhuozhuo.com.zhuo.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/9.
 */

public class RemarkBean {

    /**
     * data : [{"user_id":"8","created":"2017-11-08 20:51:46","score":"50","content":"","card":"1","account":"648","nick_name":"啄啄阿卡丽","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20170926195437.png","type":"single"},{"user_id":"8","created":"2017-11-08 20:11:10","score":"0","content":"","card":"1","account":"648","nick_name":"啄啄阿卡丽","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20170926195437.png","type":"single"},{"user_id":"8","created":"2017-11-08 20:10:38","score":"50","content":"","card":"1","account":"648","nick_name":"啄啄阿卡丽","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20170926195437.png","type":"single"},{"user_id":"8","created":"2017-11-08 20:04:35","score":"50","content":"","card":"1","account":"648","nick_name":"啄啄阿卡丽","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20170926195437.png","type":"single"},{"user_id":"8","created":"2017-11-08 19:58:02","score":"50","content":"","card":"1","account":"648","nick_name":"啄啄阿卡丽","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20170926195437.png","type":"single"},{"user_id":"8","created":"2017-11-08 19:56:35","score":"50","content":"","card":"1","account":"648","nick_name":"啄啄阿卡丽","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20170926195437.png","type":"single"},{"user_id":"8","created":"2017-11-08 19:56:12","score":"50","content":"","card":"1","account":"648","nick_name":"啄啄阿卡丽","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20170926195437.png","type":"single"},{"user_id":"8","created":"2017-11-08 19:55:53","score":"50","content":"","card":"1","account":"648","nick_name":"啄啄阿卡丽","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20170926195437.png","type":"single"},{"user_id":"8","created":"2017-11-08 19:55:41","score":"50","content":"","card":"1","account":"648","nick_name":"啄啄阿卡丽","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20170926195437.png","type":"single"},{"user_id":"8","created":"2017-11-08 19:55:27","score":"50","content":"","card":"1","account":"648","nick_name":"啄啄阿卡丽","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20170926195437.png","type":"single"}]
     * count : 99
     * unread_group_count : 0
     * last_photo_link :
     * msg : 返回评价列表
     * error_code : 0
     */

    private String count;
    private int unread_group_count;
    private String last_photo_link;
    private String msg;
    private int error_code;
    private List<DataBean> data;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getUnread_group_count() {
        return unread_group_count;
    }

    public void setUnread_group_count(int unread_group_count) {
        this.unread_group_count = unread_group_count;
    }

    public String getLast_photo_link() {
        return last_photo_link;
    }

    public void setLast_photo_link(String last_photo_link) {
        this.last_photo_link = last_photo_link;
    }

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
         * user_id : 8
         * created : 2017-11-08 20:51:46
         * score : 50
         * content :
         * card : 1
         * account : 648
         * nick_name : 啄啄阿卡丽
         * photo_link : http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20170926195437.png
         * type : single
         */

        private String user_id;
        private String created;
        private String score;
        private String content;
        private String card;
        private String account;
        private String nick_name;
        private String photo_link;
        private String type;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

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

        public String getPhoto_link() {
            return photo_link;
        }

        public void setPhoto_link(String photo_link) {
            this.photo_link = photo_link;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
