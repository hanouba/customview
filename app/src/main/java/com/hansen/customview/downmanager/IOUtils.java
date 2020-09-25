package com.hansen.customview.downmanager;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author HanN on 2020/9/25 13:24
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/9/25 13:24
 * @updateremark:
 * @version: 2.1.67
 */
public class IOUtils {
    public static void closeAll(Closeable... closeables){
        if(closeables == null){
            return;
        }
        for (Closeable closeable : closeables) {
            if(closeable!=null){
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
