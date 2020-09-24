package com.hansen.kotlindemo

import android.app.Activity
import android.app.Application
import android.content.Context
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import com.blankj.utilcode.util.Utils.init
import com.hansen.kotlindemo.utils.DisplayManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
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
        initConfig()

        DisplayManager.init(this)
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
    }

    /**
     * 初始化配置
     */
    private fun initConfig() {

        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // 隐藏线程信息 默认：显示
                .methodCount(0)         // 决定打印多少行（每一行代表一个方法）默认：2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("hao_zz")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    private val mActivityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            Log.d(TAG, "onCreated: " + activity.componentName.className)
        }

        override fun onActivityStarted(activity: Activity) {
            Log.d(TAG, "onStart: " + activity.componentName.className)
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            Log.d(TAG, "onDestroy: " + activity.componentName.className)
        }
    }

}