package com.icephone.yuhao.repairerecord.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.icephone.yuhao.repairerecord.R;
import com.icephone.yuhao.repairerecord.bean.SiteBean;

import java.util.List;

public class SiteAdapter extends BaseQuickAdapter<SiteBean.DataBean,BaseViewHolder> {

    public SiteAdapter(int layoutResId, @Nullable List<SiteBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SiteBean.DataBean item) {
        helper.setVisible(R.id.iv_forward, false);
        helper.setText(R.id.tv_name, item.getSite_name());
    }
}
