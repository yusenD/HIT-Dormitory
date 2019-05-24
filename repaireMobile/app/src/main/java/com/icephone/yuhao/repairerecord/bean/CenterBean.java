package com.icephone.yuhao.repairerecord.bean;

import java.util.List;

public class CenterBean {
    /**
     * code : 200
     * msg : 查询成功
     * data : [{"_id":"5ce12e6cd7ad2204a07aaf3a","dormitory_name":"二公寓","dormitory_number":2},{"_id":"5ce12e6cd7ad2204a07aaf3b","dormitory_name":"三公寓","dormitory_number":3},{"_id":"5ce12e6cd7ad2204a07aaf3c","dormitory_name":"四公寓","dormitory_number":4}]
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

    public static class DataBean {
        /**
         * _id : 5ce12e6cd7ad2204a07aaf3a
         * dormitory_name : 二公寓
         * dormitory_number : 2
         */

        private String _id;
        private String dormitory_name;
        private int dormitory_number;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getDormitory_name() {
            return dormitory_name;
        }

        public void setDormitory_name(String dormitory_name) {
            this.dormitory_name = dormitory_name;
        }

        public int getDormitory_number() {
            return dormitory_number;
        }

        public void setDormitory_number(int dormitory_number) {
            this.dormitory_number = dormitory_number;
        }
    }
}
