package com.hansen.aretrofitdemo.bean

/**
 *@author HanN on 2020/9/25 17:34
 *@email: 1356548475@qq.com
 *@project customview
 *@description:
 *@updateuser:
 *@updatedata: 2020/9/25 17:34
 *@updateremark:
 *@version:  2.1.67
 */
class AddUserResponse : BaseResponse {
    var user: User

    constructor(username: String, password: Int, user: User) : super(username, password) {
        this.user = user
    }
}