package com.hyphenate.chatuidemo;

import com.hyphenate.chatuidemo.my.bean.UserDB;
import com.hyphenate.chatuidemo.my.constract.GetUserMsgConstract;
import com.hyphenate.chatuidemo.my.model.GetUserMsgModel;
import com.hyphenate.chatuidemo.my.presenter.GetUserMsgPresenter;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */

public class UserMsgDBHelp {
    private static UserMsgDBHelp userMsgDBHelp;

    // ============================================================
    private UserMsgDBHelp() {

    }

    public static UserMsgDBHelp getUserMsgDBHelp() {
        if (userMsgDBHelp == null) {
            synchronized (UserMsgDBHelp.class) {
                if (userMsgDBHelp == null) {
                    userMsgDBHelp = new UserMsgDBHelp();
                }
            }
        }
        return userMsgDBHelp;
    }

    public UserDB searchByUserId(String userId) {//根据用户ID搜索信息
        List<UserDB> userDBList = DataSupport.select().where("user_id = ?", userId).find(UserDB.class);
        if (!(userDBList.size() > 0)) {
            saveMsgToDB(userId);
            return null;
        } else {
            return userDBList.get(0);
        }
    }

    public void saveMsgToDB(String userId) {//根据用户ID从服务器获取用户信息并且保存在数据库
        GetUserMsgPresenter getUserMsgPresenter=new GetUserMsgPresenter();
        getUserMsgPresenter.setVM(new GetUserMsgModel(),null);
        getUserMsgPresenter.getUserMsg(userId);
    }

    public void updateMsg(String userId) {//根据用户ID从服务器获取用户信息并且保存在数据库
        DataSupport.deleteAll(UserDB.class,"user_id = ?", userId);
        GetUserMsgPresenter getUserMsgPresenter=new GetUserMsgPresenter();
        getUserMsgPresenter.setVM(new GetUserMsgModel(),null);
        getUserMsgPresenter.getUserMsg(userId);
    }

    public void updateMsg(String userId, GetUserMsgConstract.View  call) {//根据用户ID从服务器获取用户信息并且保存在数据库
        DataSupport.deleteAll(UserDB.class,"user_id = ?", userId);
        GetUserMsgPresenter getUserMsgPresenter=new GetUserMsgPresenter();
        getUserMsgPresenter.setVM(new GetUserMsgModel(),call);
        getUserMsgPresenter.getUserMsg(userId);
    }
}
