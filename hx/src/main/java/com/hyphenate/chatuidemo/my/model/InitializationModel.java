package com.hyphenate.chatuidemo.my.model;

import com.hyphenate.chatuidemo.my.constract.InitializationConstract;
import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import li.com.base.basesinglebean.SingleStatusBean;
import rx.Observable;

/**
 * Created by Administrator on 2018/3/27.
 */

public class InitializationModel implements InitializationConstract.Model{
    @Override
    public Observable<SingleStatusBean> getSingleStatus(String token) {
        return Api.getDefault(HostType.INCLUE_COOKIE)
                .getSingleStatus(token)
                .compose(RxSchedulers.<BaseRespose<SingleStatusBean>>io_main())
                .compose(RxHelper.<SingleStatusBean>handleResult());
    }

}
