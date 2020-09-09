package com.hansen.kotlindemo

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 *@author HanN on 2020/9/9 14:41
 *@email: 1356548475@qq.com
 *@project customview
 *@description: 工具类吧
 *@updateuser:
 *@updatedata: 2020/9/9 14:41
 *@updateremark:
 *@version:  2.1.67
 */
fun Fragment.showToast(content: String) :Toast {
    val toast = Toast.makeText(this.activity?.applicationContext,content,Toast.LENGTH_SHORT)
    toast.show()
    return toast
}
fun Context.showToast(content: String) :Toast {
    val toast = Toast.makeText(MyApplication.context,content,Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

fun View.dip2px(dipValue: Float) : Int {
    val scale = this.resources.displayMetrics.density
    return (dipValue * scale + 0.5f).toInt()
}

fun View.px2dip(pxValue: Float) : Int {
    val scale = this.resources.displayMetrics.density
    
    return (pxValue / scale + 0.5f).toInt()
}

/**
 * 返回 09' 31'' 或者  11'09''
 */
fun durationFormat(duration: Long?): String {
    val minute = duration!! / 60
    val second = duration % 60
    return if (minute <= 9) {
        if (second <= 9) {
            "0$minute' 0$second''"
        }else {
            "0$minute' $second''"
        }
    }else{
        if (second <= 9) {
            "$minute' 0$second''"
        } else {
            "$minute' $second''"
        }
    }
}

fun Context.dataFromat(total: Long) : String {
    var result: String
    var speedReal: Int = (total / (1024)).toInt()
    result = if (speedReal < 512) {
        speedReal.toString() + "KB"
    }else {
        val mSpeed = speedReal / 1024.0
        //一个数字四舍五入后最接近的整数
        (Math.round(mSpeed * 100) / 100.0).toString() + "MB"
    }
    return result
}
