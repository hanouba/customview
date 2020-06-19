package com.hansen.customview.mpandroidchart;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author HanN on 2020/6/17 13:54
 * @email: 1356548475@qq.com
 * @project customview
 * @description: 视频在线率的实体类
 * @updateuser:
 * @updatedata: 2020/6/17 13:54
 * @updateremark:
 * @version: 2.1.67
 */
public class VideoBean {

    /**
     * data : {"area":["d3157f05-2dcd-4c2f-9690-8fba725fcb45","163f84af-7a8c-4f74-b1d7-42da618cce58","e761bac1-2b4e-4587-bc08-13e42eddd2a5","13f32925-6153-4d1c-b63f-547beb24fc6b","9d8413a3-8371-4e30-bad5-2ec7166e5548","84ba5b7e-87b9-4804-80d1-590b15f0e3fc","4da90921-9429-4c2b-90d4-481eba6d9d57","97deae27-8a41-48cc-a074-e287ad5ca694","b1363557-93a4-4a22-9f65-ce80b5b42ef5","b1500ac2-9308-4cc9-b813-1b1285674b02","31003261-5121-4d72-a34b-861e7136b36f","8e1cbe01-0c68-478b-9763-2002f8f65080","4ed5aca6-ce6e-431b-bf10-593925ad5c31","a1fcb1d5-647c-4d10-8d52-6ffb65f685cf","3b98db63-9081-4f23-8748-e82c49ed92eb"],"rate":{"4ed5aca6-ce6e-431b-bf10-593925ad5c31":["0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00"],"8e1cbe01-0c68-478b-9763-2002f8f65080":["100.00","0.00","0.00","100.00","0.00","100.00","0.00","100.00","100.00","0.00","0.00","100.00"],"163f84af-7a8c-4f74-b1d7-42da618cce58":["100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00"],"13f32925-6153-4d1c-b63f-547beb24fc6b":["0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00"],"3b98db63-9081-4f23-8748-e82c49ed92eb":["0.77","100.00","0.77","100.00","0.77","100.00","0.77","100.00","0.77","100.00","100.00","0.77"],"b1500ac2-9308-4cc9-b813-1b1285674b02":["0.00","100.00","100.00","0.00","100.00","0.00","100.00","0.00","100.00","0.00","100.00","0.00"],"e761bac1-2b4e-4587-bc08-13e42eddd2a5":["100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00"],"9d8413a3-8371-4e30-bad5-2ec7166e5548":["0.00","100.00","0.00","100.00","100.00","0.00","100.00","0.00","0.00","100.00","0.00","100.00"],"31003261-5121-4d72-a34b-861e7136b36f":["100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00"],"b1363557-93a4-4a22-9f65-ce80b5b42ef5":["98.31","100.00","100.00","98.31","98.31","100.00","98.31","100.00","98.31","100.00","100.00","98.31"],"d3157f05-2dcd-4c2f-9690-8fba725fcb45":["100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00"],"a1fcb1d5-647c-4d10-8d52-6ffb65f685cf":["100.00","0.00","0.00","100.00","0.00","100.00","100.00","0.00","0.00","100.00","100.00","0.00"],"84ba5b7e-87b9-4804-80d1-590b15f0e3fc":["100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00"],"97deae27-8a41-48cc-a074-e287ad5ca694":["100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00"],"4da90921-9429-4c2b-90d4-481eba6d9d57":["0.00","100.00","0.00","100.00","100.00","0.00","100.00","0.00","100.00","0.00","100.00","0.00"]},"areaName":["3城区电警","4治安卡口","微创研发二部","7乡镇街道（镇区）","100","6高速铁路","666","5重点单位","1城区道路","测试区域4","2高空瞭望","123","111111","测试区域3","测试区域"],"time":{"2020-06-11":"2020-06-11","2020-06-12":"2020-06-12","2020-06-13":"2020-06-13","2020-06-14":"2020-06-14","2020-06-15":"2020-06-15","2020-06-16":"2020-06-16"}}
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
        /**
         * area : ["d3157f05-2dcd-4c2f-9690-8fba725fcb45","163f84af-7a8c-4f74-b1d7-42da618cce58","e761bac1-2b4e-4587-bc08-13e42eddd2a5","13f32925-6153-4d1c-b63f-547beb24fc6b","9d8413a3-8371-4e30-bad5-2ec7166e5548","84ba5b7e-87b9-4804-80d1-590b15f0e3fc","4da90921-9429-4c2b-90d4-481eba6d9d57","97deae27-8a41-48cc-a074-e287ad5ca694","b1363557-93a4-4a22-9f65-ce80b5b42ef5","b1500ac2-9308-4cc9-b813-1b1285674b02","31003261-5121-4d72-a34b-861e7136b36f","8e1cbe01-0c68-478b-9763-2002f8f65080","4ed5aca6-ce6e-431b-bf10-593925ad5c31","a1fcb1d5-647c-4d10-8d52-6ffb65f685cf","3b98db63-9081-4f23-8748-e82c49ed92eb"]
         * rate : {"4ed5aca6-ce6e-431b-bf10-593925ad5c31":["0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00"],"8e1cbe01-0c68-478b-9763-2002f8f65080":["100.00","0.00","0.00","100.00","0.00","100.00","0.00","100.00","100.00","0.00","0.00","100.00"],"163f84af-7a8c-4f74-b1d7-42da618cce58":["100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00"],"13f32925-6153-4d1c-b63f-547beb24fc6b":["0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00"],"3b98db63-9081-4f23-8748-e82c49ed92eb":["0.77","100.00","0.77","100.00","0.77","100.00","0.77","100.00","0.77","100.00","100.00","0.77"],"b1500ac2-9308-4cc9-b813-1b1285674b02":["0.00","100.00","100.00","0.00","100.00","0.00","100.00","0.00","100.00","0.00","100.00","0.00"],"e761bac1-2b4e-4587-bc08-13e42eddd2a5":["100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00"],"9d8413a3-8371-4e30-bad5-2ec7166e5548":["0.00","100.00","0.00","100.00","100.00","0.00","100.00","0.00","0.00","100.00","0.00","100.00"],"31003261-5121-4d72-a34b-861e7136b36f":["100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00"],"b1363557-93a4-4a22-9f65-ce80b5b42ef5":["98.31","100.00","100.00","98.31","98.31","100.00","98.31","100.00","98.31","100.00","100.00","98.31"],"d3157f05-2dcd-4c2f-9690-8fba725fcb45":["100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00"],"a1fcb1d5-647c-4d10-8d52-6ffb65f685cf":["100.00","0.00","0.00","100.00","0.00","100.00","100.00","0.00","0.00","100.00","100.00","0.00"],"84ba5b7e-87b9-4804-80d1-590b15f0e3fc":["100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00"],"97deae27-8a41-48cc-a074-e287ad5ca694":["100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00","100.00"],"4da90921-9429-4c2b-90d4-481eba6d9d57":["0.00","100.00","0.00","100.00","100.00","0.00","100.00","0.00","100.00","0.00","100.00","0.00"]}
         * areaName : ["3城区电警","4治安卡口","微创研发二部","7乡镇街道（镇区）","100","6高速铁路","666","5重点单位","1城区道路","测试区域4","2高空瞭望","123","111111","测试区域3","测试区域"]
         * time : {"2020-06-11":"2020-06-11","2020-06-12":"2020-06-12","2020-06-13":"2020-06-13","2020-06-14":"2020-06-14","2020-06-15":"2020-06-15","2020-06-16":"2020-06-16"}
         */

