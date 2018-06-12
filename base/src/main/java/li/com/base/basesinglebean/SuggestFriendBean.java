package li.com.base.basesinglebean;

/**
 * Created by Administrator on 2018/6/12.
 */

public class SuggestFriendBean {
    String user_id;
    String nick_name;
    String sex;
    String photo_link;
    String location;

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setPhoto_link(String photo_link) {
        this.photo_link = photo_link;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public String getSex() {
        return sex;
    }

    public String getPhoto_link() {
        return photo_link;
    }

    public String getLocation() {
        return location;
    }
}
