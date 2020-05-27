package com.hansen.hellochart_lib.listener;


import com.hansen.hellochart_lib.model.SubcolumnValue;

import com.hansen.hellochart_lib.model.PointValue;


public interface ComboLineColumnChartOnValueSelectListener extends OnValueDeselectListener {

    public void onColumnValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value);

    public void onPointValueSelected(int lineIndex, int pointIndex, PointValue value);

}
