package zhuozhuo.com.zhuo.model;


import java.util.List;
import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;
import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.bean.VideoBean;
import com.hyphenate.chatuidemo.my.api.HostType;
import zhuozhuo.com.zhuo.contract.Zhuo3FragmentConstract;


/**
 * Created by Administrator on 2017/10/23.
 */

public class Zhuo3FragmentModel implements Zhuo3FragmentConstract.Model {
    @Override
    public Observable<List<VideoBean>> getShareVideo(String index, String choice_id) {
        return Api.getDefault(HostType.UNINCLUE_COOKIE)
                .getShareVideo(index,choice_id)
                .compose(RxSchedulers.<BaseRespose<List<VideoBean>>>io_main())
                .compose(RxHelper.<List<VideoBean>>handleResult());
    }
}
