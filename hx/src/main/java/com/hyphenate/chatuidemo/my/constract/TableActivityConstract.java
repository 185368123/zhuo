package com.hyphenate.chatuidemo.my.constract;

import com.hyphenate.chatuidemo.my.bean.RateBean;

import java.util.List;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface TableActivityConstract {
    interface Model extends BaseModel {
        Observable<List<RateBean>> getGroupRate(String token, String group_id);

    }

    interface View extends BaseView {
        void returnGroupRate(List<RateBean> rateBeans);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getGroupRate(String group_id);
    }
}
