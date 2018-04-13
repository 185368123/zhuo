package zhuozhuo.com.zhuo.model;


import java.util.List;
import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;
import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.bean.LoginBean;
import com.hyphenate.chatuidemo.my.api.HostType;
import zhuozhuo.com.zhuo.contract.LoginConstract;
import zhuozhuo.com.zhuo.util.MD5Utils;

/**
 * Created by Administrator on 2017/10/17.
 */

public class LoginModel implements LoginConstract.Model {
    @Override
    public Observable<List<LoginBean>> returnLoginBean(String userName, String password) {
        return Api.getDefault(HostType.UNINCLUE_COOKIE)
                .login(userName,MD5Utils.getAddmd5(password))
                .compose(RxSchedulers.<BaseRespose<List<LoginBean>>>io_main())
                .compose(RxHelper.<List<LoginBean>>handleResult());
    }
}
