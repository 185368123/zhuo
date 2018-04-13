package zhuozhuo.com.zhuo.contract;

import android.widget.Button;
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

public interface RegisterConstract {
    interface Model extends BaseModel {
        Observable<List<LoginBean>> register(String phone, String password,String code,String nick_name,String sex);
    }

    interface View extends BaseView {
        void registerSucess();
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void register(EditText phone, EditText password, EditText code, EditText nick_name, String sex);

        public abstract void getAnthCode(EditText etPhoneNumber, final Button btGteVerifyCode);

        public abstract void addClearEditListener(ImageView image, EditText editText);
    }
}
