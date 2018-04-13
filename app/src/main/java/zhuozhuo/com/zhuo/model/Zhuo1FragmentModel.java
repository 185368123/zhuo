package zhuozhuo.com.zhuo.model;

import java.util.List;

import li.com.base.baserx.BaseRespose;
import li.com.base.baserx.RxHelper;
import li.com.base.baserx.RxSchedulers;
import li.com.base.basesinglebean.SingleChooseBean;
import li.com.base.basesinglebean.SingleChooseDetailBean;
import rx.Observable;
import com.hyphenate.chatuidemo.my.api.Api;
import com.hyphenate.chatuidemo.my.api.HostType;
import com.hyphenate.chatuidemo.my.bean.HundredBean;
import li.com.base.basesinglebean.MatchPersonBean;

import zhuozhuo.com.zhuo.contract.Zhuo1FragmentConstract;

/**
 * Created by Administrator on 2017/10/21.
 */

public class Zhuo1FragmentModel implements Zhuo1FragmentConstract.Model{

    @Override
    public Observable<List<SingleChooseDetailBean>> getSingleChooseDetaile(String token, String type,String choice_name) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getChooseDetail(token,type,choice_name)
                .compose(RxSchedulers.<BaseRespose<List<SingleChooseDetailBean>>>io_main())
                .compose(RxHelper.<List<SingleChooseDetailBean>>handleResult());
    }

    @Override
    public Observable<List<SingleChooseBean>> getSingleChoose(String token, String type) {
        return Api.getDefault(HostType.INCLUE_COOKIE)
                .getSingleChoose(token,type)
                .compose(RxSchedulers.<BaseRespose<List<SingleChooseBean>>>io_main())
                .compose(RxHelper.<List<SingleChooseBean>>handleResult());
    }


    @Override
    public Observable<Object> cancelmatch(String token) {
        return Api.getDefault(HostType.INCLUE_COOKIE).cancelmatch(token)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.<Object>handleResult());
    }


    @Override
    public Observable<List<HundredBean>> getHundredMsg(String token) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getHundredMsg(token)
                .compose(RxSchedulers.<BaseRespose<List<HundredBean>>>io_main())
                .compose(RxHelper.<List<HundredBean>>handleResult());
    }

    @Override
    public Observable<Object> match_(String token, String choice_id, String user_sex, String dest_sex) {
        return Api.getDefault(HostType.INCLUE_COOKIE).playMatch(token,choice_id,user_sex,dest_sex)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.handleResult());
    }

    @Override
    public Observable<Object> accept_(String token, String choice_id,String you_accept_id, String other_party_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).acceptMatch(token,choice_id,you_accept_id,other_party_id)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.handleResult());
    }

    @Override
    public Observable<List<MatchPersonBean>> getAllMatch(String token) {
        return Api.getDefault(HostType.INCLUE_COOKIE).getAcceptAll(token)
                .compose(RxSchedulers.<BaseRespose<List<MatchPersonBean>>>io_main())
                .compose(RxHelper.<List<MatchPersonBean>>handleResult());
    }

    @Override
    public Observable<Object> joinHundredGroup(String token, String hundred_id) {
        return Api.getDefault(HostType.INCLUE_COOKIE).joinHundredGroup(token,hundred_id)
                .compose(RxSchedulers.<BaseRespose<Object>>io_main())
                .compose(RxHelper.handleResult());
    }

}
