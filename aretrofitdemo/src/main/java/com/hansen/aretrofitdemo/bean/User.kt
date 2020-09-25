package com.hansen.aretrofitdemo.bean

import androidx.annotation.IntegerRes

/**
 *@author HanN on 2020/9/25 17:31
 *@email: 1356548475@qq.com
 *@project customview
 *@description:
 *@updateuser:
 *@updatedata: 2020/9/25 17:31
 *@updateremark:
 *@version:  2.1.67
 */
class User(var username : String ,var password: Int) {
    override fun toString(): String {
        return "User(username='$username', password=$password)"
    }
}