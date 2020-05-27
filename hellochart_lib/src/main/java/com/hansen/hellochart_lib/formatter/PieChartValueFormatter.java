package com.hansen.hellochart_lib.formatter;

import com.hansen.hellochart_lib.model.SliceValue;

public interface PieChartValueFormatter {

    public int formatChartValue(char[] formattedValue, SliceValue value);
}
