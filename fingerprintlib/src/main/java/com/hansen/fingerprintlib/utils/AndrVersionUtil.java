package com.hansen.fingerprintlib.utils;

import android.os.Build;

/**
 * @author HanN on 2020/9/18 14:50
 * @email: 1356548475@qq.com
 * @project customview
 * @description: 系统判断
 * @updateuser:
 * @updatedata: 2020/9/18 14:50
 * @updateremark:
 * @version: 2.1.67
 */
public class AndrVersionUtil {

    public static boolean isAboveAndroidP() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    public static boolean isAboveAndroidM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
