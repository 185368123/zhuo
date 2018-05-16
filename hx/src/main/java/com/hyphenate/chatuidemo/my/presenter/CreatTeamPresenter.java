package com.hyphenate.chatuidemo.my.presenter;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.chatuidemo.my.constract.CreatTeamConstract;
import com.hyphenate.easeui.provider.UserInfoProvider;

import li.com.base.baserx.RxSubscriber;
import li.com.base.baseuntils.ToastUitl;

/**
 * Created by Administrator on 2018/4/21.
 */

public class CreatTeamPresenter extends CreatTeamConstract.Presenter {
    @Override
    public void getUserMsg(EditText editText1, EditText editText2) {
        String group_name=getText(editText1);
        String game_name=getText(editText2);
        if (TextUtils.isEmpty(group_name)){
            ToastUitl.showLong("队伍名称不能为空");
            return;
        }
        if (TextUtils.isEmpty(game_name)){
            ToastUitl.showLong("游戏ID不能为空");
            return;
        }
        mModel.getUserMsg(UserInfoProvider.getToken(),group_name,game_name).subscribe(new RxSubscriber<Object>(mContext,false) {
            @Override
            protected void _onNext(Object o) {
                mView.returnUserMsg();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }


    /**
     * 获取文本
     */
    public static String getText(TextView textView) {
        if (textView != null) {
            return textView.getText().toString();
        } else {
            return "";
        }
    }
}
