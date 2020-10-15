package com.hansen.customview;

import android.app.Application;

import com.github.mikephil.charting.charts.BarChart;
import com.hansen.customview.gradle.ProcessLifecycleObserver;
import com.hansen.launcher.AHansen;

import androidx.lifecycle.ProcessLifecycleOwner;

/**
 * @author HanN on 2020/6/18 11:07
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/6/18 11:07
 * @updateremark:
 * @version: 2.1.67
 */
public class BaseApp extends Application {
    private static BaseApp instance;
    private ProcessLifecycleObserver observer = new ProcessLifecycleObserver();
    public static synchronized BaseApp getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ProcessLifecycleOwner.get().getLifecycle().addObserver(observer);

        AHansen.init(this);
        AHansen.setShowLog(true);

    }
}
