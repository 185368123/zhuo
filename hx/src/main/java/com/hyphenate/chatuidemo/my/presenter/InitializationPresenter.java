package com.hyphenate.chatuidemo.my.presenter;

import com.hyphenate.chatuidemo.my.constract.InitializationConstract;
import com.hyphenate.chatuidemo.provider.UserInfoProvider;
import com.hyphenate.easeui.events.RxBusConstants;
import li.com.base.baserx.RxManager;
import li.com.base.baserx.RxSubscriber;
import li.com.base.basesinglebean.SingleBeans;
import li.com.base.basesinglebean.SingleStatusBean;

/**
 * Created by Administrator on 2018/3/27.
 */

public class InitializationPresenter extends InitializationConstract.Presenter{
    @Override
    public void getSingleStatus() {
        mModel.getSingleStatus(UserInfoProvider.getToken()).subscribe(new RxSubscriber<SingleStatusBean>(mContext) {
            @Override
            protected void _onNext(SingleStatusBean singleStatusBean) {
                SingleBeans.getInstance().setSingleStatusBean(singleStatusBean);
                SingleBeans.getInstance().getUnReadBean().setComNum(Integer.valueOf(singleStatusBean.getUnread_share_count()));

                new RxManager().post("unread",singleStatusBean.getUnread_share_count());
                if (mView!=null){
                    mView.returnSingleStatus(singleStatusBean);
                }
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

}
