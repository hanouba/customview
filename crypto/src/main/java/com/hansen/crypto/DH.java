package com.hansen.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author HanN on 2020/9/14 17:51
 * @email: 1356548475@qq.com
 * @project customview
 * @description: DH密匙交换
 *  公钥计算公式  私钥计算公式 生成随机数作为私钥 预置质数原根对
 * @updateuser:
 * @updatedata: 2020/9/14 17:51
 * @updateremark:
 * @version: 2.1.67
 */
public class DH {
    private static final int dhP = 23;
    private static final int dhG = 5;
    private int mPriKey;
    //在构造器中生成私钥
    public DH () {
        Random random = new Random();
        mPriKey =  random.nextInt(10);
        System.out.println("dh prikey is " + mPriKey);
    }

    /**
     * 获取公钥
     * 用公式计算出自己的公钥 用来交换
     * @return
     */
    public int getPublicKey() {

        return (int) (Math.pow(dhG,mPriKey) % dhP);
    }

    /**
     * 接收对方的公钥 与自己的私钥通过秘钥公式生成秘钥
     * 因为需要作为AES的密钥,所有要转换成byte[]
     * @param publicKey
     * @return
     */
    public byte[] getSecretKey(int publicKey) {
        int buf = (int) (Math.pow(publicKey,mPriKey) % dhP);
        return sha256(buf);
    }

    /**
     * 将双方相同的密钥 int  做一个hash转换
     * 转换成byte[256 ]类型 作为AES密钥
     * @param secretKey
     * @return
     */
    private byte[] sha256(int secretKey) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(DataUtils.int2byte(secretKey));
           return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new byte[]{-1};
    }
}
