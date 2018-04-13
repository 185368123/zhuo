package zhuozhuo.com.zhuo.util;
import java.util.ArrayList;
import java.util.List;

import zhuozhuo.com.zhuo.bean.viewholdbean.Zhuo2ViewHold;

/**
 * Created by Administrator on 2017/12/7.
 */

public class Zhuo2ViewHoldUtil {
    /**
     * 存放 ViewHold的集合
     */
    private ArrayList<Zhuo2ViewHold> listViewHold = new ArrayList<>();

    private   static Zhuo2ViewHoldUtil viewHoldUtil;

    private Zhuo2ViewHoldUtil() {

    }

    public static Zhuo2ViewHoldUtil getZhuo2ViewHoldUtil() {
        if (viewHoldUtil == null) {
            synchronized (Zhuo2ViewHoldUtil.class) {
                if (viewHoldUtil == null) {
                    viewHoldUtil = new Zhuo2ViewHoldUtil();

                }
            }
        }
        return viewHoldUtil;
    }

    //集合里添加控件
    public void addViewHold(Zhuo2ViewHold viewHold) {
        this.listViewHold.add(viewHold);
    }

    //获得控件
    public Zhuo2ViewHold getViewHold(int i) {
        if (i < listViewHold.size()) {
            return listViewHold.get(i);
        } else {
            return null;
        }
    }

    //清空保存控件的集合
    public void clearViewHold() {
        listViewHold.clear();
    }

    public List<Zhuo2ViewHold> getViewHoldList(){
        return listViewHold;
    }
}
