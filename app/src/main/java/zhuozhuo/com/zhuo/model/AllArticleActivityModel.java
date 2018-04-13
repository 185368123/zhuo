package zhuozhuo.com.zhuo.model;

import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.bean.AllArticleBean;
import java.util.List;
import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;
import zhuozhuo.com.zhuo.contract.AllArticleActivityConstract;
import zhuozhuo.com.zhuo.contract.MineArticleConstract;

/**
 * Created by Administrator on 2018/4/9.
 */

public class AllArticleActivityModel implements AllArticleActivityConstract.Model {
    @Override
    public Observable<List<AllArticleBean>> getAllArticle(String index) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getAllArticle(index)
                .compose(RxSchedulers.<BaseRespose<List<AllArticleBean>>>io_main())
                .compose(RxHelper.<List<AllArticleBean>>handleResult());
    }
}
