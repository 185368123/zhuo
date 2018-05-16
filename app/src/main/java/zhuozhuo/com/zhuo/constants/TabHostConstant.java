package zhuozhuo.com.zhuo.constants;


import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.view.fragment.MineFragment;
import zhuozhuo.com.zhuo.view.fragment.Zhuo1Fragment;
import zhuozhuo.com.zhuo.view.fragment.Zhuo2Fragment;
import zhuozhuo.com.zhuo.view.fragment.Zhuo3Fragment;

/**
 * Created by Administrator on 2017/10/16.
 */

public class TabHostConstant {
    public static final String[] tabText = {
            "啄",
            //"啄啄",
            "啄啄啄",
            "我"
    };
    //底部图片选择器的id数组
    public static final int[] tabImgIds = {
            R.drawable.tab_z_selector,
            //R.drawable.tab_zz_selector,
            R.drawable.tab_zzz_selector,
            R.drawable.tab_mine_selector
    };

    //标签对应的Fragment的Class
    public static final Class[] fragments = {
            Zhuo1Fragment.class,
            //Zhuo2Fragment.class,
            Zhuo3Fragment.class,
            MineFragment.class
    };
}
