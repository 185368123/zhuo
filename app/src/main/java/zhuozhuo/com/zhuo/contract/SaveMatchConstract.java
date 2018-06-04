package zhuozhuo.com.zhuo.contract;

import com.hyphenate.chatuidemo.my.bean.SaveMatchBean;

import java.util.List;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface SaveMatchConstract {
    interface Model extends BaseModel {
        Observable<List<SaveMatchBean> > getSaveMatch(String token,String page,String page_size);

        Observable<Object> relieveLine(String token,String you_user_id,String type);
    }

    interface View extends BaseView {
        public void returnSaveMatch(List<SaveMatchBean> saveMatchBeans);

        public void relieveLineSucess();
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getSaveMatch(String page,String page_size);

        public abstract void relieveLine(String you_user_id,String type);
    }
}
