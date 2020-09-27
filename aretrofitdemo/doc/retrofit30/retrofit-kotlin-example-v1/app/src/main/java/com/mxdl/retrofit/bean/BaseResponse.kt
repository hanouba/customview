package com.mxdl.retrofit.bean

/**
 * Description: <BaseResponse><br>
 * Author:      mxdl<br>
 * Date:        2020/8/9<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
open class BaseResponse(var msg:String,var code:Int) {
    override fun toString(): String {
        return "BaseResponse(msg='$msg', code=$code)"
    }
}