        private RateBean rate;
        private TimeBean time;
        private List<String> area;
        private List<String> areaName;

        public RateBean getRate() {
            return rate;
        }

        public void setRate(RateBean rate) {
            this.rate = rate;
        }

        public TimeBean getTime() {
            return time;
        }

        public void setTime(TimeBean time) {
            this.time = time;
        }

        public List<String> getArea() {
            return area;
        }

        public void setArea(List<String> area) {
            this.area = area;
        }

        public List<String> getAreaName() {
            return areaName;
        }

        public void setAreaName(List<String> areaName) {
            this.areaName = areaName;
        }

        public static class RateBean {
            @SerializedName("4ed5aca6-ce6e-431b-bf10-593925ad5c31")
            private List<String> _$4ed5aca6ce6e431bbf10593925ad5c31;
            @SerializedName("8e1cbe01-0c68-478b-9763-2002f8f65080")
            private List<String> _$8e1cbe010c68478b97632002f8f65080;
            @SerializedName("163f84af-7a8c-4f74-b1d7-42da618cce58")
            private List<String> _$163f84af7a8c4f74b1d742da618cce58;
            @SerializedName("13f32925-6153-4d1c-b63f-547beb24fc6b")
            private List<String> _$13f3292561534d1cb63f547beb24fc6b;
            @SerializedName("3b98db63-9081-4f23-8748-e82c49ed92eb")
            private List<String> _$3b98db6390814f238748e82c49ed92eb;
            @SerializedName("b1500ac2-9308-4cc9-b813-1b1285674b02")
            private List<String> b1500ac293084cc9b8131b1285674b02;
            @SerializedName("e761bac1-2b4e-4587-bc08-13e42eddd2a5")
            private List<String> e761bac12b4e4587bc0813e42eddd2a5;
            @SerializedName("9d8413a3-8371-4e30-bad5-2ec7166e5548")
            private List<String> _$9d8413a383714e30bad52ec7166e5548;
            @SerializedName("31003261-5121-4d72-a34b-861e7136b36f")
            private List<String> _$3100326151214d72a34b861e7136b36f;
            @SerializedName("b1363557-93a4-4a22-9f65-ce80b5b42ef5")
            private List<String> b136355793a44a229f65ce80b5b42ef5;
            @SerializedName("d3157f05-2dcd-4c2f-9690-8fba725fcb45")
            private List<String> d3157f052dcd4c2f96908fba725fcb45;
            @SerializedName("a1fcb1d5-647c-4d10-8d52-6ffb65f685cf")
            private List<String> a1fcb1d5647c4d108d526ffb65f685cf;
            @SerializedName("84ba5b7e-87b9-4804-80d1-590b15f0e3fc")
            private List<String> _$84ba5b7e87b9480480d1590b15f0e3fc;
            @SerializedName("97deae27-8a41-48cc-a074-e287ad5ca694")
            private List<String> _$97deae278a4148cca074e287ad5ca694;
            @SerializedName("4da90921-9429-4c2b-90d4-481eba6d9d57")
            private List<String> _$4da9092194294c2b90d4481eba6d9d57;

