package zhuozhuo.com.zhuo.model;

import java.util.List;
import java.util.Objects;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;
import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import zhuozhuo.com.zhuo.contract.ChangeMsgConstract;

/**
 * Created by Administrator on 2017/10/21.
 */

public class ChangeMsgModel implements ChangeMsgConstract.Model{

    @Override
    public Observable<List<Object>> changeMsg(String token, String key, String value) {
        return Api.getDefault(HostType.UNINCLUE_COOKIE)
                .changeMsg(token,key,value)
                .compose(RxSchedulers.<BaseRespose<List<Object>>>io_main())
                .compose(RxHelper.<List<Object>>handleResult());
    }
}
