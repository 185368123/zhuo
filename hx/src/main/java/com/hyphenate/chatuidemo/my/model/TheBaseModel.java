package com.hyphenate.chatuidemo.my.model;

import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.constract.TheBaseConstract;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;

/**
 * Created by Administrator on 2018/4/24.
 */

public class TheBaseModel implements TheBaseConstract.Model {
    @Override
    public Observable<Object> receiveInvite(String token, String line_id, String group_id, String nick_user_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).receiveInvite(token, line_id, group_id, nick_user_id)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.handleResult());
    }

    @Override
    public Observable<Object> refuseInvite(String token, String line_id, String group_id, String nick_user_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).refuseInvite(token, line_id, group_id, nick_user_id)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.handleResult());
    }

    @Override
    public Observable<Object> refuseApply(String token, String line_id, String you_user_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).refuseApply(token, line_id, you_user_id)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.handleResult());
    }

    @Override
    public Observable<Object> receiveApply(String token, String line_id, String you_user_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).receiveApply(token, line_id, you_user_id)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.handleResult());
    }

    @Override
    public Observable<Object> accept(String token, String from_user_id, String team_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).accept(token, from_user_id, team_id)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.handleResult());
    }

    @Override
    public Observable<Object> deny(String token, String from_user_id, String team_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).deny(token, from_user_id, team_id)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.handleResult());
    }
}
