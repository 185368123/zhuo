package com.hyphenate.chatuidemo.my.presenter;

import com.hyphenate.chatuidemo.my.constract.ShareVideoConstract;
import com.hyphenate.easeui.provider.UserInfoProvider;

import li.com.base.baserx.RxSubscriber;

/**
 * Created by Administrator on 2018/5/23.
 */

public class ShareVideoPresenter extends ShareVideoConstract.Presenter {
    @Override
    public void shareVideo(String video_link) {
        mModel.shareVideo(UserInfoProvider.getToken(),video_link).subscribe(new RxSubscriber<Object>(mContext,false) {
            @Override
            protected void _onNext(Object o) {
                mView.shareSudcess();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
