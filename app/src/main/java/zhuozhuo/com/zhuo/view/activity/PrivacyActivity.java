package zhuozhuo.com.zhuo.view.activity;

import android.graphics.Color;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import zhuozhuo.com.zhuo.R;
import com.hyphenate.chatuidemo.my.api.UrlConstant;
import zhuozhuo.com.zhuo.widget.TitleBarLayout;

public class PrivacyActivity extends BaseActivity {


    private TitleBarLayout titleBar_privacy;
    private WebView webView;
    @Override
    public int getLayoutId() {
        return R.layout.activity_privacy;
    }

    @Override
    public void initPresenter() {

    }
    @Override
    public void initView() {
        titleBar_privacy = (TitleBarLayout) findViewById(R.id.titlebar_privacy);
        webView = (WebView) findViewById(R.id.web_privacy);
        webView.loadUrl(UrlConstant.Privacy_URL);
        titleBar_privacy.setLeftTitle("返回");
        titleBar_privacy.setLeftTextIsVisible(0);
        titleBar_privacy.setRightTextIsVisible(0);
        titleBar_privacy.setTitle("隐私政策");
        titleBar_privacy.setLeftTextColor(Color.WHITE);
        titleBar_privacy.setTitleTextColor(Color.WHITE);

        titleBar_privacy.setLeftTextFocusable(true);
        titleBar_privacy.setOnTitleBarClickListener(new TitleBarLayout.OnTitleBarClickListener() {
            @Override
            public void onLeftLinearClick(LinearLayout linearLeft, ImageView imageLeft) {

            }

            @Override
            public void onRightLinearClick(LinearLayout linearRight, ImageView imageRight) {

            }

            @Override
            public void onLeftTextClick(TextView tvLeft) {
                finish();
            }

            @Override
            public void onRightTextClick(TextView tvRight) {

            }
        });

    }


}
