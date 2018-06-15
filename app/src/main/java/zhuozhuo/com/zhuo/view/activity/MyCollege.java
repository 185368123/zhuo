package zhuozhuo.com.zhuo.view.activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chatuidemo.my.bean.FriendsCollegeBean;
import com.hyphenate.easeui.provider.UserInfoProvider;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.util.Random;

import li.com.base.basesinglebean.SingleBeans;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.contract.MyCollegeConstract;
import zhuozhuo.com.zhuo.model.MyCollegeModel;
import zhuozhuo.com.zhuo.presenter.MyCollegePresent;
import zhuozhuo.com.zhuo.widget.MyFlowLayout;

public class MyCollege extends BaseActivity<MyCollegeModel, MyCollegePresent> implements MyCollegeConstract.View {

    private EaseTitleBar mytitlebar;
    private MyFlowLayout flowLayout;
    private TextView tv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_college;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(mModel, this);
    }

    @Override
    public void initView() {
        initTitleBar();
        mPresenter.getCollege(SingleBeans.getInstance().getFriensToString());
        flowLayout = findViewById(R.id.flow_layout);
        tv = findViewById(R.id.tv_coloege_num);
    }

    protected void initTitleBar() {
        mytitlebar = (EaseTitleBar) findViewById(R.id.college_titlebar);
        mytitlebar.setTitle("好友学院分布");
        mytitlebar.setLeftImageResource(R.drawable.ease_mm_title_back);
        mytitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
    public void returnCollege(FriendsCollegeBean friendsCollegeBean) {
        int num = 0;
        Random random = new Random();
        if (UserInfoProvider.getLocation().split(",")[0].equals("深大")) {
            for (int i = 0; i < friendsCollegeBean.getShenda().size(); i++) {
                TextView textView = new TextView(this);
                //textView.setBackgroundColor(ContextCompat.getColor(this, R.color.main_color4));
                textView.setTextColor(Color.WHITE);
                textView.setPadding(5,5,5,5);
                textView.setGravity(Gravity.CENTER);
                textView.setText(friendsCollegeBean.getShenda().get(i).getCollege() + " " + friendsCollegeBean.getShenda().get(i).getNum());
                textView.setTextSize(14);
                // 设置彩色背景
                GradientDrawable normalDrawable = new GradientDrawable();
                normalDrawable.setShape(GradientDrawable.RECTANGLE);
                int a = 255;
                int r = 150 + random.nextInt(100);
                int g = 150 + random.nextInt(100);
                int b = 150 + random.nextInt(100);
                normalDrawable.setColor(Color.argb(a, r, g, b));

                // 设置按下的灰色背景
                GradientDrawable pressedDrawable = new GradientDrawable();
                pressedDrawable.setShape(GradientDrawable.RECTANGLE);
                pressedDrawable.setColor(Color.GRAY);

                // 背景选择器
                StateListDrawable stateDrawable = new StateListDrawable();
                stateDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
                stateDrawable.addState(new int[]{}, normalDrawable);

                textView.setBackground(stateDrawable);
                flowLayout.addView(textView);
            }
            for (int i = 0; i <friendsCollegeBean.getShenzhi().size() ; i++) {
                num+=friendsCollegeBean.getShenzhi().get(i).getNum();
            }
        } else if (UserInfoProvider.getLocation().split(",")[0].equals("深职")) {
            for (int i = 0; i < friendsCollegeBean.getShenzhi().size(); i++) {
                TextView textView = new TextView(this);
                //textView.setBackgroundColor(ContextCompat.getColor(this, R.color.main_color4));
                textView.setTextColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(5,5,5,5);
                textView.setText(friendsCollegeBean.getShenzhi().get(i).getCollege() + " " + friendsCollegeBean.getShenzhi().get(i).getNum());
                textView.setTextSize(14);
                // 设置彩色背景
                GradientDrawable normalDrawable = new GradientDrawable();
                normalDrawable.setShape(GradientDrawable.RECTANGLE);
                int a = 255;
                int r = 100 + random.nextInt(155);
                int g = 100 + random.nextInt(155);
                int b = 100 + random.nextInt(155);
                normalDrawable.setColor(Color.argb(a, r, g, b));

                // 设置按下的灰色背景
                GradientDrawable pressedDrawable = new GradientDrawable();
                pressedDrawable.setShape(GradientDrawable.RECTANGLE);
                pressedDrawable.setColor(Color.GRAY);

                // 背景选择器
                StateListDrawable stateDrawable = new StateListDrawable();
                stateDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
                stateDrawable.addState(new int[]{}, normalDrawable);

                textView.setBackground(stateDrawable);
                flowLayout.addView(textView);
            }
            for (int i = 0; i <friendsCollegeBean.getShenda().size() ; i++) {
                num+=friendsCollegeBean.getShenda().get(i).getNum();
            }
        }
        tv.setText("其他学校"+num+"人");
    }
}
