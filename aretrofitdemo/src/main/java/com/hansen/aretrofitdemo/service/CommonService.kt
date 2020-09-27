package com.hansen.aretrofitdemo.service

import com.hansen.aretrofitdemo.bean.BaseResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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
     * 文件上传
     */
    @POST("api/common/upload")
    @Multipart
    fun uploadFile(@Part part: MultipartBody.Part) : Call<BaseResponse>
}