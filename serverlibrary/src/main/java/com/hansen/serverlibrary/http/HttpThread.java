package com.hansen.serverlibrary.http;

import android.content.pm.PackageInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author HanN on 2020/9/14 11:41
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/9/14 11:41
 * @updateremark:
 * @version: 2.1.67
 */
public class HttpThread implements Runnable{
    private static final String TAG = PackageInfo.class.getSimpleName();
    private Socket mSocket;
    //回调监听器  不做业务处理
    private HttpCallBack mHttpCallBack;
    public HttpThread(Socket socket,HttpCallBack httpCallBack) {
        this.mSocket = socket;
        this.mHttpCallBack = httpCallBack;
    }

    @Override
    public void run() {
        //任务 读取客户端请求 根据业务数据采取相应操作, 返回信息
        try {
            //提升IO效率 便于逐行读取
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            String content;
            StringBuilder request = new StringBuilder();
            //客户端内容逐行读取
            while ((content = bufferedReader.readLine()) != null && !content.trim().isEmpty()) {
                 request.append(content).append("\n");

            }
            System.out.println("run: request:\n" + request);
            //返回的数据
            byte[] response = new byte[0];
            if (mHttpCallBack != null) {
                response =   mHttpCallBack.onResponse(request.toString());
            }
            //将业务方的数据包装成http格式
            //1 拼接请求行
            String responseFirstLine = "HTTP/1.1 200 OK \r\n";
            //2 拼接请求头
            String responseHeader = "Content-type:" + "text/html" + "\r\n";
            //
            OutputStream outputStream = mSocket.getOutputStream();
            //发送请求行
            outputStream.write(responseFirstLine.getBytes());
            //发送请求头
            outputStream.write(responseHeader.getBytes());
            //换行
            outputStream.write("\r\n".getBytes());
            outputStream.write(response);
            mSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
