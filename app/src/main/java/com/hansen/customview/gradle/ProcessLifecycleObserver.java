package com.hansen.customview.gradle;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * @author HanN on 2020/7/2 8:46
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/7/2 8:46
 * @updateremark:
 * @version: 2.1.67
 */
public class ProcessLifecycleObserver implements DefaultLifecycleObserver {
    private final static String TAG = "ProcessLifecycleObserver";
    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        Log.i(TAG, "onStart: ");
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        Log.i(TAG, "onResume: ");
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        Log.i(TAG, "onPause: ");
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        Log.i(TAG, "onStop: ");
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        Log.i(TAG, "onDestroy: ");
    }
}
