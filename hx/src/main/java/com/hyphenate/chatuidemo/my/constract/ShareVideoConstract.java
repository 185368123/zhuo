package com.hyphenate.chatuidemo.my.constract;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface ShareVideoConstract {
    interface Model extends BaseModel {
        Observable<Object> shareVideo(String token, String video_link);

    }

    interface View extends BaseView {
        void shareSudcess();

    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void shareVideo(String video_link);

    }
}
