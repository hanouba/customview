package com.hansen.hellochart_lib.listener;


import com.hansen.hellochart_lib.model.BubbleValue;

public interface BubbleChartOnValueSelectListener extends OnValueDeselectListener {

    public void onValueSelected(int bubbleIndex, BubbleValue value);

}