            public List<String> get_$4ed5aca6ce6e431bbf10593925ad5c31() {
                return _$4ed5aca6ce6e431bbf10593925ad5c31;
            }

            public void set_$4ed5aca6ce6e431bbf10593925ad5c31(List<String> _$4ed5aca6ce6e431bbf10593925ad5c31) {
                this._$4ed5aca6ce6e431bbf10593925ad5c31 = _$4ed5aca6ce6e431bbf10593925ad5c31;
            }

            public List<String> get_$8e1cbe010c68478b97632002f8f65080() {
                return _$8e1cbe010c68478b97632002f8f65080;
            }

            public void set_$8e1cbe010c68478b97632002f8f65080(List<String> _$8e1cbe010c68478b97632002f8f65080) {
                this._$8e1cbe010c68478b97632002f8f65080 = _$8e1cbe010c68478b97632002f8f65080;
            }

            public List<String> get_$163f84af7a8c4f74b1d742da618cce58() {
                return _$163f84af7a8c4f74b1d742da618cce58;
            }

            public void set_$163f84af7a8c4f74b1d742da618cce58(List<String> _$163f84af7a8c4f74b1d742da618cce58) {
                this._$163f84af7a8c4f74b1d742da618cce58 = _$163f84af7a8c4f74b1d742da618cce58;
            }

            public List<String> get_$13f3292561534d1cb63f547beb24fc6b() {
                return _$13f3292561534d1cb63f547beb24fc6b;
            }

            public void set_$13f3292561534d1cb63f547beb24fc6b(List<String> _$13f3292561534d1cb63f547beb24fc6b) {
                this._$13f3292561534d1cb63f547beb24fc6b = _$13f3292561534d1cb63f547beb24fc6b;
            }

            public List<String> get_$3b98db6390814f238748e82c49ed92eb() {
                return _$3b98db6390814f238748e82c49ed92eb;
            }

            public void set_$3b98db6390814f238748e82c49ed92eb(List<String> _$3b98db6390814f238748e82c49ed92eb) {
                this._$3b98db6390814f238748e82c49ed92eb = _$3b98db6390814f238748e82c49ed92eb;
            }

            public List<String> getB1500ac293084cc9b8131b1285674b02() {
                return b1500ac293084cc9b8131b1285674b02;
            }

            public void setB1500ac293084cc9b8131b1285674b02(List<String> b1500ac293084cc9b8131b1285674b02) {
                this.b1500ac293084cc9b8131b1285674b02 = b1500ac293084cc9b8131b1285674b02;
            }

            public List<String> getE761bac12b4e4587bc0813e42eddd2a5() {
                return e761bac12b4e4587bc0813e42eddd2a5;
            }

            public void setE761bac12b4e4587bc0813e42eddd2a5(List<String> e761bac12b4e4587bc0813e42eddd2a5) {
                this.e761bac12b4e4587bc0813e42eddd2a5 = e761bac12b4e4587bc0813e42eddd2a5;
            }

            public List<String> get_$9d8413a383714e30bad52ec7166e5548() {
                return _$9d8413a383714e30bad52ec7166e5548;
            }

            public void set_$9d8413a383714e30bad52ec7166e5548(List<String> _$9d8413a383714e30bad52ec7166e5548) {
                this._$9d8413a383714e30bad52ec7166e5548 = _$9d8413a383714e30bad52ec7166e5548;
            }

