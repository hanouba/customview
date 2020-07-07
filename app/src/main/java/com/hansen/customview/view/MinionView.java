package com.hansen.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author HanN on 2020/7/7 18:17
 * @email: 1356548475@qq.com
 * @project customview
 * @description: 绘制小黄人
 * @updateuser:
 * @updatedata: 2020/7/7 18:17
 * @updateremark:
 * @version: 2.1.67
 */
public class MinionView extends View {


    public MinionView(Context context) {
        this(context, null);
    }

    public MinionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MinionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private static final int DEFAULT_SIZE = 200; // View 默认大小
    private int widthForUnspecified;
    private int heightForUnspecified;

    private float bodyWidth;
    private float bodyHeight;
    private static final float BODY_SCALE = 0.6f; // 身体主干占整个view的比重
    private static final float BODY_WIDTH_HEIGHT_SCALE = 0.6f; // 身体的比例设定为 w:h = 3:5

    private Paint mPaint = new Paint();
    {
        mPaint.setAntiAlias(true); //抗锯齿
    }
    private float mStrokeWidth = 4; //描边宽度
    private float offset;//考虑描边偏移
    private float radius; //上下圆半径
    private RectF bodyRect = new RectF();
    private int colorClothes = Color.rgb(32, 116, 160); // 衣服的颜色
    private int colorBody = Color.rgb(249, 217, 70); // 身体的颜色
    private int colorStroke = Color.BLACK;

