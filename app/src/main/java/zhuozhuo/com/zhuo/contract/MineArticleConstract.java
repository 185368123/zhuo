package zhuozhuo.com.zhuo.contract;

import com.hyphenate.chatuidemo.my.bean.MineArticleBean;
import java.util.List;
import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface MineArticleConstract {
    interface Model extends BaseModel {
        Observable<List<MineArticleBean>> getMyArticle(String token, String index);
    }

    interface View extends BaseView {
        void returnMyArticlr(List<MineArticleBean> mineArticleBeans);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getMyArticle(String index);
    }
}
