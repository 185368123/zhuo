package com.hyphenate.chatuidemo.my.model;

import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.constract.CreatTeamConstract;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;

/**
 * Created by Administrator on 2018/4/21.
 */

public class CreatTeamModel implements CreatTeamConstract.Model {
    @Override
    public Observable<Object> getUserMsg(String token, String group_name, String game_name) {
        return Api.getDefault(HostType.INCLUE_COOKIE).creatTeam(token,group_name,game_name)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.handleResult());
    }
}
