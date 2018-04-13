package zhuozhuo.com.zhuo.model;

import com.hyphenate.chatuidemo.my.bean.GroupChoicesBean;
import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;

import java.util.List;
import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import li.com.base.basesinglebean.GroupStatusBean;
import rx.Observable;
import zhuozhuo.com.zhuo.contract.Zhuo2FragmentConstract;

/**
 * Created by Administrator on 2018/3/30.
 */

public class Zhuo2FragmentModel implements Zhuo2FragmentConstract.Model {
    @Override
    public Observable<GroupStatusBean> getGroupStatus(String token) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getGroupStatus(token)
                .compose(RxSchedulers.<BaseRespose<GroupStatusBean>>io_main())
                .compose(RxHelper.<GroupStatusBean>handleResult());
    }

    @Override
    public Observable<List<GroupChoicesBean>> getGroupChoices(String token) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getGroupChoices(token)
                .compose(RxSchedulers.<BaseRespose<List<GroupChoicesBean>>>io_main())
                .compose(RxHelper.<List<GroupChoicesBean>>handleResult());
    }

    @Override
    public Observable<Object> beginGroupMatch(String token, String member_ids, String members, String choice_id, String team_id, String group_name, String choice_number) {
        return Api.getDefault(HostType.INCLUE_COOKIE).beginGroupMatch(token,member_ids,members,choice_id,team_id,group_name,choice_number)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.<Object>handleResult());
    }

    @Override
    public Observable<Object> cancleGroupMatch(String token, String team_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).cancleGroupMatch(token,team_id)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.<Object>handleResult());
    }

    @Override
    public Observable<Object> groupRemark(String token, String team_id, String content, String group_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).groupRemark(token,team_id,content,group_id)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.<Object>handleResult());
    }

    @Override
    public Observable<Object> leaveTeam(String token, String team_id, String group_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).leaveTeam(token,team_id,group_id)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.<Object>handleResult());
    }
}
