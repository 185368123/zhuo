package zhuozhuo.com.zhuo.model;

import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import li.com.base.basesinglebean.CitiesSingBean;

import java.util.List;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import li.com.base.basesinglebean.MatchPersonBean;
import li.com.base.basesinglebean.SingleChooseBean;
import li.com.base.basesinglebean.SingleChooseDetailBean;
import li.com.base.basesinglebean.SingleStatusBean;
import li.com.base.basesinglebean.SuggestFriendBean;
import rx.Observable;
import zhuozhuo.com.zhuo.contract.Zhuo1FragmentNewConstract;

/**
 * Created by Administrator on 2017/10/21.
 */

public class Zhuo1FragmentNewModel implements Zhuo1FragmentNewConstract.Model {

    @Override
    public Observable<List<SingleChooseDetailBean>> getSingleChooseDetaile(String token, String type, String choice_name) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getChooseDetail(token, type, choice_name)
                .compose(RxSchedulers.<BaseRespose<List<SingleChooseDetailBean>>>io_main())
                .compose(RxHelper.<List<SingleChooseDetailBean>>handleResult());
    }

    @Override
    public Observable<List<SingleChooseBean>> getSingleChoose(String token, String type) {
        return Api.getDefault(HostType.INCLUE_COOKIE)
                .getSingleChoose(token, type)
                .compose(RxSchedulers.<BaseRespose<List<SingleChooseBean>>>io_main())
                .compose(RxHelper.<List<SingleChooseBean>>handleResult());
    }

    @Override
    public Observable<List<Object>> matchBegin(String token, String choice_id, String user_sex, String dest_sex, String type, String status, String destination, String location) {
        return Api.getDefault(HostType.INCLUE_COOKIE)
                .matchBegin_new(token, choice_id, user_sex, dest_sex, type, status, destination, location)
                .compose(RxSchedulers.<BaseRespose<List<Object>>>io_main())
                .compose(RxHelper.<List<Object>>handleResult());
    }

    @Override
    public Observable<List<Object>> matchAccept(String token, String choice_id, String you_user_id, String other_party_id, String is_status) {
        return Api.getDefault(HostType.INCLUE_COOKIE)
                .matchAccept_new(token, choice_id, you_user_id, other_party_id, is_status)
                .compose(RxSchedulers.<BaseRespose<List<Object>>>io_main())
                .compose(RxHelper.<List<Object>>handleResult());
    }

    @Override
    public Observable<List<Object>> matchCancle(String token, String choice_id, String status) {
        return Api.getDefault(HostType.INCLUE_COOKIE)
                .matchCancle_new(token, choice_id, status)
                .compose(RxSchedulers.<BaseRespose<List<Object>>>io_main())
                .compose(RxHelper.<List<Object>>handleResult());
    }


    @Override
    public Observable<List<MatchPersonBean>> getAllMatch(String token) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getAcceptAll(token)
                .compose(RxSchedulers.<BaseRespose<List<MatchPersonBean>>>io_main())
                .compose(RxHelper.<List<MatchPersonBean>>handleResult());
    }

    @Override
    public Observable<Object> matchSet(String token, String you_user_id, String choice_id, String content) {
        return Api.getDefault(HostType.INCLUE_COOKIE).matchSet(token, you_user_id, choice_id, content)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.handleResult());
    }

    @Override
    public Observable<SingleStatusBean> getSingleStatus(String token) {
        return Api.getDefault(HostType.INCLUE_COOKIE)
                .getSingleStatus(token)
                .compose(RxSchedulers.<BaseRespose<SingleStatusBean>>io_main())
                .compose(RxHelper.<SingleStatusBean>handleResult());
    }

    @Override
    public Observable<List<CitiesSingBean>> getAllCities(String token) {
        return Api.getDefault(HostType.INCLUE_COOKIE)
                .getProvices(token)
                .compose(RxSchedulers.<BaseRespose<List<CitiesSingBean>>>io_main())
                .compose(RxHelper.<List<CitiesSingBean>>handleResult());
    }

    @Override
    public Observable<List<SuggestFriendBean>> getSuggestFriend(String token, String you_user_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE)
                .getSuggestFriend(token, you_user_id)
                .compose(RxSchedulers.<BaseRespose<List<SuggestFriendBean>>>io_main())
                .compose(RxHelper.<List<SuggestFriendBean>>handleResult());
    }
}
