package zhuozhuo.com.zhuo.presentermodel;

import com.google.gson.Gson;
import com.hyphenate.chatuidemo.provider.UserInfoProvider;
import com.zhy.http.okhttp.request.RequestCall;
import org.json.JSONObject;
import java.util.HashMap;
import li.com.base.baseuntils.LogUtils;
import zhuozhuo.com.zhuo.bean.RemarkBean;
import com.hyphenate.chatuidemo.my.api.UrlConstant;
import zhuozhuo.com.zhuo.http.MyOkHttpClient;
import zhuozhuo.com.zhuo.presenter.BaseImportPresenter;
import zhuozhuo.com.zhuo.util.RequestUtils;
import zhuozhuo.com.zhuo.view.RemarkView;

/**
 * Created by Administrator on 2017/11/9.
 */

public class GetRemarkPersenterModel extends BaseImportPresenter {
    RemarkView remarkView;

    public GetRemarkPersenterModel(RemarkView view) {
        remarkView = view;
    }

    ;

    public void getRemarkList(int index) {//获取选项列表
        HashMap map = new HashMap();
        map.put("token", UserInfoProvider.getToken());
        map.put("user_id", UserInfoProvider.getUserID());
        map.put("index", index + "");
        // 获取 Post 请求的 RequestCall
        RequestCall requestCall = MyOkHttpClient.getPostRequest(UrlConstant.GetRemark_URL, map, UserInfoProvider.getSessionId());


        RequestUtils.sendPostRequest(requestCall, this);

    }

    @Override
    public void onVerifySucceed(String strResponse, JSONObject jsonResponse, String errMsg, int id) {
        LogUtils.logd("GetRemarkPersenterModel获取打分列表 -->"+strResponse);
        try {
                RemarkBean bean = new Gson().fromJson(strResponse, RemarkBean.class);
                remarkView.changeRemark(bean.getData());
        } catch (Exception e) {
            LogUtils.logd("GetRemarkPersenterModel获取打分列表解析失败 -->"+e);
            remarkView.changeRemark(null);
        }
    }
}
