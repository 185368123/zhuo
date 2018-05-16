package com.hyphenate.chatuidemo.my.presenter;

import com.hyphenate.chatuidemo.my.constract.TheBaseConstract;
import com.hyphenate.easeui.provider.UserInfoProvider;

import li.com.base.baserx.RxSubscriber;

/**
 * Created by Administrator on 2018/4/24.
 */

public class TheBasePresenter extends TheBaseConstract.Presenter {
    @Override
    public void receiveInvite(String line_id, String group_id, String nick_user_id) {
        mModel.receiveInvite(UserInfoProvider.getToken(), line_id, group_id, nick_user_id).subscribe(new RxSubscriber<Object>(mContext, false) {
            @Override
            protected void _onNext(Object o) {
                mView.receiveInviteSucess();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void refuseInvite(String line_id, String group_id, String nick_user_id) {
        mModel.refuseInvite(UserInfoProvider.getToken(), line_id, group_id, nick_user_id).subscribe(new RxSubscriber<Object>(mContext, false) {
            @Override
            protected void _onNext(Object o) {
                mView.refuseInviteSucess();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void refuseApply(String line_id, String you_user_id) {
        mModel.refuseApply(UserInfoProvider.getToken(),line_id,you_user_id).subscribe(new RxSubscriber<Object>(mContext,false) {
            @Override
            protected void _onNext(Object o) {
                mView.receiveApplySucess();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void receiveApply(String line_id, String you_user_id) {
        mModel.receiveApply(UserInfoProvider.getToken(),line_id,you_user_id).subscribe(new RxSubscriber<Object>(mContext,false) {
            @Override
            protected void _onNext(Object o) {
                mView.receiveApplySucess();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void accept(String from_user_id, String team_id) {
        mModel.accept(UserInfoProvider.getToken(),from_user_id,team_id).subscribe(new RxSubscriber<Object>(mContext,false) {
            @Override
            protected void _onNext(Object o) {
                mView.acceptSucess();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void deny(String from_user_id, String team_id) {
        mModel.deny(UserInfoProvider.getToken(),from_user_id,team_id).subscribe(new RxSubscriber<Object>(mContext,false) {
            @Override
            protected void _onNext(Object o) {
                mView.denySucess();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
