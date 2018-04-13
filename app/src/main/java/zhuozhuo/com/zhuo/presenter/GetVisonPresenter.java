package zhuozhuo.com.zhuo.presenter;

import li.com.base.basesinglebean.SingleBeans;
import li.com.base.basesinglebean.VisonBean;

import li.com.base.baserx.RxSubscriber;
import li.com.base.baseuntils.LogUtils;
import zhuozhuo.com.zhuo.MainApplication;
import zhuozhuo.com.zhuo.contract.GetVisonConstract;

/**
 * Created by Administrator on 2018/4/4.
 */

public class GetVisonPresenter extends GetVisonConstract.Presenter {
    @Override
    public void getVison() {
        mModel.getVison("2").subscribe(new RxSubscriber<VisonBean>(mContext,false) {
            @Override
            protected void _onNext(VisonBean visonBean) {
                LogUtils.logd("visonBean.getAndroid_version():"+visonBean.getAndroid_version()+"----MainApplication.getInstance().getVison():"+MainApplication.getInstance().getVison());
                    mView.returnVison(visonBean.getAndroid_version().equals(MainApplication.getInstance().getVison()),visonBean.getAndroid_url(),visonBean.getData().getPhone().getImage_url());
                SingleBeans.getInstance().setVisonBean(visonBean);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
