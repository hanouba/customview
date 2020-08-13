package com.hansen.kotlindemo.base

/**
 *@author HanN on 2020/8/13 15:13
 *@email: 1356548475@qq.com
 *@project customview
 *@description:
 *@updateuser:
 *@updatedata: 2020/8/13 15:13
 *@updateremark:
 *@version:  2.1.67
 */

//i in a..b 表示 i 是否在 a 到 b 区间
//in 可以检查字符区间，对象区间（实例对象的类必须实现Comparable），集合
interface IBasePresent<in V:IBaseView> {
    fun attachView(mRootView: V)
    fun detachView()
}