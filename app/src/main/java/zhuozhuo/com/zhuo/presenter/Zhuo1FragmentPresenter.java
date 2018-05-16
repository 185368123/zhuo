package zhuozhuo.com.zhuo.presenter;

import com.hyphenate.chatuidemo.my.bean.HundredBean;
import li.com.base.basesinglebean.MatchPersonBean;
import com.hyphenate.easeui.provider.UserInfoProvider;

import java.util.List;

import li.com.base.baserx.RxSubscriber;
import li.com.base.basesinglebean.SingleBeans;
import li.com.base.basesinglebean.SingleChooseBean;
import li.com.base.basesinglebean.SingleChooseDetailBean;
import zhuozhuo.com.zhuo.contract.Zhuo1FragmentConstract;

/**
 * Created by Administrator on 2017/10/21.
 */

public class Zhuo1FragmentPresenter extends Zhuo1FragmentConstract.Presenter {


    @Override
    public void getSingleChooseDetaile(String choice_name) {
        mModel.getSingleChooseDetaile(UserInfoProvider.getToken(), "single", choice_name).subscribe(new RxSubscriber<List<SingleChooseDetailBean>>(mContext,false) {
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
        mModel.getSingleChoose(UserInfoProvider.getToken(), "single").subscribe(new RxSubscriber<List<SingleChooseBean>>(mContext,false) {
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
    public void cancelMatch() {
        mModel.cancelmatch(UserInfoProvider.getToken()).subscribe(new RxSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object o) {
                if (o==null){
                    cancelMatch();
                }else {
                    if (mView!=null){
                        mView.cancleMatchSucess();
                    }
                }

            }

            @Override
            protected void _onError(String message) {

            }
        });
    }



    @Override
    public void getHundredMsg() {
        mModel.getHundredMsg(UserInfoProvider.getToken()).subscribe(new RxSubscriber<List<HundredBean>>(mContext) {
            @Override
            protected void _onNext(List<HundredBean> list) {
                mView.returnHundredMsg(list);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void match_(String choice_id) {
        mModel.match_(UserInfoProvider.getToken(),choice_id,UserInfoProvider.getSex(),UserInfoProvider.getMatchCondition()+"").subscribe(new RxSubscriber<Object>(mContext,false) {
            @Override
            protected void _onNext(Object o) {
                mView.match_SucessReturn();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void accept_(String other_party_id,String user_id,String choice_id) {
        mModel.accept_(UserInfoProvider.getToken(),choice_id,user_id,other_party_id)
        .subscribe(new RxSubscriber<Object>(mContext,false) {
            @Override
            protected void _onNext(Object o) {

            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void getAllMatch() {
        mModel.getAllMatch(UserInfoProvider.getToken()).subscribe(new RxSubscriber<List<MatchPersonBean>>(mContext,false) {
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
    public void joinHundredGroup(String hundred_id) {
        mModel.joinHundredGroup(UserInfoProvider.getToken(),hundred_id).subscribe(new RxSubscriber<Object>(mContext,false) {
            @Override
            protected void _onNext(Object o) {
                mView.joinSucess();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
