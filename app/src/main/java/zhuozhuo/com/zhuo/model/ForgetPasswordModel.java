package zhuozhuo.com.zhuo.model;

import java.util.List;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;
import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.bean.LoginBean;
import com.hyphenate.chatuidemo.my.api.HostType;
import zhuozhuo.com.zhuo.contract.ForgetPasswordConstract;

/**
 * Created by Administrator on 2017/10/19.
 */

public class ForgetPasswordModel implements ForgetPasswordConstract.Model {

    @Override
    public Observable<List<LoginBean>> changePassword(String phone, String code, String password) {
        return Api.getDefault(HostType.UNINCLUE_COOKIE)
                .changePassword(phone,code,password)
                .compose(RxSchedulers.<BaseRespose<List<LoginBean>>>io_main())
                .compose(RxHelper.<List<LoginBean>>handleResult());
    }
}
