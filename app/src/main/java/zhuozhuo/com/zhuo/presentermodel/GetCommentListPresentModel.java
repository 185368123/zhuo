package zhuozhuo.com.zhuo.presentermodel;

import com.google.gson.Gson;
import com.zhy.http.okhttp.request.RequestCall;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

import li.com.base.baseuntils.LogUtils;
import zhuozhuo.com.zhuo.bean.CommentBean;
import com.hyphenate.chatuidemo.my.api.UrlConstant;
import zhuozhuo.com.zhuo.http.MyOkHttpClient;
import zhuozhuo.com.zhuo.presenter.BaseImportPresenter;
import zhuozhuo.com.zhuo.util.RequestUtils;
import zhuozhuo.com.zhuo.view.FullScreenView;

/**
 * Created by Administrator on 2017/11/8.
 */

public class GetCommentListPresentModel extends BaseImportPresenter {
    FullScreenView view;

    public GetCommentListPresentModel(FullScreenView fullScreenView) {
        view = fullScreenView;
    }

    ;

    public void getList(int index, String id) {
        HashMap map = new HashMap();
        map.put("index", String.valueOf(index));
        // 获取 Post 请求的 RequestCall
        RequestCall requestCall = MyOkHttpClient.getPostRequest(UrlConstant.GetComment_URL + id, map);

        // 发送登录请求
        RequestUtils.sendPostRequest(requestCall, this);

    }

    @Override
    public void onVerifySucceed(String strResponse, JSONObject jsonResponse, String errMsg, int id) {

        try {
            if (new JSONObject(strResponse).getJSONArray("data").length() == 0) {
                view.changeList(null);
            } else {
                CommentBean commentBean = new Gson().fromJson(strResponse, CommentBean.class);
                view.changeList(commentBean.getData());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
