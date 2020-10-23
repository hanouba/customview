package com.hansen.databinding.bean;

import com.hansen.customview.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * @author HanN on 2020/10/21 16:26
 * @email: 1356548475@qq.com
 * @project customview
 * @description: 商品信息
 * @updateuser:
 * @updatedata: 2020/10/21 16:26
 * @updateremark:
 * @version: 2.1.67
 */
public class Goods extends BaseObservable {
    //如果是 public 修饰符，则可以直接在成员变量上方加上 @Bindable 注解
    @Bindable
    public String name;

    //如果是 private 修饰符，则在成员变量的 get 方法上添加 @Bindable 注解
    private String details;

    private float price;

    public Goods(String name, String details, float price) {
        this.name = name;
        this.details = details;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        //只更新本字段
        notifyPropertyChanged(BR.name);
    }
    @Bindable
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
//               //更新所有字段
        notifyChange();
    }
    @Bindable
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
