package com.hyphenate.chatuidemo.my.constract;

import com.hyphenate.chatuidemo.my.bean.VideoLinkBean;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;


public interface ChatFragmentConstract {
    interface Model extends BaseModel {
        Observable<Object> remark(String token, String userId, String remark, String score, String card);

        Observable<Object> changeFinish(String token, String user_id);

        Observable<Object> setStepPhtot(String token, String photo_link, String you_user_id, String thumb_link);

        Observable<Object> setMoney(String token, String choice_money, String group_id);

        Observable<VideoLinkBean> getVideo(String token, String you_user_id);

        Observable<Object> startVideo(String token, String video_id);
    }

    interface View extends BaseView {

        void remarkSucess();

        void setStepPhtotSucess();

        void setMoneySucess();

        void returnVieo(String video_link);

    }

    abstract static class Presenter extends BasePresenter<View, Model> {

        public abstract void remark(String userId, String score, String card);

        public abstract void changeFinish(String user_id);

        public abstract void setStepPhtot(String photo_link, String you_user_id, String thumb_link);

        public abstract void setMoney(String choice_money, String group_id);

        public abstract void getVideo(String you_user_id);

        public abstract void startVideo(String video_id);
    }
}
