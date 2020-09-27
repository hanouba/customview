package com.mxdl.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.mxdl.retrofit.bean.AddUserResponse
import com.mxdl.retrofit.bean.BaseResponse
import com.mxdl.retrofit.bean.User
import com.mxdl.retrofit.service.UserService
import io.reactivex.rxjava3.core.Observable
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.lang.reflect.Proxy
import java.util.function.Function

class MainActivity : AppCompatActivity() {
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //var file = File(getExternalFilesDir(""),"a.jpg")
        //file.createNewFile()
    }

    //1.用户登录
    fun login(view: View) {
        //1.创建一个Retorfit
        var retrofit = Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BASIC)
                    )
                    .build()
            )
            .baseUrl("http://192.168.31.105:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        //2.创建用户服务
        var userService = retrofit.create(UserService::class.java)

        //3.创建用户登录命令
        var loginCall = userService.login("mxdl", 123456)

//        userService.toString();
//        userService.equals();
//
        //4.执行请求命令
        loginCall.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.v("MYTAG", "onResponse start...")
                Log.v("MYTAG", "body:" + response.body().toString())
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.v("MYTAG", "onFail start...")
                Log.v("MYTAG", "error:" + t.toString());
            }
        })
    }

    //2.用户注册
    fun addUser(view: View) {
        //1.创建一个Retorfit
        var retrofit = Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BASIC)
                    )
                    .build()
            )
            .baseUrl("http://192.168.31.105:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        //2.创建用户服务
        var userService = retrofit.create(UserService::class.java)

        //3.创建一个用户注册的命令
        var addUserCall = userService.addUser(User("zhangsan", "1234556"))

        //4.执行用户注册命令
        addUserCall.enqueue(object : Callback<AddUserResponse> {
            override fun onResponse(
                call: Call<AddUserResponse>,
                response: Response<AddUserResponse>
            ) {
                Log.v("MYTAG", "onFail onResponse...")
                Log.v("MYTAG", "body:" + response.body().toString())
            }

            override fun onFailure(call: Call<AddUserResponse>, t: Throwable) {
                Log.v("MYTAG", "onFail start...")
                Log.v("MYTAG", "error:" + t.toString());
            }
        })
    }
    //3.QueryMap
    fun loginQueryMap(view: View){
        Log.v("MYTAG","loginQueryMap start...")
        //1.创建一个Retorfit
        var retrofit = Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BASIC)
                    )
                    .build()
            )
            .baseUrl("http://192.168.31.105:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        //2.创建用户服务
        var userService = retrofit.create(UserService::class.java)

        //3.创建用户登录命令
        var loginMap = HashMap<String,String>()
        loginMap.put("username","mxdl")
        loginMap.put("password","123456")

        var loginCall = userService.loginQueryMap(loginMap)

        //4.执行请求命令
        loginCall.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.v("MYTAG", "onResponse start...")
                Log.v("MYTAG", "body:" + response.body().toString())
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.v("MYTAG", "onFail start...")
                Log.v("MYTAG", "error:" + t.toString());
            }
        })
    }
    //4.@Filed
    fun loginForm(view: View){
        Log.v("MYTAG","loginFrom start...")
        //1.创建一个Retorfit
        var retrofit = Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BASIC)
                    )
                    .build()
            )
            .baseUrl("http://192.168.31.105:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        //2.创建用户服务
        var userService = retrofit.create(UserService::class.java)

        //3.创建用户登录命令
        var loginCall = userService.loginForm("mxdl", 123456)

        //4.执行请求命令
        loginCall.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.v("MYTAG", "onResponse start...")
                Log.v("MYTAG", "body:" + response.body().toString())
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.v("MYTAG", "onFail start...")
                Log.v("MYTAG", "error:" + t.toString());
            }
        })
    }

    //5.@FiledMap
    fun loginFormMap(view: View){
        Log.v("MYTAG","loginFrom start...")
        //1.创建一个Retorfit
        var retrofit = Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BASIC)
                    )
                    .build()
            )
            .baseUrl("http://192.168.31.105:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        //2.创建用户服务
        var userService = retrofit.create(UserService::class.java)

        //3.创建用户登录命令
        var loginMap = HashMap<String,String>()
        loginMap.put("username","mxdl")
        loginMap.put("password","123456")
        var loginCall = userService.loginFormMap(loginMap)

        //4.执行请求命令
        loginCall.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.v("MYTAG", "onResponse start...")
                Log.v("MYTAG", "body:" + response.body().toString())
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.v("MYTAG", "onFail start...")
                Log.v("MYTAG", "error:" + t.toString());
            }
        })
    }
    //4.@Filed
    fun loginUrl(view: View){
        Log.v("MYTAG","loginFrom start...")
        //1.创建一个Retorfit
        var retrofit = Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BASIC)
                    )
                    .build()
            )
            .baseUrl("http://192.168.31.105:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        //2.创建用户服务
        var userService = retrofit.create(UserService::class.java)

        //3.创建用户登录命令
        var loginCall = userService.loginFormUrl("http://192.168.31.105:8088/api/user/login/get/url","mxdl", 123456)

        //4.执行请求命令
        loginCall.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.v("MYTAG", "onResponse start...")
                Log.v("MYTAG", "body:" + response.body().toString())
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.v("MYTAG", "onFail start...")
                Log.v("MYTAG", "error:" + t.toString());
            }
        })
    }
    //6.@Paht
    fun loginPath(view: View){
        Log.v("MYTAG","loginFrom start...")
        //1.创建一个Retorfit
        var retrofit = Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BASIC)
                    )
                    .build()
            )
            .baseUrl("http://192.168.31.105:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        //2.创建用户服务
        var userService = retrofit.create(UserService::class.java)

        //3.创建用户登录命令
        var getUserCall = userService.getUserById(6)

        //4.执行请求命令
        getUserCall.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.v("MYTAG", "onResponse start...")
                Log.v("MYTAG", "body:" + response.body().toString())
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.v("MYTAG", "onFail start...")
                Log.v("MYTAG", "error:" + t.toString());
            }
        })
    }
    //1.用户登录
    fun loginHttp(view: View) {
        //1.创建一个Retorfit
        var retrofit = Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BASIC)
                    )
                    .build()
            )
            .baseUrl("http://192.168.31.105:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        //2.创建用户服务
        var userService = retrofit.create(UserService::class.java)

        //3.创建用户登录命令
        var loginCall = userService.loginHttp("mxdl", 123456)

        //4.执行请求命令
        loginCall.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.v("MYTAG", "onResponse start...")
                Log.v("MYTAG", "body:" + response.body().toString())
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.v("MYTAG", "onFail start...")
                Log.v("MYTAG", "error:" + t.toString());
            }
        })
    }
    //1.用户登录
    fun loginHeader(view: View) {
        //1.创建一个Retorfit
        var retrofit = Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BASIC)
                    )
                    .build()
            )
            .baseUrl("http://192.168.31.105:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        //2.创建用户服务
        var userService = retrofit.create(UserService::class.java)

        //3.创建用户登录命令
        var loginCall = userService.loginHeader("3","mxdl", 123456)

        //4.执行请求命令
        loginCall.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.v("MYTAG", "onResponse start...")
                Log.v("MYTAG", "body:" + response.body().toString())
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.v("MYTAG", "onFail start...")
                Log.v("MYTAG", "error:" + t.toString());
            }
        })
    }
}