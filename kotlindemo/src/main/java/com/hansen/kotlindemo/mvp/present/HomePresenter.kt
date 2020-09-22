package com.hansen.kotlindemo.mvp.present

import com.hansen.kotlindemo.base.BasePresenter
import com.hansen.kotlindemo.mvp.contract.HomeContract
import com.hansen.kotlindemo.mvp.model.HomeModel
import com.hansen.kotlindemo.mvp.model.bean.HomeBean
import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import io.reactivex.internal.util.ExceptionHelper

/**
 *@author HanN on 2020/9/21 17:19
 *@email: 1356548475@qq.com
 *@project customview
 *@description:
 *@updateuser:
 *@updatedata: 2020/9/21 17:19
 *@updateremark:
 *@version:  2.1.67
 */
class HomePresenter : BasePresenter<HomeContract.View>(),HomeContract.Presenter {
    //数据实体类
    private var bannerHomeBean: HomeBean? = null
    //下一页的地址
    private var nextPageUrl: String? = null
        // ????
    private val homeModel by lazy {
        HomeModel()
    }

    override fun requestHomeData(num: Int) {
        checkViewAttached()

        mRootView?.showLoading()

        val disposable = homeModel.requestHomeData(num)
                .flatMap ({homeBean ->
                    val bannerItemList = homeBean.issueList[0].itemList

                    bannerItemList.filter { item ->
                        item.type=="banner2"|| item.type=="horizontalScrollCard"
                    }.forEach{ item ->
                        //移除 item
                        bannerItemList.remove(item)
                    }

                    bannerHomeBean = homeBean //
                    homeModel.loadMoreData(homeBean.nextPageUrl)
                })
                .subscribe({ homeBean ->
                    mRootView?.apply {
                        dismissLoading()
                        nextPageUrl = homeBean.nextPageUrl
                        val newBannerItemList = homeBean.issueList[0].itemList
                        //过滤数据
                        newBannerItemList.filter { item ->
                            item.type=="banner2" || item.type == "horizontalScrollCard"
                        }.forEach { item ->
                            newBannerItemList.remove(item)
                        }
                        // 重新赋值 Banner 长度
                        bannerHomeBean!!.issueList[0].count = bannerHomeBean!!.issueList[0].itemList.size

                        //赋值过滤后的数据 + banner 数据
                        bannerHomeBean?.issueList!![0].itemList.addAll(newBannerItemList)

                        setHomeData(bannerHomeBean!!)
                    }
                }, {t ->
                    mRootView?.apply {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(t),ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    override fun loadMoreData() {
        val disposable = nextPageUrl?.let {
            homeModel.loadMoreData(it)
                    .subscribe({ homeBean->
                        mRootView?.apply {
                            //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                            val newItemList = homeBean.issueList[0].itemList

                            newItemList.filter { item ->
                                item.type=="banner2"||item.type=="horizontalScrollCard"
                            }.forEach{ item ->
                                //移除 item
                                newItemList.remove(item)
                            }

                            nextPageUrl = homeBean.nextPageUrl
                            setMoreData(newItemList)
                        }

                    },{ t ->
                        mRootView?.apply {
                            showError(ExceptionHandle.handleException(t),ExceptionHandle.errorCode)
                        }
                    })


        }
        if (disposable != null) {
            addSubscription(disposable)
        }
    }



}