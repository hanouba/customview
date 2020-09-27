package com.mxdl.retrofit.bean

/**
 * Description: <User><br>
 * Author:      mxdl<br>
 * Date:        2020/8/9<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class User(var username:String,var password:String) {
    override fun toString(): String {
        return "User(username='$username', password=$password)"
    }
}