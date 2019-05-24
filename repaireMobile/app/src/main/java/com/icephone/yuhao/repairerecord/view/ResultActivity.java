package com.icephone.yuhao.repairerecord.view;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.icephone.yuhao.repairerecord.R;
import com.icephone.yuhao.repairerecord.Util.OutputEXLUtil;
import com.icephone.yuhao.repairerecord.Util.StringConstant;
import com.icephone.yuhao.repairerecord.Util.TimeUtil;
import com.icephone.yuhao.repairerecord.Util.ToastUtil;
import com.icephone.yuhao.repairerecord.Util.UserInfoUtil;
import com.icephone.yuhao.repairerecord.adapter.AdapterFacory;
import com.icephone.yuhao.repairerecord.adapter.RepairRecordAdapter;
import com.icephone.yuhao.repairerecord.bean.CenterBean;
import com.icephone.yuhao.repairerecord.bean.GetResultBean;
import com.icephone.yuhao.repairerecord.bean.RepairRecordBean;
import com.icephone.yuhao.repairerecord.net.ApiBuilder;
import com.icephone.yuhao.repairerecord.net.ApiClient;
import com.icephone.yuhao.repairerecord.net.CallBack;
import com.icephone.yuhao.repairerecord.net.URLConstant;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

public class ResultActivity extends BaseActivity {

//    private final int READ_WRITE_MEMORY = 0;
//    final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "固弘安防";

    @BindView(R.id.rv_record_list)
    RecyclerView rvRecordList;

    @OnClick(R.id.rl_back)
    void back() {
        onBackPressed();
    }

    private RepairRecordAdapter recordAdapter;
    private List<RepairRecordBean.DataBean> recordBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        initDate();
        initView();
    }

    @Override
    public void initView() {
        View emptyView = getLayoutInflater().inflate(R.layout.layout_empty, (ViewGroup) rvRecordList.getParent(), false);
        recordAdapter = AdapterFacory.getRepairRecordAdater(R.layout.layout_repaire_item, recordBeanList);
        recordAdapter.setEmptyView(emptyView);
        recordAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recordAdapter.isFirstOnly(false);
        recordAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(StringConstant.KEY_MODE, StringConstant.KEY_LOOK_MODE); //点击这个维修项，然后查看详细
                bundle.putSerializable(StringConstant.KEY_TRANSFER_RECORD, recordBeanList.get(position));
                openActivity(RecordDetailActivity.class, bundle);
            }
        });
        rvRecordList.setLayoutManager(new LinearLayoutManager(this));
        rvRecordList.setAdapter(recordAdapter);
    }

    @Override
    public void initDate() {
        refreshList();
    }

    //查询列表
    private void refreshList() {

        String centerNameSearch, startTimeSearch, endTimeSearch,curStateSearch,siteNameSearch;

        centerNameSearch = getIntent().getStringExtra(StringConstant.KEY_SEARCH_DORMITORY_NAME);
        siteNameSearch = getIntent().getStringExtra(StringConstant.KEY_SEARCH_SITE_NAME);
        startTimeSearch = getIntent().getStringExtra(StringConstant.KEY_SEARCH_START_TIME);
        endTimeSearch = getIntent().getStringExtra(StringConstant.KEY_SEARCH_END_TIME);
        curStateSearch = getIntent().getStringExtra(StringConstant.KEY_SEARCH_CUR_STATE);

//        Log.i("维修查询参数Result", centerName + ":" + startTime + "--" + endTime);

        ApiBuilder builder = new ApiBuilder().Url(URLConstant.REPAIR_GET_LIST)
                .Params("dormitory_name", centerNameSearch)
                .Params("site_name",siteNameSearch)
                .Params("repair_state",curStateSearch)
                .Params("start_time", startTimeSearch)
                .Params("end_time", endTimeSearch);

        ApiClient.getInstance().doGet(builder, new CallBack<RepairRecordBean>() {
            @Override
            public void onResponse(RepairRecordBean data) {
                if (data.getCode() == URLConstant.SUCCUSS_CODE) {
                    if (data.getData() != null) {
                        recordBeanList = data.getData();
                        recordAdapter.setNewData(recordBeanList);
//                        rvSiteList.setAdapter(siteAdapter);
                    }
                } else {
                    ToastUtil.showToastShort(ResultActivity.this, "查询失败请重试");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(ResultActivity.this, "查询失败请重试");
            }
        }, RepairRecordBean.class);
    }

}
