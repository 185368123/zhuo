package zhuozhuo.com.zhuo.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/30.
 */

public class UnReadBean {

    /**
     * data : [{"unread_count":6,"share_id":"1407","type":"blog","video_link":"","share_title":"测测","share_brief":"","nick_name":"小小","photo_link":"http://wx.qlogo.cn/mmopen/vi_32/pvfmKQoWkC1BAmnXGicLibt5KQUibPaOfReDORYHNcqjxt9J3jOT2xiaksuQczcxwvvMDQEng0hytiapgEMEm47wEQw/0","user_id":"77","created":"2017-11-23 16:55:35","zan":"","last_comment":"1555","comment_count":6},{"unread_count":1,"share_id":"1357","type":"blog","video_link":"","share_title":"压缩","share_brief":"","nick_name":"小小","photo_link":"http://wx.qlogo.cn/mmopen/vi_32/pvfmKQoWkC1BAmnXGicLibt5KQUibPaOfReDORYHNcqjxt9J3jOT2xiaksuQczcxwvvMDQEng0hytiapgEMEm47wEQw/0","user_id":"77","created":"2017-11-21 16:47:14","zan":"","last_comment":"144","comment_count":1},{"unread_count":1,"share_id":"1283","type":"share_story","video_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserVideo/55.mov","share_title":"吃货","share_brief":"","nick_name":"小小","photo_link":"http://wx.qlogo.cn/mmopen/vi_32/pvfmKQoWkC1BAmnXGicLibt5KQUibPaOfReDORYHNcqjxt9J3jOT2xiaksuQczcxwvvMDQEng0hytiapgEMEm47wEQw/0","user_id":"77","created":"2017-11-17 16:20:52","zan":"啄啄阿卡丽","last_comment":"","comment_count":2},{"unread_count":2,"share_id":"1227","type":"share_story","video_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserVideo/31.mov","share_title":"共享舍友","share_brief":"","nick_name":"小小","photo_link":"http://wx.qlogo.cn/mmopen/vi_32/pvfmKQoWkC1BAmnXGicLibt5KQUibPaOfReDORYHNcqjxt9J3jOT2xiaksuQczcxwvvMDQEng0hytiapgEMEm47wEQw/0","user_id":"77","created":"2017-11-14 16:13:50","zan":"小小,啄啄阿卡丽,Roger","last_comment":"789798798797678685865865675765764754564654754547676586586585676","comment_count":30}]
     * msg : 返回消息列表
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
         * unread_count : 6
         * share_id : 1407
         * type : blog
         * video_link :
         * share_title : 测测
         * share_brief :
         * nick_name : 小小
         * photo_link : http://wx.qlogo.cn/mmopen/vi_32/pvfmKQoWkC1BAmnXGicLibt5KQUibPaOfReDORYHNcqjxt9J3jOT2xiaksuQczcxwvvMDQEng0hytiapgEMEm47wEQw/0
         * user_id : 77
         * created : 2017-11-23 16:55:35
         * zan :
         * last_comment : 1555
         * comment_count : 6
         */

        private int unread_count;
        private String share_id;
        private String type;
        private String video_link;
        private String share_title;
        private String share_brief;
        private String nick_name;
        private String photo_link;
        private String user_id;
        private String created;
        private String zan;
        private String last_comment;
        private int comment_count;

        public int getUnread_count() {
            return unread_count;
        }

        public void setUnread_count(int unread_count) {
            this.unread_count = unread_count;
        }

        public String getShare_id() {
            return share_id;
        }

        public void setShare_id(String share_id) {
            this.share_id = share_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getVideo_link() {
            return video_link;
        }

        public void setVideo_link(String video_link) {
            this.video_link = video_link;
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

        public String getZan() {
            return zan;
        }

        public void setZan(String zan) {
            this.zan = zan;
        }

        public String getLast_comment() {
            return last_comment;
        }

        public void setLast_comment(String last_comment) {
            this.last_comment = last_comment;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }
    }
}
