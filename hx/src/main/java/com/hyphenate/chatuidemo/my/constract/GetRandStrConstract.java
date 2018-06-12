package com.hyphenate.chatuidemo.my.constract;

import android.widget.EditText;

import java.util.List;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import li.com.base.basesinglebean.RandStrBean;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface GetRandStrConstract {
    interface Model extends BaseModel {
        Observable<List<RandStrBean>> getRandStr(String token);

    }

    interface View extends BaseView {

    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getRandStr();
    }
}
