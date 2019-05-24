package com.icephone.yuhao.repairerecord.bean;

import java.util.List;

// 维修项目
public class DeviceBean {

    /**
     * code : 200
     * msg : 查询成功
     * data : [{"_id":"5ce15c3d5d8c892a2cdabf7f","device_name":"喵喵锤","__v":0},{"_id":"5ce4baca9928a6333457e7e1","device_name":"MK50","__v":0}]
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
         * _id : 5ce15c3d5d8c892a2cdabf7f
         * device_name : 喵喵锤
         * __v : 0
         */

        private String _id;
        private String device_name;
        private int __v;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }
    }
}
