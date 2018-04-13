package zhuozhuo.com.zhuo.view.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import li.com.base.baseuntils.ToastUitl;
import zhuozhuo.com.zhuo.MainApplication;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.contract.ForgetPasswordConstract;
import zhuozhuo.com.zhuo.model.ForgetPasswordModel;
import zhuozhuo.com.zhuo.presenter.ForgetPasswordPresenter;
import zhuozhuo.com.zhuo.widget.TitleBarLayout;

public class ForgetPasswordActivity extends BaseActivity<ForgetPasswordModel,ForgetPasswordPresenter> implements View.OnClickListener, ForgetPasswordConstract.View {

    private EditText etPhoneNumber;
    private EditText etVerifyCode;
    private Button btGteVerifyCode;
    private EditText etPassword;
    private Button forget_button2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initPresenter() {
       mPresenter.setVM(mModel,this);
    }

    @Override
    public void initView() {
        MainApplication.getInstance().addActivity(this);
        // 手机号码
        etPhoneNumber = (EditText) findViewById(R.id.register_edit1);
        // 验证码
        etVerifyCode = (EditText) findViewById(R.id.register_edit2);
        // 密码
        etPassword = (EditText) findViewById(R.id.register_edit3);
        // 获取验证码
        btGteVerifyCode = (Button) findViewById(R.id.forget_button1);
        //确定
        forget_button2 = (Button) findViewById(R.id.forget_button2);

        btGteVerifyCode.setOnClickListener(this);
        forget_button2.setOnClickListener(this);

        initTitleBar();

        mPresenter.addClearEditListener((ImageView)findViewById( R.id.register_image1),etPhoneNumber);
        mPresenter.addClearEditListener((ImageView)findViewById( R.id.register_image2),etVerifyCode);
        mPresenter.addClearEditListener((ImageView)findViewById( R.id.register_image3),etPassword);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forget_button1://获取验证码
                mPresenter.getAnthCode(etPhoneNumber, btGteVerifyCode);
                break;
            case R.id.forget_button2: // 下一步
                mPresenter.changePassword(etPhoneNumber,etVerifyCode,etPassword);
                break;

        }

    }

    protected void initTitleBar() {
        TitleBarLayout titlebar_fargetpasswor = (TitleBarLayout) findViewById(R.id.titlebar_fargetpasswor);
        titlebar_fargetpasswor.setLeftTitle("返回");
        titlebar_fargetpasswor.setLeftTextIsVisible(0);
        titlebar_fargetpasswor.setRightTextIsVisible(0);
        titlebar_fargetpasswor.setTitle("找回密码");
        titlebar_fargetpasswor.setLeftTextColor(Color.WHITE);
        titlebar_fargetpasswor.setTitleTextColor(Color.WHITE);

        titlebar_fargetpasswor.setLeftTextFocusable(true);
        titlebar_fargetpasswor.setOnTitleBarClickListener(new TitleBarLayout.OnTitleBarClickListener() {
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

    @Override
    public void changePasswordSucess() {
        ToastUitl.showLong("密码修改成功");
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }
}
