package zhuozhuo.com.zhuo.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.EditText;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.hyphenate.chatuidemo.provider.UserInfoProvider;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;

import li.com.base.baseuntils.LogUtils;
import zhuozhuo.com.zhuo.MainApplication;
import zhuozhuo.com.zhuo.http.UpLoad;
import zhuozhuo.com.zhuo.model.WriteArticleModel;
import zhuozhuo.com.zhuo.myedittext.RichTextEditor;
import zhuozhuo.com.zhuo.myedittext.SDCardUtil;
import zhuozhuo.com.zhuo.service.UICallback;
import zhuozhuo.com.zhuo.util.ImageUtils;
import zhuozhuo.com.zhuo.util.MainUtils;
import zhuozhuo.com.zhuo.util.ToastUtils;
import com.hyphenate.chatuidemo.my.EmptyView;

/**
 * Created by Administrator on 2017/11/20.
 */

public class WriteArticlePresent extends BaseImportPresenter {

    private static final String TAG = WriteArticlePresent.class.getSimpleName() + " --> ";
    private static final String p="&nbsp;";

    private  WriteArticleModel writeArticleModel;
    private EmptyView emptyView;
    private String objectName;

    public WriteArticlePresent(EmptyView emptyView) {
        writeArticleModel = new WriteArticleModel(this);
        this.emptyView=emptyView;
    }

    public void write(EditText title, RichTextEditor content) {
        String str_title = MainUtils.getText(title);
        String str_content = getEditData(content);

        if (TextUtils.isEmpty(str_title)) {
            // 标题 为空
            ToastUtils.showToast("标题不能为空");
            return;
        }

        LogUtils.logd(TAG + "标题：" + str_title + "内容" + str_content);
        writeArticleModel.write(str_title,str_content );
    }

    /**
     * 负责处理编辑数据提交等事宜，请自行实现
     */
    private String getEditData(RichTextEditor et_new_content) {
        List<RichTextEditor.EditData> editList = et_new_content.buildEditData();
        StringBuffer content = new StringBuffer();
        for (RichTextEditor.EditData itemData : editList) {
            if (itemData.inputStr != null) {
                content.append(tohtml(itemData.inputStr));
                //Log.d("RichEditor", "commit inputStr=" + itemData.inputStr);
            } else if (itemData.imagePath != null) {
                objectName = "bbs/" + UserInfoProvider.getUserID() + System.currentTimeMillis() + ".jpg";
                try {
                    updata(itemData.imagePath,objectName);
                } catch (ClientException e) {
                    e.printStackTrace();
                } catch (ServiceException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                content.append("<img src=\"").append("http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/" + objectName).append("\">");
                //Log.d("RichEditor", "commit imgePath=" + itemData.imagePath);
                //imageList.add(itemData.imagePath);
            }
        }
        return content.toString();
    }

    public String tohtml(String str) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<p>");
        buffer.append(str.replaceAll("[\\t\\n\\r]", "</p><p>"));//.replaceAll(" ",p)
        buffer.append("</p>");
        return buffer.toString();
    }


    private void updata(String url,String name) throws ClientException, ServiceException, IOException {
        /**
         * 上传服务器代码
         */
        int width = getScreenWidth(MainApplication.getInstance().getBaseContext());
        int height = getScreenHeight(MainApplication.getInstance().getBaseContext());
        Bitmap bitmap = ImageUtils.getSmallBitmap(url, width, height);//压缩图片
        String imagePath = SDCardUtil.saveToSdCard(bitmap);
        UpLoad.getInstance().beginUpLoad(objectName, imagePath, getPutCallback());
    }
    public UICallback<PutObjectRequest, PutObjectResult> getPutCallback() {
        return new UICallback<PutObjectRequest, PutObjectResult>(null) {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                LogUtils.logd(TAG + "图片上传成功" );
                super.onSuccess(request, result);

            }
            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                onFailure(request, clientExcepion, serviceException);
                LogUtils.logd(TAG + "图片上传失败" );
            }
        };
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
    public void onVerifySucceed(String strResponse, JSONObject jsonResponse, String errMsg, int id) {
        ToastUtils.showToast("发表成功");
        emptyView.emptyBack();
    }
}
