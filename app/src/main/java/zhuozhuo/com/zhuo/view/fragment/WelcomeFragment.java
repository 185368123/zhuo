package zhuozhuo.com.zhuo.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import li.com.base.base.BaseFragment;
import zhuozhuo.com.zhuo.R;


/**
 * Created by Administrator on 2017/1/16.
 */
public class WelcomeFragment extends BaseFragment {

    @Override
    protected int getLayoutResource() {
        return R.layout.slpash_fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView(View view) {
        //得到传入的参数
        Bundle bundle = getArguments();
        int index = bundle.getInt("index", 0);

        //根据参数值，设置对应的图片
        ImageView iv1 = (ImageView) view.findViewById(R.id.iv1);
        switch (index) {
            case 0: {
                Glide.with(this).load(R.drawable.welcome_1).into(iv1);
            }
            break;
            case 1: {
                Glide.with(this).load(bundle.getString("image")).error(R.drawable.welcome).into(iv1);
            }
            break;
        }
    }
}
