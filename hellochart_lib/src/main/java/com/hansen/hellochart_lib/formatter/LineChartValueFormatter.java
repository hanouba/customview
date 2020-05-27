package com.hansen.hellochart_lib.formatter;


import com.hansen.hellochart_lib.model.PointValue;

public interface LineChartValueFormatter {

    public int formatChartValue(char[] formattedValue, PointValue value);
}
