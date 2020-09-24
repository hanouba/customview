package com.hansen.kotlindemo.net

import com.hansen.kotlindemo.MyApplication
import com.hansen.kotlindemo.api.ApiService
import com.hansen.kotlindemo.api.UrlConstant
import com.hansen.kotlindemo.utils.AppUtils
import com.hansen.kotlindemo.utils.NetworkUtil
import com.hansen.kotlindemo.utils.Preference
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.time.temporal.ChronoField
import java.util.concurrent.TimeUnit

/**
 *@author HanN on 2020/9/21 17:24
 *@email: 1356548475@qq.com
 *@project customview
 *@description:
 *@updateuser:
 *@updatedata: 2020/9/21 17:24
 *@updateremark:
 *@version:  2.1.67

 */
object RetrofitManager {
    val service: ApiService by lazy  (LazyThreadSafetyMode.SYNCHRONIZED) {
        getRertrofit().create(ApiService::class.java)
    }
//
    private var token:String by Preference("token","")


    private fun getRertrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(UrlConstant.BASE_URL)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    //获取okhttpclient
    private fun getOkHttpClient(): OkHttpClient {
        //添加一个log拦截器 打印所有的log
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        //请求过滤的水平
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        //设置 请求的缓存的大小位置
        val cacheFile = File(MyApplication.context.cacheDir,"cache")
        val cache  = Cache(cacheFile,1024*1024*50)

        return OkHttpClient.Builder()
                .addInterceptor(addQueryParameterInterceptor())
                .addInterceptor(addHeaderInterceptor())
                .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应度看到
                .cache(cache)  //添加缓存
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS)
                .build()

    }

    /**
     * 添加公共参数
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url().newBuilder()
                    .addQueryParameter("udid","d2807c895f0348a180148c9dfa6f2feeac0781b5")
                    .addQueryParameter("deviceModel",AppUtils.getMobileModel())
                    .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }
    }

    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                    .header("token",token)
                    .method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }


    /**
     * 设置缓存
     */
    private fun addCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!NetworkUtil.isNetworkAvailable(MyApplication.context)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }
            val response = chain.proceed(request)
            if (NetworkUtil.isNetworkAvailable(MyApplication.context)) {
                val maxAge = 0
                // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build()
            } else {
                // 无网络时，设置超时为4周  只对get有用,post没有缓冲
                val maxStale = 60 * 60 * 24 * 28
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("nyn")
                        .build()
            }
            response
        }
    }
}



