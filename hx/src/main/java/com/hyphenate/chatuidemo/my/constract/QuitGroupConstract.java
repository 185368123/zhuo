package com.hyphenate.chatuidemo.my.constract;

import com.hyphenate.easeui.TeamUnreadBean;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface QuitGroupConstract {
    interface Model extends BaseModel {
        Observable<Object> quitGroup(String token,String group_id);

    }

    interface View extends BaseView {
        void quitSudcess();

    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void quitGroup(String group_id);

    }
}
