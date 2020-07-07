package com.hansen.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.hansen.customview.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author HanN on 2020/6/3 17:08
 * @email: 1356548475@qq.com
 * @project trunk
 * @description: 自定义的流程节点实现
 * @updateuser:
 * @updatedata: 2020/6/3 17:08
 * @updateremark:
 * @version: 2.1.67
 */
public class FLowLineView extends View {
    /**
     * 圆的直径
     */
    private int mRoundSize = ConvertUtils.dp2px(20);
    /**
     * 光晕透明度
     */
    private int haloAlpha =  ConvertUtils.dp2px(10);
    /**
     * 光晕的宽度
     */
    private int haloWidth = ConvertUtils.dp2px(2);
    /**
     * 节点画笔
     */
    private Paint mPaint;
    /**
     * 文字描述的画笔
     */
    private TextPaint tp;
    /**
     * 流程线的画笔
     */
    private Paint mLinePaint;
    /**
     * 当前已完成的最新节点
     */
    private int tag;
    /**
     * 需要显示的流程节点集合
     */
    private List<FlowChart> mFlowCharts = new ArrayList<>();
    /**
     * 每一个节点item（包含文字）所要占用的最小宽度即最长文字的宽度
     */
    private int mItemMaxWidth;
    /**
     * 当前控件宽度
     */
    private int mCurrViewWidth;

    /**
     * 当前控件高度
     */
    private int mCurrViewHeight=0;
    /**
     * 存放每个item最长的文字宽度
     */
    private List<Integer> mItemMaxTextViewWidthList = new ArrayList<>();
    /**
     * 布局是否超出屏幕
     */
    private boolean full;
    /**
     * 流程线的高度
     */
    private int lineHeight = ConvertUtils.dp2px(2);
    /**
     * 文字大小
     */
    private int textSize = ConvertUtils.dp2px(8);

    /**
     * 已完成节点颜色
     */
    private int doneColor;
    /**
     * 进行中节点颜色
     */
    private int doingColor;
    /**
     * 未开始节点颜色
     */
    private int todoColor;
    private int whiteColor;
    /**
     * 行间距
     */
    private int rowRpacing = ConvertUtils.dp2px(10);

    private boolean doubleBottom;

    public FLowLineView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public FLowLineView(Context context) {
        this(context,null);
    }

