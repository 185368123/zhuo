package com.hyphenate.chatuidemo.my;

import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.my.button.CameraListener;
import com.hyphenate.chatuidemo.my.videobeauty.BeautyCamerView;
import com.hyphenate.chatuidemo.ui.BaseActivity;



public class CameActivity extends BaseActivity {

    private BeautyCamerView camerView;

    MediaScannerConnection msc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_came);
        camerView = (BeautyCamerView) findViewById(R.id.bcv);

        camerView.setDuration(30 * 1000);
        camerView.setMinDuration(2 * 1000);

        camerView.setCameraLisenter(new CameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {

            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
                sendVideo(url);
            }

            @Override
            public void quit() {
                finish();
            }
        });
    }


    public void sendVideo(final String savePath) {
        if (TextUtils.isEmpty(savePath)) {
            return;
        }
        if (msc == null)
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
                            msc.scanFile(savePath, "video/*");
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
        camerView.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        camerView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        camerView.onDestroy();
    }
}
