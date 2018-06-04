package zhuozhuo.com.zhuo.presenter;

import com.hyphenate.chatuidemo.my.bean.SaveMatchBean;
import com.hyphenate.easeui.provider.UserInfoProvider;

import java.util.List;

import li.com.base.baserx.RxSubscriber;
import zhuozhuo.com.zhuo.contract.SaveMatchConstract;

/**
 * Created by Administrator on 2018/5/29.
 */

public class SaveMatchPresent extends SaveMatchConstract.Presenter {
    @Override
    public void getSaveMatch(String page,String page_size) {
        mModel.getSaveMatch(UserInfoProvider.getToken(),page,page_size).subscribe(new RxSubscriber<List<SaveMatchBean>>(mContext,false) {
            @Override
            protected void _onNext(List<SaveMatchBean> list) {
                  mView.returnSaveMatch(list);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void relieveLine(String you_user_id, String type) {
        mModel.relieveLine(UserInfoProvider.getToken(),you_user_id,type).subscribe(new RxSubscriber<Object>(mContext,false) {
            @Override
            protected void _onNext(Object o) {
                mView.relieveLineSucess();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
