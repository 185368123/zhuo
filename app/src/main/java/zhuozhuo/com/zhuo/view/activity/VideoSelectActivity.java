package zhuozhuo.com.zhuo.view.activity;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.View;
import android.widget.GridView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.hyphenate.chatuidemo.my.https.UICallback;
import com.hyphenate.chatuidemo.my.https.UIDispatcher;
import com.hyphenate.chatuidemo.my.https.UpLoad;
import com.hyphenate.easeui.provider.UserInfoProvider;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.io.IOException;

import li.com.base.baseuntils.ToastUitl;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.adapter.VideoSelectAdapter;
import zhuozhuo.com.zhuo.contract.ChangeMsgConstract;
import zhuozhuo.com.zhuo.model.ChangeMsgModel;
import zhuozhuo.com.zhuo.presenter.ChangeMsgPresenter;

public class VideoSelectActivity extends BaseActivity <ChangeMsgModel, ChangeMsgPresenter> implements ChangeMsgConstract.View, LoaderManager.LoaderCallbacks<Cursor>,VideoSelectAdapter.OnVideoSelectListener {

    private EaseTitleBar titlebar;
    private GridView gridview;
    private VideoSelectAdapter mVideoAdapter;
    public static final String PROJECT_VIDEO = MediaStore.MediaColumns._ID;

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_select;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(mModel,this);
    }

    @Override
    public void initView() {
     getLoaderManager().initLoader(0,null,this);

        titlebar = (EaseTitleBar) findViewById(R.id.titlebar_video_select);
        titlebar.setTitle("选择视频(4-17s)");
        titlebar.setLeftImageResource(R.drawable.ease_mm_title_back);
        titlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        gridview=(GridView)findViewById(R.id.gridview_media_video);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String order = MediaStore.MediaColumns.DATE_ADDED + " DESC";
        return new CursorLoader(getApplicationContext(), videoUri, new String[]{MediaStore.Video.Media.DATA, PROJECT_VIDEO}, null, null, order);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null || data.getCount() <= 0) {
            return;
        }
        if (mVideoAdapter == null) {
            mVideoAdapter = new VideoSelectAdapter(getApplicationContext(), data);
            mVideoAdapter.setMediaSelectVideoActivity(this);
            mVideoAdapter.setOnSelectChangedListener(this);
        } else {
            mVideoAdapter.swapCursor(data);
        }


        if (gridview.getAdapter() == null) {
            gridview.setAdapter(mVideoAdapter);
        }
        mVideoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (mVideoAdapter != null)
            mVideoAdapter.swapCursor(null);
    }
    @Override
    protected void onDestroy() {
        getLoaderManager().destroyLoader(0);
        Glide.get(this).clearMemory();
        super.onDestroy();
    }
    @Override
    public void onSelect(String path, String cover) {
        try {
            updata(path);
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showLoading("视频上传中......");
    }

    String objectName;

    private UIDispatcher UIDispatcher = new UIDispatcher(Looper.getMainLooper());
    ;

    private void updata(String url) throws ClientException, ServiceException, IOException {
        /**
         * 上传服务器代码
         */

        objectName = "Single/" + UserInfoProvider.getNickName() + System.currentTimeMillis() + ".mp4";
        UpLoad.getInstance().beginUpLoad(objectName, url, getPutCallback());
    }

    //图片上传阿里服务器成功时回调
    public UICallback<PutObjectRequest, PutObjectResult> getPutCallback() {
        return new UICallback<PutObjectRequest, PutObjectResult>(UIDispatcher) {
            @Override
            public void onSuccess(final PutObjectRequest request, final PutObjectResult result) {
                addCallback(new Runnable() {
                    @Override
                    public void run() {
                        String url = "http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/" + objectName;
                        mPresenter.changeMsg("user_video", url);
                    }
                }, null);
                super.onSuccess(request, result);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                stopLoading();
                String info = "";
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    info = clientExcepion.toString();
                }
                if (serviceException != null) {
                    info = serviceException.toString();
                }
                addCallback(null, new Runnable() {
                    @Override
                    public void run() {
                    }
                });
                onFailure(request, clientExcepion, serviceException);
            }
        };
    }

    @Override
    public void showLoading(String title) {
        startProgressDialog(title);
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @Override
    public void showErrorTip(String msg) {

    }


    @Override
    public void changeMsgSucess() {
        stopLoading();
        UserInfoProvider.setUserVideo("http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/" + objectName);
        ToastUitl.showLong("修改成功");
        finish();
    }
}
