package zhuozhuo.com.zhuo.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */

public class StoryVideoBean {


    /**
     * data : [{"share_id":"1253","share_title":"吃货","share_brief":"","created":"2017-11-14 20:21:17","video_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserVideo/40.mov","video_thumb_link":"http://wx.qlogo.cn/mmopen/vi_32/pvfmKQoWkC1BAmnXGicLibt5KQUibPaOfReDORYHNcqjxt9J3jOT2xiaksuQczcxwvvMDQEng0hytiapgEMEm47wEQw/0","comment_count":"0","zan":""},{"share_id":"1251","share_title":"游戏","share_brief":"","created":"2017-11-14 18:04:08","video_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserVideo/39.mov","video_thumb_link":"http://wx.qlogo.cn/mmopen/vi_32/pvfmKQoWkC1BAmnXGicLibt5KQUibPaOfReDORYHNcqjxt9J3jOT2xiaksuQczcxwvvMDQEng0hytiapgEMEm47wEQw/0","comment_count":"0","zan":""},{"share_id":"1249","share_title":"疯狂","share_brief":"","created":"2017-11-14 17:51:48","video_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserVideo/38.mov","video_thumb_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/Single/820171114175001.jpg","comment_count":"0","zan":""},{"share_id":"1247","share_title":"疯狂","share_brief":"","created":"2017-11-14 17:51:45","video_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserVideo/38.mov","video_thumb_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/Single/820171114175001.jpg","comment_count":"0","zan":""},{"share_id":"1245","share_title":"疯狂","share_brief":"","created":"2017-11-14 17:48:22","video_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserVideo/37.mov","video_thumb_link":"http://wx.qlogo.cn/mmopen/vi_32/pvfmKQoWkC1BAmnXGicLibt5KQUibPaOfReDORYHNcqjxt9J3jOT2xiaksuQczcxwvvMDQEng0hytiapgEMEm47wEQw/0","comment_count":"0","zan":""},{"share_id":"1243","share_title":"吃货","share_brief":"","created":"2017-11-14 17:31:30","video_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserVideo/36.mov","video_thumb_link":"http://wx.qlogo.cn/mmopen/vi_32/pvfmKQoWkC1BAmnXGicLibt5KQUibPaOfReDORYHNcqjxt9J3jOT2xiaksuQczcxwvvMDQEng0hytiapgEMEm47wEQw/0","comment_count":"0","zan":""},{"share_id":"1241","share_title":"吃货","share_brief":"","created":"2017-11-14 17:05:33","video_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserVideo/35.mov","video_thumb_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/Single/820171114170358.jpg","comment_count":"0","zan":""},{"share_id":"1239","share_title":"吃货","share_brief":"","created":"2017-11-14 17:05:31","video_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserVideo/35.mov","video_thumb_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/Single/820171114170358.jpg","comment_count":"0","zan":""},{"share_id":"1235","share_title":"共享舍友","share_brief":"","created":"2017-11-14 17:01:55","video_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserVideo/33.mov","video_thumb_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/Single/320171114170006.jpg","comment_count":"0","zan":""},{"share_id":"1233","share_title":"共享舍友","share_brief":"","created":"2017-11-14 17:01:53","video_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserVideo/33.mov","video_thumb_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/Single/320171114170006.jpg","comment_count":"0","zan":""}]
     * msg : 视频页面返回视频列表
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
         * share_id : 1253
         * share_title : 吃货
         * share_brief :
         * created : 2017-11-14 20:21:17
         * video_link : http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserVideo/40.mov
         * video_thumb_link : http://wx.qlogo.cn/mmopen/vi_32/pvfmKQoWkC1BAmnXGicLibt5KQUibPaOfReDORYHNcqjxt9J3jOT2xiaksuQczcxwvvMDQEng0hytiapgEMEm47wEQw/0
         * comment_count : 0
         * zan :
         */

        private String share_id;
        private String share_title;
        private String share_brief;
        private String created;
        private String video_link;
        private String video_thumb_link;
        private String comment_count;
        private String zan;

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

        public String getVideo_link() {
            return video_link;
        }

        public void setVideo_link(String video_link) {
            this.video_link = video_link;
        }

        public String getVideo_thumb_link() {
            return video_thumb_link;
        }

        public void setVideo_thumb_link(String video_thumb_link) {
            this.video_thumb_link = video_thumb_link;
        }

        public String getComment_count() {
            return comment_count;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public String getZan() {
            return zan;
        }

        public void setZan(String zan) {
            this.zan = zan;
        }
    }
}
