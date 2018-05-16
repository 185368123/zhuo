package zhuozhuo.com.zhuo.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import zhuozhuo.com.zhuo.MainApplication;
import zhuozhuo.com.zhuo.R;
import com.hyphenate.chatuidemo.my.bean.LoginBean;
import com.hyphenate.easeui.provider.UserInfoProvider;

import zhuozhuo.com.zhuo.contract.LoginConstract;
import zhuozhuo.com.zhuo.model.LoginModel;
import zhuozhuo.com.zhuo.presenter.LoginPresenter;
import zhuozhuo.com.zhuo.util.ToastUtils;
import zhuozhuo.com.zhuo.widget.TitleBarLayout;

public class LoginPhoneActivity extends BaseActivity<LoginModel,LoginPresenter> implements LoginConstract.View, View.OnClickListener{

    /**
     * 用户名输入框
     */
    private EditText etUserName;
    /**
     * 密码输入框
     */
    private EditText etPassword;

    private TitleBarLayout titleBar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_phone;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(mModel, this);
    }

    @Override
    public void initView() {
        MainApplication.getInstance().addActivity(this);
        setResult(RESULT_OK);


        etUserName = (EditText) findViewById(R.id.login_edit1);
        etPassword = (EditText) findViewById(R.id.login_edit2);
        Button button1= (Button)findViewById(R.id.login_button1);
        button1 .setOnClickListener(this);
        Button button2= (Button)findViewById(R.id.login_button2);
        button2 .setOnClickListener(this);
        TextView tv1= (TextView)findViewById(R.id.login_text1);
        tv1 .setOnClickListener(this);
        TextView tv2= (TextView)findViewById(R.id.login_text2);
        tv2 .setOnClickListener(this);
        TextView tv3= (TextView)findViewById(R.id.login_text3);
        tv3 .setOnClickListener(this);

        // 给在 EditText 右边的图片添加监听器，点击图片清空 EditText 中的内容
        mPresenter.addClearEditListener((ImageView) findViewById(R.id.login_image1), etUserName);
        mPresenter.addClearEditListener((ImageView) findViewById(R.id.login_image2), etPassword);

        //初始化标题
        titleBar = (TitleBarLayout) findViewById(R.id.titlebar_loginphone);
        titleBar.setLeftTitle("返回");
        titleBar.setLeftTextIsVisible(0);
        titleBar.setRightTextIsVisible(0);
        titleBar.setTitle("用户登录");
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setTitleTextColor(Color.WHITE);

        titleBar.setLeftTextFocusable(true);
        titleBar.setOnTitleBarClickListener(new TitleBarLayout.OnTitleBarClickListener() {
            @Override
            public void onLeftLinearClick(LinearLayout linearLeft, ImageView imageLeft) {

            }

            @Override
            public void onRightLinearClick(LinearLayout linearRight, ImageView imageRight) {

            }

            @Override
            public void onLeftTextClick(TextView tvLeft) {
                finish();
            }

            @Override
            public void onRightTextClick(TextView tvRight) {

            }
        });
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button1://登录
                try {
                    mPresenter.returnLoginBean(etUserName, etPassword);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.login_button2://注册
                startActivity(TermsActivity.class);
                break;
            case R.id.login_text1://忘记密码
                startActivity(ForgetPasswordActivity.class);
                break;
            case R.id.login_text2://使用条款
                startActivity(EulaActivity.class);
                break;
            case R.id.login_text3://隐私政策
                startActivity(PrivacyActivity.class);
                break;
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
         startProgressDialog("正在登陆......");
    }

    @Override
    public void stopLoading() {
          stopProgressDialog();
    }

    @Override
    public void showErrorTip(String msg) {

    }
}
