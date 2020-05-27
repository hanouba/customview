package com.hansen.hellochart_lib.listener;


import com.hansen.hellochart_lib.model.PointValue;

public interface LineChartOnValueSelectListener extends OnValueDeselectListener {

    public void onValueSelected(int lineIndex, int pointIndex, PointValue value);

}
