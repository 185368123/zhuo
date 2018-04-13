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

public interface ForgetPasswordConstract {
    interface Model extends BaseModel {
        Observable<List<LoginBean>> changePassword(String phone, String code, String password);
    }

    interface View extends BaseView {
        void changePasswordSucess();
    }

    abstract static class Presenter extends BasePresenter<View, Model> {

        public abstract void changePassword(EditText phone, EditText code, EditText newPassword);

        public abstract void getAnthCode(EditText etPhoneNumber, final Button btGteVerifyCode);

        public abstract void addClearEditListener(ImageView image, EditText editText);
    }
}
