package com.hyphenate.chatuidemo.my.presenter;

import com.hyphenate.chatuidemo.my.bean.RateBean;
import com.hyphenate.chatuidemo.my.constract.TableActivityConstract;
import com.hyphenate.chatuidemo.provider.UserInfoProvider;

import java.util.List;

import li.com.base.baserx.RxSubscriber;

/**
 * Created by Administrator on 2018/3/27.
 */

public class TableActivityPresenter extends TableActivityConstract.Presenter{
    @Override
    public void getGroupRate(String group_id) {
        mModel.getGroupRate(UserInfoProvider.getToken(),group_id).subscribe(new RxSubscriber<List<RateBean>>(mContext,false) {
            @Override
            protected void _onNext(List<RateBean> rateBeans) {
                if (rateBeans.size()>0){
                    mView.returnGroupRate(rateBeans);
                }
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
