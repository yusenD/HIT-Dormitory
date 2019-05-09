package com.icephone.yuhao.repairerecord.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.icephone.yuhao.repairerecord.R;
import com.icephone.yuhao.repairerecord.bean.DeviceBean;

import java.util.List;

public class DeviceAdapter extends BaseQuickAdapter<DeviceBean.DataBean,BaseViewHolder> {

    public DeviceAdapter(int layoutResId, @Nullable List<DeviceBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceBean.DataBean item) {
        helper.setVisible(R.id.iv_forward, false);
        helper.setText(R.id.tv_name, item.getDevice_name());
    }
}
