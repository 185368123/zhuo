package com.hyphenate.chatuidemo.my.model;

import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.bean.VideoLinkBean;
import com.hyphenate.chatuidemo.my.constract.ChatFragmentConstract;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import rx.Observable;

/**
 * Created by Administrator on 2018/3/27.
 */

public class ChatFragmentModel implements ChatFragmentConstract.Model{
    @Override
    public Observable<Object> remark(String token, String userId, String remark, String score, String card) {
        return Api.getDefault(HostType.INCLUE_COOKIE).remark(token,userId,remark,score,card)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.<Object>handleResult());
    }

    @Override
    public Observable<Object> changeFinish(String token, String user_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).changeFinish(token,user_id)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.<Object>handleResult());
    }

    @Override
    public Observable<Object> setStepPhtot(String token, String photo_link, String you_user_id, String thumb_link) {
        return Api.getDefault(HostType.INCLUE_COOKIE).setStepPhtot(token,photo_link,you_user_id,thumb_link)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.<Object>handleResult());
    }

    @Override
    public Observable<Object> setMoney(String token, String choice_money, String group_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).setMoney(token,choice_money,group_id)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.<Object>handleResult());
    }

    @Override
    public Observable<VideoLinkBean> getVideo(String token, String you_user_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getVideo(token,you_user_id)
                .compose(RxSchedulers.<BaseRespose<VideoLinkBean>>io_main())
                .compose(RxHelper.<VideoLinkBean>handleResult());
    }

    @Override
    public Observable<Object> startVideo(String token, String video_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).startVideo(token,video_id)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.handleResult());
    }

}
