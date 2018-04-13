package zhuozhuo.com.zhuo.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.chatuidemo.provider.PreferenceManager;
import com.hyphenate.easeui.events.RxBusConstants;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import li.com.base.R;
import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.baserx.RxManager;
import li.com.base.baseuntils.LogUtils;
import li.com.base.baseuntils.TUtil;
import li.com.base.baseuntils.ToastUitl;
import li.com.base.basewidget.LoadingDialog;
import rx.functions.Action1;
import zhuozhuo.com.zhuo.MainApplication;
import zhuozhuo.com.zhuo.presentermodel.AcceptPresentModel;
import zhuozhuo.com.zhuo.presentermodel.DenyPresentModel;

/**
 * Created by Administrator on 2018/2/27.
 */

public abstract class BaseActivity<M extends BaseModel, P extends BasePresenter> extends AppCompatActivity {
    public P mPresenter;
    public M mModel;
    public Context mContext;
    public RxManager mRxManager;
    private AlertDialog dialog;
    protected boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetcontentView();
        setContentView(getLayoutId());
        mContext = this;
        mModel = TUtil.getT(this, 0);
        mPresenter = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
        if (flag){
            MainApplication.getInstance().addActivity(this);
        }
        initRxBus();
        initPresenter();
        initView();
    }

    protected void initRxBus() {
        mRxManager = new RxManager();
        mRxManager.on("accept", new Action1<String>() {
            @Override
            public void call(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("method").equals("accept")) {
                        dialog = new AlertDialog.Builder(mContext)
                                .setCancelable(false)
                                .setMessage(object.getString("nick_name") + "  接受了邀请")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.dismiss();
                                    }
                                }).create();
                    }
                    dialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        mRxManager.on("deny", new Action1<String>() {
            @Override
            public void call(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("method").equals("deny")) {
                        dialog = new AlertDialog.Builder(mContext)
                                .setCancelable(false)
                                .setMessage(object.getString("nick_name") + "  拒绝了邀请")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.dismiss();
                                    }
                                }).create();
                    }
                    dialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        mRxManager.on("invite", new Action1<String>() {
            @Override
            public void call(String s) {
                try {
                    final JSONObject object = new JSONObject(s);
                    final String user_id = object.getString("current_user_id");
                    final String team_id = object.getString("team_id");
                    dialog = new AlertDialog.Builder(mContext)
                            .setTitle("邀请函")
                            .setCancelable(false)
                            .setMessage("来自" + object.getString("nick_name") + "用户的组队邀请")
                            .setPositiveButton("丑拒", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    new DenyPresentModel().deny(user_id, team_id);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("接受", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    new AcceptPresentModel().accept(user_id, team_id);
                                    dialog.dismiss();
                                }
                            }).create();
                    dialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        mRxManager.on(RxBusConstants.RESTART, new Action1<String>() {
            @Override
            public void call(String unm) {
                ToastUitl.showLong("用户信息失效，请从新登陆!");
                startProgressDialog("正在注销...");
                DemoHelper.getInstance().logout(true, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        LogUtils.logd("环信注销成功"+PreferenceManager.getPreferenceManager().isFirstStart());
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

                    }
                });
            }
        });
    }

    ;


    /**
     * 设置layout前配置
     */
    public void doBeforeSetcontentView() {
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /*********************子类实现*****************************/
    //获取布局文件
    public abstract int getLayoutId();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    public abstract void initView();


    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * 开启浮动加载进度条
     */
    public void startProgressDialog() {
        LoadingDialog.showDialogForLoading(this);
    }

    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        LoadingDialog.showDialogForLoading(this, msg, true);
    }

    /**
     * 停止浮动加载进度条
     */
    public void stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading();
    }

    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
        ToastUitl.showShort(text);
    }

    /**
     * 短暂显示Toast提示(id)
     **/
    public void showShortToast(int resId) {
        ToastUitl.showShort(resId);
    }

    /**
     * 长时间显示Toast提示(来自res)
     **/
    public void showLongToast(int resId) {
        ToastUitl.showLong(resId);
    }

    /**
     * 长时间显示Toast提示(来自String)
     **/
    public void showLongToast(String text) {
        ToastUitl.showLong(text);
    }

    /**
     * 带图片的toast
     *
     * @param text
     * @param res
     */
    public void showToastWithImg(String text, int res) {
        ToastUitl.showToastWithImg(text, res);
    }

    /**
     * 网络访问错误提醒
     */
    public void showNetErrorTip() {
        ToastUitl.showToastWithImg(getText(R.string.net_error).toString(), R.drawable.ic_wifi_off);
    }

    public void showNetErrorTip(String error) {
        ToastUitl.showToastWithImg(error, R.drawable.ic_wifi_off);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);//友盟统计
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);//友盟统计
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();
        if (mRxManager != null) {
            mRxManager.clear();
        }
    }
}
