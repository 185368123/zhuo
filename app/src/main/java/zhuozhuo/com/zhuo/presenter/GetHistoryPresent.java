package zhuozhuo.com.zhuo.presenter;

import org.json.JSONObject;

import zhuozhuo.com.zhuo.model.GetHistoryModel;

/**
 * Created by Administrator on 2018/1/19.
 */

public class GetHistoryPresent extends BaseImportPresenter {

    private static final String TAG = GetHistoryPresent.class.getSimpleName() + " --> ";
    private final GetHistoryModel model;

    public GetHistoryPresent() {
        model = new GetHistoryModel(this);
    }
    public void getHistoryRate(int index){
        model.getHistoryRate(index);
    }
    @Override
    public void onVerifySucceed(String strResponse, JSONObject jsonResponse, String errMsg, int id) {

    }
}
