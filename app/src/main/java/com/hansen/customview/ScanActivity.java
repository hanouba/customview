package com.hansen.customview;

import androidx.appcompat.app.AppCompatActivity;
import cn.bingoogolapple.qrcode.core.QRCodeView;

import android.os.Bundle;
import android.os.Vibrator;

import com.blankj.utilcode.util.ToastUtils;
import com.hansen.customview.view.zxing.ZXingView;

/**
 * 扫码效果验证
 */
public class ScanActivity extends AppCompatActivity implements QRCodeView.Delegate {
    private ZXingView mZXingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        mZXingView = findViewById(R.id.zxingview);

        mZXingView.setDelegate(this);

        //        mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别

        mZXingView.startSpotAndShowRect();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        ToastUtils.showShort("扫描结果"+result);

        vibrate();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        String tipText = mZXingView.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                mZXingView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                mZXingView.getScanBoxView().setTipText(tipText);
            }
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        ToastUtils.showShort("扫描结果"+"onScanQRCodeOpenCameraError");
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
}