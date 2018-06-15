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

    private SingleChooseDetailBean singleChooseDetailBean;

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

    private List<MatchPersonBean> matchPersonBeans;

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

    private SingleUnReadBean unReadBean;

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

    public String  getFriensToString(){
        StringBuffer sb=new StringBuffer();
        if (friends!=null){
        for (int i = 0; i < friends.size(); i++) {
            if (i==(friends.size()-1)){
                sb.append(friends.get(i));
            }else {
                sb.append(friends.get(i)+",");
            }
        }
            return sb.toString();
        }else return "";
    }

    private String choose_id;

    public String getChoose_id() {
        return choose_id;
    }

    public void setChoose_id(String choose_id) {
        this.choose_id = choose_id;
    }

    private String match_type= "";
    private String location= "";
    private String location_id= "";
    private String statu= "";

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getMatch_type() {
        return match_type;
    }


    public String getLocation() {
        return location;
    }

    public void setMatch_type(String match_type) {
        this.match_type = match_type;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private List<CitiesSingBean> singBeans;

    public void setCitiesSingBean(List<CitiesSingBean> singBeans) {
        this.singBeans = singBeans;
    }

    public List<CitiesSingBean> getCitiesSingBean() {
        return singBeans;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public String getStatu() {
        return statu;
    }

    private String city1 = "";
    private String city2 = "";
    private String city3 = "";

    private String city1Name = "";
    private String city2Name = "";
    private String city3Name = "";

    public void setCity1(String city1) {
        this.city1 = city1;
    }

    public void setCity2(String city2) {
        this.city2 = city2;
    }

    public void setCity3(String city3) {
        this.city3 = city3;
    }

    public String getCityID() {
        String cityID = "";
        if (city1.equals("")) {
            if (city2.equals("")) {
                if (city3.equals("")) {

                } else {
                    cityID = city3;
                }
            } else {
                if (city3.equals("")) {
                    cityID = city2;
                } else {
                    cityID = city2 + "," + city3;
                }
            }
        } else {
            if (city2.equals("")) {
                if (city3.equals("")) {
                    cityID = city1;
                } else {
                    cityID = city1 + "," + city3;
                }
            } else {
                if (city3.equals("")) {
                    cityID = city1 + "," + city2;
                } else {
                    cityID = city1 + "," + city2 + "," + city3;
                }
            }
        }
        return cityID;
    }

    public void setCity1Name(String city1Name) {
        this.city1Name = city1Name;
    }

    public void setCity2Name(String city2Name) {
        this.city2Name = city2Name;
    }

    public void setCity3Name(String city3Name) {
        this.city3Name = city3Name;
    }

    public String getCity1Name() {
        return city1Name;
    }

    public String getCity2Name() {
        return city2Name;
    }

    public String getCity3Name() {
        return city3Name;
    }


    public String getCityName() {
        String cityName = "";
        if (city1.equals("")) {
            if (city2.equals("")) {
                if (city3.equals("")) {

                } else {
                    cityName = city3Name;
                }
            } else {
                if (city3.equals("")) {
                    cityName = city2Name;
                } else {
                    cityName = city2Name + "," + city3Name;
                }
            }
        } else {
            if (city2.equals("")) {
                if (city3.equals("")) {
                    cityName = city1Name;
                } else {
                    cityName = city1Name + "," + city3Name;
                }
            } else {
                if (city3.equals("")) {
                    cityName = city1Name + "," + city2Name;
                } else {
                    cityName = city1Name + "," + city2Name + "," + city3Name;
                }
            }
        }
        return cityName;
    }
    List<RandStrBean> randStrBeans;

    public List<RandStrBean> getRandStrBeans() {
        return randStrBeans;
    }

    public void setRandStrBeans(List<RandStrBean> randStrBeans) {
        this.randStrBeans = randStrBeans;
    }
}
