package com.hansen.stextlib;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;

import com.hansen.utils.LogUtil;

import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

/**
 * @author HanN on 2020/5/20 10:16
 * @email: 1356548475@qq.com
 * @project customview
 * @description: 仿支付宝密码输入框、微信密码输入框，美团外卖验证码输入框等。有实心，空心以及下划线形式。可控制文本是否显示
 * @updateuser:
 * @updatedata: 2020/5/20 10:16
 * @updateremark:
 * @version: 2.1.67
 */
public class SeparatedEditText extends AppCompatEditText {
    private static final String TAG = "SeparatedEditText_TAG";
    /**
     * 定义三种状态
     *
     * @param context
     */
    //空心
    private static final int TYPE_HOLLOW = 1;
    //实心
    private static final int TYPE_SOLID = 2;
    //下划线
    private static final int TYPE_UNDERLINE = 3;
    //是否是密码格式
    private boolean password;
    //是否显示光标
    private boolean showCursor;
    //显示类型
    private int type;
    //最大位数
    private int maxLenth;
    //角度
    private int corner;
    private int blockColor;
    private int borderColor;
    private int textColor;
    private int cursorColor;
    //方块之间间隙
    private int blockSpacing;
    //光标持续时间
    private int cursorDuration;
    private int cursorWidth;
    //边界粗细
    private int borderWidth;

    //边界画笔
    private Paint borderPaint;
    //实心块画笔
    private Paint blockPaint;
    private Paint textPaint;
    private Paint cursorPaint;
    //光标是否显示
    private boolean isCursorShowing;

    private TextChangedListener textChangedListener;
    private CharSequence contentText;
    private RectF borderRectf;
    private RectF boxRectf;
    private int width;
    private int height;
    private int boxWidth;
    private int boxHeight;
    private Timer timer;
    private TimerTask timerTask;

    public SeparatedEditText(Context context) {
        this(context, null);
    }

