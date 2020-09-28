/*
 * Copyright (C) 2020  Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hansen.rxjava3lib.rxjava3;

import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.adapter.http.http.ExceptionHandler;
import retrofit2.adapter.http.http.ResponseException;

final class RxJava3CallAdapter<R> implements CallAdapter<R, Object> {
  private final Type responseType;
  private final @Nullable Scheduler scheduler;
  private final boolean isAsync;
  private final boolean isResult;
  private final boolean isBody;
  private final boolean isFlowable;
  private final boolean isSingle;
  private final boolean isMaybe;
  private final boolean isCompletable;

  RxJava3CallAdapter(
      Type responseType,
      @Nullable Scheduler scheduler,
      boolean isAsync,
      boolean isResult,
      boolean isBody,
      boolean isFlowable,
      boolean isSingle,
      boolean isMaybe,
      boolean isCompletable) {
    this.responseType = responseType;
    this.scheduler = scheduler;
    this.isAsync = isAsync;
    this.isResult = isResult;
    this.isBody = isBody;
    this.isFlowable = isFlowable;
    this.isSingle = isSingle;
    this.isMaybe = isMaybe;
    this.isCompletable = isCompletable;
  }

  @Override
  public Type responseType() {
    return responseType;
  }

  @Override
  public Object adapt(Call<R> call) {
    Observable<Response<R>> responseObservable =
        isAsync ? new CallEnqueueObservable<>(call) : new CallExecuteObservable<>(call);

    Observable<?> observable;
    if (isResult) {
      observable = new ResultObservable<>(responseObservable);
    } else if (isBody) {
      observable = new BodyObservable<>(responseObservable);
    } else {
      observable = responseObservable;
    }

    if (scheduler != null) {
      observable = observable.subscribeOn(scheduler);
    }

    if (isFlowable) {
      return observable.toFlowable(BackpressureStrategy.LATEST);
    }
    if (isSingle) {
      return observable.singleOrError();
    }
    if (isMaybe) {
      return observable.singleElement();
    }
    if (isCompletable) {
      return observable.ignoreElements();
    }
    return RxJavaPlugins.onAssembly(observable)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(new Function<Object, Object>() {
              @Override
              public Object apply(Object o) throws Throwable {
                //if(o instanceof BaseResponse){
                    //通用应用级的错误处理
                //}
                return o;
              }
            })
            .onErrorResumeNext(new Function<Throwable, ObservableSource<?>>() {
              @Override
              public ObservableSource<?> apply(Throwable throwable) throws Throwable {
                ResponseException responseException = ExceptionHandler.handleException(throwable);
                if(responseException.code == ExceptionHandler.SYSTEM_ERROR.NETWORD_ERROR){
                  Log.v("MYTAG","网络不给力！");
                }else if(responseException.code == ExceptionHandler.SYSTEM_ERROR.UNAUTHORIZED){
                  Log.v("MYTAG","鉴权失败");
                }
                return Observable.error(throwable);
              }
            });
  }
}
