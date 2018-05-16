package com.hyphenate.chatuidemo.my.constract;

import android.widget.EditText;

import com.hyphenate.chatuidemo.my.bean.UserMsgBean;

import java.util.List;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface CreatTeamConstract {
    interface Model extends BaseModel {
        Observable<Object> getUserMsg(String token,String group_name,String game_name);

    }

    interface View extends BaseView {
        void returnUserMsg();

    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getUserMsg(EditText editText1,EditText editText2);

    }
}
