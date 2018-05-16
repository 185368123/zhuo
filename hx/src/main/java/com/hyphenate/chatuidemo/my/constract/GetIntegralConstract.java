package com.hyphenate.chatuidemo.my.constract;

import com.hyphenate.chatuidemo.my.bean.IntegralBean;
import com.hyphenate.chatuidemo.my.bean.TeamScoreBean;

import java.util.List;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface GetIntegralConstract {
    interface Model extends BaseModel {
        Observable<List<IntegralBean>> getIntegral(String token,String hunderd_id);

    }

    interface View extends BaseView {
        void returnIntegral(List<TeamScoreBean> teamScoreBeans);

    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getIntegral(String hunderd_id);

    }
}
