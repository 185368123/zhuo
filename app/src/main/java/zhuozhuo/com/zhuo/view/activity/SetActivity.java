package zhuozhuo.com.zhuo.view.activity;

import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.chatuidemo.DemoModel;
import com.hyphenate.chatuidemo.provider.PreferenceManager;
import com.hyphenate.chatuidemo.provider.UserInfoProvider;
import com.hyphenate.chatuidemo.widget.BottomDialog;
import com.hyphenate.easeui.widget.EaseSwitchButton;

import java.util.ArrayList;
import java.util.List;

import zhuozhuo.com.zhuo.MainApplication;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.adapter.SpinnerAdapter;
import zhuozhuo.com.zhuo.contract.GetVisonConstract;
import zhuozhuo.com.zhuo.model.GetVisonModel;
import zhuozhuo.com.zhuo.presenter.GetVisonPresenter;
import zhuozhuo.com.zhuo.widget.TitleBarLayout;

public class SetActivity extends BaseActivity<GetVisonModel, GetVisonPresenter> implements AdapterView.OnItemSelectedListener, View.OnClickListener, GetVisonConstract.View {

    private DemoModel settingsModel;

    private TitleBarLayout titlebar_set;

    /**
     * new message notification
     */
    private RelativeLayout rl_switch_notification;
    /**
     * sound
     */
    private RelativeLayout rl_switch_sound;
    /**
     * vibration
     */
    private RelativeLayout rl_switch_vibrate;

