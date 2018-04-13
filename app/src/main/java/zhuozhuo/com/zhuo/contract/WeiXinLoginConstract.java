package zhuozhuo.com.zhuo.contract;

import java.util.List;
import java.util.Map;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;
import com.hyphenate.chatuidemo.my.bean.LoginBean;

/**
 * Created by Administrator on 2018/3/10.
 */

public interface WeiXinLoginConstract {
    interface Model extends BaseModel {
        Observable< List<LoginBean> > login(String wechat_id, String nick_name,String photo_link,String sex,String location);
    }

    interface View extends BaseView {
        void returnLoginBean(List<LoginBean> loginBeen);
    }

    abstract static class Presenter extends BasePresenter<WeiXinLoginConstract.View, WeiXinLoginConstract.Model> {
        public abstract void login(Map<String, String> data);
    }
}
