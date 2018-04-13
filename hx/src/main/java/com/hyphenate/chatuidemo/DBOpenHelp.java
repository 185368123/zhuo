package com.hyphenate.chatuidemo;


import android.util.Log;

import com.hyphenate.chatuidemo.my.bean.UserDB;
import com.hyphenate.chatuidemo.my.model.GetUserMsgModel;
import com.hyphenate.chatuidemo.my.presenter.GetUserMsgPresenter;
import com.hyphenate.easeui.domain.EaseUser;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2017/11/11.
 */

public class DBOpenHelp {
    private  static DBOpenHelp dbOpenHelp;
    // ============================================================
    private DBOpenHelp() {
    }
    public static DBOpenHelp getDBOpenHelp() {
        if (dbOpenHelp == null) {
            synchronized (DBOpenHelp.class) {
                if (dbOpenHelp == null) {
                    dbOpenHelp = new DBOpenHelp();
                }
            }
        }
        return dbOpenHelp;
    }


    public EaseUser selectByUserId(String value){
        List<UserDB> userDBList = DataSupport.select().where("user_id = ?", value).find(UserDB.class);
        Log.e("userDb", userDBList.size()+"");
        if (userDBList.size() > 0) {
            UserDB userDB = userDBList.get(0);
            EaseUser easeUser = new EaseUser(userDB.getUser_id());
            easeUser.setNickname(userDB.getNick_name());
            easeUser.setAvatar(userDB.getPhoto_link());
            return easeUser;
        } else {
            GetUserMsgPresenter getUserMsgPresenter=new GetUserMsgPresenter();
            getUserMsgPresenter.setVM(new GetUserMsgModel(),null);
            getUserMsgPresenter.getUserMsg(value);
            return null;
        }
    }
    public void delete(String user_id){
        DataSupport.deleteAll(UserDB.class, "user_id = ? ", user_id);
    }

}
