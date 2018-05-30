package zhuozhuo.com.zhuo.model;

import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.bean.SaveMatchBean;

import java.util.List;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;
import zhuozhuo.com.zhuo.contract.SaveMatchConstract;

/**
 * Created by Administrator on 2018/5/29.
 */

public class SaveMatchModel implements SaveMatchConstract.Model {
    @Override
    public Observable<List<SaveMatchBean>> getSaveMatch(String token,String page,String page_size) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getSaveUser(token, page, page_size)
                .compose(RxSchedulers.<BaseRespose<List<SaveMatchBean>>>io_main())
                .compose(RxHelper.<List<SaveMatchBean>>handleResult());
    }
}
