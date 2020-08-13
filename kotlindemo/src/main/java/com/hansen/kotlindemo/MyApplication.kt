package com.hansen.kotlindemo

import android.app.Application
import android.content.Context
import android.nfc.Tag
import com.squareup.leakcanary.RefWatcher
import kotlin.properties.Delegates

/**
 *@author HanN on 2020/8/13 16:59
 *@email: 1356548475@qq.com
 *@project customview
 *@description:
 *@updateuser:
 *@updatedata: 2020/8/13 16:59
 *@updateremark:
 *@version:  2.1.67
 */
class MyApplication : Application() {
    /**
     * 内存检查工具声明变量
     */
    private var refWatcher: RefWatcher? = null

    /**
     * object  一种是对象表达式,另一种是 对象声明
     * 用object 修饰的类为静态类，里面的方法和变量都为静态的。
     * companion object 修饰为伴生对象,伴生对象在类中只能存在一个
     * companion object 中调用非静态的成员变量也是调用不到的
     */
    companion object {
        private val TAG = "MyApplication"
        //不为空
        var context: Context by Delegates.notNull()
        private set


        /**
         *  as  “不安全的” 类型转换操作符
         */
        fun  getRefWatcher(context: Context): RefWatcher? {
            val myApplication = context.applicationContext as MyApplication
            return myApplication.refWatcher
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}