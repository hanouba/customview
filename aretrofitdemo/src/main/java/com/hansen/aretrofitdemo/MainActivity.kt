package com.hansen.aretrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.hansen.aretrofitdemo.bean.BaseResponse
import com.hansen.aretrofitdemo.service.UserService
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

    fun login(view: View) {
        var retrofit = Retrofit.Builder()
                .baseUrl("http:192.168.3.107:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var userService = retrofit.create(UserService::class.java)

      var loginCall =  userService.login("hn",123456)

        loginCall.enqueue(object: Callback<BaseResponse> {
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.e("mmmni", "onFailure: 登录失败",t)
            }

            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.e("mmmni", "onResponse: 登录成功"+response.toString())
            }

        })

    }
    fun addUser(view: View) {

    }
}
