package com.hyphenate.chatuidemo.my.model;

import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.bean.GroupTypeBean;
import com.hyphenate.chatuidemo.my.bean.IsRemarkBean;
import com.hyphenate.chatuidemo.my.bean.TagBean;
import com.hyphenate.easeui.HundredCupBean;
import com.hyphenate.chatuidemo.my.bean.VideoLinkBean;
import com.hyphenate.chatuidemo.my.constract.ChatFragmentConstract;
import com.hyphenate.easeui.provider.UserInfoProvider;

import java.util.List;

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

    @Override
    public Observable<HundredCupBean> getDetail(String token, String hundred_id,String group_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getHundredCupDetail(token,hundred_id,group_id)
                .compose(RxSchedulers.<HundredCupBean>io_main());
    }

    @Override
    public Observable<Object> teamRegister(String token, String line_id,String hundred_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).teamRegister(token,line_id,hundred_id)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.handleResult());
    }

    @Override
    public Observable<TagBean> getTag(String token, String choice_id, String you_user_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getTag(token, choice_id, you_user_id)
                .compose(RxSchedulers.<TagBean>io_main());
    }

    @Override
    public Observable<List<Object>> getRandom(String token, String you_user_id, String choice_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getRandom(token, you_user_id, choice_id)
                .compose(RxSchedulers.<BaseRespose<List<Object>>>io_main())
                .compose(RxHelper.<List<Object>>handleResult());
    }

    @Override
    public Observable<IsRemarkBean> isEvaluate(String token, String you_user_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE)
                .isEvaluate(token, you_user_id)
                .compose(RxSchedulers.<BaseRespose<IsRemarkBean>>io_main())
                .compose(RxHelper.<IsRemarkBean>handleResult());
    }

    @Override
    public Observable<GroupTypeBean> getGroupType(String token, String group_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE)
                .getGroupType(token, group_id)
                .compose(RxSchedulers.<BaseRespose<GroupTypeBean>>io_main())
                .compose(RxHelper.<GroupTypeBean>handleResult());
    }

    @Override
    public Observable<Object> groupSignOut(String token, String group_id, String you_user_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE)
                .groupSignOut(token, group_id, you_user_id)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.handleResult());
    }

}
