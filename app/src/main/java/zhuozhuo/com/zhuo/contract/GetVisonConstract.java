package zhuozhuo.com.zhuo.contract;

import li.com.base.basesinglebean.VisonBean;

import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface GetVisonConstract {

    interface Model extends BaseModel {
        Observable<VisonBean> getVison(String type);

        Observable<Object>  order(String token,String goods_id,String goods_price,String pay_type);
    }

    interface View extends BaseView {
        void returnVison(boolean isUpate,String upateUrl,String imageUrl);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getVison();

        public abstract void order();
    }
}
