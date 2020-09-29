package com.hansen.customview;

import android.os.Environment;

import java.io.File;

import retrofit2.http.PUT;

/**
 * @author HanN on 2020/9/23 17:42
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/9/23 17:42
 * @updateremark:
 * @version: 2.1.67
 */
public class Constant {
    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "com.hen";
}
