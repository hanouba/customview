package com.hansen.aretrofitdemo.bean

/**
 *@author HanN on 2020/9/25 17:33
 *@email: 1356548475@qq.com
 *@project customview
 *@description:
 *@updateuser:
 *@updatedata: 2020/9/25 17:33
 *@updateremark:
 *@version:  2.1.67
 */
open class BaseResponse(var username: String ,var password: Int) {
    override fun toString(): String {
        return "BaseResponse(username='$username', password=$password)"
    }
}