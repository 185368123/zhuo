package zhuozhuo.com.zhuo.presenter;

import com.hyphenate.chatuidemo.provider.UserInfoProvider;

import java.util.List;
import li.com.base.baserx.RxSubscriber;
import com.hyphenate.chatuidemo.my.bean.UserOnlineBean;
import zhuozhuo.com.zhuo.contract.InviteConstract;

/**
 * Created by Administrator on 2018/3/26.
 */

public class InvitePresenter extends InviteConstract.Presenter {

    @Override
    public void getUserList(int index) {
        mModel.getUserList(index).subscribe(new RxSubscriber<List<UserOnlineBean>>(mContext,false) {
            @Override
            protected void _onNext(List<UserOnlineBean> userOnlineBeans) {
                mView.returnUserList(userOnlineBeans);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void invite(String team_id, String user_id) {
        mModel.invite(UserInfoProvider.getToken(),team_id,user_id).subscribe(new RxSubscriber<List<Object>>(mContext,false) {
            @Override
            protected void _onNext(List<Object> objects) {
                mView.inviteSucess();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
