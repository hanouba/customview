package com.mxdl.retrofit.service

import com.mxdl.retrofit.bean.BaseResponse
import com.mxdl.retrofit.bean.User
import com.mxdl.retrofit.security.Token
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

/**
 * Description: <CommonService><br>
 * Author:      mxdl<br>
 * Date:        2020/8/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
interface CommonService {
    //    单文件上传
    @POST("/api/common/upload")
    @Multipart
    fun uploadFile(@Part part:MultipartBody.Part): Call<BaseResponse>

//    多文件上传
    @POST("/api/common/uploads")
    @Multipart
    fun uploadFiles(@Part parts:List<MultipartBody.Part>): Call<BaseResponse>

    @POST("/api/common/uploads")
    @Multipart
    fun uploadFiles1(@Part("files") requestsBody:MultipartBody): Call<BaseResponse>

    //   文件的下载
    @GET
    fun downloadFile(@Url url:String):Call<ResponseBody>
//    带进度条的文件下载
//    5.自定义数据转换器
    @POST("/api/common/upload")
    @Multipart
    fun uploadFileConver(@Part("file") file: File):Call<BaseResponse>

    //1.获取token
    @POST("/oauth/token?grant_type=password")
    @Headers("Authorization:Basic bXhkbC1jbGllbnQ6bXhkbC1zZWNyZXQ=")
    fun getToken(@Query("username") username:String,@Query("password") password:Int):Call<Token>

    //2.使用token
    @GET("/api/user/list")
    fun getUsers(): Call<List<User>>

    @GET("/api/user/login/get")
    fun loginRxJava(@Query("username") username: String,@Query("password") password: Int): Observable<BaseResponse>

    @GET("/api/user/login/get")
    fun loginDeferred(@Query("username") username: String,@Query("password") password: Int): Deferred<BaseResponse>

    @GET("/api/user/login/get")
    suspend fun loginCoroutine(@Query("username") username: String,@Query("password") password: Int):BaseResponse
//    8.动态的切换多个url
//    9.网络的缓存设置
//    10.添加一个HTTPS的请求
//    11.添加token访问认证
//    12.RxJava的结合使用
//    13.RxJava请求错误的通用处理
//    14.与RxLifecycle的结合使用
//    15.与Deferred的结合使用
//    16.与Kotlin协程的结合使用
}