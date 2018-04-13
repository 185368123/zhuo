package zhuozhuo.com.zhuo.presenter;

import com.google.gson.Gson;

import org.json.JSONObject;

import li.com.base.baseuntils.LogUtils;
import zhuozhuo.com.zhuo.bean.TotalRateBean;
import zhuozhuo.com.zhuo.model.MyLevelModel;
import zhuozhuo.com.zhuo.view.TotalRateView;

/**
 * Created by Administrator on 2018/1/19.
 */

public class MyLevelPresent extends BaseImportPresenter {

    private static final String TAG = MyLevelPresent.class.getSimpleName() + " --> ";
    private final MyLevelModel myLevelModel;
    private TotalRateView view;

    public MyLevelPresent(TotalRateView view) {
        myLevelModel = new MyLevelModel(this);
        this.view=view;
    }

    public void getTotalRate(int index, String user_ids) {
        myLevelModel.getTotalRate(index, user_ids);
    }

    @Override
    public void onVerifySucceed(String strResponse, JSONObject jsonResponse, String errMsg, int id) {
        LogUtils.logd(TAG + strResponse);
        try {
            TotalRateBean rateBean=new Gson().fromJson(strResponse,TotalRateBean.class);
            view.refreshListView(rateBean.getData());
        }catch (Exception e){
            view.refreshListView(null);
        }

    }
}
