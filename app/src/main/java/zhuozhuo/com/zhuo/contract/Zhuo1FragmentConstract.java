package zhuozhuo.com.zhuo.contract;

import com.hyphenate.chatuidemo.my.bean.HundredBean;
import li.com.base.basesinglebean.MatchPersonBean;

import java.util.List;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import li.com.base.basesinglebean.SingleChooseBean;
import li.com.base.basesinglebean.SingleChooseDetailBean;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface Zhuo1FragmentConstract {
    interface Model extends BaseModel {

        Observable<List<SingleChooseDetailBean>> getSingleChooseDetaile(String token, String type,String choice_name);

        Observable<List<SingleChooseBean>> getSingleChoose(String token, String type);

        Observable<Object> cancelmatch(String token);

        Observable<List<HundredBean>> getHundredMsg(String token);

        Observable<Object> match_(String token,String choice_id, String user_sex,String dest_sex);

        Observable<Object> accept_(String token,String choice_id,String you_accept_id, String other_party_id);

        Observable<List<MatchPersonBean>> getAllMatch(String token);

        Observable<Object> joinHundredGroup(String token, String hundred_id);

    }

    interface View extends BaseView {
        void returnSingleChooseDetaile(List<SingleChooseDetailBean> singleChooseBeans);

        void returnSingleChoose(List<SingleChooseBean>  data);

        void cancleMatchSucess();

        void  returnHundredMsg(List<HundredBean> list);

        void  match_SucessReturn();

        void  joinSucess();

        void returnAllMatch(List<MatchPersonBean>  data);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getSingleChooseDetaile(String choice_name);

        public abstract void getSingleChoose();

        public abstract void cancelMatch();

        public abstract void getHundredMsg();

        public abstract void match_(String choice_id);

        public abstract void accept_(String other_party_id,String user_id,String choice_id);

        public abstract void getAllMatch();

        public abstract void joinHundredGroup(String hundred_id);

    }
}
