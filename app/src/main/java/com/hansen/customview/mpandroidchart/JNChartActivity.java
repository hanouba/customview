package com.hansen.customview.mpandroidchart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.hansen.customview.R;

import java.util.ArrayList;

public class JNChartActivity extends AppCompatActivity {
    private BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jnchart);
        barChart = findViewById(R.id.bar_chart);


        ArrayList<BarEntry> xValues = new ArrayList<>();
        xValues.add(new BarEntry(Float.parseFloat( "123"),0));
        xValues.add(new BarEntry(Float.parseFloat( "45"),1));
        xValues.add(new BarEntry(Float.parseFloat( "78"),2));
        xValues.add(new BarEntry(Float.parseFloat( "52"),3));
        xValues.add(new BarEntry(Float.parseFloat( "52"),4));
        xValues.add(new BarEntry(Float.parseFloat( "9"),5));


        ArrayList<BarEntry> yValues = new ArrayList<>();
        yValues.add(new BarEntry((float) 2.09,0));
        yValues.add(new BarEntry((float) 2.81,1));
        yValues.add(new BarEntry((float) 1.49,2));
        yValues.add(new BarEntry((float) 5.,3));
        yValues.add(new BarEntry((float)9,4));
        yValues.add(new BarEntry((float) 3.86,5));
        BarChartManager.setUnit("单位：万户");
        BarChartManager.initBarChart(this,barChart,xValues,yValues);
    }
}
