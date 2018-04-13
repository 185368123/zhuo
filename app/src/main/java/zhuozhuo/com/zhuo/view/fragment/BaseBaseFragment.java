package zhuozhuo.com.zhuo.view.fragment;

import android.support.v4.app.Fragment;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2018/3/6.
 */

public class BaseBaseFragment extends Fragment {
    public void onResume()
    {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName()); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause()
    {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }
}
