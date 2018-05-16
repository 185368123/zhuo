package com.hyphenate.chatuidemo.my.presenter;

import com.hyphenate.chatuidemo.my.constract.GetTeamUnreadConstract;
import com.hyphenate.easeui.TeamUnreadBean;
import com.hyphenate.easeui.provider.UserInfoProvider;

import li.com.base.baserx.RxSubscriber;

/**
 * Created by Administrator on 2018/4/25.
 */

public class GetTeamUnreadPresenter extends GetTeamUnreadConstract.Presenter {
    @Override
    public void getTeamUnread() {
        mModel.getTeamUnread(UserInfoProvider.getToken()).subscribe(new RxSubscriber<TeamUnreadBean>(mContext,false) {
            @Override
            protected void _onNext(TeamUnreadBean teamUnreadBean) {
              mView.returnTeamUnread(teamUnreadBean);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
