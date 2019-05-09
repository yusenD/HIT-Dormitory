package com.icephone.yuhao.repairerecord.bean;

import java.util.List;

public class SiteBean {


    /**
     * code : 200
     * msg : 查询成功
     * data : [{"_id":"5bdc4230f4d21d91346961bf","site_name":"第一储蓄所","site_property":"清苑联社"},{"_id":"5bdc423bf4d21d91346961c0","site_name":"第二储蓄所","site_property":"清苑联社"},{"_id":"5bdc4245f4d21d91346961c1","site_name":"南大冉信用社","site_property":"清苑联社"},{"_id":"5bdc4252f4d21d91346961c2","site_name":"城关分社","site_property":"清苑联社"},{"_id":"5bdc425bf4d21d91346961c3","site_name":"玉川信用社","site_property":"满城联社"},{"_id":"5bdc4265f4d21d91346961c4","site_name":"白龙信用社","site_property":"满城联社"},{"_id":"5bdc426df4d21d91346961c5","site_name":"西庄信用社","site_property":"满城联社"},{"_id":"5bdc4278f4d21d91346961c6","site_name":"要庄信用社","site_property":"满城联社"},{"_id":"5bdc4281f4d21d91346961c7","site_name":"黄庄信用社","site_property":"满都联社"},{"_id":"5bdc428af4d21d91346961c8","site_name":"固庄信用社","site_property":"满都联社"},{"_id":"5bdc4292f4d21d91346961c9","site_name":"小店信用社","site_property":"博野联社"},{"_id":"5bdc429bf4d21d91346961ca","site_name":"冯村信用社","site_property":"博野联社"},{"_id":"5bdc42a2f4d21d91346961cb","site_name":"程委信用社","site_property":"博野联社"},{"_id":"5bdc42abf4d21d91346961cc","site_name":"王安镇信用社","site_property":"涞源联社"},{"_id":"5bdc42b3f4d21d91346961cd","site_name":"下北头信用社","site_property":"涞源联社"},{"_id":"5bdc42bdf4d21d91346961ce","site_name":"东团堡信用社","site_property":"涞源联社"},{"_id":"5bdc42dff4d21d91346961cf","site_name":"中心储蓄所","site_property":"顺平联社"},{"_id":"5bdc42e9f4d21d91346961d0","site_name":"腰山信用社","site_property":"顺平联社"},{"_id":"5bdc42fef4d21d91346961d1","site_name":"城关信用社","site_property":"曲阳联社"}]
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
         * _id : 5bdc4230f4d21d91346961bf
         * site_name : 第一储蓄所
         * site_property : 清苑联社
         */

        private String _id;
        private String site_name;
        private String site_property;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getSite_name() {
            return site_name;
        }

        public void setSite_name(String site_name) {
            this.site_name = site_name;
        }

        public String getSite_property() {
            return site_property;
        }

        public void setSite_property(String site_property) {
            this.site_property = site_property;
        }
    }
}
