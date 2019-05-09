package com.icephone.yuhao.repairerecord.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.icephone.yuhao.repairerecord.R;
import com.icephone.yuhao.repairerecord.bean.ProjectBean;

import java.util.List;

public class ProjectAdapter extends BaseQuickAdapter<ProjectBean.DataBean,BaseViewHolder> {

    public ProjectAdapter(int layoutResId, @Nullable List<ProjectBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectBean.DataBean item) {
        helper.setVisible(R.id.iv_forward, false);
        helper.setText(R.id.tv_name, item.getProject_name());
    }
}
