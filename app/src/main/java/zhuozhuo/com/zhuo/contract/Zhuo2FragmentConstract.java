package zhuozhuo.com.zhuo.contract;

import com.hyphenate.chatuidemo.my.bean.GroupChoicesBean;

import li.com.base.basesinglebean.GroupStatusBean;

import java.util.List;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface Zhuo2FragmentConstract {
    interface Model extends BaseModel {
        Observable<GroupStatusBean> getGroupStatus(String token);

        Observable<List<GroupChoicesBean>> getGroupChoices(String token);

        Observable<Object> beginGroupMatch(String token,String member_ids, String members,String choice_id,String team_id,String group_name,String choice_number);

        Observable<Object> cancleGroupMatch(String token,String team_id);

        Observable<Object> groupRemark(String token,String team_id,String content,String group_id);

        Observable<Object> leaveTeam(String token,String team_id,String group_id);
    }

    interface View extends BaseView {
        void returnGroupStatus(GroupStatusBean groupStatusBean);

        void returnGroupChoices(List<GroupChoicesBean> list);

        void beginGroupMatchSucess();

        void groupMatchSucess(String group_id);

        void cancleGroupMatchSucess();

        void remarkSucess();

        void leaveTeamSucess();
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getGroupStatus();

        public abstract void getGroupChoices();

        public abstract void beginGroupMatch(String choice_id,String group_name,int choice_number);

        public abstract void cancleGroupMatch();

        public abstract void groupRemark(String  s, boolean isRemark);

        public abstract void leaveTeam();
    }
}
