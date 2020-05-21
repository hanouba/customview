package com.hansen.utils;

import android.util.Log;

/**
 * @author HanN on 2020/5/20 10:16
 * @email: 1356548475@qq.com
 * @project LogUtil
 * @description: 日志打印
 * @updateuser:
 * @updatedata: 2020/5/20 10:16
 * @updateremark:
 * @version: 2.1.67
 */
public class LogUtil {

    private static boolean isDebug = true;

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, "thread-" + Thread.currentThread().getName() + "_" + Thread.currentThread().getId() + "   message- " + msg);
        }
    }

    public static void d(Object object, String msg) {
        if (isDebug) {
            Log.d(object.getClass().getSimpleName(), "thread-" + Thread.currentThread().getName() + "_" + Thread.currentThread().getId() + "   message- " + msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, "thread-" + Thread.currentThread().getName() + "_" + Thread.currentThread().getId() + "   message- " + msg);
        }
    }

    public static void e(Object object, String msg) {
        if (isDebug) {
            Log.e(object.getClass().getSimpleName(), "thread-" + Thread.currentThread().getName() + "_" + Thread.currentThread().getId() + "   message- " + msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, "thread-" + Thread.currentThread().getName() + "_" + Thread.currentThread().getId() + "   message- " + msg);
        }
    }

    public static void i(Object object, String msg) {
        if (isDebug) {
            Log.i(object.getClass().getSimpleName(), "thread-" + Thread.currentThread().getName() + "_" + Thread.currentThread().getId() + "   message- " + msg);
        }
    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            Log.v(tag, "thread-" + Thread.currentThread().getName() + "_" + Thread.currentThread().getId() + "   message- " + msg);
        }
    }

    public static void v(Object object, String msg) {
        if (isDebug) {
            Log.v(object.getClass().getSimpleName(), "thread-" + Thread.currentThread().getName() + "_" + Thread.currentThread().getId() + "   message- " + msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            Log.w(tag, "thread-" + Thread.currentThread().getName() + "_" + Thread.currentThread().getId() + "   message- " + msg);
        }
    }

    public static void w(Object object, String msg) {
        if (isDebug) {
            Log.w(object.getClass().getSimpleName(), "thread-" + Thread.currentThread().getName() + "_" + Thread.currentThread().getId() + "   message- " + msg);
        }
    }
}
