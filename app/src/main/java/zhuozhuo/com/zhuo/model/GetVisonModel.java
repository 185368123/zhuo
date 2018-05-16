package zhuozhuo.com.zhuo.model;

import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import li.com.base.basesinglebean.VisonBean;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;
import zhuozhuo.com.zhuo.contract.GetVisonConstract;

/**
 * Created by Administrator on 2018/4/4.
 */

public class GetVisonModel implements GetVisonConstract.Model {
    @Override
    public Observable<VisonBean> getVison(String type) {
        return Api.getDefault(HostType.UNINCLUE_COOKIE).getVison(type)
                .compose(RxSchedulers.<BaseRespose<VisonBean>>io_main())
                .compose(RxHelper.<VisonBean>handleResult());
    }
}
