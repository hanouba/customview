package com.hansen.customview.hellochart;

import java.util.List;

/**
 * Created by lijiao on 2018/12/25
 * Describe: 第四个tab 页面   图表页面bean
 */
public class ChartBean {
    List<ChartInfo> countReplaceInfo;
    List<ChartInfo> countWorkInTime;

    public List<ChartInfo> getCountReplaceInfo() {
        return countReplaceInfo;
    }

    public void setCountReplaceInfo(List<ChartInfo> countReplaceInfo) {
        this.countReplaceInfo = countReplaceInfo;
    }

    public List<ChartInfo> getCountWorkInTime() {
        return countWorkInTime;
    }

    public void setCountWorkInTime(List<ChartInfo> countWorkInTime) {
        this.countWorkInTime = countWorkInTime;
    }

    public static class ChartInfo {
        String PHOTO;
        String COUNT;
        String NAME;
        String rankNumber;

        public String getRankNumber() {
            return rankNumber;
        }

        public void setRankNumber(String rankNumber) {
            this.rankNumber = rankNumber;
        }

        public String getPhoto() {
            return PHOTO;
        }

        public void setPhoto(String photo) {
            this.PHOTO = photo;
        }

        public String getCOUNT() {
            return COUNT;
        }

        public void setCOUNT(String COUNT) {
            this.COUNT = COUNT;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }
    }
}
