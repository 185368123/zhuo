package zhuozhuo.com.zhuo.view.activity;

import android.view.View;
import android.webkit.WebView;
import zhuozhuo.com.zhuo.R;
import com.hyphenate.chatuidemo.my.api.UrlConstant;

public class TermsActivity extends BaseActivity implements View.OnClickListener {
    private WebView webView;
    @Override
    public int getLayoutId() {
        return R.layout.activity_terms;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        webView = (WebView) findViewById(R.id.web_terms);
        webView.loadUrl(UrlConstant.Terms_URL);
        findViewById(R.id.button_yes).setOnClickListener(this);
        findViewById(R.id.button_no).setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_yes:
                startActivity(RegisterActivity.class);
                finish();
            break;
            case R.id.button_no:
                finish();
            break;
        }
    }


}
