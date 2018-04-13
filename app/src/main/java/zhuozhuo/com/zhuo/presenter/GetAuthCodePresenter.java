package zhuozhuo.com.zhuo.presenter;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
import java.util.Objects;

import li.com.base.baserx.RxSubscriber;
import zhuozhuo.com.zhuo.contract.AnthCodeConstract;
import zhuozhuo.com.zhuo.util.CountDownUtils;
import zhuozhuo.com.zhuo.util.ImportCheckUtils;
import zhuozhuo.com.zhuo.util.MainUtils;

/**
 * 验证码 业务逻辑
 */
public class GetAuthCodePresenter extends AnthCodeConstract.Presenter {

    /**
     * 倒计时
     */
    private CountDownUtils countDownUtils;


    /**
     * 停止倒计时
     */
    private void stopDownTimer(Button btGteVerifyCode) {
        if (countDownUtils != null) {
            countDownUtils.stopAuthCodeCountDown(btGteVerifyCode);
        }
    }


    @Override
    public void getAnthCode(EditText etPhoneNumber, final Button btGteVerifyCode, String opt) {
        // 接收短信的手机号码
        String phoneNumber = MainUtils.getText(etPhoneNumber);
        // 判断 手机号码 是否合法
        if (!ImportCheckUtils.checkPhoneNumber(etPhoneNumber)) {
            return;
        }
        mModel.getAnthCode(phoneNumber,opt).subscribe(new RxSubscriber<List<Object>>(mContext,false) {
            @Override
            protected void _onNext(List<Object> object) {
                // 启动计时器
                countDownUtils = CountDownUtils.startAuthCodeCountDown(btGteVerifyCode);
            }

            @Override
            protected void _onError(String message) {
                // 停止倒计时
                stopDownTimer(btGteVerifyCode);
            }
        });
    }
}