    public FLowLineView(Context context,  AttributeSet attrs) {
        this (context, attrs, 0);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FLowLineView);//下面是在读取布局里面设置的属性值
        doneColor = typedArray.getColor(R.styleable.FLowLineView_doneColor, Color.parseColor("#0B8FFE"));
        doingColor = typedArray.getColor(R.styleable.FLowLineView_doingColor, Color.parseColor("#DADADA"));
        todoColor = typedArray.getColor(R.styleable.FLowLineView_todongColor, Color.parseColor("#DADADA"));
        whiteColor = typedArray.getColor(R.styleable.FLowLineView_whiteColor, Color.parseColor("#ff4331"));
        textSize = typedArray.getDimensionPixelSize(R.styleable.FLowLineView_fLowtextSize, textSize);
        lineHeight = typedArray.getDimensionPixelSize(R.styleable.FLowLineView_lineHeight, lineHeight);
        haloWidth = typedArray.getDimensionPixelSize(R.styleable.FLowLineView_haloWidth, haloWidth);
        haloAlpha = typedArray.getInt(R.styleable.FLowLineView_haloAlpha, haloAlpha);
        rowRpacing = typedArray.getDimensionPixelSize(R.styleable.FLowLineView_rowRpacing, rowRpacing);
        initView();
    }

    private void initView() {
        LogUtils.d("onMeasure--1");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        LogUtils.d("onMeasure--2");
        super.onDraw(canvas);


        DisplayMetrics dm = getResources().getDisplayMetrics();

        //圆的半径
        int radius = mRoundSize / 2;
        int startY = rowRpacing+textSize/2;


        mPaint = new Paint();
        mLinePaint = new Paint();
        tp = new TextPaint();//以上三行可以放在初始化的地方没这样子写其实很不优雅，但是我现在不想改
        for (int a = 0; a < mFlowCharts.size(); a++) {
            tp.setTextSize(textSize);
            tp.setTypeface(Typeface.SANS_SERIF);
            //文字字体加粗
            tp.setFakeBoldText(false);
            //笔宽5像素
            mLinePaint.setStrokeWidth(lineHeight);
            //下面开始设置画笔的颜色这些
            if (a <= tag) {
                mPaint.setColor(doneColor);
                tp.setColor(doneColor);
                mLinePaint.setColor(todoColor);
            } else if (a == tag + 1) {
                mPaint.setColor(todoColor);
                tp.setColor(todoColor);
                mLinePaint.setColor(todoColor);
            } else {
                mPaint.setColor(todoColor);
                tp.setColor(todoColor);
                mLinePaint.setColor(todoColor);
            }
            // 计算文字的宽度
            int topTextWidth = getTextWidth(tp, mFlowCharts.get(a).getTopName());
            int bottomTextWidth = getTextWidth(tp, mFlowCharts.get(a).getBottomName());
            int timeTextX = getTextWidth(tp, mFlowCharts.get(a).getTime());

            int maxTextWidth;
            int topX;
            int bottomX;
            int timeX;
            int roundCenterX;
            int rouCenterX;
            if (full) {
                maxTextWidth = mItemMaxWidth;
            } else {
                maxTextWidth = Math.max(topTextWidth, bottomTextWidth);
                if (doubleBottom) {
                    maxTextWidth = Math.max(maxTextWidth, timeTextX);
                }
                maxTextWidth += 20;
            }
            roundCenterX = mItemMaxWidth * a  + maxTextWidth / 2;
            topX = roundCenterX - topTextWidth / 2;
            bottomX = roundCenterX - bottomTextWidth / 2;
            timeX = roundCenterX - timeTextX / 2;
            // 绘制文字
            canvas.drawText(mFlowCharts.get(a).getTopName(), topX, startY, tp);
            canvas.drawText(mFlowCharts.get(a).getBottomName(), bottomX, startY + rowRpacing * 2 + mRoundSize + haloWidth * 2, tp);
            if (doubleBottom) {//不显示最后一行文字
                canvas.drawText(mFlowCharts.get(a).getTime(), timeX, startY + rowRpacing * 3 + textSize / 2 + mRoundSize + haloWidth * 2, tp);
            }
            if (a < mFlowCharts.size() - 1) {
                // 绘制线
                canvas.drawLine(roundCenterX, startY + rowRpacing, roundCenterX + mItemMaxWidth, startY + rowRpacing, mLinePaint);
                if (tag == 0) {
                    if (a == 0) {
                        mLinePaint.setColor(doneColor);
                        canvas.drawLine(roundCenterX, startY + rowRpacing, roundCenterX + mItemMaxWidth/2, startY + rowRpacing, mLinePaint);
                    }
                }else if (tag == 1) {
                    if (a == 0) {
                        mLinePaint.setColor(doneColor);
                        canvas.drawLine(roundCenterX, startY + rowRpacing, roundCenterX + mItemMaxWidth, startY + rowRpacing, mLinePaint);
                    }else if (a == 1) {
                        mLinePaint.setColor(doneColor);
                        rouCenterX = roundCenterX + roundCenterX/2 ;
                        canvas.drawLine(roundCenterX, startY + rowRpacing, rouCenterX, startY + rowRpacing, mLinePaint);
                    }
                }else if (tag == 2) {
                    if (a == 0) {
                        mLinePaint.setColor(doneColor);
                        canvas.drawLine(roundCenterX, startY + rowRpacing, roundCenterX + mItemMaxWidth, startY + rowRpacing, mLinePaint);
                    }else if (a == 1) {
                        mLinePaint.setColor(doneColor);
                        canvas.drawLine(roundCenterX, startY + rowRpacing,roundCenterX + mItemMaxWidth, startY + rowRpacing, mLinePaint);
                    }
                }
            }
            // 画圆
            RectF rf2 = new RectF(roundCenterX - radius, startY + rowRpacing - radius, roundCenterX + radius, startY + rowRpacing + radius);
            canvas.drawOval(rf2, mPaint);
            if (a <= tag) {
                mPaint.setColor(whiteColor);
                int startX =roundCenterX - radius/2;
                int startYy = startY + rowRpacing*8/10;
                int midx = roundCenterX;
                int midY = startY + rowRpacing*6/5;
                int endX = roundCenterX+radius*2/3;
                int endy =  startY + rowRpacing*8/10;

                canvas.drawLine(startX,startYy,midx ,midY,mPaint);
                canvas.drawLine(midx,midY,endX,endy,mPaint);
            }

            if (a == tag + 1) {
                mPaint.setAlpha(haloAlpha);
                RectF haloRectF = new RectF(roundCenterX - (radius + haloWidth), startY + rowRpacing - radius - haloWidth, roundCenterX + (radius + haloWidth), startY + rowRpacing + radius + haloWidth);
                canvas.drawOval(haloRectF, mPaint);
            }
            mPaint.reset();
            tp.reset();
        }
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

    }

    /**
     * 计算文字宽度
     */
    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    public void setFlowCharts(List<FlowChart> flowCharts) {
        if (flowCharts == null) {
            flowCharts = new ArrayList<>();
        }
        mFlowCharts = flowCharts;

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels-ConvertUtils.px2dp(200);
        if (width < getViewMinWidth()) {
            width = getViewMinWidth() + 5 * (mFlowCharts.size() + 1);
            mItemMaxWidth = width / mFlowCharts.size();
            full = true;
        } else {
            mItemMaxWidth = (width - 20 - getFlowMinWidth(mFlowCharts.get(0)) / 2 - getFlowMinWidth(mFlowCharts.get(mFlowCharts.size() - 1)) / 2) / (mFlowCharts.size() - 1);
            full = false;
        }
        mCurrViewWidth = width;
        if (doubleBottom) {//不现实最后一行文字就不参与计算
            mCurrViewHeight = rowRpacing + textSize / 2 + rowRpacing * 3 + textSize / 2 + mRoundSize + haloWidth * 2+rowRpacing;
        }else {
            mCurrViewHeight = rowRpacing + textSize / 2 +rowRpacing*2+mRoundSize+haloWidth*2+rowRpacing;
        }
        invalidate();
        requestLayout();
    }

    public void setTag(int tag) {
        this.tag = tag;
        invalidate();
    }

    /**
     * 获取所有文字占用的最小宽度
     *
     * @return
     */
    public int getViewMinWidth() {
        int minWidth;

        if (mFlowCharts != null && !mFlowCharts.isEmpty()) {
            for (FlowChart chart : mFlowCharts) {
                // 计算文字的宽度
                mItemMaxTextViewWidthList.add(getFlowMinWidth(chart));
            }
        } else {
        }
        int max = Collections.max(mItemMaxTextViewWidthList);
        minWidth = (max+5) * mFlowCharts.size();
        return minWidth;
    }

    /**
     * 计算每个item的最长文字占用宽度作为item的最小宽度
     *
     * @param chart
     * @return
     */
    public int getFlowMinWidth(FlowChart chart) {
        tp = new TextPaint();
        tp.setTextSize(textSize);
        tp.setTypeface(Typeface.SANS_SERIF);
        //文字字体加粗
        tp.setFakeBoldText(false);
        int topTextWidth = getTextWidth(tp, chart.getTopName());
        int bottomTextWidth = getTextWidth(tp, chart.getBottomName());
        int timeTextX = getTextWidth(tp, chart.getTime());
        int maxTextWidth = Math.max(topTextWidth, bottomTextWidth);
        if (doubleBottom) {
            maxTextWidth = Math.max(maxTextWidth, timeTextX);
        }
        return maxTextWidth;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtils.d("onMeasure--3");
       setMeasuredDimension(mCurrViewWidth,mCurrViewHeight);
    }



    public void setDoingColor(int doingColor) {
        this.doingColor = doingColor;
    }

    public void setDoneColor(int doneColor) {
        this.doneColor = doneColor;
    }

    public void setTodoColor(int todoColor) {
        this.todoColor = todoColor;
    }

    public void setHaloAlpha(int haloAlpha) {
        this.haloAlpha = haloAlpha;
    }

    public void setHaloWidth(int haloWidth) {
        this.haloWidth = haloWidth;
    }

    public void setRoundSize(int roundSize) {
        mRoundSize = roundSize;
    }

    public void setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        invalidate();
    }

    public void setDoubleBottom(boolean doubleBottom) {
        this.doubleBottom = doubleBottom;
        invalidate();
    }
}


