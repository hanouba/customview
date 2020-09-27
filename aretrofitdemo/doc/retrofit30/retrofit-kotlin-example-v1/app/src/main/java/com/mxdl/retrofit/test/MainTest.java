package com.mxdl.retrofit.test;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;

/**
 * Description: <MainTest><br>
 * Author:      mxdl<br>
 * Date:        2020/8/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class MainTest {
    public static void main(String[] args) {
        Observable.just("long","longer","longest0")
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Throwable {
                        return s.length();
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Throwable {
                System.out.println("Integer:"+integer);
            }
        });
    }
}
