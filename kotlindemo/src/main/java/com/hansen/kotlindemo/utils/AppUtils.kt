package com.hansen.kotlindemo.utils

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Debug
import java.lang.Exception
import java.security.MessageDigest

/**
 *@author HanN on 2020/9/22 9:26
 *@email: 1356548475@qq.com
 *@project customview
 *@description:
 *@updateuser:
 *@updatedata: 2020/9/22 9:26
 *@updateremark:
 *@version:  2.1.67
 */
class AppUtils
private constructor() {
    init {
        throw error("Do not need instantiate!")

    }

    companion object {
        private val DEBUG = true
        private val TAG = "AppUtils"

        /**
         * 获取versioncode
         */
        fun getVerCode(context: Context): Int {
            var verCode = -1
            try {
                //获取包名 查询版本
                val packageName = context.packageName
                verCode = context.packageManager.getPackageInfo(packageName, 0).versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return verCode
        }

        /**
         * 获取运行的最大内存
         */
        val maxMemory: Long
            get() = Runtime.getRuntime().maxMemory() / 1024

        /**
         * 获取版本名称
         */
        fun getVersionName(context: Context): String {
            var verName = ""
            try {
                val packageName = context.packageName
                verName = context.packageManager.getPackageInfo(packageName, 0).versionName

            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return verName
        }

        /**
         * 获取应用签名
         */
        fun getSign(context: Context, pkgName: String): String? {
            return try {
                val pis = context.packageManager.getPackageInfo(pkgName, PackageManager.GET_SIGNATURES)
                hexDigext(pis.signatures[0].toByteArray())
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                null
            }
        }

        /**
         * 将签名字符串转换成需要ad32位签名
         */
        private fun hexDigext(paramArrayOfByte: ByteArray): String {
            System.out.println("签名前的字符串" + paramArrayOfByte)
            val hexDigits = charArrayOf(48.toChar(), 49.toChar(), 50.toChar(),
                    51.toChar(), 52.toChar(), 53.toChar(), 54.toChar(), 55.toChar(), 56.toChar(), 57.toChar(), 97.toChar(), 98.toChar(), 99.toChar(), 100.toChar(), 101.toChar(), 102.toChar())

            try {
                val localMessageDigest = MessageDigest.getInstance("MD5")
                localMessageDigest.update(paramArrayOfByte)
                val arrayOfByte = localMessageDigest.digest()
                val arrayOfChar = CharArray(32)
                var i = 0
                var j = 0
                while (true) {
                    if (i >= 16) {
                        return String(arrayOfChar)
                        System.out.println("签名后的字符串" + String(arrayOfChar))
                    }

                    val k = arrayOfByte[i].toInt()

                    arrayOfChar[j] = hexDigits[0xF and k.ushr(4)]
                    arrayOfChar[++j] = hexDigits[k and 0xF]
                    i++
                    j++
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }


            return ""
        }

        /**
         * 获取应用可用内存大小
         */
        fun  getDeviceUsabelMemory(context: Context): Int {
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val mi = ActivityManager.MemoryInfo()
            am.getMemoryInfo(mi)
            //
            return ((mi.availMem) / (1024 * 1024)).toInt()
        }

        /**
         * 获取手机版本
         */
        fun getMobileModel(): String {
            var model: String? = Build.MODEL
            model = model?.trim(){it  <= ' '} ?:""
            return model
        }

        val sdkVersion: Int
        get() = Build.VERSION.SDK_INT
    }


}