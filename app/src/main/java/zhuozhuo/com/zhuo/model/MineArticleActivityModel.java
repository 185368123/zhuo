package zhuozhuo.com.zhuo.model;

import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.bean.MineArticleBean;
import java.util.List;
import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;
import zhuozhuo.com.zhuo.contract.MineArticleConstract;

/**
 * Created by Administrator on 2018/4/9.
 */

public class MineArticleActivityModel implements MineArticleConstract.Model {
    @Override
    public Observable<List<MineArticleBean>> getMyArticle(String token, String index) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getMyArticle(token,index)
                .compose(RxSchedulers.<BaseRespose<List<MineArticleBean>>>io_main())
                .compose(RxHelper.<List<MineArticleBean>>handleResult());
    }
}
