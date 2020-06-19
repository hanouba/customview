package com.hansen.customview.mpandroidchart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.hansen.customview.MainActivity;
import com.hansen.customview.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 模拟实现 ivsom优化项目的 视频在线率设计图
 */
public class VideoRateActivity extends AppCompatActivity {
    String json = "{\t\"data\": {\t\t\"area\": [\"d3157f05-2dcd-4c2f-9690-8fba725fcb45\", \"163f84af-7a8c-4f74-b1d7-42da618cce58\", \"e761bac1-2b4e-4587-bc08-13e42eddd2a5\", \"13f32925-6153-4d1c-b63f-547beb24fc6b\", \"9d8413a3-8371-4e30-bad5-2ec7166e5548\", \"84ba5b7e-87b9-4804-80d1-590b15f0e3fc\", \"4da90921-9429-4c2b-90d4-481eba6d9d57\", \"97deae27-8a41-48cc-a074-e287ad5ca694\", \"b1363557-93a4-4a22-9f65-ce80b5b42ef5\", \"b1500ac2-9308-4cc9-b813-1b1285674b02\", \"31003261-5121-4d72-a34b-861e7136b36f\", \"8e1cbe01-0c68-478b-9763-2002f8f65080\", \"4ed5aca6-ce6e-431b-bf10-593925ad5c31\", \"a1fcb1d5-647c-4d10-8d52-6ffb65f685cf\", \"3b98db63-9081-4f23-8748-e82c49ed92eb\"],\t\t\"rate\": {\t\t\t\"4ed5aca6-ce6e-431b-bf10-593925ad5c31\": [\"0.00\", \"0.00\", \"0.00\", \"0.00\", \"0.00\", \"0.00\", \"0.00\", \"0.00\", \"0.00\", \"0.00\", \"0.00\", \"0.00\"],\t\t\t\"8e1cbe01-0c68-478b-9763-2002f8f65080\": [\"100.00\", \"0.00\", \"0.00\", \"100.00\", \"0.00\", \"100.00\", \"0.00\", \"100.00\", \"100.00\", \"0.00\", \"0.00\", \"100.00\"],\t\t\t\"163f84af-7a8c-4f74-b1d7-42da618cce58\": [\"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\"],\t\t\t\"13f32925-6153-4d1c-b63f-547beb24fc6b\": [\"0.00\", \"0.00\", \"0.00\", \"0.00\", \"0.00\", \"0.00\", \"0.00\", \"0.00\", \"0.00\", \"0.00\", \"0.00\", \"0.00\"],\t\t\t\"3b98db63-9081-4f23-8748-e82c49ed92eb\": [\"0.77\", \"100.00\", \"0.77\", \"100.00\", \"0.77\", \"100.00\", \"0.77\", \"100.00\", \"0.77\", \"100.00\", \"100.00\", \"0.77\"],\t\t\t\"b1500ac2-9308-4cc9-b813-1b1285674b02\": [\"0.00\", \"100.00\", \"100.00\", \"0.00\", \"100.00\", \"0.00\", \"100.00\", \"0.00\", \"100.00\", \"0.00\", \"100.00\", \"0.00\"],\t\t\t\"e761bac1-2b4e-4587-bc08-13e42eddd2a5\": [\"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\"],\t\t\t\"9d8413a3-8371-4e30-bad5-2ec7166e5548\": [\"0.00\", \"100.00\", \"0.00\", \"100.00\", \"100.00\", \"0.00\", \"100.00\", \"0.00\", \"0.00\", \"100.00\", \"0.00\", \"100.00\"],\t\t\t\"31003261-5121-4d72-a34b-861e7136b36f\": [\"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\"],\t\t\t\"b1363557-93a4-4a22-9f65-ce80b5b42ef5\": [\"98.31\", \"100.00\", \"100.00\", \"98.31\", \"98.31\", \"100.00\", \"98.31\", \"100.00\", \"98.31\", \"100.00\", \"100.00\", \"98.31\"],\t\t\t\"d3157f05-2dcd-4c2f-9690-8fba725fcb45\": [\"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\"],\t\t\t\"a1fcb1d5-647c-4d10-8d52-6ffb65f685cf\": [\"100.00\", \"0.00\", \"0.00\", \"100.00\", \"0.00\", \"100.00\", \"100.00\", \"0.00\", \"0.00\", \"100.00\", \"100.00\", \"0.00\"],\t\t\t\"84ba5b7e-87b9-4804-80d1-590b15f0e3fc\": [\"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\"],\t\t\t\"97deae27-8a41-48cc-a074-e287ad5ca694\": [\"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\", \"100.00\"],\t\t\t\"4da90921-9429-4c2b-90d4-481eba6d9d57\": [\"0.00\", \"100.00\", \"0.00\", \"100.00\", \"100.00\", \"0.00\", \"100.00\", \"0.00\", \"100.00\", \"0.00\", \"100.00\", \"0.00\"]\t\t},\t\t\"areaName\": [\"3城区电警\", \"4治安卡口\", \"微创研发二部\", \"7乡镇街道（镇区）\", \"100\", \"6高速铁路\", \"666\", \"5重点单位\", \"1城区道路\", \"测试区域4\", \"2高空瞭望\", \"123\", \"111111\", \"测试区域3\", \"测试区域\"],\t\t\"time\": {\t\t\t\"2020-06-11\": \"2020-06-11\",\t\t\t\"2020-06-12\": \"2020-06-12\",\t\t\t\"2020-06-13\": \"2020-06-13\",\t\t\t\"2020-06-14\": \"2020-06-14\",\t\t\t\"2020-06-15\": \"2020-06-15\",\t\t\t\"2020-06-16\": \"2020-06-16\"\t\t}\t},\t\"msg\": \"\",\t\"id\": \":RO;\",\t\"result\": \"ok\"}";
    private BarChart chart;
    private HashMap<Integer ,String> map = new HashMap<>();
    private List<DataBean> dataLists = new ArrayList<>();
    private int date;
    private GreenHelper instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_rate2);
        initView();
        initBarChar();
        instance = GreenHelper.getInstance();

        instance.clearDb();
        initData();

    }
    private void initData() {
        Gson gson = new Gson();
        VideoBean videoBean = gson.fromJson(json, VideoBean.class);


        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONObject dataObject = jsonObject.getJSONObject("data");
            //得到区域id
            JSONArray areaArray = dataObject.getJSONArray("area");
            //遍历区域id
            for (int i = 0; i < areaArray.length(); i++) {
                //得到单个id
                String areaId = areaArray.getString(i);
                //获取每个区域的数据
                JSONObject rateObject = dataObject.getJSONObject("rate");
                //通过区域id 查询到对应的区域数据
                JSONArray dataRoot = rateObject.getJSONArray(areaArray.getString(0));
                //有几天
                date = dataRoot.length();

                JSONArray dataArray = rateObject.getJSONArray(areaArray.getString(i));
                //dataArray的长度就是横坐尺寸
                for (int j = 0; j < dataArray.length(); j++) {
                    instance.insertVideoInfo(j+"",dataArray.getString(j),areaId,i+"name"+j);
                }








                //存储相同数据的集合
            }

            for (int i = 0; i < date; i++) {
                List<VideoDateInfoBean> sameDateData = instance.getSameDateData(i + "");
                for (int j = 0; j < sameDateData.size(); j++) {
                    String areaData = sameDateData.get(j).getAreaData();
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }





    }

    private void showBarChart(JSONArray dataArray, String indexData) {
        float start = 1f;

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = (int) start; i < start + dataArray.length(); i++) {
            values.add(new BarEntry(i, Float.parseFloat(indexData)));

        }

        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet(values, "The year 2017");
            set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);

            data.setBarWidth(0.9f);

            chart.setData(data);
        }
    }

    private void initView() {
        chart = findViewById(R.id.bar_view);

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
}
