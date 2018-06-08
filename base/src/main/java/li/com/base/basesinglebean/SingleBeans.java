package li.com.base.basesinglebean;

import java.util.List;

import rx.Single;

/**
 * Created by Administrator on 2018/3/26.
 * 管理需要临时存储的数据
 */

public class SingleBeans {
    private static SingleBeans singleBeans;

    private SingleBeans() {
    }

    public static SingleBeans getInstance() {
        if (singleBeans == null) {
            synchronized (SingleBeans.class) {
                if (singleBeans == null) {
                    singleBeans = new SingleBeans();
                }
            }
        }
        return singleBeans;
    }

    private List<SingleChooseBean> singleChooseBeans;

    public List<SingleChooseBean> getSingleChooseBeans() {
        return singleChooseBeans;
    }

    public void setSingleChooseBeans(List<SingleChooseBean> singleChooseBeans) {
        this.singleChooseBeans = singleChooseBeans;
    }

    private SingleStatusBean singleStatusBean;

    public SingleStatusBean getSingleStatusBean() {
        return singleStatusBean;
    }

    public void setSingleStatusBean(SingleStatusBean singleStatusBean) {
        this.singleStatusBean = singleStatusBean;
    }

    private     SingleChooseDetailBean singleChooseDetailBean;

    public SingleChooseDetailBean getSingleChooseDetailBean() {
        return singleChooseDetailBean;
    }

    public void setSingleChooseDetailBean(SingleChooseDetailBean singleChooseDetailBean) {
        this.singleChooseDetailBean = singleChooseDetailBean;
    }

    private String you_user_id;

    public String getYou_user_id() {
        return you_user_id;
    }

    public void setYou_user_id(String you_user_id) {
        this.you_user_id = you_user_id;
    }

    private  List<MatchPersonBean>  matchPersonBeans;

    public List<MatchPersonBean> getMatchPersonBeans() {
        return matchPersonBeans;
    }

    public void setMatchPersonBeans(List<MatchPersonBean> matchPersonBeans) {
        this.matchPersonBeans = matchPersonBeans;
    }

    private GroupStatusBean groupStatusBean;

    public GroupStatusBean getGroupStatusBeans() {
        return groupStatusBean;
    }

    public void setGroupStatusBeans(GroupStatusBean groupStatusBean) {
        this.groupStatusBean = groupStatusBean;
    }

    private VisonBean visonBean;

    public VisonBean getVisonBean() {
        return visonBean;
    }

    public void setVisonBean(VisonBean visonBean) {
        this.visonBean = visonBean;
    }

    private SingleUnReadBean  unReadBean;

    public void setUnReadBean(SingleUnReadBean unReadBean) {
        this.unReadBean = unReadBean;
    }

    public SingleUnReadBean getUnReadBean() {
        if (unReadBean == null) {
            synchronized (SingleBeans.class) {
                if (unReadBean == null) {
                    unReadBean = new SingleUnReadBean();
                }
            }
        }
        return unReadBean;
    }

    private boolean isTeam;

    public boolean isTeam() {
        return isTeam;
    }

    public void setTeam(boolean team) {
        isTeam = team;
    }


    private String line_id;

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getLine_id() {
        return line_id;
    }

    private List<String> friends;

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public List<String> getFriends() {
        return friends;
    }

    private String choose_id;

    public String getChoose_id() {
        return choose_id;
    }

    public void setChoose_id(String choose_id) {
        this.choose_id = choose_id;
    }

    private String match_type;
    private String destination;
    private String location;

    public String getMatch_type() {
        return match_type;
    }

    public String getDestination() {
        return destination;
    }

    public String getLocation() {
        return location;
    }

    public void setMatch_type(String match_type) {
        this.match_type = match_type;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private  List<CitiesSingBean> singBeans;

    public void setCitiesSingBean(List<CitiesSingBean> singBeans) {
        this.singBeans = singBeans;
    }

    public List<CitiesSingBean> getCitiesSingBean() {
        return singBeans;
    }
}
