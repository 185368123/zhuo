package zhuozhuo.com.zhuo.presentermodel;

import com.google.gson.Gson;
import com.zhy.http.okhttp.request.RequestCall;
import org.json.JSONObject;
import java.util.HashMap;

import li.com.base.baseuntils.LogUtils;
import zhuozhuo.com.zhuo.bean.ArticleDetailBean;
import com.hyphenate.chatuidemo.my.api.UrlConstant;
import zhuozhuo.com.zhuo.http.MyOkHttpClient;
import zhuozhuo.com.zhuo.presenter.BaseImportPresenter;
import zhuozhuo.com.zhuo.util.RequestUtils;
import zhuozhuo.com.zhuo.view.ArticleDetaileView;

/**
 * Created by Administrator on 2017/11/20.
 */

public class GetArticleDetailePresentModel extends BaseImportPresenter {
    ArticleDetaileView detaileView;

    public GetArticleDetailePresentModel(ArticleDetaileView detaileView) {
        this.detaileView = detaileView;
    }

    public void getArticleDetaile(String articleId) {
        HashMap map = new HashMap();
        // 获取 Post 请求的 RequestCall
        RequestCall requestCall = MyOkHttpClient.getPostRequest(UrlConstant.GetBBS_URL + "/" + articleId, map);

        // 发送登录请求
        RequestUtils.sendPostRequest(requestCall, this);
    }

    @Override
    public void onVerifySucceed(String strResponse, JSONObject jsonResponse, String errMsg, int id) {
        LogUtils.logd("获取帖子详情:" + strResponse);
        ArticleDetailBean bean = new Gson().fromJson(strResponse, ArticleDetailBean.class);
        detaileView.showArticleDeatile(bean.getData());
    }
}
