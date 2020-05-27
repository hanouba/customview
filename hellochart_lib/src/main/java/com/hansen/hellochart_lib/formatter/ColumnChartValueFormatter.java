package com.hansen.hellochart_lib.formatter;

import com.hansen.hellochart_lib.model.SubcolumnValue;

public interface ColumnChartValueFormatter {

    public int formatChartValue(char[] formattedValue, SubcolumnValue value);

}
