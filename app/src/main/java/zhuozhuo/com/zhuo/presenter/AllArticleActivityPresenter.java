package zhuozhuo.com.zhuo.presenter;

import com.hyphenate.chatuidemo.my.bean.AllArticleBean;

import java.util.List;

import li.com.base.baserx.RxSubscriber;
import zhuozhuo.com.zhuo.contract.AllArticleActivityConstract;

/**
 * Created by Administrator on 2018/4/9.
 */

public class AllArticleActivityPresenter extends AllArticleActivityConstract.Presenter {
    @Override
    public void getAllArticle(String index) {
        mModel.getAllArticle(index).subscribe(new RxSubscriber<List<AllArticleBean>>(mContext,false) {
            @Override
            protected void _onNext(List<AllArticleBean> allArticleBeans) {
                mView.returnAllArticle(allArticleBeans);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
