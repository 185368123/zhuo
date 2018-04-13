package zhuozhuo.com.zhuo.view.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import com.hyphenate.chatuidemo.my.okhttp.ToastUtils;
import java.util.ArrayList;
import me.iwf.photopicker.PhotoPicker;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.myedittext.RichTextEditor;
import zhuozhuo.com.zhuo.myedittext.SDCardUtil;
import zhuozhuo.com.zhuo.presenter.WriteArticlePresent;
import zhuozhuo.com.zhuo.util.ImageUtils;
import com.hyphenate.chatuidemo.my.EmptyView;

public class WriteArticleActivity extends BaseActivity implements EmptyView {

    private WriteArticlePresent writeArticlePresent;
    private EditText edit_title;
    private RichTextEditor edit_detail;
    private ProgressDialog loadingDialog;
    private ProgressDialog insertDialog;
    private Subscription subsInsert;

    @Override
    public int getLayoutId() {
        return R.layout.activity_write_article;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initTitleBar();
        writeArticlePresent = new WriteArticlePresent(this);
        edit_title = (EditText) findViewById(R.id.edit_title);
        edit_detail = (RichTextEditor) findViewById(R.id.edit_detail);

        insertDialog = new ProgressDialog(this);
        insertDialog.setMessage("正在插入图片...");
        insertDialog.setCanceledOnTouchOutside(false);

        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage("图片解析中...");
        loadingDialog.setCanceledOnTouchOutside(false);
    }

    protected void initTitleBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_new);
        toolbar.setTitle("新帖");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toolbar.setNavigationIcon(R.drawable.ic_dialog_info);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 异步方式插入图片
     * @param data
     */
    private void insertImagesSync(final Intent data){
        insertDialog.show();

        subsInsert = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try{
                    edit_detail.measure(0, 0);
                    int width = getScreenWidth(WriteArticleActivity.this);
                    int height = getScreenHeight(WriteArticleActivity.this);
                    ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    //可以同时插入多张图片
                    for (String imagePath : photos) {
                        Bitmap bitmap = ImageUtils.getSmallBitmap(imagePath, width, height);//压缩图片
                        imagePath = SDCardUtil.saveToSdCard(bitmap);
                        subscriber.onNext(imagePath);
                    }
                    subscriber.onCompleted();
                }catch (Exception e){
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())//生产事件在io
                .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        insertDialog.dismiss();
                        edit_detail.addEditTextAtIndex(edit_detail.getLastIndex(), " ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        insertDialog.dismiss();
                        ToastUtils.showToast("图片插入失败:"+e.getMessage());
                    }

                    @Override
                    public void onNext(String imagePath) {
                        edit_detail.insertImage(imagePath, edit_detail.getMeasuredWidth());
                    }
                });
    }

    /**
     * 调用图库选择
     */
    private void callGallery(){
//        //调用系统图库
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");// 相片类型
//        startActivityForResult(intent, 1);

        //调用第三方图库选择
        PhotoPicker.builder()
                .setPhotoCount(5)//可选择图片数量
                .setShowCamera(true)//是否显示拍照按钮
                .setShowGif(true)//是否显示动态图
                .setPreviewEnabled(true)//是否可以预览
                .start(this, PhotoPicker.REQUEST_CODE);
    }


    /**
     * 获得屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == 1){
                    //处理调用系统图库
                } else if (requestCode == PhotoPicker.REQUEST_CODE){
                    //异步方式插入图片
                    insertImagesSync(data);
                }
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_write, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_insert_image:
                callGallery();
                break;
            case R.id.action_new_save:
                writeArticlePresent.write(edit_title, edit_detail);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void emptyBack() {
        finish();
    }


}
