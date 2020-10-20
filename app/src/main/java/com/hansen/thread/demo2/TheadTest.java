package com.hansen.thread.demo2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author HanN on 2020/10/16 10:35
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * public ThreadPoolExecutor(int corePoolSize, 核心线程池大小
 *                           int maximumPoolSize, 最大线程池大小
 *                           long keepAliveTime,线程最大空闲时间
 *                           TimeUnit unit,时间单位
 *                           BlockingQueue<Runnable> workQueue, 线程等待队列
 *                           ThreadFactory threadFactory,线程创建工厂
 *                           RejectedExecutionHandler handler) {拒绝策略
 *     if (corePoolSize < 0 ||
 *         maximumPoolSize <= 0 ||
 *         maximumPoolSize < corePoolSize ||
 *         keepAliveTime < 0)
 *         throw new IllegalArgumentException();
 *     if (workQueue == null || threadFactory == null || handler == null)
 *         throw new NullPointerException();
 *     this.corePoolSize = corePoolSize;
 *     this.maximumPoolSize = maximumPoolSize;
 *     this.workQueue = workQueue;
 *     this.keepAliveTime = unit.toNanos(keepAliveTime);
 *     this.threadFactory = threadFactory;
 *     this.handler = handler;
 * }
 * @updateuser:
 * @updatedata: 2020/10/16 10:35
 * @updateremark:
 * @version: 2.1.67
 */
class TheadTest {
    //核心线程池大小
    private int corePoolSize = 2;
//    最大线程池大小
    private int maximumPoolSize = 4;
//    线程最大空闲时间
    private long keepAliveTime = 10;
    //秒
    TimeUnit unit = TimeUnit.SECONDS;
    //线程等待队列
    BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);


    static class  NameTreadFactory implements ThreadFactory {
        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "my-thread-" + mThreadNum.getAndIncrement());
            return null;
        }
    }



}
