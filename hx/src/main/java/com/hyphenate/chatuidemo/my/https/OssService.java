package com.hyphenate.chatuidemo.my.https;

import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.io.File;
import java.util.HashMap;

/**
 */
public class OssService {

    private OSS oss;
    private String bucket;

    private String callbackAddress;
    //根据实际需求改变分片大小
    private final static int partSize = 256 * 1024;


    public OssService(OSS oss, String bucket) {
        this.oss = oss;
        this.bucket = bucket;

    }

    public void asyncPutImage(String object,
                              String localFile,
                              @NonNull final OSSCompletedCallback<PutObjectRequest, PutObjectResult> userCallback) {
        if (object.equals("")) {
            Log.w("AsyncPutImage", "ObjectNull");
            return;
        }

        File file = new File(localFile);
        if (!file.exists()) {
            Log.w("AsyncPutImage", "FileNotExist");
            Log.w("LocalFile", localFile);
            return;
        }


        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucket, object, localFile);

        if (callbackAddress != null) {
            // 传入对应的上传回调参数，这里默认使用OSS提供的公共测试回调服务器地址
            put.setCallbackParam(new HashMap<String, String>() {
                {
                    put("callbackUrl", callbackAddress);
                    //callbackBody可以自定义传入的信息
                    put("callbackBody", "filename=${object}");
                }
            });
        }

        OSSAsyncTask task = oss.asyncPutObject(put, userCallback);
    }

}
