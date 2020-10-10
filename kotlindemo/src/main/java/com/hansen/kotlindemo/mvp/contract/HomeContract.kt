package com.hansen.kotlindemo.mvp.contract

import com.hansen.kotlindemo.base.IBasePresent
import com.hansen.kotlindemo.base.IBaseView
import com.hansen.kotlindemo.mvp.model.bean.HomeBean
import java.util.ArrayList

/**
 *@author HanN on 2020/9/9 15:21
 *@email: 1356548475@qq.com
 *@project customview
 *@description: 首页的契约类
 *@updateuser:
 *@updatedata: 2020/9/9 15:21
 *@updateremark:
 *@version:  2.1.67
 */
interface HomeContract {
    interface View : IBaseView {
        fun setHomeData(homeBean: HomeBean);
        fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>)
        fun showError(msg: String,error:Int)
    }

    interface Presenter : IBasePresent<View> {
        fun requestHomeData(num: Int)
        fun loadMoreData()

    }

}