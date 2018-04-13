package com.hyphenate.chatuidemo.my.model;

import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.bean.RateBean;
import com.hyphenate.chatuidemo.my.constract.TableActivityConstract;

import java.util.List;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;

/**
 * Created by Administrator on 2018/3/27.
 */

public class TableActivityModel implements TableActivityConstract.Model{
    @Override
    public Observable<List<RateBean>> getGroupRate(String token, String group_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getGroupRate(token,group_id)
                .compose(RxSchedulers.<BaseRespose<List<RateBean>>>io_main())
                .compose(RxHelper.<List<RateBean>>handleResult());
    }
}
