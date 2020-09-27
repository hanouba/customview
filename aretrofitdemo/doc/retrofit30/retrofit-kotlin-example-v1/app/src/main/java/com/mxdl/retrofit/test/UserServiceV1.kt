package com.mxdl.retrofit.test

import com.mxdl.retrofit.bean.BaseResponse
import com.mxdl.retrofit.bean.User
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

/**
 * Description: <UserServiceV1><br>
 * Author:      mxdl<br>
 * Date:        2020/8/13<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
interface UserServiceV1 {
    //1.设置多个查询参数
    @GET(value = "/api/user/login")
    fun loginQueryMap(@QueryMap loginMap: Map<String,String>): Call<BaseResponse>

    //2.以表单的形式提交
    @POST(value = "/api/user/login")
    @FormUrlEncoded
    fun loginFormField(@Field("username") username: String,@Field("password") password: Int): Call<BaseResponse>

    //3.以表单集合的方式提交
    @POST(value = "/api/user/login")
    @FormUrlEncoded
    fun loginFormMap(@FieldMap logMap: Map<String,String>): Call<BaseResponse>

    //3.动态设置url的参数
    @GET("/api/user/get/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>

    //4.@Http
    @HTTP(method = "GET",path = "/api/user/get/{id}")
    fun getUserByIdHttp(@Path("id") id:Int): Call<User>

    @POST("/api/user/login")
    @Multipart
    fun loginFormPart(@Part("username") username: String, @Part("password") password: Int):Call<BaseResponse>

    @POST("/api/user/upload")
    @Multipart
    fun uploadFile(@Part("username") username: String, @Part("file") resquestBody:MultipartBody):Call<BaseResponse>

    @POST("/api/user/upload/part")
    @Multipart
    fun uploadFileByPart(@Part part:MultipartBody.Part):Call<BaseResponse>

    @POST("/api/user/upload/parts")
    @Multipart
    fun uploadFileByParts(@Part part:List<MultipartBody.Part>):Call<BaseResponse>

    @POST("/api/user/upload/file")
    @Multipart
    fun uploadFile(@Part("file") file: File):Call<BaseResponse>

    @GET("/api/user/header")
    @Headers("a:1","b:2")
    fun loginHeader(@Header("c") token: String): Call<BaseResponse>

}