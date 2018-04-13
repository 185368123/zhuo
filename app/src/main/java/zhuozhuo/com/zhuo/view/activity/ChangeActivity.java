package zhuozhuo.com.zhuo.view.activity;


import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import li.com.base.baseuntils.ToastUitl;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.contract.ChangeMsgConstract;
import zhuozhuo.com.zhuo.model.ChangeMsgModel;
import zhuozhuo.com.zhuo.presenter.ChangeMsgPresenter;
import zhuozhuo.com.zhuo.util.MainUtils;
import zhuozhuo.com.zhuo.widget.TitleBarLayout;

public class ChangeActivity extends BaseActivity<ChangeMsgModel,ChangeMsgPresenter> implements View.OnClickListener, ChangeMsgConstract.View {


    private TitleBarLayout titlebar_namechange;
    private EditText etNameChange;
    private String key;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change;
    }

    @Override
    public void initPresenter() {
      mPresenter.setVM(mModel,this);
    }

    @Override
    public void initView() {
        key = getIntent().getStringExtra("key");
        findViewById(R.id.button_namechange).setOnClickListener(this);
        etNameChange = (EditText) findViewById(R.id.et_namechange);
        initTitleBar();
    }

    protected void initTitleBar() {
        titlebar_namechange = (TitleBarLayout) findViewById(R.id.titlebar_namechange);
        titlebar_namechange.setLeftTitle("返回");
        titlebar_namechange.setLeftTextIsVisible(0);
        titlebar_namechange.setRightTextIsVisible(0);
        titlebar_namechange.setTitle("修改信息");
        titlebar_namechange.setTitleTextColor(Color.WHITE);
        titlebar_namechange.setLeftTextColor(Color.WHITE);

        titlebar_namechange.setLeftTextFocusable(true);
        titlebar_namechange.setOnTitleBarClickListener(new TitleBarLayout.OnTitleBarClickListener() {
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
    public void onClick(View view) {
        String value = MainUtils.getText(etNameChange);
        mPresenter.changeMsg(key, value);
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

    @Override
    public void changeMsgSucess() {
     finish();
        ToastUitl.showLong("修改成功");
    }
}
