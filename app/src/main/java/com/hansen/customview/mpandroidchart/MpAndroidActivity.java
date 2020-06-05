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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MpAndroidActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    private BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp_android);
        chart = findViewById(R.id.view_barchart);

        initBarChar();
        initData();
    }



    private void initData() {
        String testdata = "{    \"data\": {        \"dataList\": [            {                \"unArrivedCount\": \"30\",                \"arrivedCount\": \"10\",                \"USERNAME\": \"维护组长\",                \"EXT_REPAIR_UID\": \"001402_维护组长\",                \"allCount\": \"40\"            },            {                \"unArrivedCount\": \"12\",                \"arrivedCount\": \"15\",                \"USERNAME\": \"张小帅\",                \"EXT_REPAIR_UID\": \"001402_张小帅\",                \"allCount\": \"27\"            }        ]    },    \"msg\": \"\",    \"id\": \":RO;\",    \"result\": \"ok\"}";
        Gson gson = new Gson();
        UnDoneChartBean unDoneChart = gson.fromJson(testdata, UnDoneChartBean.class);
        List<UnDoneChartBean.DataBean.DataListBean> undoneOrderInfo = unDoneChart.getData().getDataList();

        int groupCount = undoneOrderInfo.size();
        int startYear = 0;
        int endYear = startYear + groupCount;

        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = startYear; i < endYear; i++) {

            String unArrivedCount = undoneOrderInfo.get(i).getUnArrivedCount();
            String arrivedCount = undoneOrderInfo.get(i).getArrivedCount();
            float val1 = Float.valueOf(unArrivedCount);
            float val2 = Float.valueOf(arrivedCount);
            //            float val3 = (float) (Math.random() * mul) + mul / 3;

            values.add(new BarEntry(i,  new float[]{val1, val2}));
        }





        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Statistics Vienna 2014");
            set1.setDrawIcons(false);
            set1.setColors(getColors());


            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            //true 数据块分开显示 false  数据显示总数  decimals 数据精度
            data.setValueFormatter(new StackedValueFormatter(true, "", 0));
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(10f);
            chart.setData(data);
        }

        chart.setFitBars(true);
        chart.invalidate();
    }




    private void initBarChar() {


        chart.getDescription().setEnabled(false);
        chart.setPinchZoom(false);
        chart.setTouchEnabled(false);
        chart.setDrawBarShadow(false);
        chart.animateY(1500);
        chart.setDrawValueAboveBar(false);
        chart.setDrawGridBackground(false);
        chart.setHighlightFullBarEnabled(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setYOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(10f);
        l.setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineColor(getResources().getColor(R.color.colorAccent));
        xAxis.setCenterAxisLabels(true);
        xAxis.setTextSize(10f);


        chart.getAxisRight().setEnabled(false);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });


        YAxis leftAxis = chart.getAxisLeft();

        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setTextSize(10f);
        leftAxis.setDrawGridLines(false);

    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }


    public class MyXAxisValueFormatter extends ValueFormatter  {

        private List<String> mValues;

        public MyXAxisValueFormatter(List<String> values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            int x = (int) (value);
            if (x < 0)
                x = 0;
            if (x >= mValues.size())
                x = mValues.size() - 1;
            return mValues.get(x);
        }
    }


    private int[] getColors() {

        // have as many colors as stack-values per entry
        int[] colors = new int[2];

        System.arraycopy(ColorTemplate.MATERIAL_COLORS, 0, colors, 0, 2);

        return colors;
    }
}
