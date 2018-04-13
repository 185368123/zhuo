package zhuozhuo.com.zhuo.model;

import java.util.List;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;
import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.bean.LoginBean;
import com.hyphenate.chatuidemo.my.api.HostType;
import zhuozhuo.com.zhuo.contract.WeiXinLoginConstract;


/**
 * Created by Administrator on 2017/10/21.
 */

public class WeiXinLoginModel implements WeiXinLoginConstract.Model {

    @Override
    public Observable<List<LoginBean>> login(String wechat_id, String nick_name, String photo_link, String sex, String location) {
        return Api.getDefault(HostType.UNINCLUE_COOKIE)
                .wlogin(wechat_id, nick_name, photo_link, sex, location)
                .compose(RxSchedulers.<BaseRespose<List<LoginBean>>>io_main())
                .compose(RxHelper.<List<LoginBean>>handleResult());
    }
}
