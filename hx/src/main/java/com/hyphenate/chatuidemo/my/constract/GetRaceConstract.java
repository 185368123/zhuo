package com.hyphenate.chatuidemo.my.constract;

import android.widget.EditText;

import com.hyphenate.chatuidemo.my.bean.ConfrontationBean;
import com.hyphenate.chatuidemo.my.bean.RaceBean;

import java.util.List;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface GetRaceConstract {
    interface Model extends BaseModel {
        Observable<List<RaceBean>> getRace(String token,String hunderd_id);

    }

    interface View extends BaseView {
        void returnRace(List<ConfrontationBean>  list);

    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getRace(String hunderd_id);

    }
}
