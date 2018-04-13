package zhuozhuo.com.zhuo.contract;

import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;
import com.hyphenate.chatuidemo.my.bean.LoginBean;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface LoginConstract {
    interface Model extends BaseModel {
        Observable< List<LoginBean> > returnLoginBean(String userName, String password);
    }

    interface View extends BaseView {
        void returnLoginBean(List<LoginBean> loginBeen);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void returnLoginBean(EditText etUserName, EditText etPassword);

        public abstract void addClearEditListener(ImageView image, EditText etUserName);
    }
}
