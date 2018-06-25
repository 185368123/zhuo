package com.hyphenate.chatuidemo.my.presenter;

import com.hyphenate.chatuidemo.my.bean.JoinGroupBean;
import com.hyphenate.chatuidemo.my.constract.UserProfileConstract;
import com.hyphenate.easeui.provider.UserInfoProvider;

import li.com.base.baserx.RxSubscriber;

/**
 * Created by Administrator on 2018/6/20.
 */

public class UserProfilePresenter extends UserProfileConstract.Presenter {
    @Override
    public void joinUserGroup(String nick_name, String you_user_id) {
        mModel.joinUserGroup(UserInfoProvider.getToken(),nick_name,you_user_id).subscribe(new RxSubscriber<JoinGroupBean>(mContext,false) {
            @Override
            protected void _onNext(JoinGroupBean groupId) {

                mView.joinUserGroupSucess(groupId);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
