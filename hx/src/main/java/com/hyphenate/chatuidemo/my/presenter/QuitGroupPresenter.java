package com.hyphenate.chatuidemo.my.presenter;

import com.hyphenate.chatuidemo.my.constract.QuitGroupConstract;
import com.hyphenate.easeui.provider.UserInfoProvider;

import li.com.base.baserx.RxSubscriber;

/**
 * Created by Administrator on 2018/5/23.
 */

public class QuitGroupPresenter extends QuitGroupConstract.Presenter {
    @Override
    public void quitGroup(String group_id) {
        mModel.quitGroup(UserInfoProvider.getToken(),group_id).subscribe(new RxSubscriber<Object>(mContext,false) {
            @Override
            protected void _onNext(Object o) {
                mView.quitSudcess();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
