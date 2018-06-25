package zhuozhuo.com.zhuo.view.activity;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.provider.UserInfoProvider;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import li.com.base.basesinglebean.SingleBeans;
import li.com.base.baseuntils.ToastUitl;
import zhuozhuo.com.zhuo.MainApplication;
import zhuozhuo.com.zhuo.R;
import com.hyphenate.chatuidemo.my.bean.LoginBean;
import zhuozhuo.com.zhuo.contract.WeiXinLoginConstract;
import zhuozhuo.com.zhuo.model.WeiXinLoginModel;
import zhuozhuo.com.zhuo.presenter.WeiXinLoginPresenter;
import zhuozhuo.com.zhuo.util.ToastUtils;


public class LoginWeiXinActivity extends BaseActivity<WeiXinLoginModel, WeiXinLoginPresenter> implements View.OnClickListener, WeiXinLoginConstract.View {
    private Button button_weixin;
    private Button button_phonelogin;
    private ImageView iv_login;

    @Override
    public void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(mModel, this);
    }

    @Override
    public void initView() {
        iv_login = (ImageView) findViewById(R.id.iv_login);
        button_weixin = (Button) findViewById(R.id.button_weixinlogin);
        button_phonelogin = (Button) findViewById(R.id.button_phonelogin);
        button_weixin.setOnClickListener(this);
        button_phonelogin.setOnClickListener(this);

        MainApplication.getInstance().addActivity(this);
        try {
            if (SingleBeans.getInstance().getVisonBean().getPhone()!=null){
                Glide.with(this).load(SingleBeans.getInstance().getVisonBean().getPhone().getImage_url()).into(iv_login);
            }
        }catch (NullPointerException c){
            Glide.with(this).load(R.drawable.logingbackground).into(iv_login);
        }

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_weixinlogin://微信登录
                button_weixin.setClickable(false);
                UMShareAPI.get(this).doOauthVerify(this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case R.id.button_phonelogin:
                button_phonelogin.setClickable(false);
                startActivityForResult(LoginPhoneActivity.class, 001);
                break;
        }
    }


    UMAuthListener umAuthListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            UMShareAPI.get(LoginWeiXinActivity.this).getPlatformInfo(LoginWeiXinActivity.this, SHARE_MEDIA.WEIXIN, authListener);

        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            button_weixin.setClickable(true);
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {


        }
    };

    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            String temp = "";
            for (String key : data.keySet()) {
                temp = temp + key + " : " + data.get(key) + "\n";
            }
            mPresenter.login(data);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            button_weixin.setClickable(true);
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {

        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 001) {
            button_phonelogin.setClickable(true);
        } else {
            UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void returnLoginBean(List<LoginBean> loginBeen) {
        if (UserInfoProvider.getLocation().equals("")||UserInfoProvider.getLocation()==null||UserInfoProvider.getLocation().equals("无")){
            Intent intent=new Intent(this,LocationChangeActivity.class);
            intent.putExtra("isFirst",true);
            startActivity(intent);
        }else {
            this.startActivity(MainActivity.class);
        }
        // 登陆成功
        ToastUtils.showToast(R.string.login_text8);
        JPushInterface.setAlias(getApplicationContext(), 1,"zhuozhuo"+UserInfoProvider.getUserID());
        MainApplication.getInstance().finishAllActivity();
    }

    @Override
    public void showLoading(String title) {
        startProgressDialog(title);
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @Override
    public void showErrorTip(String msg) {
        ToastUitl.showLong(msg);
    }
}
