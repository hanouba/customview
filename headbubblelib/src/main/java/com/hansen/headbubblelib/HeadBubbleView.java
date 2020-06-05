package com.hansen.headbubblelib;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author HanN on 2020/6/5 9:26
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/6/5 9:26
 * @updateremark:
 * @version: 2.1.67
 */
public class HeadBubbleView extends FrameLayout {
    //这个position很重要 不断的取出图片资源 靠它累加完成的
    private int position = 0;
    private int viewWidth ,viewHeight;
    private int marginLeft;
    private int marginBot;
    private float[] pos;
    private float[] tan;

    public HeadBubbleView(@NonNull Context context) {
        this(context,null);
    }

    public HeadBubbleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setFocusable(false);
        //三阶贝塞尔曲线控制点
        Point controlPointOne = new Point();
        Point controlPointTwo = new Point();
        //每个子view的宽度高度固定
        viewWidth = viewHeight = ConvertUtils.dp2px(22);
        marginLeft = ConvertUtils.dp2px(15);
        marginBot = ConvertUtils.dp2px(21);
        //父view的高度也是固定的
        int height = ConvertUtils.dp2px(130);
        //用从PathMeasure 中不断的取出 曲线的路径
        pos = new float[2];
        tan = new float[2];

        initView();



    }

    private void initView() {
        //这个imageview 不执行动画,用于底部不断切换图片展示
        ImageView tempImageView = getImageView();
        TextView textView  = getTextView();
        initData(tempImageView);
    }

    private void initData(ImageView tempImageView) {

    }
    //创建用于显示坐标xx来过的TextView
    private TextView getTextView() {
        int bottom = ConvertUtils.dp2px(23);
        int right = ConvertUtils.dp2px(41);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.END | Gravity.BOTTOM;
        layoutParams.setMargins(0,0,right,bottom);
        TextView tv_name = new TextView(getContext());
        tv_name.setTextSize(12);
        tv_name.setTextColor(Color.WHITE);
        addView(tv_name,layoutParams);

        return tv_name;
    }
    //创建执行动画的具体角色
    private ImageView getImageView() {
        LayoutParams layoutParams = new LayoutParams(viewWidth,viewHeight);
        ImageView roundedImageView = new ImageView(getContext());
        roundedImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        layoutParams.gravity = Gravity.BOTTOM | Gravity.END;
        layoutParams.setMargins(0,0,marginLeft,marginBot);
        addView(roundedImageView,layoutParams);
        return roundedImageView;
    }
}
