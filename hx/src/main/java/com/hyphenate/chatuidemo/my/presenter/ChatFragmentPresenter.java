package com.hyphenate.chatuidemo.my.presenter;


import com.hyphenate.chatuidemo.my.bean.VideoLinkBean;
import com.hyphenate.chatuidemo.my.constract.ChatFragmentConstract;
import com.hyphenate.chatuidemo.provider.UserInfoProvider;
import li.com.base.baserx.RxSubscriber;

/**
 * Created by Administrator on 2018/3/27.
 */

public class ChatFragmentPresenter extends ChatFragmentConstract.Presenter{
    @Override
    public void remark(String userId, String score, String card) {
            mModel.remark(UserInfoProvider.getToken(), userId,"", score, card).subscribe(new RxSubscriber<Object>(mContext) {
                @Override
                protected void _onNext(Object o) {
                    if (mView!=null){
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
        mModel.changeFinish(UserInfoProvider.getToken(),user_id).subscribe(new RxSubscriber<Object>(mContext,false) {
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
        mModel.setStepPhtot(UserInfoProvider.getToken(),photo_link,you_user_id,thumb_link).subscribe(new RxSubscriber<Object>(mContext,false) {
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
        mModel.setMoney(UserInfoProvider.getToken(),choice_money,group_id).subscribe(new RxSubscriber<Object>(mContext,false) {
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
        mModel.getVideo(UserInfoProvider.getToken(),you_user_id).subscribe(new RxSubscriber<VideoLinkBean>(mContext,false) {
            @Override
            protected void _onNext(VideoLinkBean videoLinkBean) {
                    if (!videoLinkBean.getVideo_link().equals("")){
                        mView.returnVieo(videoLinkBean.getVideo_link());
                    }else if (videoLinkBean.getVideo_link().equals("1")){
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
        mModel.startVideo(UserInfoProvider.getToken(),video_id).subscribe(new RxSubscriber<Object>(mContext,false) {
            @Override
            protected void _onNext(Object o) {

            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

}
