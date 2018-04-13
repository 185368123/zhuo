package zhuozhuo.com.zhuo.contract;

import java.util.List;
import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;
import com.hyphenate.chatuidemo.my.bean.UserOnlineBean;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface InviteConstract {
    interface Model extends BaseModel {
        Observable<List<UserOnlineBean>> getUserList(int index);

        Observable<List<Object>> invite(String token,String team_id,String user_id);
    }

    interface View extends BaseView {
        void returnUserList(List<UserOnlineBean> data);

        void inviteSucess();
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getUserList(int index);

        public abstract void invite(String team_id,String user_id);

    }
}
