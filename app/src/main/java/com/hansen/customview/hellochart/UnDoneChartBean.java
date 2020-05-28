package com.hansen.customview.hellochart;

import java.util.List;

/**
 * @author HanN on 2020/5/27 16:56
 * @email: 1356548475@qq.com
 * @project ywzs
 * @description: 未完成工单数据
 * @updateuser:
 * @updatedata: 2020/5/27 16:56
 * @updateremark:
 * @version: 2.1.67
 */
public class UnDoneChartBean {

    /**
     * data : {"dataList":[{"unArrivedCount":"3","arrivedCount":"2","USERNAME":"维护组长","EXT_REPAIR_UID":"001402_维护组长","allCount":"5"}]}
     * msg :
     * id : :RO;
     * result : ok
     */

    private DataBean data;
    private String msg;
    private String id;
    private String result;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static class DataBean {
        private List<DataListBean> dataList;

        public List<DataListBean> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListBean> dataList) {
            this.dataList = dataList;
        }

        public static class DataListBean {
            /**
             * unArrivedCount : 3
             * arrivedCount : 2
             * USERNAME : 维护组长
             * EXT_REPAIR_UID : 001402_维护组长
             * allCount : 5
             */

            private String unArrivedCount;
            private String arrivedCount;
            private String USERNAME;
            private String EXT_REPAIR_UID;
            private String allCount;

            public String getUnArrivedCount() {
                return unArrivedCount;
            }

            public void setUnArrivedCount(String unArrivedCount) {
                this.unArrivedCount = unArrivedCount;
            }

            public String getArrivedCount() {
                return arrivedCount;
            }

            public void setArrivedCount(String arrivedCount) {
                this.arrivedCount = arrivedCount;
            }

            public String getUSERNAME() {
                return USERNAME;
            }

            public void setUSERNAME(String USERNAME) {
                this.USERNAME = USERNAME;
            }

            public String getEXT_REPAIR_UID() {
                return EXT_REPAIR_UID;
            }

            public void setEXT_REPAIR_UID(String EXT_REPAIR_UID) {
                this.EXT_REPAIR_UID = EXT_REPAIR_UID;
            }

            public String getAllCount() {
                return allCount;
            }

            public void setAllCount(String allCount) {
                this.allCount = allCount;
            }
        }
    }
}
