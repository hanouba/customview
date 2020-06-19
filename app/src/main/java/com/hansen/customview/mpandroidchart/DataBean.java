package com.hansen.customview.mpandroidchart;

/**
 * @author HanN on 2020/6/18 9:35
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/6/18 9:35
 * @updateremark:
 * @version: 2.1.67
 */
public class DataBean {
    //日期
    private int index;
    //数据
    private String data;


    public DataBean(int index, String data) {
        this.index = index;
        this.data = data;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
