package com.icephone.yuhao.repairerecord.bean;

import java.util.List;

public class PeopleBean {

    /**
     * code : 200
     * msg : 查询成功
     * data : [{"_id":"5ce11afdd7ad22118c3e204f","account":"6_666","site_name":"666","permission":"学生","nick_name":"","dormitory_name":"八公寓","password":"6_666"},{"_id":"5ce764c8da769f756ddbe4e7","account":"lisan","site_name":"","permission":"维修人员","nick_name":"李三","dormitory_name":"","password":"lisan"},{"_id":"5ce76503da769f756ddbe4fb","account":"zhaosi","site_name":"","permission":"管理员","nick_name":"赵四","dormitory_name":"","password":"zhaosi"}]
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
         * _id : 5ce11afdd7ad22118c3e204f
         * account : 6_666
         * site_name : 666
         * permission : 学生
         * nick_name :
         * dormitory_name : 八公寓
         * password : 6_666
         */

        private String _id;
        private String account;
        private String site_name;
        private String permission;
        private String nick_name;
        private String dormitory_name;
        private String password;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getSite_name() {
            return site_name;
        }

        public void setSite_name(String site_name) {
            this.site_name = site_name;
        }

        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getDormitory_name() {
            return dormitory_name;
        }

        public void setDormitory_name(String dormitory_name) {
            this.dormitory_name = dormitory_name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
