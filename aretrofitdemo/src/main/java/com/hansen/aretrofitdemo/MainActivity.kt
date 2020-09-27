package com.hansen.aretrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.hansen.aretrofitdemo.bean.AddUserResponse
import com.hansen.aretrofitdemo.bean.BaseResponse
import com.hansen.aretrofitdemo.bean.User
import com.hansen.aretrofitdemo.service.UserService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //    01 用户登录: 单个查询参数设置【@GET+@Query】
    fun login(view: View) {
        var retrofit = Retrofit.Builder()
                .client(
                        OkHttpClient.Builder()
                                .addInterceptor(
                                        HttpLoggingInterceptor()
                                                .setLevel(HttpLoggingInterceptor.Level.BASIC)
                                )
                                .build()
                )
                .baseUrl("http:192.168.3.107:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var userService = retrofit.create(UserService::class.java)

        var loginCall = userService.login("hn", 123456)

        loginCall.enqueue(object : Callback<BaseResponse> {
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.e("mmmni", "onFailure: 登录失败", t)
            }

            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.e("mmmni", "onResponse: 登录成功" + response.body().toString())
            }

        })

    }

    //    02 用户注册：请求体JSON对象设置【@POST+@Body】
    fun addUser(view: View) {
        var retrofit = Retrofit.Builder()
                .client(
                        OkHttpClient.Builder()
                                .addInterceptor(
                                        HttpLoggingInterceptor()
                                                .setLevel(HttpLoggingInterceptor.Level.BASIC)
                                )
                                .build()
                )
                .baseUrl("http:192.168.3.107:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        //:: 只能将函数作为参数来使用，不能直接用来调用函数：
        var userService = retrofit.create(UserService::class.java)

        var addUser = userService.addUser(User("hn", 123456))

        addUser.enqueue(object : Callback<AddUserResponse> {
            override fun onFailure(call: Call<AddUserResponse>, t: Throwable) {
                Log.e("mmmni", "onFailure: 注册失败", t)
            }

            override fun onResponse(call: Call<AddUserResponse>, response: Response<AddUserResponse>) {
                Log.e("mmmni", "onFailure: 注册成功" + response.body().toString())
            }


        })
    }


    //    03 用户登录： 多个参数设置【@GET+@QueryMap
    fun Button5(view: View) {
        var retrofit = Retrofit.Builder()
                .client(
                        OkHttpClient.Builder()
                                .addInterceptor(
                                        HttpLoggingInterceptor()
                                                .setLevel(HttpLoggingInterceptor.Level.BASIC)
                                )
                                .build()
                )
                .baseUrl("http:192.168.3.107:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var userService = retrofit.create(UserService::class.java)

        var loginmap = HashMap<String ,String>()
        loginmap.put("username","hn")
        loginmap.put("password","123456")
        var loginCall = userService.loginQueryMap(loginmap)

        loginCall.enqueue(object : Callback<BaseResponse> {
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.e("mmmni", "onFailure: 登录失败", t)
            }

            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.e("mmmni", "onResponse: 登录成功" +  response.body().toString())
            }

        })
    }

    //    04 用户登录：单个表单参数设置【@POST+@FormURLEncoded+@Field】
    fun Button6(view: View) {
        var retrofit = Retrofit.Builder()
                .client(
                        OkHttpClient.Builder()
                                .addInterceptor(
                                        HttpLoggingInterceptor()
                                                .setLevel(HttpLoggingInterceptor.Level.BASIC)
                                )
                                .build()
                )
                .baseUrl("http:192.168.3.107:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var userService = retrofit.create(UserService::class.java)


        var loginCall = userService.loginForm("hn",123456)

        loginCall.enqueue(object : Callback<BaseResponse> {
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.e("mmmni", "onFailure: form登录失败", t)
            }

            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.e("mmmni", "onResponse: form登录成功" +  response.body().toString())
            }

        })
    }

    //    05用户登录 ： 多个表单参数设置【@POST+@FormURLEncoded+@FieldMap】
    fun Button7(view: View) {
        var retrofit = Retrofit.Builder()
                .client(
                        OkHttpClient.Builder()
                                .addInterceptor(
                                        HttpLoggingInterceptor()
                                                .setLevel(HttpLoggingInterceptor.Level.BASIC)
                                )
                                .build()
                )
                .baseUrl("http:192.168.3.107:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var userService = retrofit.create(UserService::class.java)
        var loginmap = HashMap<String ,String>()
        loginmap.put("username","hn")
        loginmap.put("password","123456")

        var loginCall = userService.loginFormMap(loginmap)

        loginCall.enqueue(object : Callback<BaseResponse> {
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.e("mmmni", "onFailure: formMap登录失败", t)
            }

            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.e("mmmni", "onResponse: formMap登录成功" +  response.body().toString())
            }

        })
    }

    //    06 查询用户： 动态的URL变参设置【@GET+@Path】
    fun Button8(view: View) {
        var retrofit = Retrofit.Builder()
                .client(
                        OkHttpClient.Builder()
                                .addInterceptor(
                                        HttpLoggingInterceptor()
                                                .setLevel(HttpLoggingInterceptor.Level.BASIC)
                                )
                                .build()
                )
                .baseUrl("http:192.168.3.107:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var userService = retrofit.create(UserService::class.java)


        var loginCall = userService.getUserById(1)

        loginCall.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("mmmni", "onFailure: fgetUserById登录失败", t)
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.e("mmmni", "onResponse: getUserById登录成功" + response.body().toString())
            }

        })
    }

    //    07 查询用户：动态的URL设置【@GET+@Url】
    fun Button9(view: View) {
        var retrofit = Retrofit.Builder()
                .client(
                        OkHttpClient.Builder()
                                .addInterceptor(
                                        HttpLoggingInterceptor()
                                                .setLevel(HttpLoggingInterceptor.Level.BASIC)
                                )
                                .build()
                )
                .baseUrl("http:192.168.3.107:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var userService = retrofit.create(UserService::class.java)


        var loginCall = userService.loginFormUrl("http:192.168.3.107:8088/login/get/url","hn",123456)

        loginCall.enqueue(object : Callback<BaseResponse> {
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.e("mmmni", "onFailure: form动态url登录失败", t)
            }

            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.e("mmmni", "onResponse: form动态url登录成功" +  response.body().toString())
            }

        })
    }

    //   08 查询用户： 多个参数集成设置【@HTTP】
    fun Button10(view: View) {
        var retrofit = Retrofit.Builder()
                .client(
                        OkHttpClient.Builder()
                                .addInterceptor(
                                        HttpLoggingInterceptor()
                                                .setLevel(HttpLoggingInterceptor.Level.BASIC)
                                )
                                .build()
                )
                .baseUrl("http://192.168.3.107:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //2.创建用户服务
        var userService = retrofit.create(UserService::class.java)

        //3.创建用户登录命令
        var loginCall = userService.loginHttp("hn", 123456)

        //4.执行请求命令
        loginCall.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.v("mmmni", "onResponse start...")
                Log.v("mmmni", "body:" + response.body().toString())
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.v("mmmni", "onFail start...")
                Log.v("mmmni", "error:" + t.toString());
            }
        })
    }

    //    09 添加请求头： 请求头设置【@Headers+@Header】
    fun Button11(view: View) {
        var retrofit = Retrofit.Builder()
                .client(
                        OkHttpClient.Builder()
                                .addInterceptor(
                                        HttpLoggingInterceptor()
                                                .setLevel(HttpLoggingInterceptor.Level.BASIC)
                                )
                                .build()
                )
                .baseUrl("http://192.168.3.107:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //2.创建用户服务
        var userService = retrofit.create(UserService::class.java)

        //3.创建用户登录命令
        var loginCall = userService.loginHeader("3", "hn", 123456)

        //4.执行请求命令
        loginCall.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.v("mmmni", "onResponse start...")
                Log.v("mmmni", "body:" + response.body().toString())
            }
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.v("mmmni", "onFail start...")
                Log.v("mmmni", "error:" + t.toString());
            }
        })

    }



}


