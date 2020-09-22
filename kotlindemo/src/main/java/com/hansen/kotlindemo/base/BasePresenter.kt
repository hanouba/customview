package com.hansen.kotlindemo.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

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
    /**
     * 成员变量
     */
    var mRootView:T? = null
    private set

    /**
     * 复合容器
     */
    private var compositeDisposable = CompositeDisposable()

    /**
     * 绑定界面
     */
    override fun attachView(mRootView: T) {
        this.mRootView = mRootView
    }

    /**
     * 分离视图
     */
    override fun detachView() {
        mRootView = null
        /*
            保证activity 结束时取消所有正在执行的订阅
         */
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    /**
     * 定义一个变量界面是否绑定 由mRootView是否为空决定
     */
    private val isViewAttach: Boolean
    get() = mRootView != null

    /**
     * 检查界面是否绑定
     */
    fun checkViewAttached() {
        if (!isViewAttach) throw MvpViewNotAttachedException()
    }

    /**
     * 定义一个内部类 抛出异常提示用户绑定界面
     */
    private class MvpViewNotAttachedException internal constructor() : RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")

    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}