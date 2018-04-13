package com.hyphenate.chatuidemo.my.bean;

/**
 * Created by Administrator on 2018/4/2.
 */

public class VideoLinkBean {
    /**
     * video_link : http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserVideo/140.mov
     */

    private String video_link;

    private String is_fail;

    public String getIs_fail() {
        return is_fail;
    }

    public void setIs_fail(String is_fail) {
        this.is_fail = is_fail;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }
}
