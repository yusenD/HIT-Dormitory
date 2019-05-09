package com.icephone.yuhao.repairerecord.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.icephone.yuhao.repairerecord.R;
import com.icephone.yuhao.repairerecord.Util.TimeUtil;
import com.icephone.yuhao.repairerecord.bean.InstallRecordBean;

import java.util.List;

public class InstallRecordAdapter extends BaseQuickAdapter<InstallRecordBean.DataBean,BaseViewHolder> {

    public InstallRecordAdapter(int layoutResId, @Nullable List<InstallRecordBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InstallRecordBean.DataBean item) {
        helper.setText(R.id.tv_center_name, item.getCenter_name())
                .setText(R.id.tv_site_name, item.getSite_name())
                .setText(R.id.tv_time, TimeUtil.transferTimeToShow(item.getTime()))
                .setText(R.id.tv_repair_pro, item.getInstall_pro());
    }
}
