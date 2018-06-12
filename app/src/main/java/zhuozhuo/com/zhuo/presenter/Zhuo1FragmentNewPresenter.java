package zhuozhuo.com.zhuo.presenter;

import li.com.base.basesinglebean.CitiesSingBean;

import com.hyphenate.easeui.provider.UserInfoProvider;

import java.util.List;

import li.com.base.baserx.RxManager;
import li.com.base.baserx.RxSubscriber;
import li.com.base.basesinglebean.MatchPersonBean;
import li.com.base.basesinglebean.SingleBeans;
import li.com.base.basesinglebean.SingleChooseBean;
import li.com.base.basesinglebean.SingleChooseDetailBean;
import li.com.base.basesinglebean.SingleStatusBean;
import li.com.base.basesinglebean.SuggestFriendBean;
import li.com.base.baseuntils.LogUtils;
import zhuozhuo.com.zhuo.contract.Zhuo1FragmentNewConstract;

import static zhuozhuo.com.zhuo.view.fragment.Zhuo1NewItemFragment_.ToItemFragment1;

/**
 * Created by Administrator on 2017/10/21.
 */

public class Zhuo1FragmentNewPresenter extends Zhuo1FragmentNewConstract.Presenter {


    @Override
    public void getSingleChooseDetaile(String choice_name) {
        mModel.getSingleChooseDetaile(UserInfoProvider.getToken(), "single", choice_name).subscribe(new RxSubscriber<List<SingleChooseDetailBean>>(mContext, false) {
            @Override
            protected void _onNext(List<SingleChooseDetailBean> singleChooseDetailBeans) {
                mView.returnSingleChooseDetaile(singleChooseDetailBeans);
                SingleBeans.getInstance().setSingleChooseDetailBean(singleChooseDetailBeans.get(0));
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void getSingleChoose() {
        mModel.getSingleChoose(UserInfoProvider.getToken(), "single").subscribe(new RxSubscriber<List<SingleChooseBean>>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            protected void _onNext(List<SingleChooseBean> singleChooseBeans) {
                mView.returnSingleChoose(singleChooseBeans);
                SingleBeans.getInstance().setSingleChooseBeans(singleChooseBeans);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void matchBegin(String choice_id, String type, String status, String destination, String location) {
        mModel.matchBegin(UserInfoProvider.getToken(), choice_id, UserInfoProvider.getSex(), UserInfoProvider.getMatchCondition()+"", type, status, destination, location).subscribe(new RxSubscriber<List<Object>>(mContext, false) {
            @Override
            protected void _onNext(List<Object> o) {
                mView.matchBeginSucess();
            }

            @Override
            protected void _onError(String message) {
                LogUtils.logd("matchBegin请求匹配开始失败---"+message);
                new RxManager().post(ToItemFragment1,"");
            }
        });
    }

    @Override
    public void matchAccept(String choice_id, String you_user_id, String other_party_id, String is_status) {
        mModel.matchAccept(UserInfoProvider.getToken(), choice_id, you_user_id, other_party_id, is_status).subscribe(new RxSubscriber<List<Object>>(mContext, false) {
            @Override
            protected void _onNext(List<Object> o) {
                mView.matchAcceptSucess();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void matchCancle(String choice_id, String status) {
        mModel.matchCancle(UserInfoProvider.getToken(), choice_id, status).subscribe(new RxSubscriber<List<Object>>(mContext, false) {
            @Override
            protected void _onNext(List<Object> o) {
                mView.matchCancle();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }


    @Override
    public void getAllMatch() {
        mModel.getAllMatch(UserInfoProvider.getToken()).subscribe(new RxSubscriber<List<MatchPersonBean>>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            protected void _onNext(List<MatchPersonBean> matchPersonBeans) {
                SingleBeans.getInstance().setMatchPersonBeans(matchPersonBeans);
                mView.returnAllMatch(matchPersonBeans);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }


    @Override
    public void matchSet(String you_user_id, String choice_id, String content) {
        mModel.matchSet(UserInfoProvider.getToken(), you_user_id, choice_id, content).subscribe(new RxSubscriber<Object>(mContext, false) {
            @Override
            protected void _onNext(Object o) {

            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void getSingleStatus() {
        mModel.getSingleStatus(UserInfoProvider.getToken()).subscribe(new RxSubscriber<SingleStatusBean>(mContext) {
            @Override
            protected void _onNext(SingleStatusBean singleStatusBean) {
                SingleBeans.getInstance().setSingleStatusBean(singleStatusBean);
                SingleBeans.getInstance().getUnReadBean().setComNum(Integer.valueOf(singleStatusBean.getUnread_share_count()));
                SingleBeans.getInstance().getUnReadBean().setReamrkNum(singleStatusBean.getUnread_msg_count());
                RxManager manager=new RxManager();
                manager.post("remarkUnread","");
                manager.post("unread",singleStatusBean.getUnread_share_count());
                if (mView!=null){
                    mView.returnSingleStatus(singleStatusBean);
                }
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void getAllCities() {
        mModel.getAllCities(UserInfoProvider.getToken()).subscribe(new RxSubscriber<List<CitiesSingBean>>(mContext,false) {
            @Override
            protected void _onNext(List<CitiesSingBean> list) {
              SingleBeans.getInstance().setCitiesSingBean(list);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void getSuggestFriend(String you_user_id) {
        mModel.getSuggestFriend(UserInfoProvider.getToken(),you_user_id).subscribe(new RxSubscriber<List<SuggestFriendBean>>(mContext,false) {
            @Override
            protected void _onNext(List<SuggestFriendBean> suggestFriendBeans) {
                mView.returnSuggestFriend(suggestFriendBeans);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
