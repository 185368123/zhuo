package com.hyphenate.chatuidemo.my.constract;


import java.util.List;
import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import li.com.base.basesinglebean.SingleStatusBean;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface InitializationConstract {
    interface Model extends BaseModel {
        Observable<SingleStatusBean> getSingleStatus(String token);

    }

    interface View extends BaseView {
        void returnSingleStatus(SingleStatusBean singleStatusBean);

    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getSingleStatus();

    }
}
