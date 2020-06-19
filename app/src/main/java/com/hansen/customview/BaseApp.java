package com.hansen.customview;

import android.app.Application;

import com.github.mikephil.charting.charts.BarChart;

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

    public static synchronized BaseApp getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }
}
