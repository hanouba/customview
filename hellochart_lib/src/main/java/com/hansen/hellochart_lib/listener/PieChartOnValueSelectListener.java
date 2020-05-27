package com.hansen.hellochart_lib.listener;


import com.hansen.hellochart_lib.model.SliceValue;

public interface PieChartOnValueSelectListener extends OnValueDeselectListener {

    public void onValueSelected(int arcIndex, SliceValue value);

}
