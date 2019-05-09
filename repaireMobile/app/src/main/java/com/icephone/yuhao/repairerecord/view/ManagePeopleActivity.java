package com.icephone.yuhao.repairerecord.view;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
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
import com.icephone.yuhao.repairerecord.Util.UserInfoUtil;
import com.icephone.yuhao.repairerecord.adapter.AdapterFacory;
import com.icephone.yuhao.repairerecord.adapter.PeopleAdapter;
import com.icephone.yuhao.repairerecord.bean.CenterBean;
import com.icephone.yuhao.repairerecord.bean.GetResultBean;
import com.icephone.yuhao.repairerecord.bean.PeopleBean;
import com.icephone.yuhao.repairerecord.net.ApiBuilder;
import com.icephone.yuhao.repairerecord.net.ApiClient;
import com.icephone.yuhao.repairerecord.net.CallBack;
import com.icephone.yuhao.repairerecord.net.URLConstant;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManagePeopleActivity extends BaseActivity {

    private List<PeopleBean.DataBean> peopleBeanList = new ArrayList<>();
    private PeopleAdapter peopleAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_people);
        ButterKnife.bind(this);
        initDate();
        initView();
    }

    @OnClick(R.id.fl_add)
    void add() {
        Bundle bundle = new Bundle();
        bundle.putString(StringConstant.KEY_MODE, StringConstant.KEY_ADD_PEOPLE);
        openActivity(PeopleDetailActivity.class, bundle);
    }

    @OnClick(R.id.rl_back)
    void back() {
        onBackPressed();
    }

    @BindView(R.id.rv_people_list)
    RecyclerView rvPeopleList;

    @Override
    public void initView() {
        rvPeopleList.setLayoutManager(new LinearLayoutManager(this));
        peopleAdapter = AdapterFacory.getPeopleAdapter(peopleBeanList);
        View emptyView = getLayoutInflater().inflate(R.layout.layout_empty, (ViewGroup) rvPeopleList.getParent(), false);
        peopleAdapter.setEmptyView(emptyView);
        peopleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                // 需要传递各种数据
                Bundle bundle = new Bundle();
                bundle.putSerializable(StringConstant.KEY_TRANSFER_PEOPLE,peopleBeanList.get(position));
                bundle.putSerializable(StringConstant.KEY_MODE, StringConstant.KEY_LOOK_PEOPLE);
                openActivity(PeopleDetailActivity.class,bundle);
            }
        });

        //长按删除
        peopleAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                DialogUtil.showAlertDialog(ManagePeopleActivity.this, "确定删除吗", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItem(position);
                    }
                },null);
                return false;
            }
        });
        rvPeopleList.setAdapter(peopleAdapter);
    }

    @Override
    public void initDate() {
        refreshList();
    }

    //获取List
    private void refreshList() {
        ApiBuilder builder = new ApiBuilder().Url(URLConstant.PERSON_GET_LIST)
                .Params("limit", StringConstant.NULL_STRING);
        ApiClient.getInstance().doGet(builder, new CallBack<PeopleBean>() {
            @Override
            public void onResponse(PeopleBean data) {
                if (data.getCode() == URLConstant.SUCCUSS_CODE) {
                    if (data.getData() != null) {
                        peopleBeanList = data.getData();
                        peopleAdapter.setNewData(peopleBeanList);
//                        rvSiteList.setAdapter(siteAdapter);
                    }
                } else {
                    ToastUtil.showToastShort(ManagePeopleActivity.this, "查询失败请重试");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(ManagePeopleActivity.this, "查询失败请重试");
            }
        }, PeopleBean.class);
    }

    //长按删除对应项
    private void deleteItem(final int position) {
        ApiBuilder builder = new ApiBuilder().Url(URLConstant.PERSON_DELETE)
                .Params("_id", peopleBeanList.get(position).get_id());
        ApiClient.getInstance().doGet(builder, new CallBack<GetResultBean>() {
            @Override
            public void onResponse(GetResultBean data) {
                if (data.getCode() == URLConstant.SUCCUSS_CODE) {
                    peopleAdapter.remove(position);
                }
                ToastUtil.showToastShort(ManagePeopleActivity.this, data.getMsg());
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(ManagePeopleActivity.this, msg + "请重试");
            }
        }, GetResultBean.class);
    }
}
