package li.com.base.basesinglebean;

/**
 * Created by Administrator on 2018/3/27.
 */

public class MatchPersonBean {

    /**
     * nick_name :  2000011
     * finish :  0
     *  status : 0
     * is_status : 0
     * user_id  : 48
     * create_time  : 1522149554
     * r_user_id : 48
     */

    private String nick_name;
    private String finish;
    private String status;
    private String is_status;
    private String user_id;
    private String create_time;
    private String r_user_id;

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIs_status() {
        return is_status;
    }

    public void setIs_status(String is_status) {
        this.is_status = is_status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getR_user_id() {
        return r_user_id;
    }

    public void setR_user_id(String r_user_id) {
        this.r_user_id = r_user_id;
    }

    public boolean isMe(String user_id){
       return this.user_id.equals(user_id);
    }

    @Override
    public String toString() {
        return "MatchPersonBean{" +
                "nick_name='" + nick_name + '\'' +
                ", finish='" + finish + '\'' +
                ", status='" + status + '\'' +
                ", is_status='" + is_status + '\'' +
                ", user_id='" + user_id + '\'' +
                ", create_time='" + create_time + '\'' +
                ", r_user_id='" + r_user_id + '\'' +
                '}';
    }
}
