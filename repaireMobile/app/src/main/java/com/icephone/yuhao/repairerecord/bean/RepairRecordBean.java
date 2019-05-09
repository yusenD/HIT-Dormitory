package com.icephone.yuhao.repairerecord.bean;

import java.io.Serializable;
import java.util.List;

public class RepairRecordBean implements Serializable {

    /**
     * code : 200
     * msg : 查询成功
     * data : [{"_id":"5bcc5ea1775c42d5c8480028","time":"20181007","site_name":"南大冉信用社","repair_pro":"电视监控","repair_person":"林雨","site_person":"张三，王五","center_name":"清苑联社","device":"电视监控","fix_state":"摄像机无图像，保修期内进行更换","return_fix":"返厂维修","fix_cost":0,"return_time":"未返厂","warranty":"是"},{"_id":"5bcc5f23775c42d5c848002a","time":"20181007","site_name":"南大冉信用社","repair_pro":"电视监控","repair_person":"林雨","site_person":"张三，王五","center_name":"清苑联社","device":"电视监控","fix_state":"摄像机无图像，保修期内进行更换","return_fix":"不返厂维修","fix_cost":0,"return_time":"未返厂","warranty":"是"},{"_id":"5bf0ee84f120772bd90c8e67","time":"20181118","site_name":"中心储蓄所","repair_pro":"周界报警，电视维修","repair_person":"林雨","site_person":"小王","center_name":"顺平联社","device":"柜员枪机，网线","fix_state":"维修","return_fix":"未返厂维修","fix_cost":100,"return_time":"未填写","warranty":"是"},{"_id":"5bf0eef5f120772bd90c8e68","time":"20181118","site_name":"中心储蓄所","repair_pro":"高清摄像机","repair_person":"林雨","site_person":"小张","center_name":"顺平联社","device":"红外摄像机","fix_state":"维修","return_fix":"未返厂维修","fix_cost":200,"return_time":"未填写","warranty":"是"},{"_id":"5bf0ef7df120772bd90c8e6a","time":"20181118","site_name":"白龙信用社","repair_pro":"周界报警","repair_person":"林雨","site_person":"小张","center_name":"满城联社","device":"柜员枪机","fix_state":"维修","return_fix":"返厂维修","fix_cost":200,"return_time":"未填写","warranty":"是"},{"_id":"5bf1172ff120772bd90c8e72","time":"20181118","site_name":"第一储蓄所","repair_pro":"高清摄像机","repair_person":"林雨","site_person":"小张","center_name":"清苑联社","device":"柜员枪机","fix_state":"枪机换1个","return_fix":"未返厂维修","fix_cost":200,"return_time":"未填写","warranty":"是"},{"_id":"5bf12fc1f120772bd90c8e73","time":"20181118","site_name":"中心储蓄所","repair_pro":"高清摄像机","repair_person":"林雨，李艳辉","site_person":"李","center_name":"顺平联社","device":"柜员枪机","fix_state":"窆","return_fix":"未返厂维修","fix_cost":220,"return_time":"未填写","warranty":"是"},{"_id":"5bf12ff7f120772bd90c8e74","time":"20181118","site_name":"王安镇信用社","repair_pro":"高清摄像机","repair_person":"李艳辉","site_person":"张","center_name":"涞源联社","device":"柜员枪机","fix_state":"李","return_fix":"返厂维修","fix_cost":300,"return_time":"未填写","warranty":"是"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * _id : 5bcc5ea1775c42d5c8480028
         * time : 20181007
         * site_name : 南大冉信用社
         * repair_pro : 电视监控
         * repair_person : 林雨
         * site_person : 张三，王五
         * center_name : 清苑联社
         * device : 电视监控
         * fix_state : 摄像机无图像，保修期内进行更换
         * return_fix : 返厂维修
         * fix_cost : 0
         * return_time : 未返厂
         * warranty : 是
         */

        private String _id;
        private String time;
        private String site_name;
        private String repair_pro;
        private String repair_person;
        private String site_person;
        private String center_name;
        private String device;
        private String fix_state;
        private String return_fix;
        private int fix_cost;
        private String return_time;
        private String warranty;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getSite_name() {
            return site_name;
        }

        public void setSite_name(String site_name) {
            this.site_name = site_name;
        }

        public String getRepair_pro() {
            return repair_pro;
        }

        public void setRepair_pro(String repair_pro) {
            this.repair_pro = repair_pro;
        }

        public String getRepair_person() {
            return repair_person;
        }

        public void setRepair_person(String repair_person) {
            this.repair_person = repair_person;
        }

        public String getSite_person() {
            return site_person;
        }

        public void setSite_person(String site_person) {
            this.site_person = site_person;
        }

        public String getCenter_name() {
            return center_name;
        }

        public void setCenter_name(String center_name) {
            this.center_name = center_name;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String getFix_state() {
            return fix_state;
        }

        public void setFix_state(String fix_state) {
            this.fix_state = fix_state;
        }

        public String getReturn_fix() {
            return return_fix;
        }

        public void setReturn_fix(String return_fix) {
            this.return_fix = return_fix;
        }

        public int getFix_cost() {
            return fix_cost;
        }

        public void setFix_cost(int fix_cost) {
            this.fix_cost = fix_cost;
        }

        public String getReturn_time() {
            return return_time;
        }

        public void setReturn_time(String return_time) {
            this.return_time = return_time;
        }

        public String getWarranty() {
            return warranty;
        }

        public void setWarranty(String warranty) {
            this.warranty = warranty;
        }
    }
}
