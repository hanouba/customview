package com.hansen.hellochart_lib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.hansen.hellochart_lib.BuildConfig;
import com.hansen.hellochart_lib.computator.PreviewChartComputator;
import com.hansen.hellochart_lib.gesture.PreviewChartTouchHandler;
import com.hansen.hellochart_lib.model.LineChartData;
import com.hansen.hellochart_lib.renderer.PreviewLineChartRenderer;

import androidx.core.view.ViewCompat;

/**
 * Preview chart that can be used as overview for other LineChart. When you change Viewport of this chart, visible area
 * of other chart will change. For that you need also to use
 * {@link Chart#setViewportChangeListener(com.hansen.hellochart_lib.listener.ViewportChangeListener)}
 *
 * @author Leszek Wach
 */
public class PreviewLineChartView extends LineChartView {
    private static final String TAG = "PreviewLineChartView";

    protected PreviewLineChartRenderer previewChartRenderer;

    public PreviewLineChartView(Context context) {
        this(context, null, 0);
    }

    public PreviewLineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PreviewLineChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        chartComputator = new PreviewChartComputator();
        previewChartRenderer = new PreviewLineChartRenderer(context, this, this);
        touchHandler = new PreviewChartTouchHandler(context, this);
        setChartRenderer(previewChartRenderer);
        setLineChartData(LineChartData.generateDummyData());
    }

    public int getPreviewColor() {
        return previewChartRenderer.getPreviewColor();
    }

    public void setPreviewColor(int color) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Changing preview area color");
        }

        previewChartRenderer.setPreviewColor(color);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        final int offset = computeHorizontalScrollOffset();
        final int range = computeHorizontalScrollRange() - computeHorizontalScrollExtent();
        if (range == 0) return false;
        if (direction < 0) {
            return offset > 0;
        } else {
            return offset < range - 1;
        }
    }
}
