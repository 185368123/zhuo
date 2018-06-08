package zhuozhuo.com.zhuo.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chatuidemo.UserMsgDBHelp;
import com.hyphenate.easeui.provider.PreferenceManager;
import com.hyphenate.easeui.provider.UserInfoProvider;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.contract.GetVisonConstract;
import zhuozhuo.com.zhuo.model.GetVisonModel;
import zhuozhuo.com.zhuo.presenter.GetVisonPresenter;
import zhuozhuo.com.zhuo.util.UpdateUtils;
import zhuozhuo.com.zhuo.view.fragment.WelcomeFragment;

public class Welcome2Activity extends BaseActivity<GetVisonModel, GetVisonPresenter> implements GetVisonConstract.View, UpdateUtils.OnCheckUpdateListener {

    public boolean isBack = true;
    // 要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
    private AlertDialog dialog;


    int maxIndex = 2;//Viewpage总的页数

    ViewPager viewpager;

    List<Fragment> fragments = new ArrayList<>();

    //惯性滑动
    boolean isLeftSlid = true;
    private int mTouchSlop;
    private ImageView iv;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isBack = false;
            ll.setVisibility(View.VISIBLE);
            pb.setProgress(msg.arg1);
            tv.setText("已下载" + msg.arg1 + "%");
            if (msg.arg1 == 100) {
                isBack = true;
            }
        }
    };

    private ProgressBar pb;
    private TextView tv;
    private LinearLayout ll;

    @Override
    public void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        flag = false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome2;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(mModel, this);
    }

    @Override
    public void initView() {

        //初始化控件
        viewpager = (ViewPager) findViewById(R.id.viewPager);

        UMShareAPI.get(this);
        iv = (ImageView) findViewById(R.id.iv_welcome);
        pb = (ProgressBar) findViewById(R.id.pb_welcome);
        tv = (TextView) findViewById(R.id.tv_welcome);
        ll = (LinearLayout) findViewById(R.id.ll_welcome);
        Glide.with(this).load(R.drawable.welcome2).into(iv);
        mPresenter.getVison();



        ViewConfiguration configuration = ViewConfiguration.get(this);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        viewpager.setOnTouchListener(new View.OnTouchListener() {
            int touchFlag = 0;
            float x = 0, y = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchFlag = 0;
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float xDiff = Math.abs(event.getX() - x);
                        float yDiff = Math.abs(event.getY() - y);
                        if (xDiff < mTouchSlop && xDiff >= yDiff)
                            touchFlag = 0;
                        else
                            touchFlag = -1;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (touchFlag == 0) {//ViewPage的点击事件(滑动不会触发)，点击后进入广告详情页，目前无广告设置为滑动一页
                            int currentItem = viewpager.getCurrentItem();
                            if (currentItem + 1 < maxIndex) {//没有到最后一页继续滑动
                                viewpager.setCurrentItem(currentItem + 1);
                            } else {
                                //跳转到主页
                                if (PreferenceManager.getPreferenceManager().isFirstStart()) {
                                    //跳转到登录界面
                                    UserMsgDBHelp.getUserMsgDBHelp().saveMsgToDB("1");
                                    startActivity(LoginWeiXinActivity.class);
                                    finish();
                                } else {
                                    //跳转到主界面
                                    if (UserInfoProvider.getLocation().equals("") || UserInfoProvider.getLocation() == null || UserInfoProvider.getLocation().equals("无")) {
                                        Intent intent = new Intent(Welcome2Activity.this, LocationChangeActivity.class);
                                        intent.putExtra("isFirst", true);
                                        startActivity(intent);
                                    } else {
                                        startActivity(MainActivity.class);
                                    }
                                    finish();
                                }
                            }
                        }
                        break;
                }
                return false;
            }
        });

        //设置ViewPager的滑动监听，最后一页且没有惯性滑动时，跳转到主页
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            /**滑动状态改变时
             * @param state
             */
            @Override
            public void onPageScrollStateChanged(int state) {
                //如果状态变成停止状态时，最后一页，且没有惯性滑动，就跳转到主页
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING: {
                        //手指拖动状态
                    }
                    break;
                    case ViewPager.SCROLL_STATE_IDLE: {
                        //停止状态
                        //判断是否要跳转
                        //条件：最后一页，且没有惯性滑动
                        if (!isLeftSlid && (viewpager.getCurrentItem() == viewpager.getAdapter().getCount() - 1)) {
                            //跳转到主页
                            if (PreferenceManager.getPreferenceManager().isFirstStart()) {
                                //跳转到登录界面
                                startActivity(LoginWeiXinActivity.class);
                                finish();
                            } else {
                                //跳转到主界面
                                if (UserInfoProvider.getLocation().equals("") || UserInfoProvider.getLocation() == null || UserInfoProvider.getLocation().equals("无")) {
                                    Intent intent = new Intent(Welcome2Activity.this, LocationChangeActivity.class);
                                    intent.putExtra("isFirst", true);
                                    startActivity(intent);
                                } else {
                                    startActivity(MainActivity.class);
                                }
                                finish();
                            }
                        }
                        //把该变量变成false
                        isLeftSlid = false;
                    }
                    break;
                    case ViewPager.SCROLL_STATE_SETTLING: {
                        //惯性滑动状态
                        isLeftSlid = true;
                    }
                    break;
                }
            }
        });
    }


    private void initAdapter() {
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    private void initDatas(String imageUrl) {
        //加载Fragment
        for (int i = 0; i < maxIndex; i++) {
            WelcomeFragment sf = new WelcomeFragment();
            //设置参数
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            bundle.putString("image", imageUrl);
            sf.setArguments(bundle);
            //添加到集合中
            fragments.add(sf);
        }
    }

    // 开始提交请求权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            if (requestCode == 321) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                        boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                        if (!b) {
                            // 用户还是想用我的 APP 的
                            // 提示用户去应用设置界面手动开启权限
                            showDialogTipUserGoToAppSettting();
                        } else
                            finish();
                    } else {
                        if (grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                            boolean a = shouldShowRequestPermissionRationale(permissions[1]);
                            if (!a) {
                                // 用户还是想用我的 APP 的
                                // 提示用户去应用设置界面手动开启权限
                                showDialogTipUserGoToAppSettting();
                            } else
                                finish();
                        } else {
                            checkUpdate();
                        }
                    }
                }
            }
        }
    }

    // 提示用户去应用设置界面手动开启权限

    private void showDialogTipUserGoToAppSettting() {

        dialog = new AlertDialog.Builder(this)
                .setTitle("读写权限或通话权限不可用")
                .setMessage("请在-应用设置-权限-中，打开读写权限")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);

        startActivityForResult(intent, 123);
    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSettting();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        }
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
        //加载数据
        initDatas(imageUrl);
        //设置adapter
        initAdapter();

        goNext();
    }

    public void setIv() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                iv.setVisibility(View.INVISIBLE);
                viewpager.setVisibility(View.VISIBLE);
            }
        });
    }


    public void checkUpdate() {
        UpdateUtils.getInstance().initUpdateSDK();
        UpdateUtils.getInstance().checkUpdate(this);
    }

    protected void goNext() {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int j = ContextCompat.checkSelfPermission(Welcome2Activity.this, permissions[0]);
            int i = ContextCompat.checkSelfPermission(Welcome2Activity.this, permissions[1]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (j != PackageManager.PERMISSION_GRANTED || i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                startRequestPermission();
            } else {
                checkUpdate();
            }
        } else {
            checkUpdate();
        }
    }

    @Override
    public void onBackPressed() {
        if (isBack) {
            super.onBackPressed();
        }
    }

    @Override
    public void onCheckUpdateFailure() {
        setIv();
    }

    @Override
    public void onNoUpdate() {
        setIv();
    }

    @Override
    public void already_installed() {
        // 创建构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置参数
        builder.setTitle("版本提示").setIcon(R.drawable.logo)
                .setMessage("发现新版本，是否更新？")
                .setNeutralButton("普通更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       UpdateUtils.getInstance().startUpdate(false,handler);
                    }
                }).setNegativeButton("应用宝省流量更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UpdateUtils.getInstance().startUpdate(true,handler);
            }
        }).setPositiveButton("暂不更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setIv();
            }
        });
        builder.create().show();
    }

    @Override
    public void un_installed() {
        // 创建构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置参数
        builder.setTitle("版本提示").setIcon(R.drawable.logo)
                .setMessage("发现新版本，是否更新？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UpdateUtils.getInstance().startUpdate(false,handler);
                    }
                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setIv();
            }
        });
        builder.create().show();
    }
}
