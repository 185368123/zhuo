package zhuozhuo.com.zhuo.presenter;

import com.google.gson.Gson;
import com.hyphenate.chatuidemo.provider.UserInfoProvider;
import org.json.JSONObject;

import li.com.base.baseuntils.LogUtils;
import zhuozhuo.com.zhuo.bean.UnReadBean;
import zhuozhuo.com.zhuo.model.UnReadModel;
import zhuozhuo.com.zhuo.view.UnReadView;

/**
 * Created by Administrator on 2017/11/30.
 */

public class UnReadPresent extends BaseImportPresenter {

    private UnReadModel model;
    private UnReadView view;

    public UnReadPresent(UnReadView view){
        model = new UnReadModel(this);
        this.view=view;
    }

    public void getUnread(int index){
        model.getUnread(index+"", UserInfoProvider.getToken());
    }
    @Override
    public void onVerifySucceed(String strResponse, JSONObject jsonResponse, String errMsg, int id) {
        LogUtils.logd("UnReadPresent -->"+strResponse);
        try {
            UnReadBean bean=new Gson().fromJson(strResponse,UnReadBean.class);
            view.onSucess(bean.getData());
        }catch (Exception e){
            LogUtils.logd("UnReadPresent异常 -->"+e);
            view.onSucess(null);
        }


    }
}
