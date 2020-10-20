package com.hansen.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.ToastUtils;
import com.hansen.launcher.AHansen;

import java.util.Set;

/**
 * @author HanN on 2020/10/20 9:11
 * @email: 1356548475@qq.com
 * @project customview
 * @description: 蓝牙协议 ： 核心协议 电缆替代协议 电话控制协议 链路管理 逻辑链路控制和适应协议；
 * 核心协议：基带。链路管理，逻辑链路控制和适应协议；  其中链路管理（LMP）负责蓝牙组件间连接的建立。逻辑链路控制与适应协议（L2CAP）位于基带协议上
 * 属于数据链路层。是一个为高层传输和应用层协议屏蔽基带协议的适配协议
 * @updateuser:
 * @updatedata: 2020/10/20 9:11
 * @updateremark:
 * @version: 2.1.67
 */
public class BlueToothUtils {
    private BluetoothAdapter bluetoothAdapter;
    //    1 添加蓝牙权限

    //    2 BluetoothAdapter
    public BluetoothAdapter getBluetoothAdapter() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            return bluetoothAdapter;
        } else {
            AHansen.logger.info("BLUETOOTHTAG","不支持蓝牙");
            return null;
        }
    }

    public BlueToothUtils() {
        if (bluetoothAdapter == null) {
            bluetoothAdapter = getBluetoothAdapter();
        }
    }

    /**
     * 打开蓝牙
     *
     * @param context 上下文必须是栈顶的activity
     *                Calling startActivity() from outside of an Activity
     *                context requires the FLAG_ACTIVITY_NEW_TASK flag.
     *                Is this really what you want?
     */
    public void openBlueTooth(Context context) {
        if (bluetoothAdapter.isEnabled()) {
            ToastUtils.showLong("蓝牙已经打开");
        }else {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            context.startActivity(intent);
        }

    }

    /**
     * 静默打开蓝牙
     */
    public void silenceOpenBlueTooth() {
        if (bluetoothAdapter.isEnabled()) {
            ToastUtils.showLong("蓝牙已经打开");
        }else {
            bluetoothAdapter.enable();
        }

    }

    /**
     * 关闭蓝牙
     */
    public void closeBlueTooth() {
        bluetoothAdapter.disable();
    }

    /**
     * 获取已经绑定的蓝牙设备
     *
     * @return
     */
    public Set<BluetoothDevice> getBonderDevice() {
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        return bondedDevices;
    }

    /**
     * 开始搜索蓝牙设备
     * 在Android6.0后必须添加 android.permission.ACCESS_COARSE_LOCATION 权限才可也
     */
    public void startDiscoverBlue() {
        if (bluetoothAdapter.isDiscovering()) {
            ToastUtils.showLong("正在搜索蓝牙");
        }else {
            bluetoothAdapter.startDiscovery();
        }


    }

    /**
     * 停止搜索蓝牙
     */
    public void stopDiscoverBlue() {
        bluetoothAdapter.cancelDiscovery();
    }

}
