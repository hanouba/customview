package com.hansen.rxjava;

import com.blankj.utilcode.util.LogUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author HanN on 2020/7/7 10:20
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/7/7 10:20
 * @updateremark:
 * @version: 2.1.67
 */
public class RxjavaUtils {
    public     String TAG = "RxjavaUtils";
    //一 创建被观察者
    Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
        @Override
        public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
            // 通过 ObservableEmitter类对象产生事件并通知观察者
            // ObservableEmitter类介绍
            // a. 定义：事件发射器
            // b. 作用：定义需要发送的事件 & 向观察者发送事件
            emitter.onNext(1);
            emitter.onComplete();
        }
    });

    Observable observableJust = Observable.just(1,2,3);
    String[] words = {"1","3","4"};
    Observable observableForm = Observable.fromArray(words);

    //二创建 观察者
    Observer observer = new Observer<Integer>() {
        @Override
        public void onSubscribe(Disposable d) {
            LogUtils.d(TAG,"onSubscribe" );
        }

        @Override
        public void onNext(Integer integer) {
            LogUtils.d(TAG,"onNext" +integer);
        }

        @Override
        public void onError(Throwable e) {
            LogUtils.d(TAG,"onError" +e.getMessage());
        }

        @Override
        public void onComplete() {
            LogUtils.d(TAG,"onComplete");
        }
    };
    // 创建观察者方式2
    Subscriber<Integer> subscriber = new Subscriber<Integer>() {

        @Override
        public void onSubscribe(Subscription s) {
            LogUtils.d(TAG,"onSubscribe" );
        }

        @Override
        public void onNext(Integer integer) {
            LogUtils.d(TAG,"onNext" +integer);
        }

        @Override
        public void onError(Throwable t) {
            LogUtils.d(TAG,"onError" +t.getMessage());
        }

        @Override
        public void onComplete() {
            LogUtils.d(TAG,"onComplete");
        }
    };

}
