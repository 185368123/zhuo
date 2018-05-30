package com.hyphenate.chatuidemo.my.presenter;

import com.hyphenate.chatuidemo.my.UserListBean;
import com.hyphenate.chatuidemo.my.constract.SearchUserConstract;

import java.util.List;

import li.com.base.baserx.RxSubscriber;

/**
 * Created by Administrator on 2018/5/23.
 */

public class SearchUserPresenter extends SearchUserConstract.Presenter {
    @Override
    public void searchUser(String index, String keyword) {
        mModel.searchUser(index, keyword).subscribe(new RxSubscriber<List<UserListBean>>(mContext,false) {
            @Override
            protected void _onNext(List<UserListBean> userListBeans) {
                mView.returnUser(userListBeans);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
