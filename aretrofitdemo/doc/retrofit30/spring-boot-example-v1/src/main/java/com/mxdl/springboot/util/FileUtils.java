package com.mxdl.springboot.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class FileUtils {
    public static void saveFile(InputStream inputStream) throws IOException {

        FileOutputStream fileOutputStream = null;
        InputStream fileInputStream = null;
        try {
            File myFile = new File("d:\\" + new Random().nextInt(100000) + ".jpg");
            fileOutputStream = new FileOutputStream(myFile);
            fileInputStream = inputStream;
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(buf)) != -1) {
                fileOutputStream.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }

    }
}
