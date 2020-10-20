
package com.hansen.customview.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.hansen.customview.R;
import com.hansen.launcher.AHansen;

import java.util.Set;

/**
 * 蓝牙的使用
 * 1 蓝牙的开关
 * 2 搜索蓝牙设备
 */
public class BlueToothActivity extends AppCompatActivity {
    private static final String TAG = "BLUETOOTHTAG";
    //绑定的蓝牙设备
    private TextView tvDevice;

    //查询蓝牙设备的
    private IntentFilter foundblueDeviceFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);

        tvDevice = findViewById(R.id.tv_bonded_device);

    }
//    打开蓝牙
    public void open(View view) {
        AHansen.logger.info(TAG,"打开蓝牙");
        AHansen.blueToothUtils.openBlueTooth(this);
    }
//静默打开
    public void silenceOpen(View view) {
        AHansen.logger.info(TAG,"silenceOpen");
        AHansen.blueToothUtils.silenceOpenBlueTooth();
    }
//关闭蓝牙
    public void close(View view) {
        AHansen.logger.info(TAG,"close蓝牙");
        AHansen.blueToothUtils.closeBlueTooth();
    }

    /**
     * 搜索蓝牙设备
     *
     * @param view
     */
    public void searchBlueTooth(View view) {
        showBondedBlueToothDevice();
        AHansen.logger.info(TAG,"searchBlueTooth-搜索蓝牙设备");
//       注册广播 接收已经搜索到的蓝牙设备
         foundblueDeviceFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(receiver,foundblueDeviceFilter);
        //注册搜索完毕是的广播
//        intentFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//        this.registerReceiver(receiver,intentFilter);



        if (BluetoothAdapter.getDefaultAdapter().isDiscovering()) {
            AHansen.logger.info(TAG,"searchBlueTooth-取消搜索蓝牙设备");
            AHansen.blueToothUtils.stopDiscoverBlue();
        }else {
            AHansen.logger.info(TAG,"searchBlueTooth-开始搜索蓝牙设备");
            AHansen.blueToothUtils.startDiscoverBlue();
        }
    }

    /**
     * 显示已经绑定的蓝牙设备
     */
    public void showBondedBlueToothDevice() {
        AHansen.logger.info(TAG,"showBondedBlueToothDevice-显示已经绑定的蓝牙设备");
        Set<BluetoothDevice> bonderDevices = AHansen.blueToothUtils.getBonderDevice();
        if (bonderDevices.size() > 0) {
            for (BluetoothDevice bonderDevice:
                    bonderDevices) {
                tvDevice.append(bonderDevice.getName() + ":  "+ bonderDevice.getAddress() + "\n");
            }
        }else {
            ToastUtils.showLong("无绑定的蓝牙设备");
        }
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                //搜到到的设备不是已经绑定的设备
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    //显示
                    tvDevice.append(device.getName() + "--" + device.getAddress() + "\n");
                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    //如果搜索完毕
                    ToastUtils.showLong("搜索完毕");
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}