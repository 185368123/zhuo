package com.hyphenate.chatuidemo.my.constract;

import android.widget.EditText;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;

/**
 * Created by Administrator on 2018/4/24.
 */

public interface TheBaseConstract {
    interface Model extends BaseModel {
        Observable<Object> receiveInvite(String token, String line_id, String group_id, String nick_user_id);

        Observable<Object> refuseInvite(String token, String line_id, String group_id, String nick_user_id);

        Observable<Object> refuseApply(String token, String line_id, String you_user_id );

        Observable<Object> receiveApply(String token, String line_id, String you_user_id );

        Observable<Object> accept(String token, String from_user_id, String team_id );

        Observable<Object> deny(String token, String from_user_id, String team_id );

    }

    interface View extends BaseView {

        void receiveInviteSucess();

        void refuseInviteSucess();


        void refuseApplySucess();

        void receiveApplySucess();

        void acceptSucess();

        void denySucess();

    }

    abstract static class Presenter extends BasePresenter<View, Model>{

        public abstract void receiveInvite( String line_id, String group_id, String nick_user_id);

        public abstract void refuseInvite( String line_id, String group_id, String nick_user_id);

        public abstract void refuseApply( String line_id, String you_user_id);

        public abstract void receiveApply( String line_id, String you_user_id);

        public abstract void accept(String from_user_id, String team_id );

        public abstract void deny(String from_user_id, String team_id );

    }
}
