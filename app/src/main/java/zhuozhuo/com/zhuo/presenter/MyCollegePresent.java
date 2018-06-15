package zhuozhuo.com.zhuo.presenter;

import com.hyphenate.chatuidemo.my.bean.FriendsCollegeBean;
import com.hyphenate.easeui.provider.UserInfoProvider;

import li.com.base.baserx.RxSubscriber;
import zhuozhuo.com.zhuo.contract.MyCollegeConstract;

/**
 * Created by Administrator on 2018/6/15.
 */

public class MyCollegePresent extends MyCollegeConstract.Presenter {
    @Override
    public void getCollege(String you_user_id) {
        mModel.getCollege(UserInfoProvider.getToken(), you_user_id, UserInfoProvider.getLocation().split(",")[0]).subscribe(new RxSubscriber<FriendsCollegeBean>(mContext, false) {
            @Override
            protected void _onNext(FriendsCollegeBean o) {
                mView.returnCollege(o);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
