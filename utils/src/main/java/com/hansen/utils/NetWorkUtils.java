package com.hansen.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.hansen.bean.IP_MAC;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.core.app.ActivityCompat;

/**
 * @author HanN on 2020/10/10 14:34
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/10/10 14:34
 * @updateremark:
 * @version: 2.1.67
 */
public class NetWorkUtils {
    private static final String TAG = "NetWorkUtils_tag";
    public static NetWorkUtils instance =null;
    public  static NetWorkUtils getInstance() {
        if (instance == null) {
            synchronized (NetWorkUtils.class){
                if (instance == null) {
                    instance = new NetWorkUtils();
                }
            }
        }
        return instance;

    }

    //扫描出来的所有设备
    public ArrayList<IP_MAC> mDeviceList = new ArrayList<>();
    /**
     * 扫描局域网设备
     * @param context
     */
    public ArrayList<IP_MAC> readWifiDevices(Context context) {
        String line;
        String ip;
        String mac;

        String regExp = "((([0-9,A-F,a-f]{1,2}" + ":" + "){1,5})[0-9,A-F,a-f]{1,2})";
        Pattern pattern;
        Matcher matcher;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(
                    "/proc/net/arp"));
            if (bufferedReader != null) {
                bufferedReader.readLine(); //忽略标题行
                while ((line = bufferedReader.readLine()) != null) {
                    ip = line.substring(0, line.indexOf(" "));
                    pattern = Pattern.compile(regExp);
                    matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        mac = matcher.group(1);
                        if (!mac.equals("00:00:00:00:00:00")) {
                            IP_MAC ip_mac = new IP_MAC(ip, mac.toUpperCase(Locale.US));
                            if (mDeviceList != null && !mDeviceList.contains(ip_mac)) {
                                mDeviceList.add(ip_mac);
                                LogUtils.d(TAG, "ip_mac = " + ip_mac.toString());
                            }
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mDeviceList;
    }

    /**
     * 获取设备编码
     *
     * @param mContext
     * @return 设备标识
     */
    public static String getDevicesNumber(Context mContext) {
        String deviceType = android.os.Build.MANUFACTURER;
        String deviceNum;
        if (deviceType.equals("UniStrong")) {
            deviceNum = initSn();
        } else {
            deviceNum = getIMEI(mContext);
        }
        return deviceNum;
    }

    /**
     * 获取手机IMEI
     *
     * @param mContext
     * @return
     */
    public static final String getIMEI(Context mContext) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            String imei = telephonyManager.getDeviceId();
            //在次做个验证，也不是什么时候都能获取到的啊
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 合纵思状定制使用
     *
     * @return
     */
    public static String initSn() {
        try {
            Class serviceManager = Class.forName("com.lovdream.util.SystemUtil");
            Method method = serviceManager.getMethod("getSN");
            String SN = (String) method.invoke(serviceManager.newInstance());
            return SN == null ? "" : SN.trim();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据ip 检查是ping通
     * @param ip
     * @return
     */
    public boolean isPingOk(String ip) {
        LogUtil.e(TAG, "ping ip = " + ip);
        try {
            Process p = Runtime.getRuntime()
                    .exec("/system/bin/ping -c 5 -w 4 " + ip);
            if (p == null) {
                return false;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains("bytes from")) {
                    LogUtil.e(TAG, "ping read line = " + line);
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
