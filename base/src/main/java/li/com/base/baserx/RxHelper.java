package li.com.base.baserx;

import li.com.base.baseuntils.LogUtils;
import li.com.base.baseuntils.ToastUitl;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * des:对服务器返回数据成功和失败处理
 */

/**************使用例子******************/
/*_apiService.login(mobile, verifyCode)
        .compose(RxSchedulersHelper.io_main())
        .compose(RxResultHelper.handleResult())
        .//省略*/

public class RxHelper {
    /**
     * 对服务器返回数据进行预处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<BaseRespose<T>, T> handleResult() {
        return new Observable.Transformer<BaseRespose<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseRespose<T>> tObservable) {
                return tObservable.flatMap(new Func1<BaseRespose<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(BaseRespose<T> result) {
                        LogUtils.logd("result from api : " + result);
                        if (result.success()) {//请求数据成功
                            return createData(result.data);
                        }else if (result.recall()){//请求数据失败，需要再次请求
                            LogUtils.logd("请求数据失败，需要再次请求");
                            return Observable.error(new ServerException(result.msg));
                        }else if (result.remind()){//请求数据失败，提示错误信息
                            LogUtils.logd("请求数据失败，提示错误信息");
                            ToastUitl.showLong(result.msg);
                            return Observable.error(new ServerException(result.msg));
                        }else if (result.relogin()){//Token失效，从新登陆
                            LogUtils.logd("Token失效，从新登陆");
                            new RxManager().post("restart","");
                            return Observable.error(new ServerException(result.msg));
                        } else {
                            return Observable.error(new ServerException(result.msg));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };

    }

    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

    }
}
