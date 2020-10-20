package com.hansen.launcher;

import android.app.Application;
import android.content.Context;

import com.hansen.bluetooth.BlueToothUtils;
import com.hansen.template.ILogger;
import com.hansen.utils.Consts;
import com.hansen.utils.DefaultLogger;

import io.reactivex.internal.util.AppendOnlyLinkedArrayList;

/**
 * @author HanN on 2020/10/15 13:21
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/10/15 13:21
 * @updateremark:
 * @version: 2.1.67
 */
public final class AHansen {

    private volatile static AHansen instance = null;
    public static ILogger logger;
    public static BlueToothUtils blueToothUtils ;
    private volatile static boolean hasInit = false;
    private static Context context;
    public static AHansen getInstance() {
        if (!hasInit) {
        throw  new RuntimeException("AHansen  not init");
        } else {
            if (instance == null) {
                synchronized (AHansen.class) {
                    if (instance == null) {
                        instance = new AHansen();
                    }
                }
            }
            return instance;
        }

    }

    public static void init(Application application) {
        logger = new DefaultLogger(Consts.TAG);
        blueToothUtils = new BlueToothUtils();
        hasInit = true;
        context = application;
    }

    public static synchronized void setShowLog(boolean isShow) {
        if (!hasInit) {
            throw  new RuntimeException("AHansen  not init");
        }else {
            logger.showLog(isShow);
        }

    }





}
