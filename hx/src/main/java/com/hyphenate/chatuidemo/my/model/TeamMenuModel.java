package com.hyphenate.chatuidemo.my.model;

import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.constract.TeamMenuConstract;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;

/**
 * Created by Administrator on 2018/4/23.
 */

public class TeamMenuModel implements TeamMenuConstract.Model {
    @Override
    public Observable<Object> deleteMember(String token, String line_id, String group_id, String type, String you_user_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).deleteMember(token, line_id, group_id, type, you_user_id)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.handleResult());
    }

    @Override
    public Observable<Object> deleteTeam(String token, String line_id, String group_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).deleteTeam(token,line_id,group_id)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.handleResult());
    }
}
