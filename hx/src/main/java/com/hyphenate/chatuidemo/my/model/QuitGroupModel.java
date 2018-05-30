package com.hyphenate.chatuidemo.my.model;

import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.constract.QuitGroupConstract;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;

/**
 * Created by Administrator on 2018/5/23.
 */

public class QuitGroupModel implements QuitGroupConstract.Model {
    @Override
    public Observable<Object> quitGroup(String token, String group_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).qiutGroup(token, group_id)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.handleResult());
    }
}
