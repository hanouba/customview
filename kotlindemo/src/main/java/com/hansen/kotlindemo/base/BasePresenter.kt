package com.hansen.kotlindemo.base

/**
 *@author HanN on 2020/8/13 15:16
 *@email: 1356548475@qq.com
 *@project customview
 *@description:
 *@updateuser:
 *@updatedata: 2020/8/13 15:16
 *@updateremark:
 *@version:  2.1.67
 */
//知识点
/**而在kotlin的世界里面则不是这样
 * ，在kotlin中它所有的类默认都是final的，那么就意味着不能被继承，
 * 而且在类中所有的方法也是默认是final的，那么就是kotlin的方法默认也不能被重写
 * 所有就用了open
 *
 */
open class BasePresenter<T:IBaseView> : IBasePresent<T> {
    var mRootView:T? = null
    private set

    private var compositeDisposable = CompositeDisposable()

    override fun attachView(mRootView: T) {
    }

    override fun detachView() {
    }
}