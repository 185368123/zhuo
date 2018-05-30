package com.hyphenate.chatuidemo.my.constract;

import com.hyphenate.chatuidemo.my.UserListBean;

import java.util.List;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface SearchUserConstract {
    interface Model extends BaseModel {
        Observable<List<UserListBean>> searchUser(String index, String keyword);

    }

    interface View extends BaseView {
        void returnUser(List<UserListBean>  data);

    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void searchUser(String index, String keyword);

    }
}
