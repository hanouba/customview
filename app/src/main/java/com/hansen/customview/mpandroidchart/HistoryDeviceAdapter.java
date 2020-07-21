package com.hansen.customview.mpandroidchart;

import android.content.Context;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.hansen.customview.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Create by HanN on 2019/8/19
 * 注释:
 */
public class HistoryDeviceAdapter extends RecyclerView.Adapter<HistoryDeviceAdapter.ViewHolder> {

    private List<String> mDatas;
    private Context mContext;
    private int[] titleColor = {R.color.color_3BD134, R.color.color_blue, R.color.color_blue, R.color.color_3BD134};
    public HistoryDeviceAdapter(List<String> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_chart, parent, false);
        return new HistoryDeviceAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.chart.setViewPortOffsets(0, 0, 0, 0);
        viewHolder.chart.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));

        viewHolder.chart.getDescription().setEnabled(false);

        // enable touch gestures
        viewHolder.chart.setTouchEnabled(true);

        // enable scaling and dragging
        viewHolder.chart.setDragEnabled(true);
        viewHolder.chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        viewHolder. chart.setPinchZoom(false);

        viewHolder.chart.setDrawGridBackground(false);
        viewHolder. chart.setMaxHighlightDistance(300);

        XAxis x = viewHolder.chart.getXAxis();
        x.setEnabled(false);
        YAxis y = viewHolder.chart.getAxisLeft();
        y.setEnabled(false);
        viewHolder.chart.getAxisRight().setEnabled(false);
        viewHolder.chart.getLegend().setEnabled(false);
        viewHolder.chart.animateXY(2000, 2000);



        // don't forget to refresh the drawing
        viewHolder. chart.invalidate();
        setData(viewHolder,10);
    }


    private void setData(ViewHolder viewHolder, float range) {
        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (i == 0 || i == 1) {
                values.add(new Entry(i,0));
            }else {
                values.add(new Entry(i, 10));
            }

        }

        LineDataSet set1;

        if (viewHolder.chart.getData() != null &&
                viewHolder.chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) viewHolder.chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            viewHolder.chart.getData().notifyDataChanged();
            viewHolder.chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.1f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setColor(ContextCompat.getColor(mContext, titleColor[0]));
            set1.setCircleColor(ContextCompat.getColor(mContext, titleColor[0]));
            set1.setHighLightColor(ContextCompat.getColor(mContext, titleColor[0]));
            set1.setFillColor(Color.WHITE);
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            //渐变色
            Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.fade_green);
            set1.setFillDrawable(drawable);
            // create a data object with the data sets
            LineData data = new LineData(set1);

            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            viewHolder.chart.setData(data);
        }
    }
    @Override
    public int getItemCount() {
        if (null == mDatas) {
            return 0;
        } else {
            return mDatas.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private LineChart chart;
        public ViewHolder(View itemView) {
            super(itemView);
            chart = itemView.findViewById(R.id.line_chart);
        }
    }


}
