package com.hyphenate.chatuidemo.my.presenter;

import com.hyphenate.chatuidemo.my.bean.UserDB;
import com.hyphenate.chatuidemo.my.constract.GetUserMsgConstract;
import com.hyphenate.chatuidemo.my.bean.UserMsgBean;
import java.util.List;
import li.com.base.baserx.RxSubscriber;
import li.com.base.baseuntils.LogUtils;

/**
 * Created by Administrator on 2018/3/27.
 */

public class GetUserMsgPresenter extends GetUserMsgConstract.Presenter{

    @Override
    public void getUserMsg(final String user_id) {
        mModel.getUserMsg(user_id).subscribe(new RxSubscriber<List<UserMsgBean>>(mContext) {
            @Override
            protected void _onNext(List<UserMsgBean> userMsgBeans) {
                UserMsgBean msgBean=userMsgBeans.get(0);
                UserDB userDB=new UserDB();
                userDB.setUser_id(user_id);
                userDB.setAccount(msgBean.getAccount());
                userDB.setNick_name(msgBean.getNick_name());
                userDB.setSex(msgBean.getSex());
                userDB.setPhoto_link(msgBean.getPhoto_link());
                userDB.setLocation(msgBean.getLocation());
                userDB.setExp(msgBean.getExp());
                userDB.setMatch_count(msgBean.getMatch_count());
                userDB.setGroup_match_count(msgBean.getGroup_match_count());
                userDB.setPhone_type(msgBean.getPhone_type());
                userDB.setHundred_rate(msgBean.getHundred_rate());
                userDB.setHundred_level(msgBean.getHundred_level());
                userDB.setLine_num(msgBean.getLine_num());
                userDB.setUser_video(msgBean.getUser_video());
                userDB.setCard(msgBean.getCard());
                if (mView!=null){
                    mView.returnUserMsg(msgBean);
                }
                userDB.save();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
