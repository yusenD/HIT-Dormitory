package com.icephone.yuhao.repairerecord.bean;

import java.io.Serializable;
import java.util.List;

public class RepairRecordBean {


    /**
     * code : 200
     * msg : 查询成功
     * data : [{"_id":"5ce12d88d7ad221064fc03a3","preson_phone":"13720881888","fix_state":"未完成","site_name":"221","start_time":"2018-09-19 21:50:53","site_person":"张","device":"床","repair_person":"赵波","dormitory_name":"八公寓","end_time":"2018-09-22 21:50:53","repair_state":0,"repair_reverse":"还行"},{"_id":"5ce12d88d7ad221064fc03a4","preson_phone":"13922107436","fix_state":"部分完成","site_name":"111","start_time":"2018-09-20 20:00:05","site_person":"李*","device":"窗帘","repair_person":"赵波","dormitory_name":"十公寓","end_time":"2018-09-20 20:00:05","repair_state":1,"repair_reverse":"该用户未提交反馈"}]
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
         * _id : 5ce12d88d7ad221064fc03a3
         * preson_phone : 13720881888
         * fix_state : 未完成
         * site_name : 221
         * start_time : 2018-09-19 21:50:53
         * site_person : 张
         * device : 床
         * repair_person : 赵波
         * dormitory_name : 八公寓
         * end_time : 2018-09-22 21:50:53
         * repair_state : 0
         * repair_reverse : 还行
         */

        private String _id;
        private String preson_phone;
        private String fix_state;
        private String site_name;
        private String start_time;
        private String site_person;
        private String device;
        private String repair_person;
        private String dormitory_name;
        private String end_time;
        private String repair_state;
        private String repair_reverse;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getPreson_phone() {
            return preson_phone;
        }

        public void setPreson_phone(String preson_phone) {
            this.preson_phone = preson_phone;
        }

        public String getFix_state() {
            return fix_state;
        }

        public void setFix_state(String fix_state) {
            this.fix_state = fix_state;
        }

        public String getSite_name() {
            return site_name;
        }

        public void setSite_name(String site_name) {
            this.site_name = site_name;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getSite_person() {
            return site_person;
        }

        public void setSite_person(String site_person) {
            this.site_person = site_person;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String getRepair_person() {
            return repair_person;
        }

        public void setRepair_person(String repair_person) {
            this.repair_person = repair_person;
        }

        public String getDormitory_name() {
            return dormitory_name;
        }

        public void setDormitory_name(String dormitory_name) {
            this.dormitory_name = dormitory_name;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getRepair_state() {
            return repair_state;
        }

        public void setRepair_state(String repair_state) {
            this.repair_state = repair_state;
        }

        public String getRepair_reverse() {
            return repair_reverse;
        }

        public void setRepair_reverse(String repair_reverse) {
            this.repair_reverse = repair_reverse;
        }
    }
}
