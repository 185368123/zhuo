package com.hyphenate.chatuidemo.my;

import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.ui.BaseActivity;


import java.io.File;

public class CameActivity extends BaseActivity {
    private JCameraView jCameraView;
    private boolean granted = false;
    MediaScannerConnection msc = null;
    String localPath = "";
    boolean backflag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_came);
        jCameraView = (JCameraView) findViewById(R.id.jcameraview);
        String flag=getIntent().getStringExtra("flag");
        if (flag.equals("true")){
            jCameraView.setReturnVisibility(View.GONE);
            backflag=true;
        }else {
            jCameraView.setReturnVisibility(View.VISIBLE);
            backflag=false;
        }
        //设置视频保存路径
        jCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "JCamera");

        //JCameraView监听
        jCameraView.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                //获取图片bitmap
                Log.i("JCameraView", "captureSuccess = " + bitmap.getWidth());
            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
                //获取图片bitmap
                Log.i("JCameraView", "recordSuccess = " + url);
               localPath =  url;
              sendVideo(null);
            }
            public void quit() {
                //退出按钮
               onBackPressed();
            }
        });
    }


    public void sendVideo(View view) {
        if (TextUtils.isEmpty(localPath)) {
            return;
        }
        if(msc == null)
            msc = new MediaScannerConnection(this,
                    new MediaScannerConnection.MediaScannerConnectionClient() {

                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            msc.disconnect();
                            setResult(RESULT_OK, getIntent().putExtra("uri", uri));
                            finish();
                        }

                        @Override
                        public void onMediaScannerConnected() {
                            msc.scanFile(localPath, "video/*");
                        }
                    });
        msc.connect();

    }

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
            jCameraView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        jCameraView.onPause();
    }

    @Override
    public void onBackPressed() {
        if (backflag){

        }else {
            setResult(RESULT_CANCELED, getIntent().putExtra("uri",""));
            finish();
        }
    }
}
