package zhuozhuo.com.zhuo.model;

import java.util.List;
import java.util.Objects;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;
import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import zhuozhuo.com.zhuo.contract.AnthCodeConstract;

/**
 * Created by Administrator on 2017/10/19.
 */

public class GetAnthCoeModel implements AnthCodeConstract.Model{

    @Override
    public Observable<List<Object>> getAnthCode(String etPhoneNumber, String opt) {
        return Api.getDefault(HostType.UNINCLUE_COOKIE)
                .getAnthCoe(etPhoneNumber,opt)
                .compose(RxSchedulers.<BaseRespose<List<Object>>>io_main())
                .compose(RxHelper.<List<Object>>handleResult());
    }
}
