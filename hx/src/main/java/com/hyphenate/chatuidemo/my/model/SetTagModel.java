package com.hyphenate.chatuidemo.my.model;

import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.bean.SuggestTagBean;
import com.hyphenate.chatuidemo.my.constract.SetTagConstract;

import java.util.List;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;

/**
 * Created by Administrator on 2018/5/10.
 */

public class SetTagModel implements SetTagConstract.Model {
    @Override
    public Observable<List<SuggestTagBean>> getSuggestTag(String token) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getSuggestTag(token)
                .compose(RxSchedulers.<BaseRespose<List<SuggestTagBean>>>io_main())
                .compose(RxHelper.<List<SuggestTagBean>>handleResult());
    }

    @Override
    public Observable<Object> setTag(String token, String choice_id, String you_user_id, String label) {
        return Api.getDefault(HostType.INCLUE_COOKIE).setTag(token, choice_id, you_user_id, label)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.handleResult());
    }
}
