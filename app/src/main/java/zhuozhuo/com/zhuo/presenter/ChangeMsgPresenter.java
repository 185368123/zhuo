package zhuozhuo.com.zhuo.presenter;

import com.hyphenate.chatuidemo.UserMsgDBHelp;
import com.hyphenate.chatuidemo.provider.UserInfoProvider;
import java.util.List;
import li.com.base.baserx.RxSubscriber;
import zhuozhuo.com.zhuo.contract.ChangeMsgConstract;

/**
 * Created by Administrator on 2017/10/21.
 */

public class ChangeMsgPresenter extends ChangeMsgConstract.Presenter {

    private String key;
    private String value;

    @Override
    public void changeMsg(String key1,  String value1) {
        this.key = key1;
        this.value = value1;
        mModel.changeMsg(UserInfoProvider.getToken(), key, value).subscribe(new RxSubscriber<List<Object>>(mContext,false) {
            @Override
            protected void _onNext(List<Object> object) {
                if (key.equals("nick_name")) {
                    UserInfoProvider.setNickName(value);
                } else if (key.equals("sex")) {
                    UserInfoProvider.setSex(value);
                } else if (key.equals("photo_link")) {
                    UserInfoProvider.setPhotoLink(value);
                }else if (key.equals("account")) {
                    UserInfoProvider.setHobby(value);
                }else if (key.equals("location")) {
                    UserInfoProvider.setLocation(value);
                }
                UserMsgDBHelp.getUserMsgDBHelp().updateMsg(UserInfoProvider.getUserID());
                mView.changeMsgSucess();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
