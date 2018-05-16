package com.hyphenate.chatuidemo.my.model;

import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.constract.GetTeamUnreadConstract;
import com.hyphenate.easeui.TeamUnreadBean;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;

/**
 * Created by Administrator on 2018/4/25.
 */

public class GetTeamUnreadModel implements GetTeamUnreadConstract.Model{
    @Override
    public Observable<TeamUnreadBean> getTeamUnread(String token) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getTeamUnread(token)
                .compose(RxSchedulers.<TeamUnreadBean>io_main());
    }
}
