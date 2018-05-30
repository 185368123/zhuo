package com.hyphenate.chatuidemo.my.model;

import com.hyphenate.chatuidemo.my.UserListBean;
import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.constract.SearchUserConstract;

import java.util.List;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;

/**
 * Created by Administrator on 2018/5/23.
 */

public class SearchUserModel implements SearchUserConstract.Model {
    @Override
    public Observable<List<UserListBean>> searchUser(String index, String keyword) {
        return Api.getDefault(HostType.INCLUE_COOKIE).searchUser(index, keyword)
                .compose(RxSchedulers.<BaseRespose<List<UserListBean>>>io_main())
                .compose(RxHelper.<List<UserListBean>>handleResult());
    }
}
