package com.hyphenate.chatuidemo.my.model;

import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.bean.RaceBean;
import com.hyphenate.chatuidemo.my.constract.GetRaceConstract;

import java.util.List;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;

/**
 * Created by Administrator on 2018/4/26.
 */

public class GetRaceModel implements GetRaceConstract.Model {
    @Override
    public Observable<List<RaceBean>> getRace(String token,String hunderd_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getRace(token,hunderd_id)
                .compose(RxSchedulers.<BaseRespose<List<RaceBean>>>io_main())
                .compose(RxHelper.<List<RaceBean>>handleResult());
    }
}
