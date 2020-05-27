package com.hansen.hellochart_lib.view;

import android.content.Context;

import android.util.AttributeSet;
import android.util.Log;

import com.hansen.hellochart_lib.BuildConfig;
import com.hansen.hellochart_lib.listener.BubbleChartOnValueSelectListener;
import com.hansen.hellochart_lib.listener.DummyBubbleChartOnValueSelectListener;
import com.hansen.hellochart_lib.model.BubbleChartData;
import com.hansen.hellochart_lib.model.BubbleValue;
import com.hansen.hellochart_lib.model.ChartData;
import com.hansen.hellochart_lib.model.SelectedValue;
import com.hansen.hellochart_lib.provider.BubbleChartDataProvider;
import com.hansen.hellochart_lib.renderer.BubbleChartRenderer;

import androidx.core.view.ViewCompat;

/**
 * BubbleChart, supports circle bubbles and square bubbles.
 *
 * @author lecho
 */
public class BubbleChartView extends AbstractChartView implements BubbleChartDataProvider {
    private static final String TAG = "BubbleChartView";
    protected BubbleChartData data;
    protected BubbleChartOnValueSelectListener onValueTouchListener = new DummyBubbleChartOnValueSelectListener();

    protected BubbleChartRenderer bubbleChartRenderer;

    public BubbleChartView(Context context) {
        this(context, null, 0);
    }

    public BubbleChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        bubbleChartRenderer = new BubbleChartRenderer(context, this, this);
        setChartRenderer(bubbleChartRenderer);
        setBubbleChartData(BubbleChartData.generateDummyData());
    }

    @Override
    public BubbleChartData getBubbleChartData() {
        return data;
    }

    @Override
    public void setBubbleChartData(BubbleChartData data) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Setting data for BubbleChartView");
        }

        if (null == data) {
            this.data = BubbleChartData.generateDummyData();
        } else {
            this.data = data;
        }

        super.onChartDataChange();
    }

    @Override
    public ChartData getChartData() {
        return data;
    }

    @Override
    public void callTouchListener() {
        SelectedValue selectedValue = chartRenderer.getSelectedValue();

        if (selectedValue.isSet()) {
            BubbleValue value = data.getValues().get(selectedValue.getFirstIndex());
            onValueTouchListener.onValueSelected(selectedValue.getFirstIndex(), value);
        } else {
            onValueTouchListener.onValueDeselected();
        }
    }

    public BubbleChartOnValueSelectListener getOnValueTouchListener() {
        return onValueTouchListener;
    }

    public void setOnValueTouchListener(BubbleChartOnValueSelectListener touchListener) {
        if (null != touchListener) {
            this.onValueTouchListener = touchListener;
        }
    }

    /**
     * Removes empty spaces, top-bottom for portrait orientation and left-right for landscape. This method has to be
     * called after view View#onSizeChanged() method is called and chart data is set. This method may be inaccurate.
     *
     * @see BubbleChartRenderer#removeMargins()
     */
    public void removeMargins() {
        bubbleChartRenderer.removeMargins();
        ViewCompat.postInvalidateOnAnimation(this);
    }
}
