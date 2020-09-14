package com.hansen.crypto;

import android.os.Build;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import androidx.annotation.RequiresApi;

/**
 * @author HanN on 2020/9/14 17:33
 * @email: 1356548475@qq.com
 * @project customview
 * @description: 对称加密
 * 根据AES密匙创建secret密匙向量  生成初始化参数向量 获取AES Cipher 执行加密 base64编码 (
 * @updateuser:
 * @updatedata: 2020/9/14 17:33
 * @updateremark:
 * @version: 2.1.67
 */
public class AES {
    private SecretKey mKey;
    public AES() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            //创建随机密码 并设置种子
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.setSeed(System.currentTimeMillis());
            //初始化密匙对象
            keyGenerator.init(128,secureRandom);
            //生成aes密匙并保持
            mKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 编码
     * @param content
     * @return
     */
    public byte[] encrypt(String content) {
        if (mKey == null) {
            return new byte[]{-1};
        }
        try {
            Cipher ci = Cipher.getInstance("AES");
            ci.init(Cipher.ENCRYPT_MODE,mKey);
            return ci.doFinal(content.getBytes());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return new byte[]{-1};


    }

    /**
     * 解码
     * @param content
     * @return
     */
    public byte[] decrypt(byte[] content) {
        if (mKey == null) {
            return new byte[]{-1};
        }

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,mKey);
          return   cipher.doFinal(content);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return new byte[]{-1};
    }

}
