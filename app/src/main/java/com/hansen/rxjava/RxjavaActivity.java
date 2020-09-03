package com.hansen.rxjava;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.hansen.customview.R;
import com.hansen.customview.bean.Translation;
import com.hansen.customview.bean.Translation2;
import com.hansen.rxjava.ui.login.LoginActivity;
import com.hansen.utils.LogUtil;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RxjavaActivity extends AppCompatActivity {
    private final String TAG = "RxjavaActivityTAG";
    private final String baseUrl = "http://fy.iciba.com";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

        textView = findViewById(R.id.tv_name);


    }

    public void noReson(View view) {
        //无条件网络请求
        Observable.interval(2, 1, TimeUnit.SECONDS)
                // 参数说明：
                // 参数1 = 第1次延迟时间；
                // 参数2 = 间隔时间数字；
                // 参数3 = 时间单位；
                // 该例子发送的事件特点：延迟2s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）

                /*
                 * 步骤2：每次发送数字前发送1次网络请求（doOnNext（）在执行Next事件前调用）
                 * 即每隔1秒产生1个数字前，就发送1次网络请求，从而实现轮询需求
                 **/
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LogUtils.d(TAG, "轮询次数" + aLong);
                        /*
                         * 步骤3：通过Retrofit发送网络请求
                         **/
                        // a. 创建Retrofit对象
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(baseUrl)
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .build();
                        // b. 创建 网络请求接口 的实例
                        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
                        // c. 采用Observable<...>形式 对 网络请求 进行封装
                        Observable<Translation> observable = request.getCall();
                        // d. 通过线程切换发送网络请求
                        observable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Translation>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        LogUtils.d(TAG, "onSubscribe");
                                    }

                                    @Override
                                    public void onNext(Translation translation) {
                                        translation.show();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        LogUtils.d(TAG, "onError"+e.getMessage());
                                    }

                                    @Override
                                    public void onComplete() {
                                        LogUtils.d(TAG, "onComplete");
                                    }
                                });
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        LogUtils.d(TAG, "onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(TAG, "onError");
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG, "onComplete");
                    }
                });
    }

    //模拟轮询服务器次数
    private int anInt = 0;

    /**
     * （有条件）网络请求轮询（结合Retrofit）
     *
     * @param view
     */
    public void button2(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        final Observable<Translation> observable = request.getCall();
        //发送网络请求,通过retyrwhen进行轮询
        observable.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Object o) throws Exception {
                        if (anInt > 5) {
                            return Observable.error(new Throwable("轮询结束"));
                        }
                        return Observable.just(1).delay(2000, TimeUnit.MILLISECONDS);
                    }
                });
            }
        }).subscribeOn(Schedulers.io())   // 切换到IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //切换到主线程 处理请求结果
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "button2_onSubscribe");
                    }

                    @Override
                    public void onNext(Translation translation) {
                        LogUtils.d(TAG, "button2_onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(TAG, "button2_onError");
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG, "button2_onComplete  ");
                    }
                });

    }

    Observable<Translation> observable1;
    Observable<Translation2> observable2;

    public void button3(View view) {
        // 步骤1：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // 步骤2：创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        // 步骤3：对 2个网络请求 进行封装
        observable1 =   request.getCall();
        observable2 = request.getCall_2();


        observable1.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Translation>() {
                    @Override
                    public void accept(Translation translation) throws Exception {
                        //注册成功
                        translation.show();
                    }

                }).observeOn(Schedulers.io())
                .flatMap(new Function<Translation, ObservableSource<Translation2>>() {
                    @Override
                    public ObservableSource<Translation2> apply(Translation translation) throws Exception {
                        return observable2;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Translation2>() {
                    @Override
                    public void accept(Translation2 translation2) throws Exception {
                        translation2.show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //请求失败
                        LogUtils.d(TAG,"失败"+throwable.getMessage());
                    }
                });
    }

    /**
     * 合并网络请求数据
     * @param view
     */
    public void button4(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        GetRequest_Interface request_interface = retrofit.create(GetRequest_Interface.class);
        observable1= request_interface.getCall().observeOn(Schedulers.io());
        observable2= request_interface.getCall_2().observeOn(Schedulers.io());


        /**
         * 将两个请求合并,第三个参数是合并后的数据类型
         */
        Observable.zip(observable1, observable2, new BiFunction<Translation, Translation2, String>() {
            @Override
            public String apply(Translation translation, Translation2 translation2) throws Exception {
                return translation.getStatus() +"&"+translation2.getStatus();
            }
        }).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                //两个请求的结果;
                LogUtil.d(TAG, "两个网络请求的结合体" + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //请求失败的
                LogUtils.d(TAG,"合并请求失败");
            }
        });
    }

    // 设置变量
    // 可重试次数
    private int maxConnectCount = 10;
    // 当前已重试次数
    private int currentRetryCount = 0;
    // 重试等待时间
    private int waitRetryTime = 0;
    /**
     * 网络请求失败重试
     * @param view
     */
    public void button5(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        observable1 = request.getCall();

        observable1.retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        // 输出异常信息
                        Log.d(TAG,  "发生异常 = "+ throwable.toString());
                        /**
                         * 需求1：根据异常类型选择是否重试
                         * 即，当发生的异常 = 网络异常 = IO异常 才选择重试
                         */
                        if (throwable instanceof IOException){
                            Log.d(TAG,  "属于IO异常，需重试" );

                            /**
                             * 需求2：限制重试次数
                             * 即，当已重试次数 < 设置的重试次数，才选择重试
                             */
                            if (currentRetryCount < maxConnectCount){
                                // 记录重试次数
                                currentRetryCount++;
                                Log.d(TAG,  "重试次数 = " + currentRetryCount);

                                /**
                                 * 需求2：实现重试
                                 * 通过返回的Observable发送的事件 = Next事件，从而使得retryWhen（）重订阅，最终实现重试功能
                                 *
                                 * 需求3：延迟1段时间再重试
                                 * 采用delay操作符 = 延迟一段时间发送，以实现重试间隔设置
                                 *
                                 * 需求4：遇到的异常越多，时间越长
                                 * 在delay操作符的等待时间内设置 = 每重试1次，增多延迟重试时间1s
                                 */
                                // 设置等待时间
                                waitRetryTime = 1000 + currentRetryCount* 1000;
                                Log.d(TAG,  "等待时间 =" + waitRetryTime);
                                return Observable.just(1).delay(waitRetryTime, TimeUnit.MILLISECONDS);


                            }else {
                                // 若重试次数已 > 设置重试次数，则不重试
                                // 通过发送error来停止重试（可在观察者的onError（）中获取信息）
                                return Observable.error(new Throwable("重试次数已超过设置次数 = " +currentRetryCount  + "，即 不再重试"));

                            }
                        }else {
                            return Observable.error(new Throwable("发生了非网络异常（非I/O异常）"));
                        }

                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Translation translation) {
                        Log.d(TAG,  "发送成功");
                        translation.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 获取停止重试的信息
                        Log.d(TAG,  e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //切换观察者和被观察者直接的连接
        Disposables.disposed();
//        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        compositeDisposable.clear();
    }

    /**
     * 顺序执行
     * @param view
     */
    public void button6(View view) {
        //无条件网络请求
        Observable.interval(2, 1, TimeUnit.SECONDS)
                // 参数说明：
                // 参数1 = 第1次延迟时间；
                // 参数2 = 间隔时间数字；
                // 参数3 = 时间单位；
                // 该例子发送的事件特点：延迟2s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）

                /*
                 * 步骤2：每次发送数字前发送1次网络请求（doOnNext（）在执行Next事件前调用）
                 * 即每隔1秒产生1个数字前，就发送1次网络请求，从而实现轮询需求
                 **/
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LogUtils.d(TAG, "轮询次数" + aLong);
                        /*
                         * 步骤3：通过Retrofit发送网络请求
                         **/
                        // a. 创建Retrofit对象
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(baseUrl)
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .build();
                        // b. 创建 网络请求接口 的实例
                        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
                        // c. 采用Observable<...>形式 对 网络请求 进行封装
                        Observable<Translation> observable = request.getCall();
                        // d. 通过线程切换发送网络请求
                        observable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Translation>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        LogUtils.d(TAG, "onSubscribe");
                                    }

                                    @Override
                                    public void onNext(Translation translation) {
                                        translation.show();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        LogUtils.d(TAG, "onError"+e.getMessage());
                                    }

                                    @Override
                                    public void onComplete() {
                                        LogUtils.d(TAG, "onComplete");
                                    }
                                });
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        LogUtils.d(TAG, "onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(TAG, "onError");
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG, "onComplete");
                    }
                });
    }
}