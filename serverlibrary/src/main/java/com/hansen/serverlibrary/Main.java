package com.hansen.serverlibrary;

import android.os.Build;
import android.util.Log;
import android.widget.LinearLayout;

import com.hansen.crypto.AES;
import com.hansen.crypto.DH;
import com.hansen.crypto.DataUtils;
import com.hansen.crypto.RSA;
import com.hansen.serverlibrary.http.HttpCallBack;
import com.hansen.serverlibrary.http.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;

/**
 * @author HanN on 2020/9/14 11:35
 * @email: 1356548475@qq.com
 * @project customview
 * @description: 启动服务的入口
 * @updateuser:
 * @updatedata: 2020/9/14 11:35
 * @updateremark:
 * @version: 2.1.67
 */
public class Main {

    private static final String PRI_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKGQRIUuGwPNAok9\n" +
            "ohDdYT709m3ejUHOmSTu41EsLas7J64QB074kMM2TEzQx1f8l+fVd/0g14OFeKSs\n" +
            "KZJpoX398x+KEGF8NDYcqPxPyRV/JXlo051e/HC6EpNzJaV8QeX76wMqYI7gfpSE\n" +
            "O9rAirF/cYfAuiA6JMiB74Ix/X6LAgMBAAECgYEAhGywjQzmXa4q9vyKmxIphCXa\n" +
            "2uFAnQDO68PWlJEGecRJ2NUQeH0qgqc90PSLgCXtCLwi1lqV6xp6cVcf1/82urV7\n" +
            "/e12c8e2kWVaPIYIBTOWJnPQP8IuOq7CG1uCkggvvnqmRaY/yqIRU/pYTRut1VDr\n" +
            "j0JKHyRoVKGD/J+85xECQQDXZ+T5ecFQSuhIu+z7Nxo2Ujf1pUN0RzvVAVafNUfU\n" +
            "MyjBS2IIgwpRydJaQRPv6+0hj3ZYS0oJe4hro/VbyRIpAkEAwALKAr1iU3cS89py\n" +
            "EejZloTedOXshcPnnS6pHI8r1jqZg0mhccA7qC3UzePsP+2011j1HVZ2dp/WfmxP\n" +
            "G5OpkwJAAKx9hd5AoYyqb1fPJPYGKfdV33nV3S4/3Km1aKgVAm/qh7hBpT8rsBfO\n" +
            "haPAeTamtj0ppJsPrznoslV/9fqKCQJAVHdlonrpbEovfjKUh6hlP7/HlKkouVeK\n" +
            "5iyGnFenMyCoM4cKjF3CYKN4v/IKFb1eFnW9rmBA0MJTu12uFzWIHQJAPFvTR+RD\n" +
            "gWC3eueMwlztBJwYZ6eAOi0SAih7kvHqhQ9+LJYIa74cHhGejloWHp4fgUJmjoUo\n" +
            "6pOLdJ9rtkEaQw==";
    private static final String HAND_SHAKE = "handshake";
    private static DH dhS;
    private static AES mAes;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args){
        System.out.println("main: start http server ");
        dhS = new DH();
        mAes = new AES();




        final HttpServer httpServer = new HttpServer(new HttpCallBack() {
            @Override
            public byte[] onResponse(String request) {
                //拿到请求结果 选判断是否是握手请求
                if (isHandShake(request)) {
                    //握手请求
                    Map<String, String> header = HttpServer.getHeader(request);
                    String handShake = header.get(HAND_SHAKE);
                    System.out.println("handShake--" + handShake);
                    String decypt = RSA.decypt(handShake, PRI_KEY);
                    System.out.println("handShake--decypt--" + decypt);
                    //转成int类型
                    //拿到了客户端的  公钥
                    int clientDhPubKey = Integer.parseInt(decypt);

                    //服务端通过客户端的公钥得到服务端的私钥  作为aes的密钥
                    mAes.setmKey(dhS.getSecretKey(clientDhPubKey));
                    //转成byte 将服务端公钥返回去
                    System.out.println("getPublicKey--" + dhS.getPublicKey());
                    return DataUtils.int2byte(dhS.getPublicKey());
                }else {
                    // 正常请求 通过aes加密
                    byte[] result = mAes.encrypt("imock");
                    System.out.println("httpServer--" + new String(result));
                    System.out.println("httpServer解码--" + new String(mAes.decrypt(result)));
                    return result;
                }

            }
        });
        httpServer.startHttpServer();

    }

    /**
     * 根据请求header中是否包含handshake字段来判断
     * @param request
     * @return
     */
    private static boolean isHandShake(String request) {
         return  (request != null && request.contains(HAND_SHAKE));
    }


}
