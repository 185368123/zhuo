package li.com.videobeauty;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;

import li.com.videobeauty.button.CameraListener;
import li.com.videobeauty.button.CaptureLayout;
import li.com.videobeauty.button.CaptureListener;
import li.com.videobeauty.button.ClickListener;
import li.com.videobeauty.button.ReturnListener;
import li.com.videobeauty.button.TypeListener;


/**
 * Created by Administrator on 2018/5/21.
 */

public class BeautyCamerView extends FrameLayout {

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
        View view = LayoutInflater.from(mContext).inflate(R.layout.beauty_camer_layout, this);
        cv = view.findViewById(R.id.cv);
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
             deleteVideo();
              mCaptureLayout.resetCaptureLayout();
            }

            @Override
            public void confirm() {
             cameraLisenter.recordSuccess(savePath,null);
            }
        });
        //退出
        mCaptureLayout.setReturnLisenter(new ReturnListener() {
            @Override
            public void onReturn() {
                if (cameraLisenter != null) {
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
        File file=new File(savePath);
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
                baseFolder = MyApplication.getContext().getExternalFilesDir(null).getAbsolutePath() + "/";
            }
        }
        return baseFolder;
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
        cv.onPause();
    }

    public void onDestroy() {
        cv.onDestroy();
    }

}
