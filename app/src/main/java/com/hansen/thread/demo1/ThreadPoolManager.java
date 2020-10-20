package com.hansen.thread.demo1;


import com.hansen.utils.LogUtil;

import java.util.concurrent.TimeUnit;

/**

 */
public class ThreadPoolManager {

    private static final String TAG = ThreadPoolManager.class.getSimpleName();

    //存活时间,表示最大线程池中等待任务的存活时间
    private long keepAliveTime;

    //存活时间的时间单位
    private TimeUnit unit;
    /**
     * 获取CPU数量
     */
    private int processors;

    //核心线程池数量，表示能够同时执行的任务数量
    private int corePoolSize;

    //最大线程池数量，其实是包含了核心线程池数量在内的
    private int maximumPoolSize;


    //sip线程池对象
    private ThreadPollProxy sipThreadPool;

    //RTP线程池对象
    private ThreadPollProxy rtpThreadPool;

    //RTP线程池对象
    private ThreadPollProxy logThreadPool;

    //RTP线程池对象
    private ThreadPollProxy mqThreadPool;

    //临时事件线程池对象
    private ThreadPollProxy tempThreadPool;

    //线程管理单例对象
    private static ThreadPoolManager mInstance;

    /**
     * 获取线程管理对象
     *
     * @return
     */
    public static ThreadPoolManager getInstance() {
        if (mInstance == null) {
            synchronized (ThreadPoolManager.class) {
                if (mInstance == null) {
                    mInstance = new ThreadPoolManager();
                }
            }
        }
        return mInstance;
    }

    public ThreadPoolManager() {
        keepAliveTime = 1;//存活时间,表示最大线程池中等待任务的存活时间
        unit = TimeUnit.SECONDS;//存活时间的时间单位
        processors = Runtime.getRuntime().availableProcessors();

        //核心线程池数量的计算规则：当前设备的可用处理器核心数*2 + 1,能够让CPU得到最大效率的发挥；
        corePoolSize = processors * 2 + 1;

        //最大线程池数量，其实是包含了核心线程池数量在内的
        maximumPoolSize = processors * 4 + 1;//不能是0，否则报错
    }

    //------------------------SIP信令事件线程池------------------------

    /**
     * 执行与SIP信令有关的子线程任务
     *
     * @param r
     */
    public void executeSipThread(Runnable r) {
        if (sipThreadPool == null) {
            sipThreadPool = new ThreadPollProxy(corePoolSize, maximumPoolSize, keepAliveTime, unit, "Sip");
        }
        if (r != null) {
            LogUtil.i(TAG, "executeSipThread Runnable");
            sipThreadPool.execute(r);
        } else {
            LogUtil.i(TAG, "executeSipThread null");
        }
    }

    /**
     * 关闭与sip信令有关的子线程任务
     */
    public void removeSipThread(Runnable r) {
        if (sipThreadPool != null && r != null) {
            LogUtil.i(TAG, "removeSipThread");
            sipThreadPool.remove(r);
        }
    }

    /**
     * 关闭所有与sip信令有关的子线程任务
     */
    public void closeSipThreadPool() {
        if (sipThreadPool != null) {
            LogUtil.i(TAG, "closeSipThreadPool");
            sipThreadPool.stopAllThread();
            sipThreadPool = null;
        }
    }

    //------------------------流处理事件线程池------------------------

    /**
     * 执行与音视频流有关的子线程任务
     */
    public void executeRtpThread(Runnable r) {
        if (rtpThreadPool == null) {
            rtpThreadPool = new ThreadPollProxy(corePoolSize, maximumPoolSize, keepAliveTime, unit, "Rtp");
        }
        if (r != null) {
            rtpThreadPool.execute(r);
            LogUtil.i(TAG, "executeRtpThread Runnable");
        } else {
            LogUtil.i(TAG, "executeRtpThread null");
        }
    }

    /**
     * 关闭与音视频流有关的子线程任务
     */
    public void removeRtpThread(Runnable r) {
        if (rtpThreadPool != null && r != null) {
            LogUtil.i(TAG, "removeRtpThread");
            rtpThreadPool.remove(r);
        }
    }

    /**
     * 关闭所有与音视频流有关的子线程任务
     */
    public void closeRtpThreadPool() {
        if (rtpThreadPool != null) {
            LogUtil.i(TAG, "closeRtpThreadPool");
            rtpThreadPool.stopAllThread();
            rtpThreadPool = null;
        }
    }


    //------------------------日志记录处理事件线程池------------------------

    /**
     * 执行与音视频流有关的子线程任务
     */
    public void executeLogThread(Runnable r) {
        if (logThreadPool == null) {
            logThreadPool = new ThreadPollProxy(corePoolSize, maximumPoolSize, keepAliveTime, unit, "Log");
        }
        if (r != null) {
            logThreadPool.execute(r);
            LogUtil.i(TAG, "executeLogThread Runnable");
        } else {
            LogUtil.i(TAG, "executeLogThread null");
        }
    }

    /**
     * 关闭与音视频流有关的子线程任务
     */
    public void removeLogThread(Runnable r) {
        if (logThreadPool != null && r != null) {
            LogUtil.i(TAG, "removeLogThread");
            logThreadPool.remove(r);
        }
    }

    /**
     * 关闭所有与音视频流有关的子线程任务
     */
    public void closeLogThreadPool() {
        if (logThreadPool != null) {
            LogUtil.i(TAG, "closeLogThreadPool");
            logThreadPool.stopAllThread();
            logThreadPool = null;
        }
    }

    //------------------------临时事件线程池------------------------

    /**
     * 执行临时的子线程任务
     */
    public void executeMqThread(Runnable r) {
        if (mqThreadPool == null) {
            mqThreadPool = new ThreadPollProxy(corePoolSize, maximumPoolSize, keepAliveTime, unit, "mq");
        }
        if (r != null) {
            mqThreadPool.execute(r);
            LogUtil.i(TAG, "executeMqThread Runnable");
        } else {
            LogUtil.i(TAG, "executeMqThread null");
        }
    }

    /**
     * 关闭临时的子线程任务
     */
    public void removeMqThread(Runnable r) {
        if (mqThreadPool != null && r != null) {
            LogUtil.i(TAG, "removeMqThread");
            mqThreadPool.remove(r);
        }
    }

    /**
     * 关闭所有临时的子线程任务
     */
    public void closeMqThreadPool() {
        if (mqThreadPool != null) {
            LogUtil.i(TAG, "closeMqThreadPool");
            mqThreadPool.stopAllThread();
            mqThreadPool = null;
        }
    }

    //------------------------临时事件线程池------------------------

    /**
     * 执行临时的子线程任务
     */
    public void executeTempThread(Runnable r) {
        if (tempThreadPool == null) {
            tempThreadPool = new ThreadPollProxy(corePoolSize, maximumPoolSize, keepAliveTime, unit, "Temp");
        }
        if (r != null) {
            tempThreadPool.execute(r);
            LogUtil.i(TAG, "executeTempThread Runnable");
        } else {
            LogUtil.i(TAG, "executeTempThread null");
        }
    }

    /**
     * 关闭临时的子线程任务
     */
    public void removeTempThread(Runnable r) {
        if (tempThreadPool != null && r != null) {
            LogUtil.i(TAG, "removeTempThread");
            tempThreadPool.remove(r);
        }
    }

    /**
     * 关闭所有临时的子线程任务
     */
    public void closeTempThreadPool() {
        if (tempThreadPool != null) {
            LogUtil.i(TAG, "closeTempThreadPool");
            tempThreadPool.stopAllThread();
            tempThreadPool = null;
        }
    }
}
