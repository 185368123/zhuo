package li.com.base.base;

/**
 * Created by Administrator on 2018/2/27.
 */

public interface BaseView {
    /*******内嵌加载*******/
    void showLoading(String title);
    void stopLoading();
    void showErrorTip(String msg);
}
