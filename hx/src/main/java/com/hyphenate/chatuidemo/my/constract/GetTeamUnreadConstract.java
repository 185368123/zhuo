package com.hyphenate.chatuidemo.my.constract;

import android.widget.EditText;

import com.hyphenate.easeui.TeamUnreadBean;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface GetTeamUnreadConstract {
    interface Model extends BaseModel {
        Observable<TeamUnreadBean> getTeamUnread(String token);

    }

    interface View extends BaseView {
        void returnTeamUnread(TeamUnreadBean teamUnreadBean);

    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getTeamUnread();

    }
}
