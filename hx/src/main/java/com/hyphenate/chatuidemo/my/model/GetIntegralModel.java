package com.hyphenate.chatuidemo.my.model;

import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.bean.IntegralBean;
import com.hyphenate.chatuidemo.my.constract.GetIntegralConstract;

import java.util.List;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;

/**
 * Created by Administrator on 2018/4/26.
 */

public class GetIntegralModel implements GetIntegralConstract.Model {
    @Override
    public Observable<List<IntegralBean>> getIntegral(String token,String hunderd_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getIntegral(token,hunderd_id)
                .compose(RxSchedulers.<BaseRespose<List<IntegralBean>>>io_main())
                .compose(RxHelper.<List<IntegralBean>>handleResult());
    }
}
