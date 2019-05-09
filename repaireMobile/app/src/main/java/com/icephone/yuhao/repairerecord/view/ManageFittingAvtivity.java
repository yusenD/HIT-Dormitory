package com.icephone.yuhao.repairerecord.view;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.icephone.yuhao.repairerecord.R;
import com.icephone.yuhao.repairerecord.Util.DialogUtil;
import com.icephone.yuhao.repairerecord.Util.StringConstant;
import com.icephone.yuhao.repairerecord.Util.ToastUtil;
import com.icephone.yuhao.repairerecord.adapter.AdapterFacory;
import com.icephone.yuhao.repairerecord.adapter.DeviceAdapter;
import com.icephone.yuhao.repairerecord.adapter.SiteAdapter;
import com.icephone.yuhao.repairerecord.bean.DeviceBean;
import com.icephone.yuhao.repairerecord.bean.GetResultBean;
import com.icephone.yuhao.repairerecord.bean.SiteBean;
import com.icephone.yuhao.repairerecord.net.ApiBuilder;
import com.icephone.yuhao.repairerecord.net.ApiClient;
import com.icephone.yuhao.repairerecord.net.CallBack;
import com.icephone.yuhao.repairerecord.net.URLConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageFittingAvtivity extends BaseActivity {

    @OnClick(R.id.fl_add)
    void add() {
        View view = View.inflate(this, R.layout.layout_dialog_edit, null);
        final EditText editText = view.findViewById(R.id.et_add);
        editText.setHint("请输入设备名称");
        DialogUtil.showEditTextDialog(this, "添加", view, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newCenterName = editText.getText() == null ? "" : editText.getText().toString();
                if (newCenterName.equals("")) {
                    ToastUtil.showToastShort(ManageFittingAvtivity.this, "请输入设备名称");
                } else {
                    ApiBuilder builder = new ApiBuilder().Url(URLConstant.DEVICE_ADD)
                            .Params("device_name", newCenterName);
                    ApiClient.getInstance().doGet(builder, new CallBack<GetResultBean>() {
                        @Override
                        public void onResponse(GetResultBean data) {
                            if (data.getCode() == URLConstant.SUCCUSS_CODE) {
                                refreshList();
                            }
                        }

                        @Override
                        public void onFail(String msg) {

                        }
                    }, GetResultBean.class);
                    ToastUtil.showToastShort(ManageFittingAvtivity.this, "添加成功");
                    dialog.dismiss();
                }
            }
        }, null);
    }

    @OnClick(R.id.rl_back)
    void back() {
        onBackPressed();
    }

    @BindView(R.id.rv_site_list)
    RecyclerView rvSiteList;

    private List<DeviceBean.DataBean> deviceList = new ArrayList<>();
    private DeviceAdapter deviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_fitting);
        ButterKnife.bind(this);
        initDate();
        initView();
    }

    @Override
    public void initView() {
        rvSiteList.setLayoutManager(new LinearLayoutManager(this));
        deviceAdapter = AdapterFacory.getDeviceAdapter(deviceList);
        View emptyView = getLayoutInflater().inflate(R.layout.layout_empty, (ViewGroup) rvSiteList.getParent(), false);
        deviceAdapter.setEmptyView(emptyView);
        deviceAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                DialogUtil.showAlertDialog(ManageFittingAvtivity.this, "确定删除吗", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItem(position);
                    }
                },null);
                return false;
            }
        });
        // item没有点击事件
        rvSiteList.setAdapter(deviceAdapter);
    }

    @Override
    public void initDate() {
        refreshList();
    }

    //获取List
    private void refreshList() {
        ApiBuilder builder = new ApiBuilder().Url(URLConstant.DEVICE_GET_LIST);
        ApiClient.getInstance().doGet(builder, new CallBack<DeviceBean>() {
            @Override
            public void onResponse(DeviceBean data) {
                if (data.getCode() == URLConstant.SUCCUSS_CODE) {
                    if (data.getData() != null) {
                        deviceList = data.getData();
                        deviceAdapter.setNewData(deviceList);
//                        rvSiteList.setAdapter(siteAdapter);
                    }
                } else {
                    ToastUtil.showToastShort(ManageFittingAvtivity.this, "查询失败请重试");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(ManageFittingAvtivity.this, "查询失败请重试");
            }
        }, DeviceBean.class);
    }

    //长按删除对应项
    private void deleteItem(final int position) {
        ApiBuilder builder = new ApiBuilder().Url(URLConstant.DEVICE_DELETE)
                .Params("_id", deviceList.get(position).get_id());
        ApiClient.getInstance().doGet(builder, new CallBack<GetResultBean>() {
            @Override
            public void onResponse(GetResultBean data) {
                if (data.getCode() == URLConstant.SUCCUSS_CODE) {
                    deviceAdapter.remove(position);
                }
                ToastUtil.showToastShort(ManageFittingAvtivity.this, data.getMsg());
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(ManageFittingAvtivity.this, msg + "请重试");
            }
        }, GetResultBean.class);
    }
}
