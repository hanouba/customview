package com.hansen.databinding.bean;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableFloat;

/**
 * @author HanN on 2020/10/22 13:37
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/10/22 13:37
 * @updateremark:
 * @version: 2.1.67
 */
public class ObservableGoods {
    private ObservableField<String> name;

    private ObservableFloat price;

    private ObservableField<String> details;

    public ObservableGoods(String name, float price, String details) {
        this.name = new ObservableField<>(name);
        this.price = new ObservableFloat(price);
        this.details = new ObservableField<>(details);
    }
}
