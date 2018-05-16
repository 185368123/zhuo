package zhuozhuo.com.zhuo.model;

import com.hyphenate.easeui.provider.UserInfoProvider;

import java.util.List;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;
import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.bean.UserOnlineBean;
import com.hyphenate.chatuidemo.my.api.HostType;
import zhuozhuo.com.zhuo.contract.InviteConstract;

/**
 * Created by Administrator on 2018/3/26.
 */

public class InviteModel implements InviteConstract.Model {
    @Override
    public Observable<List<UserOnlineBean>> getUserList(int index) {
        return Api.getDefault(HostType.INCLUE_COOKIE)
                .getUserList(UserInfoProvider.getToken(),index+"")
                .compose(RxSchedulers.<BaseRespose<List<UserOnlineBean>>>io_main())
                .compose(RxHelper.<List<UserOnlineBean>>handleResult());
    }

    @Override
    public Observable<List<Object>> invite(String token, String team_id, String user_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE)
                .invite(token,team_id,user_id)
                .compose(RxSchedulers.<BaseRespose<List<Object>>>io_main())
                .compose(RxHelper.<List<Object>>handleResult());
    }
}
