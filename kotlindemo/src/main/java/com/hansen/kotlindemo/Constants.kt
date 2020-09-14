package com.hansen.kotlindemo

/**
 *@author HanN on 2020/9/9 15:16
 *@email: 1356548475@qq.com
 *@project customview
 *@description: constructor 构造函数
 *@updateuser:
 *@updatedata: 2020/9/9 15:16
 *@updateremark:
 *@version:  2.1.67
 */
class Constants private constructor(){
    companion object {
        val BUNDLE_VIDEO_DATA = "video_data"
        val BUNDLE_CATEGORY_DATA = "category_data"
        //腾讯 Bugly APP id
        val BUGLY_APPID = ""

        //sp 存储的文件名
        val FILE_WATCH_HISTORY_NAME = "watch_history_file"   //观看记录

        val FILE_COLLECTION_NAME = "collection_file"    //收藏视屏缓存的文件名
    }
}