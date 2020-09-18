package com.hansen.fingerprintlib;

import android.app.Activity;

import com.hansen.fingerprintlib.utils.AndrVersionUtil;

/**
 * @author HanN on 2020/9/18 14:38
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/9/18 14:38
 * @updateremark:
 * @version: 2.1.67
 */
public class FingerprintVerifyManager {

    public FingerprintVerifyManager(Builder builder) {
        IFingerprint fingerprint;
        if (AndrVersionUtil.isAboveAndroidP()) {
            if (builder.enableAndroidP) {
                fingerprint = FingerprintAndrP.newInstance();
            }else {
                fingerprint = FingerprintAndrM.newInstance();
            }
        }else if (AndrVersionUtil.isAboveAndroidM()) {
            fingerprint = FingerprintAndrM.newInstance();
        }else {
            // 低于Android6.0 的不适配 个别定制机会有
            //硬件不支持
            builder.callback.onHwUnavailable();
            return;
        }

        if (!fingerprint.canAuthenticate(builder.context,builder.callback)) {
            return;
        }
        /**
         * 设定指纹验证框的样式
         */
        // >= Android 6.0
        VerificationDialogStyleBean bean = new VerificationDialogStyleBean();
        bean.setCancelTextColor(builder.cancelTextColor);
        bean.setUsepwdTextColor(builder.usepwdTextColor);
        bean.setFingerprintColor(builder.fingerprintColor);
        bean.setUsepwdVisible(builder.usepwdVisible);

        // >= Android 9.0
        bean.setTitle(builder.title);
        bean.setSubTitle(builder.subTitle);
        bean.setDescription(builder.description);
        bean.setCancelBtnText(builder.cancelBtnText);

        fingerprint.authenticate(builder.context, bean, builder.callback);


    }

    public static class Builder{
       private Activity context;
       private FingerprintCallback callback;

       private int cancelTextColor;
       private int usepwdTextColor;
       private int fingerprintColor;
       private boolean usepwdVisible;

       private boolean enableAndroidP;
       private String title;
       private String subTitle;
       private String description;
       private String cancelBtnText;

       public Builder(Activity activity) {
           this.context = activity;
       }


        public int getFingerprintColor() {
            return fingerprintColor;
        }

        public Builder fingerprintColor(int fingerprintColor) {
            this.fingerprintColor = fingerprintColor;
            return this;
        }

        public Builder callBack(FingerprintCallback callback) {
           this.callback = callback;
           return this;

       }
       public Builder enableAndroidP(boolean enableAndroidP) {
           this.enableAndroidP = enableAndroidP;
           return this;
       }
       /**
        * >= Android 9.0 的验证框的主标题
        *
        * @param title
        */
       public Builder title(String title) {
           this.title = title;
           return this;
       }

       /**
        * >= Android 9.0 的验证框的副标题
        *
        * @param subTitle
        */
       public Builder subTitle(String subTitle) {
           this.subTitle = subTitle;
           return this;
       }

       /**
        * >= Android 9.0 的验证框的描述内容
        *
        * @param description
        */
       public Builder description(String description) {
           this.description = description;
           return this;
       }

       public FingerprintVerifyManager build() {
           return new FingerprintVerifyManager(this);
       }

        public Builder callback(FingerprintCallback fingerprintCallback) {
           this.callback = fingerprintCallback;
           return this;
        }
    }
}
