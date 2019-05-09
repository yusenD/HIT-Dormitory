package com.icephone.yuhao.repairerecord.adapter;

import android.support.annotation.Nullable;

import com.icephone.yuhao.repairerecord.R;
import com.icephone.yuhao.repairerecord.bean.CenterBean;
import com.icephone.yuhao.repairerecord.bean.DeviceBean;
import com.icephone.yuhao.repairerecord.bean.InstallRecordBean;
import com.icephone.yuhao.repairerecord.bean.PeopleBean;
import com.icephone.yuhao.repairerecord.bean.ProjectBean;
import com.icephone.yuhao.repairerecord.bean.RepairRecordBean;
import com.icephone.yuhao.repairerecord.bean.SiteBean;

import java.util.List;

public class AdapterFacory {

    public static RepairRecordAdapter getRepairRecordAdater(int layoutResId, @Nullable List<RepairRecordBean.DataBean> data) {
        return new RepairRecordAdapter(layoutResId, data);
    }

    public static DeviceAdapter getDeviceAdapter(@Nullable List<DeviceBean.DataBean> data) {
        return new DeviceAdapter(R.layout.layout_simple_item, data);
    }

    public static InstallRecordAdapter getInstallRecordAdapter(int layoutResId, @Nullable List<InstallRecordBean.DataBean> data) {
        return new InstallRecordAdapter(layoutResId, data);
    }

    public static CenterAdapter getCenterAdapter(@Nullable List<CenterBean.DataBean> data) {
        return new CenterAdapter(R.layout.layout_simple_item, data);
    }

    public static PeopleAdapter getPeopleAdapter(@Nullable List<PeopleBean.DataBean> data) {
        return new PeopleAdapter(R.layout.layout_simple_item, data);
    }

    public static ProjectAdapter getProjectAdapter(@Nullable List<ProjectBean.DataBean> data) {
        return new ProjectAdapter(R.layout.layout_simple_item, data);
    }

    public static SiteAdapter getSiteAdapter(@Nullable List<SiteBean.DataBean> data) {
        return new SiteAdapter(R.layout.layout_simple_item, data);
    }

}
