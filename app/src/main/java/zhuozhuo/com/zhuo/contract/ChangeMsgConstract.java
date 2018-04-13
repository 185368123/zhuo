package zhuozhuo.com.zhuo.contract;

import android.widget.Button;
import android.widget.EditText;

import java.util.List;
import java.util.Objects;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface ChangeMsgConstract {
    interface Model extends BaseModel {
        Observable<List<Object>> changeMsg(String token,String key,String value);
    }

    interface View extends BaseView {
        void changeMsgSucess();
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void changeMsg(String key,String value);
    }
}
