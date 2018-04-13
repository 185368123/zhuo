package com.cjt2325.cameralibrary;

import android.graphics.Canvas;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/4/12.
 */

public class MyInvocation implements InvocationHandler {
    Object mObject ;

    public MyInvocation(Object object) {
        mObject = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 截取lockCanvas方法调用
        if ("lockCanvas".equals(method.getName())) {
            // lockCanvas方法返回值是canvas画布
            Canvas canvas = (Canvas) method.invoke(mObject,args);
            // 添加镜像
            canvas.scale(-1,1,canvas.getWidth()/2,canvas.getHeight()/2);

            return canvas;
        }
        return method.invoke(mObject,args);
    }
}
