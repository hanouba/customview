package com.hansen.customview.mpandroidchart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.StackedValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.hansen.customview.R;
import com.hansen.customview.hellochart.UnDoneChartBean;

import java.util.ArrayList;
import java.util.List;

public class MpAndroidActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    private BarChart mBarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp_android);
        mBarChart = findViewById(R.id.view_barchart);

//        mBarChart.getDescription().setEnabled(false);
//
//        // if more than 60 entries are displayed in the chart, no values will be
//        // drawn
//        mBarChart.setMaxVisibleValueCount(40);
//
//        // scaling can now only be done on x- and y-axis separately
//        mBarChart.setPinchZoom(false);
//
//        mBarChart.setDrawGridBackground(false);
//        mBarChart.setDrawBarShadow(false);
//        mBarChart.animateY(1500); // 动画
//        mBarChart.setDrawValueAboveBar(false);
//        mBarChart.setHighlightFullBarEnabled(false);
//
//        mBarChart.setOnChartValueSelectedListener(this);
//        mBarChart.getAxisRight().setEnabled(false);
//        mBarChart.setTouchEnabled(false);
//
//
//        YAxis leftAxis = mBarChart.getAxisLeft();
//        leftAxis.setDrawGridLines(false);
//        leftAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                int n = (int) value;
//                return n + "";
//            }
//        });
//        leftAxis.setSpaceTop(35f);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//
//        XAxis xLabels = mBarChart.getXAxis();
//        xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xLabels.setDrawLabels(true);
//        xLabels.setDrawGridLines(false);
//        xLabels.setGranularity(1f);
//        //        xLabels.setCenterAxisLabels();
//        xLabels.setCenterAxisLabels(true);//字体下面的标签 显示在每个直方图的中间
//
//
//        Legend l = mBarChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(true);
//        l.setFormSize(8f);
//        l.setFormToTextSpace(4f);
//        l.setXEntrySpace(6f);
//
//        l.setEnabled(false);


        mBarChart.setPinchZoom(false);

        mBarChart.setDrawGridBackground(false);
        mBarChart.setDrawBarShadow(false);
        mBarChart.animateY(1500); // 动画
        mBarChart.setDrawValueAboveBar(false);
        mBarChart.setHighlightFullBarEnabled(false);

        mBarChart.getAxisRight().setEnabled(false);
        mBarChart.setTouchEnabled(false);

        Legend l = mBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        XAxis xAxis = mBarChart.getXAxis();

        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                int n = (int) value;
                return n + "";
            }
        });

        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        mBarChart.getAxisRight().setEnabled(false);
        setData();
    }

    private ArrayList<String> xlabelDataList = new ArrayList<>();

    private void setData() {
        xlabelDataList.clear();
        String testdata = "{    \"data\": {        \"dataList\": [            {                \"unArrivedCount\": \"3\",                \"arrivedCount\": \"2\",                \"USERNAME\": \"维护组长\",                \"EXT_REPAIR_UID\": \"001402_维护组长\",                \"allCount\": \"5\"            },            {                \"unArrivedCount\": \"4\",                \"arrivedCount\": \"6\",                \"USERNAME\": \"张小帅\",                \"EXT_REPAIR_UID\": \"001402_张小帅\",                \"allCount\": \"10\"            }        ]    },    \"msg\": \"\",    \"id\": \":RO;\",    \"result\": \"ok\"}";
        Gson gson = new Gson();
        UnDoneChartBean unDoneChart = gson.fromJson(testdata, UnDoneChartBean.class);
        List<UnDoneChartBean.DataBean.DataListBean> undoneOrderInfo = unDoneChart.getData().getDataList();

        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        for (int i = 0; i < undoneOrderInfo.size(); i++) {

            String unArrivedCount = undoneOrderInfo.get(i).getUnArrivedCount();
            String arrivedCount = undoneOrderInfo.get(i).getArrivedCount();
            float val1 = Float.valueOf(unArrivedCount);
            float val2 = Float.valueOf(arrivedCount);
            //            float val3 = (float) (Math.random() * mul) + mul / 3;

            values1.add(new BarEntry(i, val1));
            values2.add(new BarEntry(i, val2));

        }

        for (int j = 0; j <undoneOrderInfo.size(); j++) {
            xlabelDataList.add(undoneOrderInfo.get(j).getUSERNAME());
        }

        ValueFormatter ix = new MyXAxisValueFormatter(xlabelDataList);
        mBarChart.getXAxis().setValueFormatter(ix);

        BarDataSet set1, set2;

        if (mBarChart.getData() != null &&
                mBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) mBarChart.getData().getDataSetByIndex(1);
            set1.setValues(values1);
            set2.setValues(values2);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values1, "");
            set1.setColors(Color.parseColor("#FEA213"));

            set2 = new BarDataSet(values1, "");
            set2.setColors(Color.parseColor("#16ADF6"));

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            dataSets.add(set2);

            BarData data = new BarData(set1,set2);
            data.setValueFormatter(new LargeValueFormatter());
            data.setValueTextColor(Color.WHITE);

            mBarChart.setData(data);
        }

        mBarChart.setFitBars(true);
        mBarChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }


    private int[] getColors() {

        // have as many colors as stack-values per entry
        int[] colors = new int[2];

        System.arraycopy(ColorTemplate.MATERIAL_COLORS, 0, colors, 0, 2);

        return colors;
    }


    public class MyXAxisValueFormatter extends ValueFormatter {

        private List<String> mValues;

        public MyXAxisValueFormatter(List<String> values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value) {
            int x = (int) (value);
            if (x < 0)
                x = 0;
            if (x >= mValues.size())
                x = mValues.size() - 1;
            return mValues.get(x);
        }
    }
}
