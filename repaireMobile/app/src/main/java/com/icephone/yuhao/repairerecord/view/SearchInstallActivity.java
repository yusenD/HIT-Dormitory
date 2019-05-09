package com.icephone.yuhao.repairerecord.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import com.icephone.yuhao.repairerecord.R;
import com.icephone.yuhao.repairerecord.Util.DialogUtil;
import com.icephone.yuhao.repairerecord.Util.StringConstant;
import com.icephone.yuhao.repairerecord.Util.TimeUtil;
import com.icephone.yuhao.repairerecord.Util.ToastUtil;
import com.icephone.yuhao.repairerecord.Util.UserInfoUtil;
import com.icephone.yuhao.repairerecord.bean.CenterBean;
import com.icephone.yuhao.repairerecord.net.ApiBuilder;
import com.icephone.yuhao.repairerecord.net.ApiClient;
import com.icephone.yuhao.repairerecord.net.CallBack;
import com.icephone.yuhao.repairerecord.net.URLConstant;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchInstallActivity extends BaseActivity {

    @BindView(R.id.tv_center_name)
    TextView tvCenterName;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;

    private String[] centerItem; //联社列表

    //选择联社，如果是联社管理员提示，只能查看所管理的联社
    @OnClick(R.id.rl_center_name)
    void chooseCenterName() {
        if (centerItem == null) {
            getCenterList();
        } else {
            if (UserInfoUtil.isCenterManager(getApplicationContext())) {
                ToastUtil.showToastShort(SearchInstallActivity.this, "只能选择查看自己管理的联社");
            } else {
                DialogUtil.showSingleChooseDialog(this, "选择联社", centerItem,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        },
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tvCenterName.setText(centerItem[which]);
                                centerName = centerItem[which];
                            }
                        }

                );
            }
        }

    }

    @OnClick(R.id.rl_start_time)
    void chooseStartTime() {
        DialogUtil.showDateDialog(this, calendar, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                tvStartTime.setText(TimeUtil.getShowTime(calendar));
                startTime = TimeUtil.getUploadTime(calendar);
            }
        });
    }

    @OnClick(R.id.rl_end_time)
    void chooseEndTime() {
        DialogUtil.showDateDialog(this, calendar, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                tvEndTime.setText(TimeUtil.getShowTime(calendar));
                endTime = TimeUtil.getUploadTime(calendar);
            }
        });
    }

    @OnClick(R.id.bt_search)
    void searchRecord() {
        Bundle bundle = new Bundle();
        bundle.putString(StringConstant.KEY_SEARCH_CENTER_NAME, centerName);
        bundle.putString(StringConstant.KEY_SEARCH_START_TIME, startTime);
        bundle.putString(StringConstant.KEY_SEARCH_END_TIME, endTime);
        openActivity(ResultInstallActivity.class, bundle);
    }

    @OnClick(R.id.rl_back)
    void back() {
        onBackPressed();
    }

    private Calendar calendar;
    private String centerName = StringConstant.NULL_STRING;
    private String startTime = StringConstant.NULL_STRING;
    private String endTime = StringConstant.NULL_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_install);
        ButterKnife.bind(this);
        initDate();
        initView();

    }

    @Override
    public void initView() {
        if (UserInfoUtil.isCenterManager(getApplicationContext())) {
            centerName = UserInfoUtil.getManageCenter(getApplicationContext());
            tvCenterName.setText(centerName);
        }
    }

    @Override
    public void initDate() {
        getCenterList();
        calendar = Calendar.getInstance();
    }

    private void getCenterList() {

        ApiBuilder builder = new ApiBuilder().Url(URLConstant.CENTER_GET_LIST);
        ApiClient.getInstance().doGet(builder, new CallBack<CenterBean>() {
            @Override
            public void onResponse(CenterBean data) {
                if (data.getCode() == URLConstant.SUCCUSS_CODE) {
                    if (data.getData() != null) {
                        centerItem = new String[data.getData().size()];
                        for (int i = 0; i < data.getData().size(); i++) {
                            centerItem[i] = data.getData().get(i).getCenter_name();
                        }
                    }
                } else {
                    ToastUtil.showToastShort(SearchInstallActivity.this, "获取联社列表失败");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(SearchInstallActivity.this, "获取联社列表失败");
            }
        }, CenterBean.class);
    }
}
