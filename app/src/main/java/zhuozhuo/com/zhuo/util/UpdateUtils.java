package zhuozhuo.com.zhuo.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.tencent.tmassistantbase.common.download.TMAssistantDownloadTaskState;
import com.tencent.tmselfupdatesdk.ITMSelfUpdateListener;
import com.tencent.tmselfupdatesdk.TMSelfUpdateConst;
import com.tencent.tmselfupdatesdk.TMSelfUpdateManager;
import com.tencent.tmselfupdatesdk.YYBDownloadListener;
import com.tencent.tmselfupdatesdk.model.TMSelfUpdateUpdateInfo;

import li.com.base.baseapp.BaseApplication;
import li.com.base.baseuntils.LogUtils;

/**
 * Created by Administrator on 2018/5/8.
 */

public class UpdateUtils {
    /**
     * 应用宝渠道号，由产品对接分配
     */
    private static final String SELF_UPDATE_CHANNEL = "1003143";
    private  Handler  handler;
    private  OnCheckUpdateListener onCheckUpdateListener;
    private static UpdateUtils updateUtils;

    private UpdateUtils() {

    }

    public static UpdateUtils getInstance() {
        if (updateUtils == null) {
            synchronized (UpdateUtils.class) {
                if (updateUtils == null) {
                    updateUtils = new UpdateUtils();

                }
            }
        }
        return updateUtils;
    }

   public interface OnCheckUpdateListener{
        //检查更新失败
        public void onCheckUpdateFailure();
        //最新版本
        public void onNoUpdate();
        // //已安装应用宝，可以跳转到应用宝的指定页面；
        public void  already_installed();
        ////已安装应用宝，但是版本过低（不支持跳转）或者没有安装应用宝；
        public void  un_installed();
    }

    private ITMSelfUpdateListener mSelfUpdateListener = new ITMSelfUpdateListener() {
        @Override
        public void onDownloadAppStateChanged(int i, int i1, String s) {

        }

        @Override
        public void onDownloadAppProgressChanged(long l, long l1) {
            if (handler!=null){
                Message msg = handler.obtainMessage();
                msg.arg1 = (int) (l * 100 / l1);
                LogUtils.logd("下载完成度：" + l * 100 / l1);
                handler.sendMessage(msg);
            }
        }

        @Override
        public void onUpdateInfoReceived(TMSelfUpdateUpdateInfo tmSelfUpdateUpdateInfo) {
            LogUtils.logd(tmSelfUpdateUpdateInfo.toString());
            if (null != tmSelfUpdateUpdateInfo) {
                int state = tmSelfUpdateUpdateInfo.getStatus();
                if (state == TMSelfUpdateUpdateInfo.STATUS_CHECKUPDATE_FAILURE) {
                    LogUtils.logd("检查更新失败");
                    if (onCheckUpdateListener!=null){
                        onCheckUpdateListener.onCheckUpdateFailure();
                    }
                    return;
                }
                switch (tmSelfUpdateUpdateInfo.getUpdateMethod()) {
                    case TMSelfUpdateUpdateInfo.UpdateMethod_NoUpdate:
                        LogUtils.logd("最新版本");
                        if (onCheckUpdateListener!=null){
                            onCheckUpdateListener.onNoUpdate();
                        }
                        break;
                    default:
                        int state_ = TMSelfUpdateManager.getInstance().checkYYBInstallState();
                        if (state_ == TMAssistantDownloadTaskState.ALREADY_INSTALLED) {
                            //已安装应用宝，可以跳转到应用宝的指定页面；
                            if (onCheckUpdateListener!=null){
                                onCheckUpdateListener.already_installed();
                            }
                        } else if (state == TMAssistantDownloadTaskState.LOWWER_VERSION_INSTALLED||state == TMAssistantDownloadTaskState.UN_INSTALLED) {
                            //已安装应用宝，但是版本过低（不支持跳转），建议提示用户升级应用宝；
                            if (onCheckUpdateListener!=null){
                                onCheckUpdateListener.un_installed();
                            }
                        }
                        break;
                }
            }
        }
        //实现请参考下一小节实现自更新状态监听器部分的内容
    };//自更新状态监听器


    private YYBDownloadListener mDownloadYYBCallback = new YYBDownloadListener() {
        @Override
        public void onDownloadYYBStateChanged(String s, int i, int i1, String s1) {

        }

        @Override
        public void onDownloadYYBProgressChanged(String s, long l, long l1) {

        }

        @Override
        public void onCheckDownloadYYBState(String s, int i, long l, long l1) {

        }
        //实现请参考下一小节实现应用宝下载状态监听器部分的内容
    };//应用宝下载状态监听器


    public  void initUpdateSDK(){
        //附加参数的bundle，一般情况下传空，可以由外部传入场景信息等，具体字段可参考示例{@link TMSelfUpdateConst}中BUNDLE_KEY_*的定义
        Bundle bundle = new Bundle();
        bundle.putString(TMSelfUpdateConst.BUNDLE_KEY_SCENE, "FOO");
        TMSelfUpdateManager.getInstance().init(BaseApplication.baseApplication, SELF_UPDATE_CHANNEL, mSelfUpdateListener, mDownloadYYBCallback, null);
    }

    public void checkUpdate(OnCheckUpdateListener onCheckUpdateListener){
        this.onCheckUpdateListener=onCheckUpdateListener;
        TMSelfUpdateManager.getInstance().checkSelfUpdate();
    }

    public void startUpdate(Boolean updateWay,Handler handler){
        this.handler=handler;
        TMSelfUpdateManager.getInstance().startSelfUpdate(updateWay);
    }
}
