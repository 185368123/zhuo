package zhuozhuo.com.zhuo.model;

import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.bean.FriendsCollegeBean;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;
import zhuozhuo.com.zhuo.contract.MyCollegeConstract;

/**
 * Created by Administrator on 2018/6/15.
 */

public class MyCollegeModel implements MyCollegeConstract.Model {
    @Override
    public Observable<FriendsCollegeBean> getCollege(String token, String you_user_id, String school) {
        return Api.getDefault(HostType.INCLUE_COOKIE)
                .getCollege(token, you_user_id,school)
                .compose(RxSchedulers.<BaseRespose<FriendsCollegeBean>>io_main())
                .compose(RxHelper.<FriendsCollegeBean>handleResult());
    }
}
