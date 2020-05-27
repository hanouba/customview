package com.hansen.hellochart_lib.formatter;

import com.hansen.hellochart_lib.model.BubbleValue;

public interface BubbleChartValueFormatter {

    public int formatChartValue(char[] formattedValue, BubbleValue value);
}
