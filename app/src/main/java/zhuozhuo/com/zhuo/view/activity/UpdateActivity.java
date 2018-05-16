package zhuozhuo.com.zhuo.view.activity;

import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.util.UpdateUtils;

public class UpdateActivity extends BaseActivity {

    private ProgressBar pb;
    private ImageView iv;
    private TextView tv;
    private boolean isBack = false;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isBack=false;
            pb.setProgress(msg.arg1);
            tv.setText("已下载" + msg.arg1 + "%");
            if (msg.arg1==100){
                isBack=true;
            }
        }
    };

    @Override
    public void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_update;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        pb = (ProgressBar)findViewById(R.id.pb_update);
        iv = (ImageView) findViewById(R.id.iv_update_background);
        tv = (TextView) findViewById(R.id.tv_update);
        Glide.with(this).load(R.drawable.welcome2).into(iv);
        UpdateUtils.getInstance().startUpdate(false,handler);
    }

    @Override
    public void onBackPressed() {
        if (isBack){
            super.onBackPressed();
        }
    }
}
