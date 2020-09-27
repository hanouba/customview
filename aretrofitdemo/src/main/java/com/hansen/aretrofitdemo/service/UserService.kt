package com.hansen.aretrofitdemo.service

import com.hansen.aretrofitdemo.bean.AddUserResponse
import com.hansen.aretrofitdemo.bean.BaseResponse
import com.hansen.aretrofitdemo.bean.User
import retrofit2.Call
import retrofit2.http.*

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
    //用户登录 单个查询参数设置【@GET+@Query】
    @GET("login/get")
    fun login(@Query("username") username:String ,@Query("password") password: Int) : Call<BaseResponse>
    //用户注册 请求体JSON对象设置【@POST+@Body】
    @POST("addUser/post")
    fun addUser(@Body user:User) : Call<AddUserResponse>
    //用户登录  多个参数设置【@GET+@QueryMap】
    @POST("login/get")
    fun loginQueryMap(@QueryMap() loginMap: Map<String,String>) : Call<BaseResponse>

    //04 用户登录：单个表单参数设置【@POST+@FormURLEncoded+@Field】  //只可以用post
    @POST("login/get/form")
    @FormUrlEncoded
    fun loginForm(@Field("username") username: String,@Field("password") password: Int) : Call<BaseResponse>
    //05用户登录 ： 多个表单参数设置【@POST+@FormURLEncoded+@FieldMap】
    @POST("login/get/formmap")
    @FormUrlEncoded
    fun loginFormMap(@FieldMap() loginMap: Map<String,String>) : Call<BaseResponse>

    //动态的URL变参设置【@GET+@Path】
    @GET("/login/{id}")
    fun getUserById(@Path("id") id: Int) : Call<User>


    //07 查询用户：动态的URL设置【@GET+@Url】  不能添加参数 地址参数都要写在新的url里面
    @GET
    fun loginFormUrl(@Url string: String,@Query("username") username: String, @Query("password") password: Int) : Call<BaseResponse>

    //08 查询用户： 多个参数集成设置【@HTTP】
    @HTTP(method = "GET",path = "/login/get" )
    fun loginHttp(@Query("username") username:String ,@Query("password") password: Int) : Call<BaseResponse>
//    09 添加请求头： 请求头设置【@Headers+@Header】
    // a和 b直接在请求中设置
    //C 在代码后面设置
    @GET("login/get/header")
    @Headers("a:1","b:2")
    fun loginHeader(@Header("c") c:String,@Query("username") username :String, @Query("password")password:Int) : Call<BaseResponse>
}