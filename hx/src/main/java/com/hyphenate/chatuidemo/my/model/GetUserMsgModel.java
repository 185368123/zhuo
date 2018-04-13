package com.hyphenate.chatuidemo.my.model;

import com.hyphenate.chatuidemo.my.constract.GetUserMsgConstract;
import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.bean.UserMsgBean;
import java.util.List;
import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;

/**
 * Created by Administrator on 2018/3/27.
 */

public class GetUserMsgModel implements GetUserMsgConstract.Model{

    @Override
    public Observable<List<UserMsgBean>> getUserMsg(String user_id) {
        return Api.getDefault(HostType.UNINCLUE_COOKIE)
                .getUserMsg(user_id)
                .compose(RxSchedulers.<BaseRespose<List<UserMsgBean>>>io_main())
                .compose(RxHelper.<List<UserMsgBean>>handleResult());
    }


}
