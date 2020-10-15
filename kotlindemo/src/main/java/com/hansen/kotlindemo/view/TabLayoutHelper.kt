package com.hansen.kotlindemo.view

import android.annotation.SuppressLint
import android.os.Build
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayout
import com.hansen.kotlindemo.utils.DisplayManager
import java.lang.reflect.Field

/**
 * Created by xuhao on 2017/12/7.
 * desc:
 */
object TabLayoutHelper{

    @SuppressLint("ObsoleteSdkInt")
    fun setUpIndicatorWidth(tabLayout: TabLayout) {
        val tabLayoutClass = tabLayout.javaClass
        var tabStrip: Field? = null
        try {
//            *通过反射修改TabLayout Indicator的宽度（仅在Android 4.2及以上生效） androix 使用slidingTabIndicator 替换TabStrip
            tabStrip = tabLayoutClass.getDeclaredField("slidingTabIndicator")
            tabStrip!!.isAccessible = true
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }

        var layout: LinearLayout? = null
        try {
            if (tabStrip != null) {
                layout = tabStrip.get(tabLayout) as LinearLayout
            }
            for (i in 0 until layout!!.childCount) {
                val child = layout.getChildAt(i)
                child.setPadding(0, 0, 0, 0)
                val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.marginStart = DisplayManager.dip2px(50f)!!
                    params.marginEnd = DisplayManager.dip2px(50f)!!
                }
                child.layoutParams = params
                child.invalidate()
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }
}