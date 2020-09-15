package com.hansen.crypto;

import android.os.Build;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Member;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import androidx.annotation.RequiresApi;

/**
 * @author HanN on 2020/9/14 14:48
 * @email: 1356548475@qq.com
 * @project customview
 * @description: ras 非对称加密实现
 * @updateuser:
 * @updatedata: 2020/9/14 14:48
 * @updateremark:
 * @version: 2.1.67
 */
public class RSA {
    private static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";
    /**
     * 加密函数,传入明文,公钥,返回密文 base64
     * @param data  明文
     * @param pubKey  OpenSSL 获取到的公钥
     * @return   base64编码的密文
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encrypt(int data, String pubKey) {
        //由于dh参数均为int 类型, 设计成int 加密

        String msessage = String.valueOf(data);
        //将base64编码的公钥 解码
        byte[] decoded = DataUtils.base64Decode(pubKey);
        //密文
        byte[] result = new byte[]{0};

        try {
           // 指定的加密算法 RSA
            //获取公钥对象
            PublicKey publicKey = KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(decoded));
            //返回实现指定转换的 Cipher 对象
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            result = cipher.doFinal(msessage.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return DataUtils.base64Encode(result);

    }


    /**
     * 传入密文 密匙 返回明文
     * @param encrypt base64 编码的密文
     * @param key  base64编码的密匙  OpenSSL 生成的密匙
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decypt(String encrypt, String key){
        //将base64编码的密匙 解码
        byte[] decodeed = DataUtils.base64Decode(key);
        byte[] content = DataUtils.base64Decode(encrypt);
        //明文
        byte[] result = new byte[]{0};
        //开始解密
        try {
            PrivateKey privateKey = KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(decodeed));
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE,privateKey);
            result = cipher.doFinal(content);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        //明文就不要转成base64le
        return new String(result);
    }
}
