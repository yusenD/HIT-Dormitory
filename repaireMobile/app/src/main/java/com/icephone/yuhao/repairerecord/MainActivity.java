package com.icephone.yuhao.repairerecord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.icephone.yuhao.repairerecord.Util.StringConstant;
import com.icephone.yuhao.repairerecord.Util.ToastUtil;
import com.icephone.yuhao.repairerecord.Util.UserInfoUtil;
import com.icephone.yuhao.repairerecord.view.BaseActivity;
import com.icephone.yuhao.repairerecord.view.CenterListActivity;
import com.icephone.yuhao.repairerecord.view.InstallRecordDetailActivity;
import com.icephone.yuhao.repairerecord.view.ManageFittingActivity;
import com.icephone.yuhao.repairerecord.view.ManagePeopleActivity;
import com.icephone.yuhao.repairerecord.view.ManageRepairProActivity;
import com.icephone.yuhao.repairerecord.view.ManageSiteActivity;
import com.icephone.yuhao.repairerecord.view.RecordDetailActivity;
import com.icephone.yuhao.repairerecord.view.SearchInstallActivity;
import com.icephone.yuhao.repairerecord.view.SearchRecordActivity;
import com.icephone.yuhao.repairerecord.view.UnrepairRecordActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    //查找维修记录
    @OnClick(R.id.cv_search_record)
    void searchRecord() {
        openActivity(SearchRecordActivity.class);
    }

    //待维修记录
    @OnClick(R.id.cv_search_install)
    void searchNotFix() {
        openActivity(UnrepairRecordActivity.class);
    }

    //申请维修
    @OnClick(R.id.cv_add_record)
    void addRecord() {
        if(UserInfoUtil.isStudent(this) || UserInfoUtil.isSuperManager(this)){
            Bundle bundle = new Bundle();
            bundle.putString(StringConstant.KEY_MODE,StringConstant.KEY_ADD_MODE);
            openActivity(RecordDetailActivity.class,bundle);
        }else{
            ToastUtil.showToastShort(this,"无权进行查看");
        }

    }

    //人员管理
    @OnClick(R.id.cv_manage_people)
    void managePeople() {
        if(UserInfoUtil.isSuperManager(this)){
            openActivity(ManagePeopleActivity.class);
        }else {
            ToastUtil.showToastShort(this,"无权进行查看");
        }
    }

    //设备管理
    @OnClick(R.id.cv_manage_fitting)
    void manageFitting() {
        if(UserInfoUtil.isSuperManager(this)){
            openActivity(ManageFittingActivity.class);
        }else {
            ToastUtil.showToastShort(this,"无权进行查看");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }


    @Override
    public void initView() {
        ToastUtil.showToastShort(this,"欢迎使用");
    }

    @Override
    public void initDate() {

    }
}
