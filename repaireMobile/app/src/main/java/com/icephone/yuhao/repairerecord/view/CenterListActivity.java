package com.icephone.yuhao.repairerecord.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.icephone.yuhao.repairerecord.R;
import com.icephone.yuhao.repairerecord.Util.DialogUtil;
import com.icephone.yuhao.repairerecord.Util.StringConstant;
import com.icephone.yuhao.repairerecord.Util.ToastUtil;
import com.icephone.yuhao.repairerecord.adapter.AdapterFacory;
import com.icephone.yuhao.repairerecord.adapter.CenterAdapter;
import com.icephone.yuhao.repairerecord.bean.CenterBean;
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

public class CenterListActivity extends BaseActivity {

    @OnClick(R.id.fl_add_center)
    void addCenter() {
        View view = View.inflate(this, R.layout.layout_dialog_edit, null);
        final EditText editText = view.findViewById(R.id.et_add);
        DialogUtil.showEditTextDialog(this, "添加联社", view, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newCenterName = editText.getText() == null ? "" : editText.getText().toString();
                if (newCenterName.equals("")) {
                    ToastUtil.showToastShort(CenterListActivity.this, "请输入名称");
                } else {
                    ApiBuilder builder = new ApiBuilder().Url(URLConstant.CENTER_ADD)
                            .Params("center_name", newCenterName);
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
                    ToastUtil.showToastShort(CenterListActivity.this, "添加成功");
                    dialog.dismiss();
                }
            }
        },null);
    }

    @OnClick(R.id.rl_back)
    void back() {
        onBackPressed();
    }

    @BindView(R.id.rv_center_list)
    RecyclerView rvCenterList;

    private List<CenterBean.DataBean> centerBeanList;
    private CenterAdapter centerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_list);
        ButterKnife.bind(this);
        initDate();
        initView();
    }

    @Override
    public void initView() {
        rvCenterList.setLayoutManager(new LinearLayoutManager(this));
        centerAdapter = AdapterFacory.getCenterAdapter(centerBeanList);
        View emptyView = getLayoutInflater().inflate(R.layout.layout_empty, (ViewGroup) rvCenterList.getParent(), false);
        centerAdapter.setEmptyView(emptyView);
        //TODO 联社中心的点击事件：点击之后进入网点管理
        centerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(StringConstant.KEY_SEARCH_CENTER_NAME,centerBeanList.get(position).getCenter_name());
                openActivity(ManageSiteActivity.class,bundle);
            }
        });
        //长按删除
        centerAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                DialogUtil.showAlertDialog(CenterListActivity.this, "确定删除吗", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItem(position);
                    }
                },null);
                return false;
            }
        });
        // item没有点击事件
        rvCenterList.setAdapter(centerAdapter);
    }

    @Override
    public void initDate() {
        refreshList();
    }

    //获取List
    private void refreshList() {
        ApiBuilder builder = new ApiBuilder().Url(URLConstant.CENTER_GET_LIST);
        ApiClient.getInstance().doGet(builder, new CallBack<CenterBean>() {
            @Override
            public void onResponse(CenterBean data) {
                if (data.getCode() == URLConstant.SUCCUSS_CODE) {
                    if (data.getData() != null) {
                        centerBeanList = data.getData();
                        centerAdapter.setNewData(centerBeanList);
//                        rvSiteList.setAdapter(siteAdapter);
                    }
                } else {
                    ToastUtil.showToastShort(CenterListActivity.this, "查询失败请重试");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(CenterListActivity.this, "查询失败请重试");
            }
        }, CenterBean.class);
    }

    //长按删除对应项
    private void deleteItem(final int position) {
        ApiBuilder builder = new ApiBuilder().Url(URLConstant.CENTER_DELETE)
                .Params("_id", centerBeanList.get(position).get_id());
        ApiClient.getInstance().doGet(builder, new CallBack<GetResultBean>() {
            @Override
            public void onResponse(GetResultBean data) {
                if (data.getCode() == URLConstant.SUCCUSS_CODE) {
                    centerAdapter.remove(position);
                }
                ToastUtil.showToastShort(CenterListActivity.this, data.getMsg());
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(CenterListActivity.this, msg + "请重试");
            }
        }, GetResultBean.class);
    }

}
