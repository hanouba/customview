package com.hansen.fingerprintlib;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.CancellationSignal;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;

import com.hansen.fingerprintlib.utils.CipherHelper;

import androidx.annotation.RequiresApi;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

/**
 * @author HanN on 2020/9/18 13:37
 * @email: 1356548475@qq.com
 * @project customview
 * @description:  指纹识别 P
 * @updateuser:
 * @updatedata: 2020/9/18 13:37
 * @updateremark:
 * @version: 2.1.67
 */
@RequiresApi(api = Build.VERSION_CODES.P)
public class FingerprintAndrP implements IFingerprint{
    private static FingerprintAndrP fingerprintAndrP;
    private FingerprintCallback fingerprintCallback;
    //用于取消扫描器的扫描动作
    private CancellationSignal cancellationSignal;
    //指纹加密
    private static BiometricPrompt.CryptoObject cryptoObject;

    /**检测指纹硬件是否可用，及是否添加指纹
     *
     * @param context
     * @param callback
     * @return
     *  * 在 Android Q，Google 提供了 Api BiometricManager.canAuthenticate() 用来检测指纹识别硬件是否可用及是否添加指纹
     *      * 不过尚未开放，标记为"Stub"(存根)
     *      * 所以暂时还是需要使用 Andorid 6.0 的 Api 进行判断
     */
    @Override
    public boolean canAuthenticate(Context context, FingerprintCallback callback) {
        if (!FingerprintManagerCompat.from(context).isHardwareDetected()) {
            fingerprintCallback.onHwUnavailable();
            return false;
        }
        //是否已经添加指纹
        if (!FingerprintManagerCompat.from(context).hasEnrolledFingerprints()) {
            fingerprintCallback.onNoneEnrolled();
            return false;
        }
        return false;
    }

    /**
     * 初始化并调起指纹验证
     * @param context
     * @param verificationDialogStyleBean
     * @param callback
     */

    @Override
    public void authenticate(Activity context, VerificationDialogStyleBean verificationDialogStyleBean,
                             FingerprintCallback callback) {
        this.fingerprintCallback = callback;
        String titile = TextUtils.isEmpty(verificationDialogStyleBean.getTitle()) ?
                context.getString(R.string.biometricprompt_fingerprint_verification) :
                verificationDialogStyleBean.getTitle();
        String cancelText = TextUtils.isEmpty(verificationDialogStyleBean.getCancelBtnText())
                ? context.getString(R.string.biometricprompt_cancel)
                :verificationDialogStyleBean.getCancelBtnText();

        BiometricPrompt.Builder builder = new BiometricPrompt.Builder(context)
                .setTitle(titile)
                .setNegativeButton(cancelText,command -> {},(dialog,which) -> {
                });
        if (!TextUtils.isEmpty(verificationDialogStyleBean.getSubTitle())) {
            builder.setSubtitle(verificationDialogStyleBean.getSubTitle());
        }
        if (!TextUtils.isEmpty(verificationDialogStyleBean.getDescription())) {
            builder.setDescription(verificationDialogStyleBean.getDescription());
        }

        BiometricPrompt biometricPrompt = builder.build();
        cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(() -> {

        });
        biometricPrompt.authenticate(cryptoObject,cancellationSignal,context.getMainExecutor(),authenticationCallback);

    }


    public static FingerprintAndrP newInstance() {
        if (fingerprintAndrP == null) {
            synchronized (fingerprintAndrP.cancellationSignal) {
                if (fingerprintAndrP == null) {
                    fingerprintAndrP = new FingerprintAndrP();
                }
            }
        }

        try {
            cryptoObject = new BiometricPrompt.CryptoObject(new CipherHelper().createCipher());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fingerprintAndrP;
    }

    private BiometricPrompt.AuthenticationCallback authenticationCallback = new BiometricPrompt.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            if (fingerprintCallback != null) {
                if (errorCode == 5) {
                    //用户取消指纹验证  不必要抛出异常
                    fingerprintCallback.onCancel();
                    return;
                }

            }
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            super.onAuthenticationHelp(helpCode, helpString);

        }

        @Override
        public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            if (fingerprintCallback != null) {
                fingerprintCallback.onSucceeded();
            }
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            if (fingerprintCallback != null) {
                fingerprintCallback.onFailed();
            }
        }
    };
}
