package retrofit2.adapter.http.http;

import android.util.Log;

import androidx.annotation.NonNull;

//import com.trello.rxlifecycle4.LifecycleProvider;
//import com.trello.rxlifecycle4.LifecycleTransformer;
//import com.trello.rxlifecycle4.android.ActivityEvent;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * Description: <RxJavaAdapter适配器><br>
 * Author:      gxl<br>
 * Date:        2019/3/18<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class RxJavaAdapter {
    /**
     * 生命周期绑定
     *
     * @param lifecycle Activity
     */
//    public static <T> LifecycleTransformer<T> bindUntilEvent(@NonNull LifecycleProvider lifecycle) {
//        if (lifecycle != null) {
//            return lifecycle.bindUntilEvent(ActivityEvent.DESTROY);
//        } else {
//            throw new IllegalArgumentException("context not the LifecycleProvider type");
//        }
//    }

    /**
     * 线程调度器
     */
    public static ObservableTransformer schedulersTransformer() {
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static ObservableTransformer exceptionTransformer() {

        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable observable) {
                return observable
                        .map(new AppErrorHandler())  //这里可以取出BaseResponse中的Result
                        .onErrorResumeNext(new HttpErrorHandler());
            }
        };
    }

    public static class HttpErrorHandler<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable t) {
            Log.v("MYTAG","HTTP异常处理");
            ResponseException exception = ExceptionHandler.handleException(t);
            Log.v("MYTAG",exception.toString());
            if(exception.code ==  ExceptionHandler.SYSTEM_ERROR.NETWORD_ERROR ){
                Log.v("MYTAG","网络不给力哦！");
            }else if(exception.code == ExceptionHandler.SYSTEM_ERROR.UNAUTHORIZED){
                //Toast.makeText(RetrofitManager.getInstance().mContext,"您的登录已失效,请重新登录", Toast.LENGTH_SHORT).show();
                //ARouterUtil.login(RetrofitManager.getInstance().mContext);
                Log.v("MYTAG","您的登录已失效,请重新登录");
            }
            return Observable.error(exception);
        }
    }

    public static class AppErrorHandler implements Function<Object, Object> {

        @Override
        public Object apply(Object o) throws Exception {
            Log.v("MYTAG","应用通用处理");
           /* if(o instanceof BaseResponseHeader){
                BaseResponseHeader baseHeader = (BaseResponseHeader) o;
                if(baseHeader.getNtspheader().errcode != ExceptionHandler.APP_ERROR.SUCC){
                    Toast.makeText(RetrofitManager.getInstance().mContext,baseHeader.getNtspheader().errmsg, Toast.LENGTH_SHORT).show();
                }
            }*/
            return o;
        }
    }

}
