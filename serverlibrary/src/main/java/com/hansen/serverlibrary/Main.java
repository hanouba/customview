package com.hansen.serverlibrary;

import android.os.Build;
import android.util.Log;

import com.hansen.crypto.AES;
import com.hansen.crypto.RSA;
import com.hansen.serverlibrary.http.HttpCallBack;
import com.hansen.serverlibrary.http.HttpServer;

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
    private static final String PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQChkESFLhsDzQKJPaIQ3WE+9PZt\n" +
            "3o1Bzpkk7uNRLC2rOyeuEAdO+JDDNkxM0MdX/Jfn1Xf9INeDhXikrCmSaaF9/fMf\n" +
            "ihBhfDQ2HKj8T8kVfyV5aNOdXvxwuhKTcyWlfEHl++sDKmCO4H6UhDvawIqxf3GH\n" +
            "wLogOiTIge+CMf1+iwIDAQAB";
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args){
        System.out.println("main: start http server ");

//        int content = 123456;
//        //将明文用公钥加密得到密文
//        String encrypt = RSA.encrypt(content, PUB_KEY);
//        System.out.println("密文--"+encrypt);
//        String decypt = RSA.decypt(encrypt, PRI_KEY);
//        System.out.println("明文--"+decypt);
        //AES 测试
        AES aes = new AES();
        String content = "this is aes";
        byte[] encrypt = aes.encrypt(content);
        System.out.println("密文--"+new String(encrypt));
        byte[] decrypt = aes.decrypt(encrypt);
        System.out.println("明文--"+new String(decrypt));

        HttpServer httpServer = new HttpServer(new HttpCallBack() {
            @Override
            public byte[] onResponse(String request) {
                return "imock".getBytes();
            }
        });
        httpServer.startHttpServer();

    }
}