    public SeparatedEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SeparatedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //不支持长按点击
        setLongClickable(false);
        //文本不可选
        setTextIsSelectable(false);
        setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });


        //导入自定义样式
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SeparatedEditText);
        password = typedArray.getBoolean(R.styleable.SeparatedEditText_password, false);
        showCursor = typedArray.getBoolean(R.styleable.SeparatedEditText_showCursor, false);
        type = typedArray.getInt(R.styleable.SeparatedEditText_separateType, TYPE_HOLLOW);
        maxLenth = typedArray.getInt(R.styleable.SeparatedEditText_maxLength, 6);
        corner = (int) typedArray.getDimension(R.styleable.SeparatedEditText_corner, 0);
        blockColor = typedArray.getColor(R.styleable.SeparatedEditText_blockColor, ContextCompat.getColor(context, R.color.colorPrimary));
        borderColor = typedArray.getColor(R.styleable.SeparatedEditText_borderColor, ContextCompat.getColor(context, R.color.lightGrey));
        textColor = typedArray.getColor(R.styleable.SeparatedEditText_textColor, ContextCompat.getColor(context, R.color.lightGrey));
        cursorColor = typedArray.getColor(R.styleable.SeparatedEditText_cursorColor, ContextCompat.getColor(context, R.color.lightGrey));
        blockSpacing = (int) typedArray.getDimension(R.styleable.SeparatedEditText_blockSpacing, 0);
        cursorDuration = typedArray.getInt(R.styleable.SeparatedEditText_cursorDuration, 500);
        cursorWidth = (int) typedArray.getDimension(R.styleable.SeparatedEditText_cursorWidth, 2);
        borderWidth = (int) typedArray.getDimension(R.styleable.SeparatedEditText_borderWidth, 5);

        typedArray.recycle();

        init();
    }

    private void init() {
        //焦点
        setFocusableInTouchMode(true);
        setFocusable(true);
        requestFocus();
        //光标是否可见
        setCursorVisible(false);
        // 过滤器
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLenth)});

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //输入法管理器
                //SHOW_FORCED表示用户强制打开输入法（如长按菜单键），一直保持打开直至只有显式关闭
                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                LogUtil.d(TAG, "输入法管理器");
            }
        }, 50000);

        //画笔
        blockPaint = new Paint();
        blockPaint.setAntiAlias(true);
        blockPaint.setColor(blockColor);
        blockPaint.setStyle(Paint.Style.FILL);
        blockPaint.setStrokeWidth(1);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setStrokeWidth(1);

        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);


        cursorPaint = new Paint();
        cursorPaint.setAntiAlias(true);
        cursorPaint.setColor(cursorColor);
        cursorPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        cursorPaint.setStrokeWidth(cursorWidth);


        borderRectf = new RectF();
        boxRectf = new RectF();

        if (type == TYPE_HOLLOW) {
            blockSpacing = 0;

        }

        timerTask = new TimerTask() {
            @Override
            public void run() {
                isCursorShowing = !isCursorShowing;
                LogUtil.d(TAG, "timerTask");
                postInvalidate();
            }
        };
        timer = new Timer();


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        LogUtil.d(TAG, "onSizeChanged");
        //得到布局的宽高
        width = w;
        height = h;

        //计算 单个方块的宽度
        /**
         * 总宽度 - 间距的宽度之和,最后评价分配宽度
         */
        boxWidth = (width - blockSpacing * (maxLenth + 1)) / maxLenth;
        //高度和布局高度一致
        boxHeight = height;

        borderRectf.set(0, 0, width, height);
        textPaint.setTextSize(boxWidth / 2);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.d(TAG, "onDraw");

        drawRect(canvas);

        drawText(canvas, contentText);

        drawCursor(canvas);
    }

    /**
     * 绘制光标
     *
     * @param canvas
     */
    private void drawCursor(Canvas canvas) {
        LogUtil.d(TAG, "drawCursor");
        if (!isCursorShowing && showCursor && contentText.length() < maxLenth && hasFocus()) {
            int cursorPostion = contentText.length() + 1;
            int startX = blockSpacing * cursorPostion + boxWidth * (cursorPostion - 1) + boxWidth / 2;
            int startY = boxHeight / 4;
            int endX = startX;
            int endY = boxHeight - startY;

            canvas.drawLine(startX, startY, endX, endY, cursorPaint);

        }
    }

    /**
     * 绘制方块
     *
     * @param canvas
     */
    private void drawRect(Canvas canvas) {
        LogUtil.d(TAG, "drawRect");
        //更具最大数量 画方块
        for (int i = 0; i < maxLenth; i++) {
            boxRectf.set(blockSpacing * (i + 1) + boxWidth * i, 0, blockSpacing * (i + 1) + boxWidth * i + boxWidth, boxHeight);

            if (type == TYPE_SOLID) {
                canvas.drawRoundRect(boxRectf, corner, corner, blockPaint);
            } else if (type == TYPE_UNDERLINE) {
                canvas.drawLine(boxRectf.left, borderRectf.bottom, boxRectf.right, boxRectf.bottom, borderPaint);
            } else if (type == TYPE_HOLLOW) {
                if (i == 0 || i == maxLenth) {
                    continue;
                }
                canvas.drawLine(boxRectf.left, boxRectf.top, boxRectf.right, boxRectf.bottom, borderPaint);
            }
        }


    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        LogUtil.d(TAG, "onTextChanged");
        contentText = text;
        invalidate();

        //判断是否输入完毕
        if (textChangedListener != null) {
            if (text.length() == maxLenth) {
                textChangedListener.textCompleted(text);
            } else {
                textChangedListener.textChanged(text);
            }
        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        timer.scheduleAtFixedRate(timerTask, 0, cursorDuration);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        timer.cancel();
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        LogUtil.d(TAG, "onTextContextMenuItem");
        return true;


    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        CharSequence text = getText();
        if (text != null) {
            if (selStart != text.length() || selEnd != text.length()) {
                setSelection(text.length(), text.length());
                return;
            }
        }
        super.onSelectionChanged(selStart, selEnd);
        LogUtil.d(TAG, "onSelectionChanged");
    }

    private void drawText(Canvas canvas, CharSequence charSequence) {
        LogUtil.d(TAG, "drawText");
        for (int i = 0; i < charSequence.length(); i++) {
            int startX = blockSpacing * (i + 1) + boxWidth * i;
            int startY = 0;
            int baseX = (int) (startX + boxWidth / 2 - textPaint.measureText(String.valueOf(charSequence.charAt(i))) / 2);
            int baseY = (int) (startY + boxHeight / 2 - (textPaint.descent() + textPaint.ascent()) / 2);
            int centerX = startX + boxWidth / 2;
            int centerY = startY + boxHeight / 2;
            int radius = Math.min(boxWidth, boxHeight) / 6;
            if (password) {

                canvas.drawCircle(centerX, centerY, radius, textPaint);
            } else {
                canvas.drawText(String.valueOf(charSequence.charAt(i)), baseX, baseY, textPaint);
            }
        }
    }

    /**
     * 密码监听者
     */
    public interface TextChangedListener {
        /**
         * 输入/删除监听
         *
         * @param changeText 输入/删除的字符
         */
        void textChanged(CharSequence changeText);

        /**
         * 输入完成
         */
        void textCompleted(CharSequence text);
    }

    public void setTextChangedListener(TextChangedListener listener) {
        textChangedListener = listener;
    }


}
