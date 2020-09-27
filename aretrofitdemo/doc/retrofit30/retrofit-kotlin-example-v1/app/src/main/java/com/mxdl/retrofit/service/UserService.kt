package com.mxdl.retrofit.service

import com.mxdl.retrofit.bean.AddUserResponse
import com.mxdl.retrofit.bean.BaseResponse
import com.mxdl.retrofit.bean.User
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.*

/**
 * Description: <UserService><br>
 * Author:      mxdl<br>
 * Date:        2020/8/9<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
interface UserService {
    //1.用户登录
    @GET("/api/user/login/get")
    fun login(@Query("username") username :String,@Query("password")password:Int) : Call<BaseResponse>

    //2.用户注册
    @POST("/api/user/addUser/post")
    fun addUser(@Body user: User) :Call<AddUserResponse>

//    03.用户登录：多个查询参数设置【@GET+@QueryMap】
    @GET("/api/user/login/get/map")
    fun loginQueryMap(@QueryMap loginMap: Map<String,String>): Call<BaseResponse>
//
//    04.用户登录：单个表单参数设置【@POST+@FormURLEncoded+@Field】
    @POST("/api/user/login/form")
    @FormUrlEncoded
    fun loginForm(@Field("username") username: String, @Field("password") password: Int):Call<BaseResponse>
//
//    05.用户登录：多个表单参数设置【@POST+@FormURLEncoded+@FieldMap】
    @POST("/api/user/login/form/map")
    @FormUrlEncoded
    fun loginFormMap(@FieldMap loginMap: Map<String, String>):Call<BaseResponse>
//
//    06.查询用户：动态的URL变参设置【@GET+@Path】
    @GET("/api/user/{id}")
    fun getUserById(@Path("id") id:Int):Call<User>
//
//    07.查询用户：动态的URL设置【@GET+@Url】
    @GET
    fun loginFormUrl(@Url url:String, @Query("username") username: String, @Query("password") password: Int):Call<BaseResponse>
//
//    08.查询用户：多个参数集成设置【@HTTP】
    @HTTP(method = "GET",path = "/api/user/login/get" )
    fun loginHttp(@Query("username") username :String,@Query("password")password:Int) : Call<BaseResponse>
//    09.添加请求头：请求头设置【@Headers+@Header】

    @GET("/api/user/login/get/header")
    @Headers("a:1","b:2")
    fun loginHeader(@Header("c") c:String,@Query("username") username :String, @Query("password")password:Int) : Call<BaseResponse>

    @GET("/api/user/login/get")
    fun loginRxLife(@Query("username") username :String,@Query("password")password:Int) : Observable<BaseResponse>
}