package com.icephone.yuhao.repairerecord.bean;

import java.io.Serializable;
import java.util.List;

public class InstallRecordBean {

    /**
     * code : 200
     * msg : 查询成功
     * data : [{"_id":"5bf01619f120772bd90c8e66","time":"20181117","site_name":"玉川信用社","install_pro":"周界报警，电视维修","install_person":"林雨","site_person":"123","center_name":"满城联社","device":"红外摄像机，柜员枪机","install_state":"123","install_complete":"完成","install_cost":100},{"_id":"5bf0ef20f120772bd90c8e69","time":"20181118","site_name":"第一储蓄所","install_pro":"高清摄像机，ATM机智能分析","install_person":"林雨，李艳辉","site_person":"小张","center_name":"顺平联社","device":"红外摄像机，柜员枪机","install_state":"安装","install_complete":"完成","install_cost":100}]
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
         * _id : 5bf01619f120772bd90c8e66
         * time : 20181117
         * site_name : 玉川信用社
         * install_pro : 周界报警，电视维修
         * install_person : 林雨
         * site_person : 123
         * center_name : 满城联社
         * device : 红外摄像机，柜员枪机
         * install_state : 123
         * install_complete : 完成
         * install_cost : 100
         */

        private String _id;
        private String time;
        private String site_name;
        private String install_pro;
        private String install_person;
        private String site_person;
        private String center_name;
        private String device;
        private String install_state;
        private String install_complete;
        private int install_cost;

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

        public String getInstall_pro() {
            return install_pro;
        }

        public void setInstall_pro(String install_pro) {
            this.install_pro = install_pro;
        }

        public String getInstall_person() {
            return install_person;
        }

        public void setInstall_person(String install_person) {
            this.install_person = install_person;
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

        public String getInstall_state() {
            return install_state;
        }

        public void setInstall_state(String install_state) {
            this.install_state = install_state;
        }

        public String getInstall_complete() {
            return install_complete;
        }

        public void setInstall_complete(String install_complete) {
            this.install_complete = install_complete;
        }

        public int getInstall_cost() {
            return install_cost;
        }

        public void setInstall_cost(int install_cost) {
            this.install_cost = install_cost;
        }
    }
}
