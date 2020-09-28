package com.hansen.aretrofitdemo.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description: <FileUtils><br>
 * Author:      mxdl<br>
 * Date:        2020/8/12<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class FileUtils {
   public static boolean saveFile(File file, InputStream inputStream) {
        FileOutputStream fileOutputStream = null;
        boolean succ = false;
        try {
            fileOutputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buf)) != -1) {
                fileOutputStream.write(buf, 0, len);
            }
            succ = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return succ;
    }
}
