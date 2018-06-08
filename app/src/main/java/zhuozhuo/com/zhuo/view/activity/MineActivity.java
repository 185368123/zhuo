package zhuozhuo.com.zhuo.view.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.my.bean.UserDB;
import com.hyphenate.chatuidemo.my.model.GetUserMsgModel;
import com.hyphenate.chatuidemo.my.presenter.GetUserMsgPresenter;
import com.hyphenate.easeui.provider.UserInfoProvider;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.hyphenate.util.PathUtil;
import org.litepal.crud.DataSupport;
import java.io.File;
import java.io.IOException;
import java.util.List;
import li.com.base.baseuntils.ToastUitl;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.contract.ChangeMsgConstract;
import zhuozhuo.com.zhuo.http.UpLoad;
import zhuozhuo.com.zhuo.model.ChangeMsgModel;
import zhuozhuo.com.zhuo.presenter.ChangeMsgPresenter;
import zhuozhuo.com.zhuo.service.UICallback;
import zhuozhuo.com.zhuo.service.UIDispatcher;
import com.hyphenate.chatuidemo.widget.BottomDialog;
import zhuozhuo.com.zhuo.widget.CircleImageView;


public class MineActivity extends BaseActivity<ChangeMsgModel,ChangeMsgPresenter> implements View.OnClickListener, ChangeMsgConstract.View {

    private String[] permissions = { Manifest.permission.CAMERA};
    //OSS的上传下载
    private zhuozhuo.com.zhuo.service.UIDispatcher UIDispatcher;

    private CircleImageView imageView;
    private TextView mine_name;
    private TextView mine_sex;
    private TextView mine_phone;
    private EaseTitleBar minedetail_bar;
    private Dialog dialog;

    private TextView tv_card1;
    private TextView tv_card2;
    private TextView tv_card3;
    private TextView tv_card4;
    private TextView tv_card5;
    private TextView tv_card6;
    private TextView tv_card7;
    private TextView tv_card8;
    private TextView tv_card9;
    private TextView tv_sex;


    protected File cameraFile;

    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择

    private String objectName;
    private TextView mine_location;
    private TextView mine_hobby;
    @Override
    public int getLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    public void initPresenter() {
mPresenter.setVM(mModel,this);
    }

    @Override
    public void initView() {
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        UIDispatcher = new UIDispatcher(Looper.getMainLooper());

        findViewById(R.id.mine_linear1).setOnClickListener(this);
        findViewById(R.id.mine_linear2).setOnClickListener(this);
        findViewById(R.id.mine_linear3).setOnClickListener(this);
        findViewById(R.id.mine_linear4).setOnClickListener(this);
        findViewById(R.id.mine_linear5).setOnClickListener(this);
        findViewById(R.id.mine_linear6).setOnClickListener(this);

        imageView = (CircleImageView) findViewById(R.id.iv_mine);
        mine_name = (TextView) findViewById(R.id.mine_name);
        mine_sex = (TextView) findViewById(R.id.mine_sex);
        mine_phone = (TextView) findViewById(R.id.mine_phone);
        mine_location = (TextView) findViewById(R.id.mine_location);
        mine_hobby = (TextView) findViewById(R.id.mine_hobby);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_card1 = (TextView) findViewById(R.id.tv_card1);
        tv_card2 = (TextView) findViewById(R.id.tv_card2);
        tv_card3 = (TextView) findViewById(R.id.tv_card3);
        tv_card4 = (TextView) findViewById(R.id.tv_card4);
        tv_card5 = (TextView) findViewById(R.id.tv_card5);
        tv_card6 = (TextView) findViewById(R.id.tv_card6);
        tv_card7 = (TextView) findViewById(R.id.tv_card7);
        tv_card8 = (TextView) findViewById(R.id.tv_card8);
        tv_card9 = (TextView) findViewById(R.id.tv_card9);

        initData();
        initTitleBar();
    }

    private void initData() {
        List<UserDB> userDBList = DataSupport.select().where("user_id = ?", UserInfoProvider.getUserID()).find(UserDB.class);
        if (!(userDBList.size() > 0)) {
            GetUserMsgPresenter getUserMsgPresenter=new GetUserMsgPresenter();
            getUserMsgPresenter.setVM(new GetUserMsgModel(),null);
            getUserMsgPresenter.getUserMsg(UserInfoProvider.getUserID());
        }else {
            List<String> card=userDBList.get(0).getCard();
            tv_card1.setText(card.get(0%card.size()));
            tv_card2.setText(card.get(1%card.size()));
            tv_card3.setText(card.get(2%card.size()));
            tv_card4.setText(card.get(3%card.size()));
            tv_card5.setText(card.get(4%card.size()));
            tv_card6.setText(card.get(5%card.size()));
            tv_card7.setText(card.get(6%card.size()));
            tv_card8.setText(card.get(7%card.size()));
            tv_card9.setText(card.get(8%card.size()));
        }
        Glide.with(this).load(UserInfoProvider.getPhotoLink()).into(imageView);
        mine_name.setText(UserInfoProvider.getNickName());
        mine_phone.setText(UserInfoProvider.getPhone());
        mine_location.setText(UserInfoProvider.getLocation());
        mine_hobby.setText(UserInfoProvider.getHobby());
        if (UserInfoProvider.getSex().equals("1")) {
            mine_sex.setText("男");
        } else
            mine_sex.setText("女");
    }

