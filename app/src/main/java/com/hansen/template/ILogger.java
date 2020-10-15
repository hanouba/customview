package com.hansen.template;


import com.hansen.utils.Consts;

/**
 * Logger
 *
 * @author 正纬 <a href="mailto:zhilong.liu@aliyun.com">Contact me.</a>
 * @version 1.0
 * @since 16/5/16 下午5:39
 */
public interface ILogger {

    boolean isShowLog = false;
    boolean isShowStackTrace = false;
    String defaultTag = Consts.TAG;
    //是否显示log
    void showLog(boolean isShowLog);
    //是否显示 堆栈踪迹
    void showStackTrace(boolean isShowStackTrace);

    void debug(String tag, String message);

    void info(String tag, String message);

    void warning(String tag, String message);

    void error(String tag, String message);
    //监视器信息
    void monitor(String message);
    //是否监视模式
    boolean isMonitorMode();
    //获取默认tag
    String getDefaultTag();
}
