package zhuozhuo.com.zhuo.presenter;

import com.hyphenate.chatuidemo.my.bean.MineArticleBean;
import com.hyphenate.chatuidemo.provider.UserInfoProvider;
import java.util.List;
import li.com.base.baserx.RxSubscriber;
import zhuozhuo.com.zhuo.contract.MineArticleConstract;

/**
 * Created by Administrator on 2018/4/9.
 */

public class MineArticleActivityPresenter extends MineArticleConstract.Presenter {
    @Override
    public void getMyArticle(String index) {
        mModel.getMyArticle(UserInfoProvider.getToken(),index).subscribe(new RxSubscriber<List<MineArticleBean>>(mContext,false) {
            @Override
            protected void _onNext(List<MineArticleBean> mineArticleBeans) {
                mView.returnMyArticlr(mineArticleBeans);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
