package zhuozhuo.com.zhuo.presentermodel;

import com.hyphenate.chatuidemo.my.EmptyView;
import com.hyphenate.easeui.provider.UserInfoProvider;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import li.com.base.baserx.RxManager;
import com.hyphenate.chatuidemo.my.api.UrlConstant;

import li.com.base.basesinglebean.SingleBeans;
import zhuozhuo.com.zhuo.http.MyOkHttpClient;
import zhuozhuo.com.zhuo.presenter.BaseImportPresenter;
import zhuozhuo.com.zhuo.util.RequestUtils;

/**
 * Created by Administrator on 2017/11/15.
 */

public class SetCaptainPresentModel extends BaseImportPresenter {
EmptyView view;

    public SetCaptainPresentModel(EmptyView view) {
     this.view=view;
    }

    public void setCaptain() {
        HashMap map = new HashMap();
        map.put("token", UserInfoProvider.getToken());
        // 获取 Post 请求的 RequestCall
        RequestCall requestCall = MyOkHttpClient.getPostRequest(UrlConstant.SetCaptain_URL, map, UserInfoProvider.getSessionId());

        // 发送登录请求
        RequestUtils.sendPostRequest(requestCall, this);
    }

    @Override
    public void onVerifySucceed(String strResponse, JSONObject jsonResponse, String errMsg, int id) {
        new RxManager().post("GroupChange","");
        try {
            JSONObject object=new JSONObject( strResponse);
            JSONObject jsonObject=object.getJSONObject("data");
            SingleBeans.getInstance().getGroupStatusBeans().setTeam_id(jsonObject.getString("team_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.emptyBack();
    }
}