    private float handsHeight;// 计算出吊带的高度时，可以用来做手的高度
    private float footHeight; // 脚的高度，用来画脚部阴影时用

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec,true),measure(heightMeasureSpec,false));
    }

    /**
     *
     * @param origin
     * @param isWidth 是否在测量宽，true 宽，false 高
     * @return
     */
    private int measure(int origin, boolean isWidth) {
        int result;
        int specMode = MeasureSpec.getMode(origin);
        int specSize = MeasureSpec.getSize(origin);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.AT_MOST:
                result = specSize;
                if (isWidth) {
                    widthForUnspecified = result;
                } else {
                    heightForUnspecified = result;
                }
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                if (isWidth) {// 宽或高未指定的情况下，可以由另一端推算出来 - -. 如果两边都没指定就用默认值
                    result = (int) (heightForUnspecified * BODY_WIDTH_HEIGHT_SCALE);
                } else {
                    result = (int) (widthForUnspecified / BODY_WIDTH_HEIGHT_SCALE);
                }
                if (result == 0) {
                    result = DEFAULT_SIZE;
                }

                break;
        }

        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initParams();
    }

    private void initParams() {
        //屏幕宽度 和 屏幕高度的0.6 取最小值后的0.6倍 作为小黄人的身体宽度
        bodyWidth = Math.min(getWidth(),getHeight()* BODY_WIDTH_HEIGHT_SCALE) * BODY_SCALE;
        //?
        bodyHeight = Math.min(getWidth(),getHeight()* BODY_WIDTH_HEIGHT_SCALE)  / BODY_WIDTH_HEIGHT_SCALE * BODY_SCALE;
        //取身体的宽度的1/50 与设定值 直接的最大值作为 描边宽度
        mStrokeWidth = Math.max(bodyWidth / 50 , mStrokeWidth);
        offset = mStrokeWidth / 2;

        bodyRect.left = (getWidth() - bodyWidth) / 2;
        bodyRect.top = (getHeight() - bodyHeight) / 2;

        bodyRect.right = bodyRect.left + bodyWidth;
        bodyRect.bottom = bodyRect.top + bodyHeight;

        radius = bodyWidth / 2;
        footHeight = radius * 0.4333f;

        handsHeight =  (getHeight() + bodyHeight) / 2   + offset - radius * 1.65f ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBody(canvas);
        drawBodyStroke(canvas);
        drawClothes(canvas);
    }

    private RectF rect = new RectF();
    private float[] pts = new float[20];// 5 条线 每条线需要4个数据定位
    private Path path = new Path();
    private void drawClothes(Canvas canvas) {
        // 底部的裤子 半圆
        rect.left = (getWidth() - bodyWidth) / 2 + offset;
        rect.top = (getHeight() + bodyHeight) / 2 - radius * 2 + offset;

        rect.right = rect.left + bodyWidth - offset * 2;
        rect.bottom = rect.top + radius * 2 - offset * 2;

        mPaint.setColor(colorClothes);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mStrokeWidth);
        //画半圆
        canvas.drawArc(rect, 0, 180, true, mPaint);
        //裤子上方的矩形
        int h = (int) (radius * 0.5);
        int w = (int) (radius * 0.3);

        rect.left += w;
        rect.top = rect.top + radius - h;
        rect.right -= w;
        rect.bottom = rect.top + h;
        canvas.drawRect(rect, mPaint);

        //画线 沿着裤子边的线
        mPaint.setColor(colorStroke);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mStrokeWidth);
        pts[0] = rect.left - w;
        pts[1] = rect.top + h;
        pts[2] = pts[0] + w;
        pts[3] = pts[1];

        pts[4] = pts[2];
        pts[5] = pts[3] + offset;
        pts[6] = pts[4];
        pts[7] = pts[3] - h;

        pts[8] = pts[6] - offset;
        pts[9] = pts[7];
        pts[10] = pts[8] + (radius - w) * 2;
        pts[11] = pts[9];

        pts[12] = pts[10];
        pts[13] = pts[11] - offset;
        pts[14] = pts[12];
        pts[15] = pts[13] + h;

        pts[16] = pts[14] - offset;
        pts[17] = pts[15];
        pts[18] = pts[16] + w;
        pts[19] = pts[17];
        canvas.drawLines(pts, mPaint);

        // 画左边的吊带
        mPaint.setColor(colorClothes);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.FILL);
        path.reset();
        path.moveTo(rect.left - w - offset, handsHeight);
        path.lineTo(rect.left + h / 4f, rect.top + h / 2f);
        final float smallW = w / 2f * (float) Math.sin(Math.PI / 4);
        path.lineTo(rect.left + h / 4f + smallW, rect.top + h / 2f - smallW);
        final float smallW2 = w / (float) Math.sin(Math.PI / 4) / 2;
        path.lineTo(rect.left - w - offset, handsHeight - smallW2);
        canvas.drawPath(path, mPaint);
        mPaint.setColor(colorStroke);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mPaint);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(rect.left + h / 5f, rect.top + h / 4f, mStrokeWidth * 0.7f, mPaint);

        // 画右吊带，代码和左吊带差不多，坐标对称
        mPaint.setColor(colorClothes);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.FILL);
        path.reset();
        path.moveTo(rect.left - w + 2 * radius - offset, handsHeight);
        path.lineTo(rect.right - h / 4f, rect.top + h / 2f);
        path.lineTo(rect.right - h / 4f - smallW, rect.top + h / 2f - smallW);
        path.lineTo(rect.left - w + 2 * radius - offset, handsHeight- smallW2);

        canvas.drawPath(path, mPaint);
        mPaint.setColor(colorStroke);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mPaint);

        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawCircle(rect.right - h / 5f, rect.top + h / 4f, mStrokeWidth * 0.7f, mPaint);

        // 中间口袋
        mPaint.setColor(colorStroke);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);

        path.reset();
        float radiusBigPocket = w / 2.0f;
        path.moveTo(rect.left + 1.5f * w, rect.bottom - h / 4f);
        path.lineTo(rect.right - 1.5f * w, rect.bottom - h / 4f);
        path.lineTo(rect.right - 1.5f * w, rect.bottom + h / 4f);
        path.addArc(rect.right - 1.5f * w - radiusBigPocket * 2, rect.bottom + h / 4f - radiusBigPocket,
                rect.right - 1.5f * w, rect.bottom + h / 4f + radiusBigPocket, 0, 90);
        path.lineTo(rect.left + 1.5f * w + radiusBigPocket, rect.bottom + h / 4f + radiusBigPocket);

        path.addArc(rect.left + 1.5f * w, rect.bottom + h / 4f - radiusBigPocket,
                rect.left + 1.5f * w + 2 * radiusBigPocket, rect.bottom + h / 4f + radiusBigPocket, 90, 90);
        path.lineTo(rect.left + 1.5f * w, rect.bottom - h / 4f - offset);
        canvas.drawPath(path, mPaint);

        // 下边一竖，分开裤子
        canvas.drawLine(bodyRect.left + bodyWidth / 2, bodyRect.bottom - h * 0.8f, bodyRect.left + bodyWidth / 2, bodyRect.bottom, mPaint);
        // 左边的小口袋
        float radiusSmallPocket = w * 1.2f;
        canvas.drawArc(bodyRect.left - radiusSmallPocket, bodyRect.bottom - radius - radiusSmallPocket,
                bodyRect.left + radiusSmallPocket, bodyRect.bottom - radius + radiusSmallPocket, 80, -60, false, mPaint);
        // 右边小口袋
        canvas.drawArc(bodyRect.right - radiusSmallPocket, bodyRect.bottom - radius - radiusSmallPocket,
                bodyRect.right + radiusSmallPocket, bodyRect.bottom - radius + radiusSmallPocket, 100, 60, false, mPaint);
    }

    private void drawBodyStroke(Canvas canvas) {
        mPaint.setColor(colorStroke);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(bodyRect, radius, radius, mPaint);
    }

    private void drawBody(Canvas canvas) {
        mPaint.setColor(colorBody);
        mPaint.setStyle(Paint.Style.FILL);
        //画两头半圆的棒子
        canvas.drawRoundRect(bodyRect, radius, radius, mPaint);
    }
}
