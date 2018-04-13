package zhuozhuo.com.zhuo.contract;

import java.util.List;
import li.com.base.base.BaseModel;
import li.com.base.base.BasePresenter;
import li.com.base.base.BaseView;
import rx.Observable;
import com.hyphenate.chatuidemo.my.bean.VideoBean;


/**
 * Created by Administrator on 2018/3/9.
 */

public interface Zhuo3FragmentConstract {
    interface Model extends BaseModel {
        Observable<List<VideoBean>> getShareVideo(String index, String choice_id);
    }

    interface View extends BaseView {
        void returnVideoList(List<VideoBean> list);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getShareVideo(int index,String choice_id);
    }
}
