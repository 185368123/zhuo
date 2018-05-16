package zhuozhuo.com.zhuo.view.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.view.View;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.hyphenate.chatuidemo.my.https.UICallback;
import com.hyphenate.chatuidemo.my.https.UIDispatcher;
import com.hyphenate.chatuidemo.my.https.UpLoad;
import com.hyphenate.easeui.provider.UserInfoProvider;

import java.io.File;
import java.io.IOException;

import li.com.base.baseuntils.ToastUitl;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.contract.ChangeMsgConstract;
import zhuozhuo.com.zhuo.model.ChangeMsgModel;
import zhuozhuo.com.zhuo.presenter.ChangeMsgPresenter;

public class RecordVideoActivity extends BaseActivity<ChangeMsgModel, ChangeMsgPresenter> implements ChangeMsgConstract.View {

    private JCameraView jv;
    private boolean granted = false;


    @Override
    protected void onStart() {
        super.onStart();
        //全屏显示
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (granted) {
            jv.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        jv.onPause();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_record_video;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(mModel, this);
    }

    @Override
    public void initView() {
        jv = findViewById(R.id.jcv_record);
        jv.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "JCamera");
        jv.setDuration(14 * 1000);
        jv.setMinDuration(4 * 1000);
        jv.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {

            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
                try {
                    updata(url);
                } catch (ClientException e) {
                    e.printStackTrace();
                } catch (ServiceException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void quit() {
                finish();
            }
        });

    }

    String objectName;

    private UIDispatcher UIDispatcher = new UIDispatcher(Looper.getMainLooper());
    ;

    private void updata(String url) throws ClientException, ServiceException, IOException {
        /**
         * 上传服务器代码
         */

        objectName = "Single/" + UserInfoProvider.getNickName() + System.currentTimeMillis() + ".mp4";
        UpLoad.getInstance().beginUpLoad(objectName, url, getPutCallback());
    }

    //图片上传阿里服务器成功时回调
    public UICallback<PutObjectRequest, PutObjectResult> getPutCallback() {
        return new UICallback<PutObjectRequest, PutObjectResult>(UIDispatcher) {
            @Override
            public void onSuccess(final PutObjectRequest request, final PutObjectResult result) {
                addCallback(new Runnable() {
                    @Override
                    public void run() {
                        String url = "http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/" + objectName;
                        mPresenter.changeMsg("user_video", url);
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
                addCallback(null, new Runnable() {
                    @Override
                    public void run() {
                    }
                });
                onFailure(request, clientExcepion, serviceException);
            }
        };
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
        UserInfoProvider.setUserVideo("http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/" + objectName);
        ToastUitl.showLong("录制成功");
         finish();
    }
}
