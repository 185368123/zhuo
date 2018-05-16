package com.hyphenate.chatuidemo.my.constract;


import com.hyphenate.chatuidemo.my.bean.CupMemberBean;
import com.hyphenate.chatuidemo.my.bean.CupTeamBean;

import java.util.List;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface InviteMemberConstract {
    interface Model extends BaseModel {

        Observable<List<CupMemberBean>> getAllMember(String token, String page,String page_size,String nick_name);

        Observable<List<CupMemberBean>> getFriends(String token, String user_ids,String page,String page_size,String nick_name);

        Observable<Object> inviteMember(String token, String line_id,String you_user_id);

        Observable<Object> applyTeam(String token, String line_id);

        Observable<List<CupTeamBean>> getTeam(String token);

    }

    interface View extends BaseView {

        void returnAllMember(List<CupMemberBean> data);

        void returnFriendsr(List<CupMemberBean> data);

        void returnTeam(List<CupTeamBean> cupTeamBeans);

    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getAllMember(String page,String page_size,String nick_name);

        public abstract void getFriends(String user_ids,String page,String page_size,String nick_name);

        public abstract void inviteMember( String line_id,String you_user_id);

        public abstract void applyTeam( String line_id);

        public abstract void getTeam();

    }
}