    protected void initTitleBar() {
        minedetail_bar = (EaseTitleBar) findViewById(R.id.minedetail_bar);
        minedetail_bar.setTitle("个人资料");
        minedetail_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        minedetail_bar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_linear1://修改头像
                changeImage();
                break;
            case R.id.mine_linear2://修改昵称
                Intent intent=new Intent(this,ChangeActivity.class);
                intent.putExtra("key","nick_name");
                startActivity(intent);
                break;
            case R.id.mine_linear3://修改性别
                // 创建构建器
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                // 设置参数
                builder.setTitle("请选择性别").setIcon(R.drawable.logo)
                        .setMessage("")
                        .setPositiveButton("男", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.changeMsg("sex", "1");
                            }
                        }).setNegativeButton("女", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.changeMsg("sex", "2");
                    }
                });
                builder.create().show();
                break;
            case R.id.mine_linear4://绑定手机

                break;
            case R.id.mine_linear5://修改学院信息
                Intent intent2=new Intent(this,LocationChangeActivity.class);
                intent2.putExtra("isFirst",false);
                startActivity(intent2);
                break;
            case R.id.mine_linear6://修改爱好
                Intent intent1=new Intent(this,ChangeActivity.class);
                intent1.putExtra("key","account");
                startActivity(intent1);
                break;
        }
    }

    private void changeImage() {
        BottomDialog dialog = BottomDialog.newInstance("",new String[]{"拍照","从相册选择"});
        dialog.show(getSupportFragmentManager(),"dialog");
        dialog.setListener(new BottomDialog.OnClickListener() {
            @Override
            public void click(int position) {
                switch (position){
                    case 0:
                        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            // 检查该权限是否已经获取
                            int j = ContextCompat.checkSelfPermission(mContext, permissions[0]);
                            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                            if (j != PackageManager.PERMISSION_GRANTED) {
                                // 如果没有授予该权限，就去提示用户请求
                                // showDialogTipUserRequestPermission();
                                startRequestPermission();
                            }else {
                                selectPicFromCamera();
                            }
                        }else {
                            selectPicFromCamera();
                        }
                        break;
                    case 1:
                        selectPicFromLocal();
                        break;
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        initData();
        super.onRestart();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_GALLERY:
                if (data != null) {
                    Uri selectedImage = data.getData();
                    try {
                        updata(sendPicByUri(selectedImage));
                    } catch (ClientException e) {
                        e.printStackTrace();
                    } catch (ServiceException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PHOTO_REQUEST_CAREMA:
                try {
                    updata(cameraFile.getAbsolutePath());
                } catch (ClientException e) {
                    e.printStackTrace();
                } catch (ServiceException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void updata(String url) throws ClientException, ServiceException, IOException {
        /**
         * 上传服务器代码
         */
        objectName = "UserIcon/" + UserInfoProvider.getUserID() + System.currentTimeMillis() + ".jpg";
        Log.e("objectName", objectName);
        UpLoad.getInstance().beginUpLoad(objectName, url, getPutCallback());
    }

    public UICallback<PutObjectRequest, PutObjectResult> getPutCallback() {
        return new UICallback<PutObjectRequest, PutObjectResult>(UIDispatcher) {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                addCallback(new Runnable() {
                    @Override
                    public void run() {
                        String url = "http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/" + objectName;
                        Log.e("图片上传", url);
                        Glide.with(mContext).load(url).into(imageView);
                        mPresenter.changeMsg("photo_link", url);
                    }
                }, null);
                super.onSuccess(request, result);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                String info = "";
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    info = clientExcepion.toString();
                }
                if (serviceException != null) {
                    info = serviceException.toString();
                }
                final String outputinfo = new String(info);
                addCallback(null, new Runnable() {
                    @Override
                    public void run() {
                        displayDialog("上传失败", outputinfo);

                    }
                });
                onFailure(request, clientExcepion, serviceException);
            }
        };
    }

    public void displayDialog(String title, String message) {
        new android.app.AlertDialog.Builder(this).setTitle(title).setMessage(message).show();
    }


    /***
     * 进入相机界面
     */
    public void selectPicFromCamera() {

        if (!EaseCommonUtils.isSdcardExist()) {
            Toast.makeText(this, com.hyphenate.easeui.R.string.sd_card_does_not_exist, Toast.LENGTH_SHORT).show();
            return;
        }

        cameraFile = new File(PathUtil.getInstance().getImagePath(), EMClient.getInstance().getCurrentUser()
                + System.currentTimeMillis() + ".jpg");
        //noinspection ResultOfMethodCallIgnored
        cameraFile.getParentFile().mkdirs();
        startActivityForResult(
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
                PHOTO_REQUEST_CAREMA);
    }

    /***
     * 进入系统相册界面
     */
    protected void selectPicFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    protected String sendPicByUri(Uri selectedImage) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(this, com.hyphenate.easeui.R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            return picturePath;
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(this, com.hyphenate.easeui.R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return null;

            }
            return file.getAbsolutePath();
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
                        }
                    } else {
                        selectPicFromCamera();
                    }
                }
            }
        }
    }

    // 提示用户去应用设置界面手动开启权限

    private void showDialogTipUserGoToAppSettting() {

        dialog = new android.app.AlertDialog.Builder(this)
                .setTitle("相机权限不可用")
                .setMessage("请在-应用设置-权限-中，打开相机权限")
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
                       dialog.dismiss();
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
        ToastUitl.showLong("修改成功");
        initData();
    }
}

