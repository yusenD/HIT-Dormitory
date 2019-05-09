package com.icephone.yuhao.repairerecord.bean;

import java.util.List;

public class CenterBean {

    /**
     * code : 200
     * msg : 查询成功
     * data : [{"_id":"5bcc5f80775c42d5c848002b","center_name":"满城联社"},{"_id":"5bcc5f89775c42d5c848002c","center_name":"清苑联社"},{"_id":"5bdc3e38f4d21d91346961ac","center_name":"望都联社"},{"_id":"5bdc3e47f4d21d91346961ad","center_name":"博野联社"},{"_id":"5bdc3e4cf4d21d91346961ae","center_name":"涞源联社"},{"_id":"5bdc3e50f4d21d91346961af","center_name":"曲阳联社"},{"_id":"5bdc3e60f4d21d91346961b1","center_name":"顺平联社"}]
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
         * _id : 5bcc5f80775c42d5c848002b
         * center_name : 满城联社
         */

        private String _id;
        private String center_name;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCenter_name() {
            return center_name;
        }

        public void setCenter_name(String center_name) {
            this.center_name = center_name;
        }
    }
}
