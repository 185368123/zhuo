package com.hyphenate.chatuidemo.my.constract;

import android.widget.EditText;

import com.hyphenate.chatuidemo.my.bean.JoinGroupBean;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface UserProfileConstract {
    interface Model extends BaseModel {
        Observable<JoinGroupBean> joinUserGroup(String token, String nick_name, String you_user_id);

    }

    interface View extends BaseView {
        void joinUserGroupSucess(JoinGroupBean joinGroupBean);

    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void joinUserGroup(String nick_name, String you_user_id);

    }
}
