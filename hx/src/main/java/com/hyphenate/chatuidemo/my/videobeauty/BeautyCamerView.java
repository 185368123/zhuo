package com.hyphenate.chatuidemo.my.videobeauty;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.VideoView;

import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.my.SimpleVideo;
import com.hyphenate.chatuidemo.my.button.CameraListener;
import com.hyphenate.chatuidemo.my.button.CaptureLayout;
import com.hyphenate.chatuidemo.my.button.CaptureListener;
import com.hyphenate.chatuidemo.my.button.ClickListener;
import com.hyphenate.chatuidemo.my.button.ReturnListener;
import com.hyphenate.chatuidemo.my.button.TypeListener;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import li.com.base.baseapp.BaseApplication;
import li.com.base.baserx.RxManager;
import rx.functions.Action1;


/**
 * Created by Administrator on 2018/5/21.
 */

public class BeautyCamerView extends FrameLayout implements View.OnTouchListener, SensorControler.CameraFocusListener {

    public static final int BUTTON_STATE_ONLY_CAPTURE = 0x101;      //只能拍照
    public static final int BUTTON_STATE_ONLY_RECORDER = 0x102;     //只能录像
    public static final int BUTTON_STATE_BOTH = 0x103;              //两者都可以

    private String savePath;
    //切换摄像头按钮的参数
    private int iconSize = 0;       //图标大小
    private int iconMargin = 0;     //右上边距
    private int iconSrc = 0;        //图标资源
    private int iconLeft = 0;       //左图标
    private int iconRight = 0;      //右图标
    private int duration = 0;       //录制时间
    private Context mContext;

    //回调监听
    private CameraListener cameraLisenter;
    private ClickListener leftClickListener;
    private ClickListener rightClickListener;

    public ImageView mSwitchCamera;
    private CaptureLayout mCaptureLayout;
    private CameraView cv;
    private FocusImageView mFocus;
    private SensorControler mSensorControler;
    private SimpleVideo video;
    private GSYVideoOptionBuilder gsyVideoOption;
    private RxManager rxManager=new RxManager();
    private final String SATRT_VIDEO="StartPlayVideo";

    public BeautyCamerView(@NonNull Context context) {
        this(context, null);
    }