            public List<String> get_$3100326151214d72a34b861e7136b36f() {
                return _$3100326151214d72a34b861e7136b36f;
            }

            public void set_$3100326151214d72a34b861e7136b36f(List<String> _$3100326151214d72a34b861e7136b36f) {
                this._$3100326151214d72a34b861e7136b36f = _$3100326151214d72a34b861e7136b36f;
            }

            public List<String> getB136355793a44a229f65ce80b5b42ef5() {
                return b136355793a44a229f65ce80b5b42ef5;
            }

            public void setB136355793a44a229f65ce80b5b42ef5(List<String> b136355793a44a229f65ce80b5b42ef5) {
                this.b136355793a44a229f65ce80b5b42ef5 = b136355793a44a229f65ce80b5b42ef5;
            }

            public List<String> getD3157f052dcd4c2f96908fba725fcb45() {
                return d3157f052dcd4c2f96908fba725fcb45;
            }

            public void setD3157f052dcd4c2f96908fba725fcb45(List<String> d3157f052dcd4c2f96908fba725fcb45) {
                this.d3157f052dcd4c2f96908fba725fcb45 = d3157f052dcd4c2f96908fba725fcb45;
            }

            public List<String> getA1fcb1d5647c4d108d526ffb65f685cf() {
                return a1fcb1d5647c4d108d526ffb65f685cf;
            }

            public void setA1fcb1d5647c4d108d526ffb65f685cf(List<String> a1fcb1d5647c4d108d526ffb65f685cf) {
                this.a1fcb1d5647c4d108d526ffb65f685cf = a1fcb1d5647c4d108d526ffb65f685cf;
            }

            public List<String> get_$84ba5b7e87b9480480d1590b15f0e3fc() {
                return _$84ba5b7e87b9480480d1590b15f0e3fc;
            }

            public void set_$84ba5b7e87b9480480d1590b15f0e3fc(List<String> _$84ba5b7e87b9480480d1590b15f0e3fc) {
                this._$84ba5b7e87b9480480d1590b15f0e3fc = _$84ba5b7e87b9480480d1590b15f0e3fc;
            }

            public List<String> get_$97deae278a4148cca074e287ad5ca694() {
                return _$97deae278a4148cca074e287ad5ca694;
            }

            public void set_$97deae278a4148cca074e287ad5ca694(List<String> _$97deae278a4148cca074e287ad5ca694) {
                this._$97deae278a4148cca074e287ad5ca694 = _$97deae278a4148cca074e287ad5ca694;
            }

            public List<String> get_$4da9092194294c2b90d4481eba6d9d57() {
                return _$4da9092194294c2b90d4481eba6d9d57;
            }

            public void set_$4da9092194294c2b90d4481eba6d9d57(List<String> _$4da9092194294c2b90d4481eba6d9d57) {
                this._$4da9092194294c2b90d4481eba6d9d57 = _$4da9092194294c2b90d4481eba6d9d57;
            }
        }

        public static class TimeBean {
            /**
             * 2020-06-11 : 2020-06-11
             * 2020-06-12 : 2020-06-12
             * 2020-06-13 : 2020-06-13
             * 2020-06-14 : 2020-06-14
             * 2020-06-15 : 2020-06-15
             * 2020-06-16 : 2020-06-16
             */

            @SerializedName("2020-06-11")
            private String _$20200611;
            @SerializedName("2020-06-12")
            private String _$20200612;
            @SerializedName("2020-06-13")
            private String _$20200613;
            @SerializedName("2020-06-14")
            private String _$20200614;
            @SerializedName("2020-06-15")
            private String _$20200615;
            @SerializedName("2020-06-16")
            private String _$20200616;

            public String get_$20200611() {
                return _$20200611;
            }

            public void set_$20200611(String _$20200611) {
                this._$20200611 = _$20200611;
            }

            public String get_$20200612() {
                return _$20200612;
            }

            public void set_$20200612(String _$20200612) {
                this._$20200612 = _$20200612;
            }

            public String get_$20200613() {
                return _$20200613;
            }

            public void set_$20200613(String _$20200613) {
                this._$20200613 = _$20200613;
            }

            public String get_$20200614() {
                return _$20200614;
            }

            public void set_$20200614(String _$20200614) {
                this._$20200614 = _$20200614;
            }

            public String get_$20200615() {
                return _$20200615;
            }

            public void set_$20200615(String _$20200615) {
                this._$20200615 = _$20200615;
            }

            public String get_$20200616() {
                return _$20200616;
            }

            public void set_$20200616(String _$20200616) {
                this._$20200616 = _$20200616;
            }
        }
    }
}
