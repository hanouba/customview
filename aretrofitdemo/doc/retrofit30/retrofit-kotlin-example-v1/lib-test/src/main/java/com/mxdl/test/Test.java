package com.mxdl.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

import sun.misc.ProxyGenerator;

/**
 * Description: <Test><br>
 * Author:      mxdl<br>
 * Date:        2020/8/30<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class Test {
    public static void main(String[] args) {
        byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
                "UserService", new Class[]{UserService.class});
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File("UserService.class"));
            fileOutputStream.write(proxyClassFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
