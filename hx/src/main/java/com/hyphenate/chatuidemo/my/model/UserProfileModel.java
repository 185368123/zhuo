package com.hyphenate.chatuidemo.my.model;

import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.bean.JoinGroupBean;
import com.hyphenate.chatuidemo.my.constract.UserProfileConstract;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;

/**
 * Created by Administrator on 2018/6/20.
 */

public class UserProfileModel implements UserProfileConstract.Model {
    @Override
    public Observable<JoinGroupBean> joinUserGroup(String token, String nick_name, String you_user_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE)
                .joinUserGroup(token, nick_name, you_user_id)
                .compose(RxSchedulers.<BaseRespose<JoinGroupBean>>io_main())
                .compose(RxHelper.<JoinGroupBean>handleResult());
    }
}
