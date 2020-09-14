package com.hansen.crypto;

import android.os.Build;
import android.util.Base64;

import java.nio.ByteBuffer;

import androidx.annotation.RequiresApi;

/**
 * @author HanN on 2020/9/14 14:51
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/9/14 14:51
 * @updateremark:
 * @version: 2.1.67
 */
public class DataUtils {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String base64Encode(byte[] data) {
        //
        try {
        return Base64.encodeToString(data,Base64.NO_WRAP);

        }catch (Exception e) {
            return java.util.Base64.getEncoder().encodeToString(data);
        }
    }

    /**
     * 解码字符串
     * 传入 base64编码后的字符串,返回解码后的byte[]
     * @param data
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static byte[] base64Decode(String data) {
        try {
           return Base64.decode(data,Base64.NO_WRAP);
        }catch (Exception e) {
            return java.util.Base64.getMimeDecoder().decode(data);
        }
    }

    /**
     * 将int转成byte
     * 一个int 4个byte
     * @param data
     * @return
     */
    public static byte[] int2byte(int data) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.putInt(data);
        return byteBuffer.array();
    }
}
