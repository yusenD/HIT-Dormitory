package com.icephone.yuhao.repairerecord.view;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.icephone.yuhao.repairerecord.R;
import com.icephone.yuhao.repairerecord.Util.DialogUtil;
import com.icephone.yuhao.repairerecord.Util.ToastUtil;
import com.icephone.yuhao.repairerecord.adapter.AdapterFacory;
import com.icephone.yuhao.repairerecord.adapter.ProjectAdapter;
import com.icephone.yuhao.repairerecord.bean.DeviceBean;
import com.icephone.yuhao.repairerecord.bean.GetResultBean;
import com.icephone.yuhao.repairerecord.bean.ProjectBean;
import com.icephone.yuhao.repairerecord.net.ApiBuilder;
import com.icephone.yuhao.repairerecord.net.ApiClient;
import com.icephone.yuhao.repairerecord.net.CallBack;
import com.icephone.yuhao.repairerecord.net.URLConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageRepairProActivity extends BaseActivity {

    @OnClick(R.id.fl_add)
    void add() {
        View view = View.inflate(this, R.layout.layout_dialog_edit, null);
        final EditText editText = view.findViewById(R.id.et_add);
        DialogUtil.showEditTextDialog(this, "添加维修项目", view, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newCenterName = editText.getText() == null ? "" : editText.getText().toString();
                if (newCenterName.equals("")) {
                    ToastUtil.showToastShort(ManageRepairProActivity.this, "请输入项目名称");
                } else {
                    ApiBuilder builder = new ApiBuilder().Url(URLConstant.PROJECT_ADD)
                            .Params("project_name", newCenterName);
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
                    ToastUtil.showToastShort(ManageRepairProActivity.this, "添加成功");
                    dialog.dismiss();
                }
            }
        },null);
    }

    @OnClick(R.id.rl_back)
    void back() {
        onBackPressed();
    }

    @BindView(R.id.rv_repair_pro_list)
    RecyclerView rvList;

    private List<ProjectBean.DataBean> projectList = new ArrayList<>();
    private ProjectAdapter projectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_repair_pro);
        ButterKnife.bind(this);
        initDate();
        initView();
    }

    @Override
    public void initView() {
        rvList.setLayoutManager(new LinearLayoutManager(this));
        projectAdapter = AdapterFacory.getProjectAdapter(projectList);
        View emptyView = getLayoutInflater().inflate(R.layout.layout_empty, (ViewGroup) rvList.getParent(), false);
        projectAdapter.setEmptyView(emptyView);
        projectAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                DialogUtil.showAlertDialog(ManageRepairProActivity.this, "确定删除吗", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItem(position);
                    }
                },null);
                return false;
            }
        });
        // item没有点击事件
        rvList.setAdapter(projectAdapter);
    }

    @Override
    public void initDate() {
        refreshList();
    }

    private void refreshList() {
        ApiBuilder builder = new ApiBuilder().Url(URLConstant.PROJECT_GET_LIST);
        ApiClient.getInstance().doGet(builder, new CallBack<ProjectBean>() {
            @Override
            public void onResponse(ProjectBean data) {
                if (data.getCode() == URLConstant.SUCCUSS_CODE) {
                    if (data.getData() != null) {
                        projectList = data.getData();
                        projectAdapter.setNewData(projectList);
//                        rvSiteList.setAdapter(siteAdapter);
                    }
                } else {
                    ToastUtil.showToastShort(ManageRepairProActivity.this, "查询失败请重试");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(ManageRepairProActivity.this, "查询失败请重试");
            }
        }, ProjectBean.class);
    }

    //长按删除对应项
    private void deleteItem(final int position) {
        ApiBuilder builder = new ApiBuilder().Url(URLConstant.PROJECT_DELETE)
                .Params("_id", projectList.get(position).get_id());
        ApiClient.getInstance().doGet(builder, new CallBack<GetResultBean>() {
            @Override
            public void onResponse(GetResultBean data) {
                if (data.getCode() == URLConstant.SUCCUSS_CODE) {
                    projectAdapter.remove(position);
                }
                ToastUtil.showToastShort(ManageRepairProActivity.this, data.getMsg());
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(ManageRepairProActivity.this, msg + "请重试");
            }
        }, GetResultBean.class);
    }
}
