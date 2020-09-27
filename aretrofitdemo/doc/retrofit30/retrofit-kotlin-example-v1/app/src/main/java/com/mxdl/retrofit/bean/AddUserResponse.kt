package com.mxdl.retrofit.bean

/**
 * Description: <AddUserResponse><br>
 * Author:      mxdl<br>
 * Date:        2020/8/9<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class AddUserResponse :BaseResponse {
    var user :User

    constructor(msg: String, code: Int, user: User) : super(msg, code) {
        this.user = user
    }
}