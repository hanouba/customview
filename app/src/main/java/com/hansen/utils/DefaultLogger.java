package com.hansen.utils;

import android.text.TextUtils;
import android.util.Log;

import com.hansen.template.ILogger;

/**
 * @author HanN on 2020/10/15 11:46
 * @email: 1356548475@qq.com
 * @project customview
 * @description: 日志打印
 * @updateuser: 日志打印实现
 * @updatedata: 2020/10/15 11:46
 * @updateremark:
 * @version: 2.1.67
 */
public class DefaultLogger implements ILogger {
    private static boolean isShowLog = false;
    private static boolean isShowStackTrace = false;
    private static boolean isMonitorMode = false;

    private String defaultTag = "HansenTAG";
    @Override
    public void showLog(boolean showlog) {
        isShowLog = showlog;
    }

    public DefaultLogger(String defaultTag) {
        this.defaultTag = defaultTag;
    }

    @Override
    public void showStackTrace(boolean showStackTrace) {
        isShowStackTrace = showStackTrace;
    }

    @Override
    public void debug(String tag, String message) {
        if (isShowLog) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.d(TextUtils.isEmpty(tag) ? getDefaultTag() : tag, message + getExtInfo(stackTraceElement));
        }
    }

    @Override
    public void info(String tag, String message) {
        if (isShowLog) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.i(TextUtils.isEmpty(tag) ? getDefaultTag() : tag, message + getExtInfo(stackTraceElement));
        }
    }

    @Override
    public void warning(String tag, String message) {
        if (isShowLog) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.w(TextUtils.isEmpty(tag) ? getDefaultTag() : tag, message + getExtInfo(stackTraceElement));
        }
    }

    @Override
    public void error(String tag, String message) {
        if (isShowLog) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.e(TextUtils.isEmpty(tag) ? getDefaultTag() : tag, message + getExtInfo(stackTraceElement));
        }
    }

    @Override
    public void monitor(String message) {
        if (isShowLog && isMonitorMode()) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.d(defaultTag + "::monitor", message + getExtInfo(stackTraceElement));
        }
    }
    public void showMonitor(boolean showMonitor) {
        isMonitorMode = showMonitor;
    }
    @Override
    public boolean isMonitorMode() {
        return  isMonitorMode;
    }

    @Override
    public String getDefaultTag() {
        return defaultTag;
    }

    public static String getExtInfo(StackTraceElement stackTraceElement) {

        String separator = " & ";
        StringBuilder sb = new StringBuilder("[");

        if (isShowStackTrace) {
            String threadName = Thread.currentThread().getName();
            String fileName = stackTraceElement.getFileName();
            String className = stackTraceElement.getClassName();
            String methodName = stackTraceElement.getMethodName();
            long threadID = Thread.currentThread().getId();
            int lineNumber = stackTraceElement.getLineNumber();

            sb.append("ThreadId=").append(threadID).append(separator);
            sb.append("ThreadName=").append(threadName).append(separator);
            sb.append("FileName=").append(fileName).append(separator);
            sb.append("ClassName=").append(className).append(separator);
            sb.append("MethodName=").append(methodName).append(separator);
            sb.append("LineNumber=").append(lineNumber);
        }

        sb.append(" ] ");
        return sb.toString();
    }
}