package com.hansen.kotlindemo.api

import com.hansen.kotlindemo.mvp.model.bean.HomeBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 *@author HanN on 2020/9/21 17:24
 *@email: 1356548475@qq.com
 *@project customview
 *@description:
 *@updateuser:
 *@updatedata: 2020/9/21 17:24
 *@updateremark:
 *@version:  2.1.67
 */
interface ApiService{
    @GET
    fun getFirstHomeData(@Query("num")num: Int): Observable<HomeBean>
    @GET
    fun getMoreHomeData(@Url url: String): Observable<HomeBean>
    /**
     * 根据item id获取相关视频
     */
    @GET("v4/video/related?")
    fun getRelatedData(@Query("id") id: Long): Observable<HomeBean.Issue>




}