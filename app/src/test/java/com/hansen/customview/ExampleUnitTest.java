package com.hansen.customview;

import com.blankj.utilcode.util.LogUtils;

import org.junit.Test;

import java.util.Calendar;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;
import kotlin.jvm.functions.Function0;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private final String TAG = "RxjavaActivityTAG";
    @Test
    public void addition_isCorrect() {
       Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.MONTH, 0);

        System.out.println("xdsfdsadfa"+calendar.getTime());
    }
    @Test
    public void rxJavaDemo() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                // 通过 ObservableEmitter类对象产生事件并通知观察者
                // ObservableEmitter类介绍
                // a. 定义：事件发射器
                // b. 作用：定义需要发送的事件 & 向观察者发送事件
                boolean disposed = emitter.isDisposed();
                emitter.onNext(2);
                emitter.onNext(2);
                emitter.onNext(23);
                emitter.onNext(24);
                boolean disposed2 = emitter.isDisposed();
                emitter.onComplete();
                System.out.println(TAG+"subscribe"+disposed+"--"+disposed2);
                emitter.serialize();
                emitter.tryOnError(new Throwable());
            }
        });

        Observer observer = new Observer<Integer>() {
            Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
//                LogUtils.d(TAG, "onSubscribe");
                disposable = d;
                System.out.println(TAG+"onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
//                LogUtils.d(TAG, "onNext"+integer);
//                if (23 == integer) {
//                    disposable.dispose();
//                }
                System.out.println(TAG+"onNext"+integer);
            }


            @Override
            public void onError(Throwable e) {
//                LogUtils.d(TAG, "onError");
                System.out.println(TAG+"onError"+e.getMessage());
            }

            @Override
            public void onComplete() {
//                LogUtils.d(TAG, "onComplete");
                System.out.println(TAG+"onComplete");
            }
        };

        observable.subscribe(observer);
    }

    @Test
    public void rxJavaDemo2() {
        Observable.just("hello").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(TAG+"accept--"+s);
            }
        });
    }
    @Test
    public void rxJavaDemo3() {
        //可采用 Disposable.dispose() 切断观察者 与 被观察者 之间的连接
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
               emitter.onNext("开始进攻");
               emitter.onComplete();
            }
        });

        Observer<String> observer = new Observer() {
            Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;

            }

            @Override
            public void onNext(Object o) {
                if (o.equals("开始")) {
                    disposable.dispose();
                }

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
}