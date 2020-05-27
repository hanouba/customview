package com.hansen.hellochart_lib.formatter;


import com.hansen.hellochart_lib.model.AxisValue;

public interface AxisValueFormatter {

    /**
     * Formats AxisValue for manual(custom) axis. Result is stored in (output) formattedValue array. Method
     * returns number of chars of formatted value. The formatted value starts at index [formattedValue.length -
     * charsNumber] and ends at index [formattedValue.length-1].
     */
    public int formatValueForManualAxis(char[] formattedValue, AxisValue axisValue);

    /**
     * Used only for auto-generated axes. If you are not going to use your implementation for aut-generated axes you can
     * skip implementation of this method and just return 0. SFormats values with given number of digits after
     * decimal separator. Result is stored in given array. Method returns number of chars for formatted value. The
     * formatted value starts at index [formattedValue.length - charsNumber] and ends at index [formattedValue
     * .length-1].
     */
    public int formatValueForAutoGeneratedAxis(char[] formattedValue, float value, int autoDecimalDigits);
}
