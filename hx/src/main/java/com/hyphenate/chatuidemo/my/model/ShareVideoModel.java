package com.hyphenate.chatuidemo.my.model;

import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.constract.ShareVideoConstract;
import com.hyphenate.easeui.provider.UserInfoProvider;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;

/**
 * Created by Administrator on 2018/5/23.
 */

public class ShareVideoModel implements ShareVideoConstract.Model {
    @Override
    public Observable<Object> shareVideo(String token, String video_link) {
        return Api.getDefault(HostType.INCLUE_COOKIE).shareVideo(token,video_link)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.handleResult());
    }
}
