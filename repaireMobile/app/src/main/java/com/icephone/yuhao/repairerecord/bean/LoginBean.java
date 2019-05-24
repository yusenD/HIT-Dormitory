package com.icephone.yuhao.repairerecord.bean;

public class LoginBean {


    /**
     * code : 200
     * msg : 欢迎访问
     * data : {"_id":"5ce11afdd7ad22118c3e204f","account":"8_569","site_name":"666","manage_center":"","permission":0,"nick_name":"","dormitory_name":"八公寓","password":"6_666"}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * _id : 5ce11afdd7ad22118c3e204f
         * account : 8_569
         * site_name : 666
         * manage_center :
         * permission : 0
         * nick_name :
         * dormitory_name : 八公寓
         * password : 6_666
         */

        private String _id;
        private String account;
        private String site_name;
        private int permission;
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

        public int getPermission() {
            return permission;
        }

        public void setPermission(int permission) {
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
