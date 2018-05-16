package com.hyphenate.chatuidemo.my.constract;

import com.hyphenate.chatuidemo.my.bean.SuggestTagBean;
import com.hyphenate.chatuidemo.my.easytagdragview.bean.Tip;

import java.util.List;
import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface SetTagConstract {
    interface Model extends BaseModel {
        Observable<List<SuggestTagBean>> getSuggestTag(String token);

        Observable<Object> setTag(String token,String choice_id,String you_user_id,String label);
    }

    interface View extends BaseView {
        void returnSuggestTag(List<SuggestTagBean> suggestTagBeans);

        void setTagSucess();
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getSuggestTag();

        public abstract void setTag(String choice_id,String you_user_id,List<Tip> tips);
    }
}
