package com.hansen.customview.bubbleView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;
import com.hansen.launcher.AHansen;
import com.hansen.thread.DefaultPoolExecutor;
import com.hansen.thread.DefaultThreadFactory;
import com.hansen.thread.demo1.ThreadPoolManager;
import com.hansen.utils.Consts;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.arch.core.executor.DefaultTaskExecutor;

/**
 * @author HanN on 2020/10/14 16:54
 * @email: 1356548475@qq.com
 * @project customview
 * @description: 1如果当前圆的数量没有超过数量上限，则随机生成半径不同的圆。
 * 2设定这些圆的初始位置。
 * 3随机设定垂直向上平移速度。
 * 4随机设定水平平移速度。
 * 5不断的刷新圆的位置然后绘制。
 * 6将超出显示区域的圆进行移除。
 * 7不断重复。
 * @updateuser:
 * @updatedata: 2020/10/14 16:54
 * @updateremark:
 * @version: 2.1.67
 */
public class BubbleView extends View {
    //气泡数量
    private int mBubbleCount = 30;
    private int mBubbleRefrshTime = 3000;
    //气泡最多半径
    private int mBubbleMaxRadius = 30;
    //气泡最小半径
    private int mBubbleMinRadius = 5;
    //气泡颜色
    // 气泡速度
    private int mBubbleMaxSpeedY = 5;
    //气泡 透明的
    private int mBubbleAlpha = 128;

    // 杯子
    //杯子的宽度
    private int mBottleWidth;
    //杯子的高度
    private int mBottleHeight;
    // 水的高度
    private int mWaterHeight;
    // 杯子边宽度
    private int mBottleBorder;
    // 杯子口的弧度的圆角半径
    private int mBottleCapRadius;
    // 杯子底部的弧度的圆角半径
    private int mBottleRadius;
    //杯子的画笔


    private RectF mContentRectF;                // 实际可用内容区域
    private RectF mWaterRectF;                  // 水占用的区域

    //    使用Path不仅能够绘制简单图形，也可以绘制这些比较复杂的图形。
    //    另外，根据路径绘制文本和剪裁画布都会用到Path
    //用path 绘制瓶子和水
    private Path mBottlePath;
    private Path mWaterPath;
    // 水

    //水的颜色
    //水的透明的
    //水的画笔
    private Paint mBottlePaint;                 // 瓶子画笔
    private Paint mWaterPaint;                  // 水画笔
    private Paint mBubblePaint;                 // 气泡画笔


    public BubbleView(Context context) {
        this(context, null);
    }

    public BubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        //瓶子尺寸设计
        mBottleWidth = ConvertUtils.dp2px(130);
        mBottleHeight = ConvertUtils.dp2px(260);
        mBottleBorder = ConvertUtils.dp2px(8);
        mBottleCapRadius = ConvertUtils.dp2px(10);
        mBottleRadius = ConvertUtils.dp2px(30);


        //瓶子线条
        mBottlePath = new Path();
        //水的线条
        mWaterPath = new Path();

        //瓶子画笔设置
        mBottlePaint = new Paint();
        //锯齿
        mBottlePaint.setAntiAlias(true);
        //中空的
        mBottlePaint.setStyle(Paint.Style.STROKE);
        mBottlePaint.setStrokeCap(Paint.Cap.ROUND);
        mBottlePaint.setColor(Color.WHITE);
        //宽度
        mBottlePaint.setStrokeWidth(mBottleBorder);

        //水的画笔设置
        mWaterPaint = new Paint();
        mWaterPaint.setAntiAlias(true);
        //水的矩形框
        mWaterRectF = new RectF();
        //水的高度
        mWaterHeight = ConvertUtils.dp2px(240);


