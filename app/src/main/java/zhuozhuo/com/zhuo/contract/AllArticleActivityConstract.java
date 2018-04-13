package zhuozhuo.com.zhuo.contract;

import android.widget.Button;
import android.widget.EditText;

import com.hyphenate.chatuidemo.my.bean.AllArticleBean;

import java.util.List;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface AllArticleActivityConstract {
    interface Model extends BaseModel {
        Observable<List<AllArticleBean>> getAllArticle(String index);
    }

    interface View extends BaseView {
        void returnAllArticle(List<AllArticleBean> allArticleBeans);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getAllArticle(String index);
    }
}
