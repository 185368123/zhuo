package com.hyphenate.chatuidemo.my.presenter;

import com.hyphenate.chatuidemo.my.bean.GroupTypeBean;
import com.hyphenate.chatuidemo.my.bean.IsRemarkBean;
import com.hyphenate.chatuidemo.my.bean.TagBean;
import com.hyphenate.easeui.HundredCupBean;
import com.hyphenate.chatuidemo.my.bean.VideoLinkBean;
import com.hyphenate.chatuidemo.my.constract.ChatFragmentConstract;
import com.hyphenate.easeui.provider.UserInfoProvider;

import java.util.List;

import li.com.base.baserx.RxSubscriber;

/**
 * Created by Administrator on 2018/3/27.
 */

public class ChatFragmentPresenter extends ChatFragmentConstract.Presenter {
    @Override
    public void remark(String userId, String score, String card) {
        mModel.remark(UserInfoProvider.getToken(), userId, "", score, card).subscribe(new RxSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object o) {
                if (mView != null) {
                    mView.remarkSucess();
                }
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void changeFinish(String user_id) {
        mModel.changeFinish(UserInfoProvider.getToken(), user_id).subscribe(new RxSubscriber<Object>(mContext, false) {
            @Override
            protected void _onNext(Object o) {

            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void setStepPhtot(String photo_link, String you_user_id, String thumb_link) {
        mModel.setStepPhtot(UserInfoProvider.getToken(), photo_link, you_user_id, thumb_link).subscribe(new RxSubscriber<Object>(mContext, false) {
            @Override
            protected void _onNext(Object o) {
                mView.setStepPhtotSucess();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void setMoney(String choice_money, String group_id) {
        mModel.setMoney(UserInfoProvider.getToken(), choice_money, group_id).subscribe(new RxSubscriber<Object>(mContext, false) {
            @Override
            protected void _onNext(Object o) {
                mView.setMoneySucess();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void getVideo(String you_user_id) {
        mModel.getVideo(UserInfoProvider.getToken(), you_user_id).subscribe(new RxSubscriber<VideoLinkBean>(mContext, false) {
            @Override
            protected void _onNext(VideoLinkBean videoLinkBean) {
                if (!videoLinkBean.getVideo_link().equals("")) {
                    mView.returnVieo(videoLinkBean.getVideo_link());
                } else if (videoLinkBean.getVideo_link().equals("1")) {
                    mView.returnVieo("");
                }
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void startVideo(String video_id) {
        mModel.startVideo(UserInfoProvider.getToken(), video_id).subscribe(new RxSubscriber<Object>(mContext, false) {
            @Override
            protected void _onNext(Object o) {

            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void getDetail(String group_id) {
        mModel.getDetail(UserInfoProvider.getToken(), null, group_id).subscribe(new RxSubscriber<HundredCupBean>(mContext, false) {
            @Override
            protected void _onNext(HundredCupBean hundredCupBean) {
                mView.returnHundredDetail(hundredCupBean);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void teamRegister(String line_id, String hundred_id) {
        mModel.teamRegister(UserInfoProvider.getToken(), line_id, hundred_id).subscribe(new RxSubscriber<Object>(mContext, false) {
            @Override
            protected void _onNext(Object o) {
                mView.teamRegisterSucess();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void getTag(String choice_id, String you_user_id) {
        mModel.getTag(UserInfoProvider.getToken(), choice_id, you_user_id).subscribe(new RxSubscriber<TagBean>(mContext, false) {
            @Override
            protected void _onNext(TagBean tagBean) {
                mView.returnTag(tagBean);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void getRandom(String you_user_id, String choice_id) {
        mModel.getRandom(UserInfoProvider.getToken(), you_user_id, choice_id).subscribe(new RxSubscriber<List<Object>>(mContext, false) {
            @Override
            protected void _onNext(List<Object> list) {


            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void isEvaluate(String you_user_id) {
        mModel.isEvaluate(UserInfoProvider.getToken(), you_user_id).subscribe(new RxSubscriber<IsRemarkBean>(mContext, false) {
            @Override
            protected void _onNext(IsRemarkBean isRemarkBean) {
                mView.getIsEvaluateSucess(isRemarkBean);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void getGroupType(String group_id) {
        mModel.getGroupType(UserInfoProvider.getToken(),group_id).subscribe(new RxSubscriber<GroupTypeBean>(mContext,false) {
            @Override
            protected void _onNext(GroupTypeBean groupTypeBean) {
                 mView.returnGroupType(groupTypeBean);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void groupSignOut(String group_id, String you_user_id) {
        mModel.groupSignOut(UserInfoProvider.getToken(),group_id,you_user_id).subscribe(new RxSubscriber<Object>(mContext,false) {
            @Override
            protected void _onNext(Object o) {
                mView.groupSignOutSucess();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

}
