package com.hyphenate.chatuidemo.my.bean;

/**
 * Created by Administrator on 2017/10/23.
 */

public class VideoBean {

    /**
     * nick_name : 2000011
     * photo_link : http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/4820180129135415.png
     * share_id : 326
     * r_user_id : 4
     * share_title : 宠物
     * share_brief : 大灰机
     * created : 2018-03-29 09:54:27
     * video_link : http://zhuozhuotech.xin/UserVideo/4820180329095345.mp4
     * video_thumb_link : http://zhuozhuotech.xin/Single/thumb_link/420180329095205.jpg
     * comment_count : 0
     * zan :
     */

    private String nick_name;
    private String photo_link;
    private String share_id;
    private String r_user_id;
    private String share_title;
    private String share_brief;
    private String created;
    private String video_link;
    private String video_thumb_link;
    private String comment_count;
    private String zan;

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

    public String getShare_id() {
        return share_id;
    }

    public void setShare_id(String share_id) {
        this.share_id = share_id;
    }

    public String getR_user_id() {
        return r_user_id;
    }

    public void setR_user_id(String r_user_id) {
        this.r_user_id = r_user_id;
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

    @Override
    public String toString() {
        return "VideoBean{" +
                "nick_name='" + nick_name + '\'' +
                ", photo_link='" + photo_link + '\'' +
                ", share_id='" + share_id + '\'' +
                ", r_user_id='" + r_user_id + '\'' +
                ", share_title='" + share_title + '\'' +
                ", share_brief='" + share_brief + '\'' +
                ", created='" + created + '\'' +
                ", video_link='" + video_link + '\'' +
                ", video_thumb_link='" + video_thumb_link + '\'' +
                ", comment_count='" + comment_count + '\'' +
                ", zan='" + zan + '\'' +
                '}';
    }
}
