package zhuozhuo.com.zhuo.presentermodel;

import com.google.gson.Gson;
import com.hyphenate.easeui.provider.UserInfoProvider;
import com.zhy.http.okhttp.request.RequestCall;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;

import li.com.base.baseuntils.LogUtils;
import zhuozhuo.com.zhuo.bean.StoryVideoBean;
import com.hyphenate.chatuidemo.my.api.UrlConstant;
import zhuozhuo.com.zhuo.http.MyOkHttpClient;
import zhuozhuo.com.zhuo.presenter.BaseImportPresenter;
import zhuozhuo.com.zhuo.util.RequestUtils;
import zhuozhuo.com.zhuo.view.GetStoryView;

/**
 * Created by Administrator on 2017/11/8.
 */

public class GetStoryPresentModel extends BaseImportPresenter {

    GetStoryView storyView;

    public GetStoryPresentModel(GetStoryView view){
        storyView=view;
    }


    public void getStory(int index){
        HashMap map=new HashMap();
        map.put("token", UserInfoProvider.getToken());
        map.put("index",String.valueOf(index));
        // 获取 Post 请求的 RequestCall
        RequestCall requestCall= MyOkHttpClient.getPostRequest(UrlConstant.GetStory_URL,map);

        // 发送登录请求
        RequestUtils.sendPostRequest(requestCall, this);

    }

    @Override
    public void onVerifySucceed(String strResponse, JSONObject jsonResponse, String errMsg, int id) {
        LogUtils.logd("GetStoryPresentModel获取个人故事列表 -->"+strResponse);
        try {
            StoryVideoBean videoBean=new Gson().fromJson(strResponse,StoryVideoBean.class);
                List<StoryVideoBean.DataBean> data=videoBean.getData();
                storyView.refreshList(data);
        } catch (Exception e) {
            LogUtils.logd("GetStoryPresentModel获取个人故事列表失败 -->"+e);
           storyView.refreshList(null);
        }

    }
}