    public BeautyCamerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BeautyCamerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        //get AttributeSet
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.JCameraView, defStyleAttr, 0);
        iconSize = a.getDimensionPixelSize(R.styleable.JCameraView_iconSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 35, getResources().getDisplayMetrics()));
        iconMargin = a.getDimensionPixelSize(R.styleable.JCameraView_iconMargin, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()));
        iconSrc = a.getResourceId(R.styleable.JCameraView_iconSrc, R.drawable.ic_camera);
        iconLeft = a.getResourceId(R.styleable.JCameraView_iconLeft, 0);
        iconRight = a.getResourceId(R.styleable.JCameraView_iconRight, 0);
        duration = a.getInteger(R.styleable.JCameraView_duration_max, 30 * 1000);       //没设置默认为30s
        a.recycle();

        initView();
    }

    private void initView() {
        rxManager.on(SATRT_VIDEO, new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (video.getCurrentState()!=SimpleVideo.CURRENT_STATE_PLAYING){
                    video.getStartButton().performClick();
                }
            }
        });
        final View view = LayoutInflater.from(mContext).inflate(R.layout.beauty_camer_layout, this);
        video = (SimpleVideo) view.findViewById(R.id.video_view);
        video.getBackButton().setVisibility(View.GONE);
        cv = (CameraView) view.findViewById(R.id.cv);
        cv.setOnTouchListener(this);
        mFocus = (FocusImageView) view.findViewById(R.id.focusImageView);
        mSensorControler = SensorControler.getInstance();
        mSensorControler.setCameraFocusListener(this);
        mSwitchCamera = (ImageView) view.findViewById(R.id.image_switch);
        mSwitchCamera.setImageResource(iconSrc);
        mCaptureLayout = (CaptureLayout) view.findViewById(R.id.capture_layout);
        mCaptureLayout.setDuration(duration);
        mCaptureLayout.setIconSrc(iconLeft, iconRight);
        mCaptureLayout.setButtonFeatures(BUTTON_STATE_ONLY_RECORDER);

        //切换摄像头
        mSwitchCamera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cv.switchCamera();
            }
        });
        //拍照 录像
        mCaptureLayout.setCaptureLisenter(new CaptureListener() {
            @Override
            public void takePictures() {

            }

            @Override
            public void recordShort(long time) {
                mCaptureLayout.setTextWithAnimation("录制时间过短");
                mCaptureLayout.resetCaptureLayout();
                mSwitchCamera.setVisibility(VISIBLE);
                cv.stopRecord();
                deleteVideo();
            }

            @Override
            public void recordStart() {
                mSwitchCamera.setVisibility(INVISIBLE);
                long time = System.currentTimeMillis();
                savePath = getPath("record/", time + ".mp4");
                cv.setSavePath(savePath);
                cv.startRecord();
            }

            @Override
            public void recordEnd(long time) {
                cv.stopRecord();
                video.setVisibility(VISIBLE);
                gsyVideoOption = new GSYVideoOptionBuilder();
                gsyVideoOption.setUrl(savePath);
                gsyVideoOption.setLooping(true);
                gsyVideoOption.build(video);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        rxManager.post(SATRT_VIDEO,"");
                    }
                },500);
            }

            @Override
            public void recordZoom(float zoom) {

            }

            @Override
            public void recordError() {
            }
        });

        //确认 取消
        mCaptureLayout.setTypeLisenter(new TypeListener() {
            @Override
            public void cancel() {
                if (video.getCurrentState()==SimpleVideo.CURRENT_STATE_PLAYING){
                    video.getStartButton().performClick();
                }
                video.setVisibility(GONE);
                deleteVideo();
                mSwitchCamera.setVisibility(VISIBLE);
                mCaptureLayout.resetCaptureLayout();
            }

            @Override
            public void confirm() {
                if (video.getCurrentState()==SimpleVideo.CURRENT_STATE_PLAYING){
                    video.getStartButton().performClick();
                }
                cameraLisenter.recordSuccess(savePath, null);
            }
        });
        //退出
        mCaptureLayout.setReturnLisenter(new ReturnListener() {
            @Override
            public void onReturn() {
                if (cameraLisenter != null) {
                    if (video.getCurrentState()==SimpleVideo.CURRENT_STATE_PLAYING){
                        video.getStartButton().performClick();
                    }
                    cameraLisenter.quit();
                }
            }
        });

        mCaptureLayout.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                if (leftClickListener != null) {
                    leftClickListener.onClick();
                }
            }
        });

        mCaptureLayout.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {
                if (rightClickListener != null) {
                    rightClickListener.onClick();
                }
            }
        });
    }

    private void deleteVideo() {
        File file = new File(savePath);
        file.delete();
    }

    //获取VideoPath
    public static String getPath(String path, String fileName) {
        String p = getBaseFolder() + path;
        File f = new File(p);
        if (!f.exists() && !f.mkdirs()) {
            return getBaseFolder() + fileName;
        }
        return p + fileName;
    }

    public static String getBaseFolder() {
        String baseFolder = Environment.getExternalStorageDirectory() + "/Codec/";
        File f = new File(baseFolder);
        if (!f.exists()) {
            boolean b = f.mkdirs();
            if (!b) {
                baseFolder = BaseApplication.getContext().getExternalFilesDir(null).getAbsolutePath() + "/";
            }
        }
        return baseFolder;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        cv.onTouch(event);
        if (cv.getCameraId() == 1) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                float sRawX = event.getRawX();
                float sRawY = event.getRawY();
                float rawY = sRawY * BaseApplication.screenWidth / BaseApplication.screenHeight;
                float temp = sRawX;
                float rawX = rawY;
                rawY = (BaseApplication.screenWidth - temp) * BaseApplication.screenHeight / BaseApplication.screenWidth;

                Point point = new Point((int) rawX, (int) rawY);
                cv.onFocus(point, callback);
                mFocus.startFocus(new Point((int) sRawX, (int) sRawY));
        }
        return true;
    }

    Camera.AutoFocusCallback callback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            //聚焦之后根据结果修改图片
            Log.e("hero", "----onAutoFocus====" + success);
            if (success) {
                mFocus.onFocusSuccess();
            } else {
                //聚焦失败显示的图片
                mFocus.onFocusFailed();

            }
        }
    };

    @Override
    public void onFocus() {
        if (cv.getCameraId() == 1) {
            return;
        }
        Point point = new Point(BaseApplication.screenWidth / 2, BaseApplication.screenHeight / 2);
        cv.onFocus(point, callback);
    }


    /**************************************************
     * 对外提供的API                     *
     **************************************************/


    public void setCameraLisenter(CameraListener cameraLisenter) {
        this.cameraLisenter = cameraLisenter;
    }

    //设置CaptureButton功能（拍照和录像）
    public void setFeatures(int state) {
        this.mCaptureLayout.setButtonFeatures(state);
    }

    public void setLeftClickListener(ClickListener clickListener) {
        this.leftClickListener = clickListener;
    }

    public void setRightClickListener(ClickListener clickListener) {
        this.rightClickListener = clickListener;
    }

    public void setSavePath(String savePath) {
        cv.setSavePath(savePath);
    }

    public void setDuration(int duration) {
        mCaptureLayout.setDuration(duration);
    }

    public void setMinDuration(int minDuration) {
        mCaptureLayout.setMinDuration(minDuration);
    }


    public void onResume() {
        cv.onResume();
    }

    public void onPause() {
        if (video.getCurrentState()==SimpleVideo.CURRENT_STATE_PLAYING){
            video.getStartButton().performClick();
        }
        cv.onPause();
    }

    public void onDestroy() {
        cv.onDestroy();
    }

}
