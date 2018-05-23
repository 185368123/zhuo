package li.com.videobeauty;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import li.com.videobeauty.button.CameraListener;

public class MainActivity extends AppCompatActivity {


    private BeautyCamerView camerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
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

    private void initView() {
        camerView = findViewById(R.id.bcv);
        camerView.setDuration(30*1000);
        camerView.setMinDuration(2*1000);

        camerView.setCameraLisenter(new CameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {

            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
               recordComplete(url);
               finish();
            }

            @Override
            public void quit() {
                   finish();
            }
        });

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

    private void recordComplete(final String path) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("文件保存路径：",path);
            }
        });
    }


}
