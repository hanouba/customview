package com.hansen.customview.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ConvertUtils;
import com.hansen.customview.R;
import com.hansen.customview.bean.AbnormalProcessBean;
import com.hansen.customview.view.FLowLineView;
import com.hansen.customview.view.FlowChart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 上个月下个月 月份选择
 * Calendar 自身就有这个功能 但是要先设置语法年份,再获取;
 * 获取到的月份会比设置的语法大一个月;
 */
public class TestActivity extends AppCompatActivity {

    private Date currentTime;
    private String currentYear;
    private String currentMonth;
    private TextView tvTime;
    private SimpleDateFormat mFormatterMonth = new SimpleDateFormat("MM");
    private SimpleDateFormat mFormatterYear = new SimpleDateFormat("yyyy");
    private Calendar calendar;
    private FLowLineView flowLineView;
    //当前进度节点 默认是0
    private int flowTAG = 0;
    //存放节点数据
    List<FlowChart> charts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Calendar calendar = Calendar.getInstance();
        currentTime = calendar.getTime();
        currentYear = mFormatterYear.format(currentTime);
        currentMonth = mFormatterMonth.format(currentTime);
        tvTime = findViewById(R.id.tv_time);
        tvTime.setText(currentYear+"--"+currentMonth);
    }

    private void initFlowView() {

//        flowLineView = findViewById(R.id.flow_line_view);
        String abnormalProcess = "[{\"name\":\"报障\",\"status\":\"0\"},{\"name\":\"报修\",\"status\":\"0\"},{\"name\":\"工单\",\"status\":\"0\"}]";


        List<AbnormalProcessBean> abnormalProcessBeans = JSON.parseArray(abnormalProcess, AbnormalProcessBean.class);
        for (int i = 0; i < abnormalProcessBeans.size(); i++) {
            FlowChart chart = new FlowChart();
            chart.setTopName("");
            chart.setName("");
            chart.setBottomName(abnormalProcessBeans.get(i).getName());
            chart.setTime("");
            charts.add(chart);
            if (abnormalProcessBeans.get(i).getStatus().equals("1")) {
                flowTAG = i;
            }

        }

        flowLineView.setDoubleBottom(true);
        flowLineView.setTextSize(ConvertUtils.dp2px(12));//文字大小
        flowLineView.setFlowCharts(charts);
        flowLineView.setTag(flowTAG);
        Log.i("newDate", "flowTAG: " +flowTAG);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    public void lastMonth(View view) {
        String date = newDate(Integer.parseInt(currentYear), Integer.parseInt(currentMonth), -1);
        currentYear =date.split("\\,")[0];
        currentMonth =date.split("\\,")[1];
        Log.i("newDate", "lastMonth: " + currentYear+"month"+currentMonth);
    }
    public void nextMonth(View view) {
        String date = newDate(Integer.parseInt(currentYear), Integer.parseInt(currentMonth), +1);
        currentYear =date.split("\\,")[0];
        currentMonth =date.split("\\,")[1];
        Log.i("newDate", "nextMonth: " + currentYear+"month"+currentMonth);
    }
    public String newDate(int year,int month, int num) {

        calendar = Calendar.getInstance();


        int newMonth = month ;
        if (num < 0) {
             newMonth = month -1;
        }else {
             newMonth = month +1;
        }
        if (newMonth == 0) {
            newMonth = 12;
            year--;
        }else if (newMonth == 13) {
            newMonth = 1;
            year++;
        }

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, newMonth-1);


        Log.i("newDate", "calendar: " + calendar.getTime());
       return year+","+newMonth;
    }
}