        initBubble();
    }

    /**
     * 气泡画笔初始化
     */
    private void initBubble() {
        mBubblePaint = new Paint();
        mBubblePaint.setColor(Color.WHITE);
        mBubblePaint.setAlpha(mBubbleAlpha);

    }

    private class Bubb {
        int radius; //气泡半径
        float x;// 气泡的x坐标
        float y; // 气泡的y坐标;
        float speedX; //水平的移动速度
        float speedY; //垂直方向的速度

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画瓶子
        canvas.drawPath(mBottlePath, mBottlePaint);
        //画水
        canvas.drawPath(mWaterPath, mWaterPaint);
        //画气泡
        drawBubble(canvas);
    }


    //气泡的集合
    private ArrayList<Bubb> mBubbles = new ArrayList<>();

    private void drawBubble(Canvas canvas) {
        //        为什么不直接遍历mBubbles呢?
        /**
         * 这里同样复制了一个新的 List 进行操作，不过这个与上面的原因不同，
         * 是为了防止多线程问题。由于在绘制的过程中，
         * 我们的计算线程可能会对原始 List 进行更新，可能导致异常的发生。
         * 为了避免这样的问题，就复制了一个 List 出来用于遍历绘制。
         */
        List<Bubb> list = new ArrayList<>(mBubbles);
        System.out.println("Bubble--画气泡drawBubble");
        for (Bubb bubb : list
        ) {
            if (null == bubb)
                continue;
            canvas.drawCircle(bubb.x, bubb.y, bubb.radius, mBubblePaint);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        System.out.println("Bubble--界面 onSizeChanged");
        //整个区域大小
        mContentRectF = new RectF(getPaddingLeft(), getPaddingTop(), w - getPaddingRight(), h - getPaddingBottom());
        //中心点的位置 - 瓶子宽度的一半  得到左边的位置  水平方向起点
        float bl = mContentRectF.centerX() - mBottleWidth / 2;
        //垂直方向起点
        float bt = mContentRectF.centerY() - mBottleHeight / 2;
        // 水平方向终点
        float br = mContentRectF.centerX() + mBottleWidth / 2;
        //垂直方向终点
        float bb = mContentRectF.centerY() + mBottleHeight / 2;

        // 画瓶子
        mBottlePath.reset();
        mBottlePath.moveTo(bl - mBottleCapRadius, bt - mBottleCapRadius);
        mBottlePath.quadTo(bl, bt - mBottleCapRadius, bl, bt);
        mBottlePath.lineTo(bl, bb - mBottleRadius);
        mBottlePath.quadTo(bl, bb, bl + mBottleRadius, bb);
        mBottlePath.lineTo(br - mBottleRadius, bb);
        mBottlePath.quadTo(br, bb, br, bb - mBottleRadius);
        mBottlePath.lineTo(br, bt);
        mBottlePath.quadTo(br, bt - mBottleCapRadius, br + mBottleCapRadius, bt - mBottleCapRadius);


        mWaterPath.reset();
        mWaterPath.moveTo(bl, bb - mWaterHeight);
        mWaterPath.lineTo(bl, bb - mBottleRadius);
        mWaterPath.quadTo(bl, bb, bl + mBottleRadius, bb);
        mWaterPath.lineTo(br - mBottleRadius, bb);
        mWaterPath.quadTo(br, bb, br, bb - mBottleRadius);
        mWaterPath.lineTo(br, bb - mWaterHeight);
        //封闭
        mWaterPath.close();
        //      配合使用 实现渐变色
        // 和new rectF效果一样 获取方块区域  并设置渐变色  如果不设置方块区域就没有渐变色效果
        mWaterRectF.set(bl, bb - mWaterHeight, br, bb);
        LinearGradient gradient = new LinearGradient(mWaterRectF.centerX(), mWaterRectF.top,
                mWaterRectF.centerX(), mWaterRectF.bottom, 0xFF4286f4, 0xFF373B44, Shader.TileMode.CLAMP);
        mWaterPaint.setShader(gradient);
    }

    //View 被添加到界面上时开启了一个线程用于生成气泡和刷新气泡位置
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startBubbleSync();
    }

    //在 View 从界面上移除的时候关闭了这个线程
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopBubbleSync();
    }

    private Random random = new Random();
    private Thread mBubbleThread;

    // 开始气泡线程
    private void startBubbleSync() {
        stopBubbleSync();
        /**
         * 这里没有用变量来控制循环，而是监听了中断事件，
         * 在当拦截到 InterruptedException 的时候，使用 break 跳出了死循环，因此线程也就结束了，方法简单粗暴。
         */
        //
        //        mBubbleThread = new Thread(new Runnable() {
        //            @Override
        //            public void run() {
        //                while (true) {
        //                    try {
        //                        //在线程里面间隔执行
        //                        Thread.sleep(mBubbleRefrshTime);
        //                        AHansen.logger.info(Consts.TAG, "bubble线程开始");
        //                        tryCreateBubble();
        //                        refreshBubbles();
        //                        //重新调用 onDraw 画气泡
        //                        postInvalidate();
        //                    } catch (InterruptedException e) {
        //                        AHansen.logger.info(Consts.TAG, "Bubble线程结束");
        //                        break;
        //                    }
        //                }
        //            }
        //        });
        //        mBubbleThread.start();

        mRun = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        //在线程里面间隔执行
                        Thread.sleep(3000);
                        AHansen.logger.info(Consts.TAG, "bubble线程开始");
                        tryCreateBubble();
                        refreshBubbles();
                        //重新调用 onDraw 画气泡
                        postInvalidate();
                    } catch (InterruptedException e) {
                        AHansen.logger.info(Consts.TAG, "Bubble线程结束");
                        break;
                    }
                }
            }
        };
        DefaultPoolExecutor.getInstance().execute(mRun);


    }

    private Runnable mRun;

    // 停止气泡线程
    private void stopBubbleSync() {
        //        if (null == mBubbleThread)
        //            return;
        ////        中断
        //        mBubbleThread.interrupt();
        //        mBubbleThread = null;

        DefaultPoolExecutor.getInstance().shutdown();

    }

    // 尝试创建气泡
    private void tryCreateBubble() {
        if (null == mContentRectF)
            return;
        //如果气泡数量大于最大数量 停止创建
        if (mBubbles.size() >= mBubbleCount) {
            return;
        }
        //随机浮点数 0-1 0.35789108   控制气泡生成速度
        if (random.nextFloat() < 0.95) {
            return;
        }
        Bubb bubble = new Bubb();
        //生成一个随机半径
        int radius = random.nextInt(mBubbleMaxRadius - mBubbleMinRadius);
        radius += mBubbleMinRadius;
        //气泡速度随机
        float speedY = random.nextFloat() * mBubbleMaxSpeedY;
        while (speedY < 1) {
            speedY = random.nextFloat() * mBubbleMaxSpeedY;
        }
        bubble.radius = radius;
        bubble.speedY = speedY;
        //瓶子的中间底部
        bubble.x = mWaterRectF.centerX();
        bubble.y = mWaterRectF.bottom - radius - mBottleBorder / 2;
        float speedX = random.nextFloat() - 0.5f;
        while (speedX == 0) {
            speedX = random.nextFloat() - 0.5f;
        }
        bubble.speedX = speedX * 2;
        mBubbles.add(bubble);
    }

    // 刷新气泡位置，对于超出区域的气泡进行移除
    private void refreshBubbles() {
        List<Bubb> list = new ArrayList<>(mBubbles);
        for (Bubb bubble : list) {
            //生成的气泡在水的区域外面就移除这个气泡
            if (bubble.y - bubble.speedY <= mWaterRectF.top + bubble.radius) {
                mBubbles.remove(bubble);
            } else {

                int i = mBubbles.indexOf(bubble);
                if (bubble.x + bubble.speedX <= mWaterRectF.left + bubble.radius + mBottleBorder / 2) {
                    //如果气泡在水的左边外面就 让气泡靠近瓶子壁
                    bubble.x = mWaterRectF.left + bubble.radius + mBottleBorder / 2;
                } else if (bubble.x + bubble.speedX >= mWaterRectF.right - bubble.radius - mBottleBorder / 2) {
                    //让气泡靠近水的边
                    bubble.x = mWaterRectF.right - bubble.radius - mBottleBorder / 2;
                } else {
                    //其他情况 x 就不断向左平移每次移动的距离就是speedx
                    bubble.x = bubble.x + bubble.speedX;
                }
                //不断向上移动 每次移动的距离是 speedy
                bubble.y = bubble.y - bubble.speedY;
                //将重新赋值的气泡重新放入集合
                mBubbles.set(i, bubble);
            }
        }
    }
}
