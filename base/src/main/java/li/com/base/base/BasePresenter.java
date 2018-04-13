package li.com.base.base;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;

import li.com.base.baserx.RxManager;

/**
 * Created by Administrator on 2018/2/27.
 */

public abstract class BasePresenter<V,M> {
    public Context mContext;
    public M mModel;
    public V mView;
    public RxManager mRxManage = new RxManager();
    public void setVM(M model,V view){
        this.mModel=model;
        this.mView=view;
    }

    public void onDestroy() {
        mRxManage.clear();
    }
}
