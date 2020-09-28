package com.hansen.aretrofitdemo.service

import com.hansen.aretrofitdemo.Token
import com.hansen.aretrofitdemo.bean.BaseResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

/**
 *@author HanN on 2020/9/27 16:34
 *@email: 1356548475@qq.com
 *@project customview
 *@description: 文件处理实操
 *@updateuser:
 *@updatedata: 2020/9/27 16:34
 *@updateremark:
 *@version:  2.1.67
 */
interface CommonService {
    /**
     * 单文件上传
     */
    @POST("api/common/upload")
    @Multipart
    fun uploadFile(@Part part: MultipartBody.Part) : Call<BaseResponse>

    /**
     * 多文件上传
     */
    @POST("api/common/uploads")
    @Multipart
    fun uploadFiles(@Part parts: List<MultipartBody.Part>) : Call<BaseResponse>

    /**
     * 多文件上传2
     */
    @POST("api/common/uploads")
    @Multipart
    fun uploadFiles1(@Part("files") requestsBody: MultipartBody) : Call<BaseResponse>

    /**
     * 文件下载
     */
    @GET
    fun downloadFile(@Url string: String) : Call<ResponseBody>

    //    带进度条的文件下载

//    5.自定义数据转换器 上传文件
    @POST("/api/common/upload")
    @Multipart
    fun uploadFileConver(@Part("file") file: File):Call<BaseResponse>

    //1.获取token
    @POST("/oauth/token?grant_type=password")
    @Headers("Authorization:Basic bXhkbC1jbGllbnQ6bXhkbC1zZWNyZXQ=")
    fun getToken(@Query("username") username:String,@Query("password") password:Int):Call<Token>
}