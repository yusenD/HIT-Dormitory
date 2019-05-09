package com.icephone.yuhao.repairerecord.view;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.icephone.yuhao.repairerecord.R;
import com.icephone.yuhao.repairerecord.Util.DialogUtil;
import com.icephone.yuhao.repairerecord.Util.StringConstant;
import com.icephone.yuhao.repairerecord.Util.ToastUtil;
import com.icephone.yuhao.repairerecord.adapter.AdapterFacory;
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

public class ManageSiteActivity extends BaseActivity {

    @OnClick(R.id.fl_add)
    void add() {
        View view = View.inflate(this, R.layout.layout_dialog_edit, null);
        final EditText editText1 = view.findViewById(R.id.et_add);
        editText1.setHint("请输入网点名称");
        DialogUtil.showEditTextDialog(this, "添加网点", view, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newSiteName = editText1.getText() == null ? "" : editText1.getText().toString();
                if (newSiteName.equals("")) {
                    ToastUtil.showToastShort(ManageSiteActivity.this, "请输入");
                } else {
                    // 网络接口上传
                    ApiBuilder builder = new ApiBuilder().Url(URLConstant.SITE_ADD)
                            .Params("site_name", newSiteName)
                            .Params("site_property", centerName);
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
                    ToastUtil.showToastShort(ManageSiteActivity.this, "添加成功");
                    dialog.dismiss();
                }
            }
        },null);
    }

    @OnClick(R.id.rl_back)
    void back() {
        onBackPressed();
    }

    @BindView(R.id.rv_site_list)
    RecyclerView rvSiteList;

    // 列表+adapter
    private List<SiteBean.DataBean> siteList = new ArrayList<>();
    private SiteAdapter siteAdapter;

    // 从前面传回来的centername
    private String centerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_site);
        ButterKnife.bind(this);
        initDate();
        initView();
    }

    @Override
    public void initView() {
        rvSiteList.setLayoutManager(new LinearLayoutManager(this));
        siteAdapter = AdapterFacory.getSiteAdapter(siteList);
        View emptyView = getLayoutInflater().inflate(R.layout.layout_empty, (ViewGroup) rvSiteList.getParent(), false);
        siteAdapter.setEmptyView(emptyView);
        siteAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                DialogUtil.showAlertDialog(ManageSiteActivity.this, "确定删除吗", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItem(position);
                    }
                },null);
                return false;
            }
        });
        // item没有点击事件
        rvSiteList.setAdapter(siteAdapter);
    }

    @Override
    public void initDate() {

        //获取centerName
        centerName = getIntent().getStringExtra(StringConstant.KEY_SEARCH_CENTER_NAME);
        Log.i("center_name:", centerName);
        refreshList();
    }

    //获取List
    private void refreshList() {
        ApiBuilder builder = new ApiBuilder().Url(URLConstant.SITE_GET_LIST)
                .Params("site_property", centerName);
        ApiClient.getInstance().doGet(builder, new CallBack<SiteBean>() {
            @Override
            public void onResponse(SiteBean data) {
                if (data.getCode() == URLConstant.SUCCUSS_CODE) {
                    if (data.getData() != null) {
                        siteList = data.getData();
                        siteAdapter.setNewData(siteList);
//                        rvSiteList.setAdapter(siteAdapter);
                    }
                } else {
                    ToastUtil.showToastShort(ManageSiteActivity.this, "查询失败请重试");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(ManageSiteActivity.this, "查询失败请重试");
            }
        }, SiteBean.class);
    }

    //长按删除对应项
    private void deleteItem(final int position) {
        ApiBuilder builder = new ApiBuilder().Url(URLConstant.SITE_DELETE)
                .Params("_id", siteList.get(position).get_id());
        ApiClient.getInstance().doGet(builder, new CallBack<GetResultBean>() {
            @Override
            public void onResponse(GetResultBean data) {
                if (data.getCode() == URLConstant.SUCCUSS_CODE) {
                    siteAdapter.remove(position);
                }
                ToastUtil.showToastShort(ManageSiteActivity.this, data.getMsg());
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(ManageSiteActivity.this, msg + "请重试");
            }
        }, GetResultBean.class);
    }
}
