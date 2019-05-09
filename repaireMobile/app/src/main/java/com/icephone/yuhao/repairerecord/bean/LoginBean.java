package com.icephone.yuhao.repairerecord.bean;

public class LoginBean {


    /**
     * code : 200
     * msg : 欢迎使用
     * data : {"_id":"5bdc3efef4d21d91346961b6","nick_name":"陈双才","account":"chenshaungcai","password":"123456","limit":1,"manage_center":"顺平联社"}
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
         * _id : 5bdc3efef4d21d91346961b6
         * nick_name : 陈双才
         * account : chenshaungcai
         * password : 123456
         * limit : 1
         * manage_center : 顺平联社
         */

        private String _id;
        private String nick_name;
        private String account;
        private String password;
        private String limit;
        private String manage_center;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }

        public String getManage_center() {
            return manage_center;
        }

        public void setManage_center(String manage_center) {
            this.manage_center = manage_center;
        }
    }
}
