package com.hansen.kotlindemo.mvp.model

import com.hansen.kotlindemo.mvp.model.bean.HomeBean
import com.hansen.kotlindemo.net.RetrofitManager
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 *@author HanN on 2020/9/21 17:22
 *@email: 1356548475@qq.com
 *@project customview
 *@description:
 *@updateuser:
 *@updatedata: 2020/9/21 17:22
 *@updateremark:
 *@version:  2.1.67
 */
class HomeModel {
    fun requestHomeData(num: Int):Observable<HomeBean> {
        return RetrofitManager.service.getFirstHomeData(num)
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多
     */
    fun loadMoreData(url:String):Observable<HomeBean>{

        return RetrofitManager.service.getMoreHomeData(url)
                .compose(SchedulerUtils.ioToMain())
    }
}