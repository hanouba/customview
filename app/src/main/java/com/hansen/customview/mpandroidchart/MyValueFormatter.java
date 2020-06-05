package com.hansen.customview.mpandroidchart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class MyValueFormatter extends ValueFormatter {


    @Override
    public String getFormattedValue(float value) {
        return String.valueOf(value);
    }
}
