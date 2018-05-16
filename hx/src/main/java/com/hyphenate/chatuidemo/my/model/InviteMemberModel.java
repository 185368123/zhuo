package com.hyphenate.chatuidemo.my.model;

import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.bean.CupMemberBean;
import com.hyphenate.chatuidemo.my.bean.CupTeamBean;
import com.hyphenate.chatuidemo.my.constract.CreatTeamConstract;
import com.hyphenate.chatuidemo.my.constract.InviteMemberConstract;

import java.util.List;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;

/**
 * Created by Administrator on 2018/4/21.
 */

public class InviteMemberModel implements InviteMemberConstract.Model {

    @Override
    public Observable<List<CupMemberBean>> getAllMember(String token, String page,String page_size,String nick_name) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getAllMember(token,page,page_size,nick_name)
                .compose(RxSchedulers.<BaseRespose<List<CupMemberBean>>>io_main())
                .compose(RxHelper.<List<CupMemberBean>>handleResult());
    }

    @Override
    public Observable<List<CupMemberBean>> getFriends(String token, String user_ids, String page, String page_size, String nick_name) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getFriends(token, user_ids, page, page_size, nick_name)
                .compose(RxSchedulers.<BaseRespose<List<CupMemberBean>>>io_main())
                .compose(RxHelper.<List<CupMemberBean>>handleResult());
    }

    @Override
    public Observable<Object> inviteMember(String token, String line_id, String you_user_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).inviteMember(token,line_id,you_user_id)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.handleResult());
    }

    @Override
    public Observable<Object> applyTeam(String token, String line_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).applyTeam(token,line_id)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.handleResult());
    }

    @Override
    public Observable<List<CupTeamBean>> getTeam(String token) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getTeam(token)
                .compose(RxSchedulers.<BaseRespose<List<CupTeamBean>>>io_main())
                .compose(RxHelper.<List<CupTeamBean>>handleResult());
    }
}
