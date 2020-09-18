package com.hansen.fingerprintlib;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by ZuoHailong on 2019/3/12.
 */
public class FingerprintDialog extends DialogFragment {

    private static FingerprintDialog mDialog;
    private OnDialogActionListener actionListener;
    //提示,取消,使用密码
    private TextView tvTip, tvCancel, tvUsepwd;
    //指纹图片
    private ImageView ivFingerprint;
    //弹窗控制相关实体类
    private VerificationDialogStyleBean verificationDialogStyleBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setCancelable(false);
        View view = inflater.inflate(R.layout.biometricprompt_layout_fingerprint_dialog, container);
        ivFingerprint = view.findViewById(R.id.ivFingerprint);
        tvTip = view.findViewById(R.id.tvTip);
        tvUsepwd = view.findViewById(R.id.tvUsepwd);
        tvUsepwd.setOnClickListener(v -> {
            if (actionListener != null)
                //调用接口 使用密码
                actionListener.onUsepwd();
            dismiss();
        });
        tvCancel = view.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(v -> {
            if (actionListener != null)
                //调用接口 取消
                actionListener.onCancle();
            dismiss();
        });

        //调用者定义验证框样式
        if (verificationDialogStyleBean != null) {
            if (verificationDialogStyleBean.getCancelTextColor() != 0)
                //设置取消字体颜色
                tvCancel.setTextColor(verificationDialogStyleBean.getCancelTextColor());
            if (verificationDialogStyleBean.getUsepwdTextColor() != 0)
                //设置使用密码字体颜色
                tvUsepwd.setTextColor(verificationDialogStyleBean.getUsepwdTextColor());

            if (verificationDialogStyleBean.getFingerprintColor() != 0) {
                //设置指纹颜色
                Drawable drawable = ivFingerprint.getDrawable();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//Android 5.0
                    //大于5.0的设备上
                    drawable.setTint(verificationDialogStyleBean.getFingerprintColor());
                }
            }
            //使用密码按钮 是否显示
            if (verificationDialogStyleBean.isUsepwdVisible()) {
                tvUsepwd.setVisibility(View.VISIBLE);
                view.findViewById(R.id.view).setVisibility(View.VISIBLE);
            } else {
                tvUsepwd.setVisibility(View.GONE);
                view.findViewById(R.id.view).setVisibility(View.GONE);
            }
        }

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (actionListener != null)
            //调用 隐藏接口
            actionListener.onDismiss();
    }

    /**
     * 单例模式
     * @return
     */
    public static FingerprintDialog newInstance() {
        if (mDialog == null) {
            synchronized (FingerprintDialog.class) {
                if (mDialog == null) {
                    mDialog = new FingerprintDialog();
                }
            }
        }
        return mDialog;
    }

    /**
     * 设置监听
     * @param actionListener
     * @return
     */
    public FingerprintDialog setActionListener(OnDialogActionListener actionListener) {
        this.actionListener = actionListener;
        return mDialog;
    }

    /**
     * 设定dialog样式
     *
     * @param bean
     */
    public FingerprintDialog setDialogStyle(VerificationDialogStyleBean bean) {
        this.verificationDialogStyleBean = bean;
        return mDialog;
    }

    /**
     * 根据指纹验证的结果更新tip的文字内容和文字颜色
     *
     * @param tip
     * @param colorId
     */
    public void setTip(String tip, @ColorRes int colorId) {
        tvTip.setText(tip);
        tvTip.setTextColor(getResources().getColor(colorId));
    }

    /**
     *
     */
    public interface OnDialogActionListener {
        //使用密码
        void onUsepwd();
        //取消
        void onCancle();
        //隐藏
        void onDismiss();
    }
}
