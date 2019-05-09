package com.icephone.yuhao.repairerecord.bean;

import java.util.List;

public class ProjectBean {


    /**
     * code : 200
     * msg : 查询成功
     * data : [{"_id":"5bdc418ef4d21d91346961bb","project_name":"周界报警"},{"_id":"5bdc4198f4d21d91346961bc","project_name":"电视维修"},{"_id":"5bdc41a0f4d21d91346961bd","project_name":"高清摄像机"},{"_id":"5bdc41a8f4d21d91346961be","project_name":"ATM机智能分析"}]
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
         * _id : 5bdc418ef4d21d91346961bb
         * project_name : 周界报警
         */

        private String _id;
        private String project_name;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getProject_name() {
            return project_name;
        }

        public void setProject_name(String project_name) {
            this.project_name = project_name;
        }
    }
}
