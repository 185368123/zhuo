package zhuozhuo.com.zhuo.presenter;

import java.util.List;
import li.com.base.baserx.RxSubscriber;
import com.hyphenate.chatuidemo.my.bean.VideoBean;
import zhuozhuo.com.zhuo.contract.Zhuo3FragmentConstract;


/**
 * Created by Administrator on 2017/10/23.
 */

public class Zhuo3FragmentPresenter extends Zhuo3FragmentConstract.Presenter {

    @Override
    public void getShareVideo(int index, String choice_id) {
        mModel.getShareVideo(index+"",choice_id).subscribe(new RxSubscriber<List<VideoBean>>(mContext,false) {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            protected void _onNext(List<VideoBean> list) {
                mView.returnVideoList(list);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        });
    }
}
