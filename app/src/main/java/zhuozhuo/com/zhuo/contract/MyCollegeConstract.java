package zhuozhuo.com.zhuo.contract;

import com.hyphenate.chatuidemo.my.bean.AllArticleBean;
import com.hyphenate.chatuidemo.my.bean.FriendsCollegeBean;

import java.util.List;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface MyCollegeConstract {
    interface Model extends BaseModel {
        Observable<FriendsCollegeBean> getCollege(String token, String you_user_id, String school);
    }

    interface View extends BaseView {
        void returnCollege(FriendsCollegeBean friendsCollegeBean);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getCollege(String you_user_id);
    }
}
