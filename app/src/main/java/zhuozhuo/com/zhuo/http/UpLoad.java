package zhuozhuo.com.zhuo.http;

import android.support.annotation.NonNull;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import zhuozhuo.com.zhuo.MainApplication;
import zhuozhuo.com.zhuo.service.OssService;

/**
 * Created by Administrator on 2017/11/1.
 */

public class UpLoad {

    private String AccessKey = "LTAIDdur3GqRpTKT";
    private String SecretKey = "Ro9ZEExYkLZaPXk0sWRS486iAKBbeN";
    private String bucket = "zhuozhuo";
    private static final String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
    private static UpLoad upLoad;
    private OssService ossService;

    private UpLoad() {
        ossService = initOSS(endpoint, bucket);
    }

    public void beginUpLoad(String object,
                            String localFile,
                            @NonNull final OSSCompletedCallback<PutObjectRequest, PutObjectResult> userCallback){
        ossService.asyncPutImage(object, localFile, userCallback);
    }

    public static UpLoad getInstance() {
        if (upLoad == null) {
            synchronized (UpLoad.class) {
                if (upLoad == null) {
                    upLoad = new UpLoad();
                }
            }
        }
        return upLoad;
    }

    //初始化一个OssService用来上传下载
    private OssService initOSS(String endpoint, String bucket) {

        //如果希望直接使用accessKey来访问的时候，可以直接使用OSSPlainTextAKSKCredentialProvider来鉴权。
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(AccessKey, SecretKey);



        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次

        OSS oss = new OSSClient(MainApplication.getInstance().getApplicationContext(), endpoint, credentialProvider, conf);

        return new OssService(oss, bucket);

    }

}
