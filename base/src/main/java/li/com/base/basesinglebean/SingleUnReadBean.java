package li.com.base.basesinglebean;

/**
 * Created by Administrator on 2018/4/11.
 */

public class SingleUnReadBean {

    private int mesNum=0;

    private int comNum=0;

    private int groupNum=0;

    private int newFriendNum=0;

    public int getNewFriendNum() {
        return newFriendNum;
    }

    public void setNewFriendNum(int newFriendNum) {
        this.newFriendNum = newFriendNum;
    }

    public void setMesNum(int mesNum) {
        this.mesNum = mesNum;
    }

    public void setComNum(int comNum) {
        this.comNum = comNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public int getMesNum() {
        return mesNum;
    }

    public int getComNum() {
        return comNum;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public int getAllMsgNum() {
        return mesNum+newFriendNum;
    }


}