    private EaseSwitchButton notifySwitch;
    private EaseSwitchButton soundSwitch;
    private EaseSwitchButton vibrateSwitch;
    private Spinner spinner;
    private SpinnerAdapter spinnerAdapter;
    List<String> list = new ArrayList<>();
    private LinearLayout linearLayout;
    private TextView tv_vison;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(mModel, this);
    }

    @Override
    public void initView() {
        initTitleBar();
        MainApplication.getInstance().addActivity(this);
        findViewById(R.id.button_set).setOnClickListener(this);
        list.add("不限");
        list.add("男");
        list.add("女");
        settingsModel = DemoHelper.getInstance().getModel();
        spinnerAdapter = new SpinnerAdapter(this, list);


        notifySwitch = (EaseSwitchButton) findViewById(R.id.switch_notification);
        soundSwitch = (EaseSwitchButton) findViewById(R.id.switch_sound);
        vibrateSwitch = (EaseSwitchButton) findViewById(R.id.switch_vibrate);
        rl_switch_notification = (RelativeLayout) findViewById(R.id.rl_switch_notification);
        rl_switch_sound = (RelativeLayout) findViewById(R.id.rl_switch_sound);
        rl_switch_vibrate = (RelativeLayout) findViewById(R.id.rl_switch_vibrate);
        linearLayout = (LinearLayout) findViewById(R.id.linearlayout_set);
        tv_vison = (TextView) findViewById(R.id.tv_vison);

        spinner = (Spinner) findViewById(R.id.spinner_set);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(UserInfoProvider.getMatchCondition());


        tv_vison.setText("V" + MainApplication.getInstance().getVison());
        spinner.setOnItemSelectedListener(this);
        notifySwitch.setOnClickListener(this);
        rl_switch_notification.setOnClickListener(this);
        rl_switch_sound.setOnClickListener(this);
        rl_switch_vibrate.setOnClickListener(this);
        linearLayout.setOnClickListener(this);

        // the vibrate and sound notification are allowed or not?
        if (settingsModel.getSettingMsgNotification()) {
            notifySwitch.openSwitch();
        } else {
            notifySwitch.closeSwitch();
        }

        // sound notification is switched on or not?
        if (settingsModel.getSettingMsgSound()) {
            soundSwitch.openSwitch();
        } else {
            soundSwitch.closeSwitch();
        }
        // vibrate notification is switched on or not?
        if (settingsModel.getSettingMsgVibrate()) {
            vibrateSwitch.openSwitch();
        } else {
            vibrateSwitch.closeSwitch();
        }

    }

    protected void initTitleBar() {
        titlebar_set = (TitleBarLayout) findViewById(R.id.titlebar_set);
        titlebar_set.setLeftTitle("返回");
        titlebar_set.setLeftTextColor(Color.WHITE);
        titlebar_set.setLeftTextIsVisible(0);
        titlebar_set.setRightTextIsVisible(0);
        titlebar_set.setTitle("设置");
        titlebar_set.setTitleTextColor(Color.WHITE);

        titlebar_set.setLeftTextFocusable(true);
        titlebar_set.setOnTitleBarClickListener(new TitleBarLayout.OnTitleBarClickListener() {
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
        int i = view.getId();
        if (i == R.id.button_set) {
            logout();
        } else if (i == R.id.rl_switch_notification) {
            if (notifySwitch.isSwitchOpen()) {
                notifySwitch.closeSwitch();
                rl_switch_sound.setVisibility(View.GONE);
                rl_switch_vibrate.setVisibility(View.GONE);
                settingsModel.setSettingMsgNotification(false);
            } else {
                notifySwitch.openSwitch();
                rl_switch_sound.setVisibility(View.VISIBLE);
                rl_switch_vibrate.setVisibility(View.VISIBLE);
                settingsModel.setSettingMsgNotification(true);
            }

        } else if (i == R.id.rl_switch_sound) {
            if (soundSwitch.isSwitchOpen()) {
                soundSwitch.closeSwitch();
                settingsModel.setSettingMsgSound(false);
            } else {
                soundSwitch.openSwitch();
                settingsModel.setSettingMsgSound(true);
            }

        } else if (i == R.id.rl_switch_vibrate) {
            if (vibrateSwitch.isSwitchOpen()) {
                vibrateSwitch.closeSwitch();
                settingsModel.setSettingMsgVibrate(false);
            } else {
                vibrateSwitch.openSwitch();
                settingsModel.setSettingMsgVibrate(true);
            }
        } else if (i == R.id.linearlayout_set) {
            mPresenter.getVison();
        }
    }

    void logout() {
        BottomDialog dialog = BottomDialog.newInstance("退出当前账号？", new String[]{"退出"});
        dialog.show(getSupportFragmentManager(), "dialog");
        dialog.setListener(new BottomDialog.OnClickListener() {
            @Override
            public void click(int position) {
                switch (position) {
                    case 0:
                        startProgressDialog("正在注销......");
                        String st = getResources().getString(com.hyphenate.chatuidemo.R.string.Are_logged_out);
                        UserInfoProvider.setStatus("0");
                        DemoHelper.getInstance().logout(true, new EMCallBack() {

                            @Override
                            public void onSuccess() {
                                PreferenceManager.getPreferenceManager().setIsFirstStart(true);
                                EMClient.getInstance().logout(true);
                                startActivity(LoginWeiXinActivity.class);
                                MainApplication.getInstance().finishAllActivity();
                                stopProgressDialog();
                            }

                            @Override
                            public void onProgress(int progress, String status) {

                            }

                            @Override
                            public void onError(int code, String message) {
                                stopProgressDialog();
                            }
                        });
                        break;
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        UserInfoProvider.setMatchCondition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
    public void returnVison(boolean isUpate, final String upateUrl, String imageUrl) {
        if (isUpate) {
            AlertDialog.Builder builder_ = new AlertDialog.Builder(this);
            builder_.setTitle("版本提示").setIcon(R.drawable.logo_80)
                    .setMessage("当前已是最新版本")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder_.create().show();
        } else {
            // 创建构建器
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // 设置参数
            builder.setTitle("版本提示").setIcon(R.drawable.logo_80)
                    .setMessage("发现新版本，是否更新？")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse(upateUrl);
                            intent.setData(content_url);
                            startActivity(intent);
                            finish();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }
}
