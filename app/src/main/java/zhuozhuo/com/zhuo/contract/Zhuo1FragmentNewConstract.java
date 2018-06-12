package zhuozhuo.com.zhuo.contract;

import li.com.base.basesinglebean.CitiesSingBean;

import java.util.List;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import li.com.base.basesinglebean.MatchPersonBean;
import li.com.base.basesinglebean.RandStrBean;
import li.com.base.basesinglebean.SingleChooseBean;
import li.com.base.basesinglebean.SingleChooseDetailBean;
import li.com.base.basesinglebean.SingleStatusBean;
import li.com.base.basesinglebean.SuggestFriendBean;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface Zhuo1FragmentNewConstract {
    interface Model extends BaseModel {

        Observable<List<SingleChooseDetailBean>> getSingleChooseDetaile(String token, String type, String choice_name);

        Observable<List<SingleChooseBean>> getSingleChoose(String token, String type);

        Observable<List<Object>> matchBegin(String token,String choice_id,String user_sex,String dest_sex,String type,String status,String destination,String location);

        Observable<List<Object>> matchAccept(String token,String choice_id,String you_user_id,String other_party_id,String is_status);

        Observable<List<Object>> matchCancle(String token,String choice_id,String status);

        Observable<List<MatchPersonBean>> getAllMatch(String token);

        Observable<Object> matchSet(String token, String you_user_id, String choice_id, String content);

        Observable<SingleStatusBean> getSingleStatus(String token);

        Observable<List<CitiesSingBean>> getAllCities(String token);

        Observable<List<SuggestFriendBean>> getSuggestFriend(String token,String you_user_id);



    }

    interface View extends BaseView {

        void returnSingleChooseDetaile(List<SingleChooseDetailBean> singleChooseBeans);

        void returnSingleChoose(List<SingleChooseBean> data);

        void matchBeginSucess();

        void matchAcceptSucess();

        void matchCancle();

        void returnAllMatch(List<MatchPersonBean> data);

        void returnSingleStatus(SingleStatusBean singleStatusBean);

        void returnSuggestFriend(List<SuggestFriendBean> suggestFriends);



    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getSingleChooseDetaile(String choice_name);

        public abstract void getSingleChoose();

        public abstract void matchBegin(String choice_id,String type,String status,String destination,String location);

        public abstract void matchAccept(String choice_id,String you_user_id,String other_party_id,String is_status);

        public abstract void matchCancle(String choice_id,String status);

        public abstract void getAllMatch();

        public abstract void matchSet(String you_user_id, String choice_id,String content);

        public abstract void getSingleStatus();

        public abstract void getAllCities();

        public abstract void getSuggestFriend(String you_user_id);


    }
}
