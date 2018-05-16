package com.hyphenate.chatuidemo.my.presenter;

import com.hyphenate.chatuidemo.my.bean.CupMemberBean;
import com.hyphenate.chatuidemo.my.bean.CupTeamBean;
import com.hyphenate.chatuidemo.my.constract.InviteMemberConstract;
import com.hyphenate.easeui.provider.UserInfoProvider;

import java.util.List;

import li.com.base.baserx.RxSubscriber;


/**
 * Created by Administrator on 2018/4/21.
 */

public class InviteMemberPresenter extends InviteMemberConstract.Presenter {


    @Override
    public void getAllMember(String page,String page_size,String nick_name) {
        mModel.getAllMember(UserInfoProvider.getToken(),page,page_size,nick_name).subscribe(new RxSubscriber<List<CupMemberBean>>(mContext,false) {
            @Override
            protected void _onNext(List<CupMemberBean> data) {
                mView.returnAllMember(data);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void getFriends(String user_ids, String page, String page_size, String nick_name) {
        mModel.getFriends(UserInfoProvider.getToken(),user_ids,page,page_size,nick_name).subscribe(new RxSubscriber<List<CupMemberBean>>(mContext,false) {
            @Override
            protected void _onNext(List<CupMemberBean> data) {
                mView.returnFriendsr(data);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void inviteMember(String line_id, String you_user_id) {
        mModel.inviteMember(UserInfoProvider.getToken(),line_id,you_user_id).subscribe(new RxSubscriber<Object>(mContext,false) {
            @Override
            protected void _onNext(Object o) {

            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void applyTeam(String line_id) {
        mModel.applyTeam(UserInfoProvider.getToken(),line_id).subscribe(new RxSubscriber<Object>(mContext,false) {
            @Override
            protected void _onNext(Object o) {

            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void getTeam() {
        mModel.getTeam(UserInfoProvider.getToken()).subscribe(new RxSubscriber<List<CupTeamBean>>(mContext,false) {
            @Override
            protected void _onNext(List<CupTeamBean> cupTeamBeans) {
            mView.returnTeam(cupTeamBeans);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
