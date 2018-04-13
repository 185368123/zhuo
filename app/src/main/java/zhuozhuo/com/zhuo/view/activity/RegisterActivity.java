package zhuozhuo.com.zhuo.view.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import li.com.base.baseuntils.ToastUitl;
import zhuozhuo.com.zhuo.MainApplication;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.contract.RegisterConstract;
import zhuozhuo.com.zhuo.model.RegisterModel;
import zhuozhuo.com.zhuo.presenter.RegisterPresenter;
import zhuozhuo.com.zhuo.widget.TitleBarLayout;

public class RegisterActivity extends BaseActivity<RegisterModel,RegisterPresenter> implements RadioGroup.OnCheckedChangeListener, RegisterConstract.View, View.OnClickListener {

    private TitleBarLayout titleBar_register;

    /**
     * 性别标记，1是男，2是女
     */
    private String sex = "1";
    /**
     * 手机号码，验证码，密码，昵称
     */
    private EditText etPhoneNumber, etVerifyCode, etPassword, etName;
    /**
     * 获取验证码，注册
     */
    private Button btGteVerifyCode;
    private RadioGroup radioGroup;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
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
        //昵称
        etName = (EditText) findViewById(R.id.register_edit4);

        // 获取验证码
        btGteVerifyCode = (Button) findViewById(R.id.register_button1);
        btGteVerifyCode.setOnClickListener(this);

        //注册按钮
        findViewById(R.id.register_button2).setOnClickListener(this);

        //性别单选按钮
        radioGroup = (RadioGroup) findViewById(R.id.register_radiogroup);
        radioGroup.setOnCheckedChangeListener(this);

        //清除输入框监听
        mPresenter.addClearEditListener((ImageView) findViewById(R.id.register_image1),etPhoneNumber);
        mPresenter.addClearEditListener((ImageView) findViewById(R.id.register_image2),etVerifyCode);
        mPresenter.addClearEditListener((ImageView) findViewById(R.id.register_image3),etPassword);


        titleBar_register = (TitleBarLayout) findViewById(R.id.titlebar_register);
        titleBar_register.setLeftTitle("返回");
        titleBar_register.setLeftTextIsVisible(0);
        titleBar_register.setRightTextIsVisible(0);
        titleBar_register.setTitle("用户注册");
        titleBar_register.setLeftTextColor(Color.WHITE);
        titleBar_register.setTitleTextColor(Color.WHITE);

        titleBar_register.setLeftTextFocusable(true);
        titleBar_register.setOnTitleBarClickListener(new TitleBarLayout.OnTitleBarClickListener() {
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
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.button_ms:
                sex="2";
                break;
            case R.id.button_mr:
                sex="1";
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_button1:
                mPresenter.getAnthCode(etPhoneNumber, btGteVerifyCode);
                break;
            case R.id.register_button2:
                mPresenter.register(etPhoneNumber, etPassword, etVerifyCode, etName, sex);
                break;
        }
    }

    @Override
    public void registerSucess() {
        this.startActivity(MainActivity.class);
        // 注册成功
        ToastUitl.showLong(R.string.register_text11);
        MainApplication.getInstance().finishAllActivity();
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
