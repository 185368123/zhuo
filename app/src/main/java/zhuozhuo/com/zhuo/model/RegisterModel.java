package zhuozhuo.com.zhuo.model;


import java.util.List;
import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;
import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.bean.LoginBean;
import com.hyphenate.chatuidemo.my.api.HostType;
import zhuozhuo.com.zhuo.contract.RegisterConstract;

/**
 * Created by Administrator on 2017/10/19.
 */

public class RegisterModel implements RegisterConstract.Model{

    /**
     * 发送 注册 请求
     */
    public Observable<List<LoginBean>> register(String phone, String password, String code, String nick_name, String sex) {
        return Api.getDefault(HostType.UNINCLUE_COOKIE)
                .register(phone,password,code,nick_name,sex)
                .compose(RxSchedulers.<BaseRespose<List<LoginBean>>>io_main())
                .compose(RxHelper.<List<LoginBean>>handleResult());
    }

}
