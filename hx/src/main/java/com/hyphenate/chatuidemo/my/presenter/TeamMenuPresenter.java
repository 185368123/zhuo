package com.hyphenate.chatuidemo.my.presenter;

import com.hyphenate.chatuidemo.my.constract.TeamMenuConstract;
import com.hyphenate.easeui.provider.UserInfoProvider;

import li.com.base.baserx.RxSubscriber;

/**
 * Created by Administrator on 2018/4/23.
 */

public class TeamMenuPresenter extends TeamMenuConstract.Presenter {
    @Override
    public void deleteMember(String line_id, String group_id, String type, String you_user_id) {
        mModel.deleteMember(UserInfoProvider.getToken(), line_id, group_id, type, you_user_id).subscribe(new RxSubscriber<Object>(mContext, false) {
            @Override
            protected void _onNext(Object o) {
                mView.deleteMemberSucess();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void deleteTeam(String line_id, String group_id) {
        mModel.deleteTeam(UserInfoProvider.getToken(), line_id, group_id).subscribe(new RxSubscriber<Object>(mContext, false) {
            @Override
            protected void _onNext(Object o) {
                mView.deleteTeamSucess();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
