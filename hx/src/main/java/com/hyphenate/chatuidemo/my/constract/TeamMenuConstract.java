package com.hyphenate.chatuidemo.my.constract;


import com.hyphenate.chatuidemo.my.bean.CupMemberBean;

import java.util.List;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface TeamMenuConstract {
    interface Model extends BaseModel {

        Observable<Object> deleteMember(String token, String line_id, String group_id, String type, String you_user_id);

        Observable<Object> deleteTeam(String token, String line_id, String group_id);

    }

    interface View extends BaseView {

        void deleteMemberSucess();

        void deleteTeamSucess();

    }

    abstract static class Presenter extends BasePresenter<View, Model> {

        public abstract void deleteMember(String line_id, String group_id, String type, String you_user_id);

        public abstract void deleteTeam( String line_id,String group_id);

    }
}
