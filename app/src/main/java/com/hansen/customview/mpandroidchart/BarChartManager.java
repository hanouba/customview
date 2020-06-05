package com.hansen.customview.mpandroidchart;

import android.content.Context;
import android.graphics.Color;


import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

/**
 * @Author: pyz
 * @Package: com.pyz.myproject.utils
 * @Description: TODO
 * @Project: MyProject
 * @Company: 深圳君南信息系统有限公司
 * @Date: 2016/7/21 10:09
 */
public class BarChartManager {
    private static String unit = null;

    public static void initBarChart(Context context, BarChart barChart,
                                    ArrayList<BarEntry> xValues, ArrayList<BarEntry> yValues) {
        initDataStyle(context,barChart);
        //设置柱状图的样式
        BarDataSet dataSet = new BarDataSet(yValues, unit);
        dataSet.setColor(Color.parseColor("#7EC0EE"));
        dataSet.setDrawValues(true);
        dataSet.setValueTextColor(Color.RED);

//        dataSet.setValueFormatter(new PercentFormatter(new DecimalFormat("%").format()));

        BarDataSet dataSetX = new BarDataSet(xValues, unit);
        dataSetX.setColor(Color.parseColor("#7EC0EE"));
        dataSetX.setDrawValues(true);
        dataSetX.setValueTextColor(Color.RED);


        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);
        dataSets.add(dataSetX);



        //构建一个barData  将dataSets放入
        BarData barData = new BarData(dataSets);
        //将数据插入
        barChart.setData(barData);

        //设置动画效果

//        barChart.animateX(2000, Easing.EasingOption.Linear);
        barChart.invalidate();
    }

    /**
     *  @Description:初始化图表的样式
     * @param context
     * @param barChart

     */
    private static void initDataStyle(Context context, BarChart barChart) {
        //设置图表是否支持触控操作
        barChart.setTouchEnabled(false);
        //设置点击折时，显示其数值
  /*      MyMakerView mv = new MyMakerView(context, R.layout.item_mark_layout);
        barChart.setMarkerView(mv);*/
        //设置柱状的描述的样式（默认在图表的左下角）
        Legend title = barChart.getLegend();
        title.setForm(Legend.LegendForm.SQUARE);
        //设置x轴的样式
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineColor(Color.parseColor("#66CDAA"));
        xAxis.setAxisLineWidth(5);
        xAxis.setDrawGridLines(false);
        //设置是否显示x轴
        xAxis.setEnabled(true);

        //设置左边y轴的样式
        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setAxisLineColor(Color.parseColor("#66CDAA"));
        yAxisLeft.setAxisLineWidth(5);
        yAxisLeft.setDrawGridLines(false);

        //设置右边y轴的样式
        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false);

    }

    /**
     * 设置单位值
     * @param barUnit
     */
    public static void  setUnit(String barUnit){
        unit = barUnit;
    }
}
