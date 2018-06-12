package com.hyphenate.chatuidemo.my.model;

import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.constract.GetRandStrConstract;

import java.util.List;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import li.com.base.basesinglebean.RandStrBean;
import rx.Observable;

/**
 * Created by Administrator on 2018/6/12.
 */

public class GetRandStrModel implements GetRandStrConstract.Model {
    @Override
    public Observable<List<RandStrBean>> getRandStr(String token) {
        return Api.getDefault(HostType.INCLUE_COOKIE)
                .getRandStr(token)
                .compose(RxSchedulers.<BaseRespose<List<RandStrBean>>>io_main())
                .compose(RxHelper.<List<RandStrBean>>handleResult());
    }
}
