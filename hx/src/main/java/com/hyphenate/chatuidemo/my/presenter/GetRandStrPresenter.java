package com.hyphenate.chatuidemo.my.presenter;

import com.hyphenate.chatuidemo.my.constract.GetRandStrConstract;
import com.hyphenate.chatuidemo.ui.UserProfileActivity;
import com.hyphenate.easeui.provider.UserInfoProvider;

import java.util.List;

import li.com.base.baserx.RxSubscriber;
import li.com.base.basesinglebean.RandStrBean;
import li.com.base.basesinglebean.SingleBeans;

/**
 * Created by Administrator on 2018/6/12.
 */

public class GetRandStrPresenter extends GetRandStrConstract.Presenter {
    @Override
    public void getRandStr() {
        mModel.getRandStr(UserInfoProvider.getToken()).subscribe(new RxSubscriber<List<RandStrBean>>(mContext,false) {
            @Override
            protected void _onNext(List<RandStrBean> randStrBeans) {
                SingleBeans.getInstance().setRandStrBeans(randStrBeans);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
