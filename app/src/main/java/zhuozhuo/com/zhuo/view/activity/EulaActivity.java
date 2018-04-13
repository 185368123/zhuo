package zhuozhuo.com.zhuo.view.activity;

import android.graphics.Color;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import zhuozhuo.com.zhuo.R;
import com.hyphenate.chatuidemo.my.api.UrlConstant;
import zhuozhuo.com.zhuo.widget.TitleBarLayout;

public class EulaActivity extends BaseActivity {


    private TitleBarLayout titleBar_terms;
    private WebView webView;
    @Override
    public int getLayoutId() {
        return R.layout.activity_eula;
    }

    @Override
    public void initPresenter() {

    }
    @Override
    public void initView() {
        titleBar_terms = (TitleBarLayout) findViewById(R.id.titlebar_terms);
        webView = (WebView) findViewById(R.id.web_eula);
        webView.loadUrl(UrlConstant.Eula_URL);
        titleBar_terms.setLeftTitle("返回");
        titleBar_terms.setRightTextIsVisible(0);
        titleBar_terms.setTitle("使用条款");
        titleBar_terms.setLeftTextColor(Color.WHITE);
        titleBar_terms.setTitleTextColor(Color.WHITE);
        titleBar_terms.setLeftTextFocusable(true);
        titleBar_terms.setOnTitleBarClickListener(new TitleBarLayout.OnTitleBarClickListener() {
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
