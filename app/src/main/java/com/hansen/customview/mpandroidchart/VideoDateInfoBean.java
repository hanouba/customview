package com.hansen.customview.mpandroidchart;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * @author HanN on 2020/6/18 10:01
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/6/18 10:01
 * @updateremark:
 * @version: 2.1.67
 */
@Entity
public class VideoDateInfoBean {
    //主键自增长
    @Id(autoincrement = true)
    private Long id;

    private String dataAreaid;
    private String dataAreaName;
    //数据
    private String areaData;
    //日期
    private String areaDate;
    @Generated(hash = 1948671373)
    public VideoDateInfoBean(Long id, String dataAreaid, String dataAreaName,
            String areaData, String areaDate) {
        this.id = id;
        this.dataAreaid = dataAreaid;
        this.dataAreaName = dataAreaName;
        this.areaData = areaData;
        this.areaDate = areaDate;
    }

    public VideoDateInfoBean(String dataAreaName,
                             String areaData, String areaDate) {
        this.dataAreaName = dataAreaName;
        this.areaData = areaData;
        this.areaDate = areaDate;
    }
    @Generated(hash = 738200320)
    public VideoDateInfoBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDataAreaid() {
        return this.dataAreaid;
    }
    public void setDataAreaid(String dataAreaid) {
        this.dataAreaid = dataAreaid;
    }
    public String getDataAreaName() {
        return this.dataAreaName;
    }
    public void setDataAreaName(String dataAreaName) {
        this.dataAreaName = dataAreaName;
    }
    public String getAreaData() {
        return this.areaData;
    }
    public void setAreaData(String areaData) {
        this.areaData = areaData;
    }
    public String getAreaDate() {
        return this.areaDate;
    }
    public void setAreaDate(String areaDate) {
        this.areaDate = areaDate;
    }


}
