package com.icephone.yuhao.repairerecord;

import android.os.Bundle;

import com.icephone.yuhao.repairerecord.Util.StringConstant;
import com.icephone.yuhao.repairerecord.Util.ToastUtil;
import com.icephone.yuhao.repairerecord.Util.UserInfoUtil;
import com.icephone.yuhao.repairerecord.view.BaseActivity;
import com.icephone.yuhao.repairerecord.view.ManageFittingActivity;
import com.icephone.yuhao.repairerecord.view.ManagePeopleActivity;
import com.icephone.yuhao.repairerecord.view.RecordDetailActivity;
import com.icephone.yuhao.repairerecord.view.ResultActivity;
import com.icephone.yuhao.repairerecord.view.SearchRecordActivity;

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

        String centerNameSearch, startTimeSearch, endTimeSearch,curStateSearch,siteNameSearch;

        if(UserInfoUtil.isStudent(this)){
            centerNameSearch = UserInfoUtil.getDormitoryName(this);
            siteNameSearch = UserInfoUtil.getSiteName(this);
        } else{
            centerNameSearch = StringConstant.NULL_STRING;
            siteNameSearch = StringConstant.NULL_STRING;
        }

        curStateSearch = "未维修";
        startTimeSearch = StringConstant.NULL_STRING;
        endTimeSearch = StringConstant.NULL_STRING;

        Bundle bundle = new Bundle();

        bundle.putString(StringConstant.KEY_SEARCH_DORMITORY_NAME,centerNameSearch);
        bundle.putString(StringConstant.KEY_SEARCH_SITE_NAME,siteNameSearch);
        bundle.putString(StringConstant.KEY_SEARCH_CUR_STATE,curStateSearch);
        bundle.putString(StringConstant.KEY_SEARCH_START_TIME, startTimeSearch);
        bundle.putString(StringConstant.KEY_SEARCH_END_TIME, endTimeSearch);

        openActivity(ResultActivity.class, bundle);
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
