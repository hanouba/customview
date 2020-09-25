package com.hansen.aretrofitdemo.service

import com.hansen.aretrofitdemo.bean.AddUserResponse
import com.hansen.aretrofitdemo.bean.BaseResponse
import com.hansen.aretrofitdemo.bean.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *@author HanN on 2020/9/25 17:37
 *@email: 1356548475@qq.com
 *@project customview
 *@description:
 *@updateuser:
 *@updatedata: 2020/9/25 17:37
 *@updateremark:
 *@version:  2.1.67
 */
interface UserService {
    @GET("login/get")
    fun login(@Query("username") username:String ,@Query("password") password: Int) : Call<BaseResponse>

    @POST("addUser/post")
    fun addUser(@Body user:User) : Call<AddUserResponse>
}