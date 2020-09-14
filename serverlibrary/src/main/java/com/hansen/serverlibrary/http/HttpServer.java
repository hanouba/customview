package com.hansen.serverlibrary.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author HanN on 2020/9/14 11:37
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/9/14 11:37
 * @updateremark:
 * @version: 2.1.67
 */
public class HttpServer  {
    //是否在运行
    private boolean mRunning = false;
    //请求回调
    private HttpCallBack mCallBack;
    //构造方法
    public HttpServer(HttpCallBack httpCallBack) {
        this.mCallBack = httpCallBack;
    }

    /**
     * 启动服务
     */
    public void startHttpServer() {
        if (mRunning) {
            //如果在运行 返回错误信息
            return;
        }
        mRunning = true;
        //SocketServer 三步法 一  启动socket
        try {
            ServerSocket serverSocket = new ServerSocket(80);
            while (mRunning) {

            Socket socket = serverSocket.accept();

                ExecutorService threadPool = Executors.newCachedThreadPool();
                threadPool.execute(new HttpThread(socket,mCallBack));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
