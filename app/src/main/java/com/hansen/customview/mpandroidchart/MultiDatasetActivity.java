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
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;
import com.hansen.customview.R;
import com.hansen.customview.hellochart.UnDoneChartBean;

import java.util.ArrayList;
import java.util.List;

public class MultiDatasetActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    private BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_dataset);
        chart = findViewById(R.id.view_barchart);


        initBarChar();
        initData();
    }

    private ArrayList<String> xlabelDataList = new ArrayList<>();

    private void initData() {

        String testdata = "{    \"data\": {        \"dataList\": [            {                \"unArrivedCount\": \"5\",                \"arrivedCount\": \"6\",                \"USERNAME\": \"维护组长\",                \"EXT_REPAIR_UID\": \"001402_维护组长\",                \"allCount\": \"0\"            },            {                \"unArrivedCount\": \"7\",                \"arrivedCount\": \"8\",                \"USERNAME\": \"张小帅\",                \"EXT_REPAIR_UID\": \"001402_张小帅\",                \"allCount\": \"10\"            }        ]    },    \"msg\": \"\",    \"id\": \":RO;\",    \"result\": \"ok\"}";
        String testData = "{\t\"data\": {\t\t\"countWorkInTime\": [{\t\t\t\"NAME\": \"何行\",\t\t\t\"PHOTO\": \"./df?groupValue=%E4%BD%95%E8%A1%8C&fileValue=a2e0f963-4d69-4a89-88fd-fbcc91de11fe&sid=&repositoryName=%21photo-&appId=_bpm.platform&attachment=true&fileName=%E4%BD%95%E8%A1%8C.png&lastModified=1595400275000&lastModified=1595400275000\",\t\t\t\"UID\": \"何行\",\t\t\t\"COUNT\": \"100.00%\"\t\t}, {\t\t\t\"UID\": \"卢峰\",\t\t\t\"photo\": \"../commons/img/photo.png\",\t\t\t\"COUNT\": \"0.00%\",\t\t\t\"NAME\": \"卢峰\"\t\t}, {\t\t\t\"UID\": \"孙大为\",\t\t\t\"photo\": \"../commons/img/photo.png\",\t\t\t\"COUNT\": \"0.00%\",\t\t\t\"NAME\": \"孙大为\"\t\t}],\t\t\"countReplaceInfo\": [{\t\t\t\"NAME\": \"何行\",\t\t\t\"PHOTO\": \"./df?groupValue=%E4%BD%95%E8%A1%8C&fileValue=a2e0f963-4d69-4a89-88fd-fbcc91de11fe&sid=&repositoryName=%21photo-&appId=_bpm.platform&attachment=true&fileName=%E4%BD%95%E8%A1%8C.png&lastModified=1595400275000&lastModified=1595400275000\",\t\t\t\"UID\": \"何行\",\t\t\t\"COUNT\": 6\t\t}, {\t\t\t\"NAME\": \"卢峰\",\t\t\t\"PHOTO\": \"../commons/img/photo.png\",\t\t\t\"UID\": \"卢峰\",\t\t\t\"COUNT\": 1\t\t}, {\t\t\t\"NAME\": \"孙大为\",\t\t\t\"PHOTO\": \"../commons/img/photo.png\",\t\t\t\"UID\": \"孙大为\",\t\t\t\"COUNT\": 1\t\t}]\t},\t\"msg\": \"\",\t\"id\": \":RO;\",\t\"result\": \"ok\"}";
        Gson gson = new Gson();
        UnDoneChartBean unDoneChart = gson.fromJson(testdata, UnDoneChartBean.class);
        List<UnDoneChartBean.DataBean.DataListBean> undoneOrderInfo = unDoneChart.getData().getDataList();



        float groupSpace = 0.08f;
        float barSpace =0.06f; // x4 DataSet
        float barWidth = 0.4f;
        float val1 = 0;
        // (0.4 + 0.06) * 2 + 0.08 = 1.00 -> interval per "group"
        int groupCount = undoneOrderInfo.size();
        int startYear = 0;
        int endYear = startYear + groupCount;


        ArrayList<BarEntry> values1 = new ArrayList<>();





        for (int i = startYear; i < endYear; i++) {
//            values1.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
//            values2.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            if (i == 0) {
                 val1 = Float.valueOf(0);
            }else {
                String unArrivedCount = undoneOrderInfo.get(i-1).getUnArrivedCount();

                val1 = Float.valueOf(unArrivedCount);
            }


            //            float val3 = (float) (Math.random() * mul) + mul / 3;

            values1.add(new BarEntry(i, val1));

        }


        xlabelDataList.clear();
        for (int i = 1; i <= undoneOrderInfo.size(); i++) {
            xlabelDataList.add(undoneOrderInfo.get(i - 1).getUSERNAME());
        }
        ValueFormatter ix = new MyXAxisValueFormatter(xlabelDataList);
        chart.getXAxis().setValueFormatter(ix);





        BarDataSet set1, set2;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);


            set1.setValues(values1);


            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            // create 4 DataSets
            set1 = new BarDataSet(values1, "");
            set1.setColor(Color.rgb(29, 169, 241));
            BarData data = new BarData(set1);
            data.setValueFormatter(new LargeValueFormatter());
            data.setValueTextSize(14f);
            chart.setData(data);
        }

        // specify the width each bar should have
        chart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        chart.getXAxis().setAxisMinimum(startYear);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
//        chart.getXAxis().setAxisMaximum(startYear + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        chart.invalidate();
    }

    private void initBarChar() {
        chart.setOnChartValueSelectedListener(this);
        chart.getDescription().setEnabled(false);
        chart.setPinchZoom(false);
        chart.setTouchEnabled(true);
        chart.setDrawBarShadow(false);


        chart.setDrawGridBackground(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(12f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineColor(getResources().getColor(R.color.colorAccent));
        xAxis.setCenterAxisLabels(false);
        xAxis.setTextSize(15f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue (float value) {
                return String.valueOf((int) value);
            }
        });
        chart.zoom(4, 1f, 0, 0);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setYOffset(0f);
        leftAxis.setAxisMinimum(0f);
         // this replaces setStartAtZero(true)
        leftAxis.setTextSize(14f);
        leftAxis.setLabelCount(1);

        chart.getAxisRight().setEnabled(false);
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
}
