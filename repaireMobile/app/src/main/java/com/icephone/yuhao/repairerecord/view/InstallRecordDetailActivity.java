package com.icephone.yuhao.repairerecord.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icephone.yuhao.repairerecord.R;
import com.icephone.yuhao.repairerecord.Util.DialogUtil;
import com.icephone.yuhao.repairerecord.Util.StringConstant;
import com.icephone.yuhao.repairerecord.Util.StringFormatUtil;
import com.icephone.yuhao.repairerecord.Util.TimeUtil;
import com.icephone.yuhao.repairerecord.Util.ToastUtil;
import com.icephone.yuhao.repairerecord.Util.UserInfoUtil;
import com.icephone.yuhao.repairerecord.bean.CenterBean;
import com.icephone.yuhao.repairerecord.bean.DeviceBean;
import com.icephone.yuhao.repairerecord.bean.GetResultBean;
import com.icephone.yuhao.repairerecord.bean.InstallRecordBean;
import com.icephone.yuhao.repairerecord.bean.PeopleBean;
import com.icephone.yuhao.repairerecord.bean.ProjectBean;
import com.icephone.yuhao.repairerecord.bean.RepairRecordBean;
import com.icephone.yuhao.repairerecord.bean.SiteBean;
import com.icephone.yuhao.repairerecord.net.ApiBuilder;
import com.icephone.yuhao.repairerecord.net.ApiClient;
import com.icephone.yuhao.repairerecord.net.CallBack;
import com.icephone.yuhao.repairerecord.net.URLConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class InstallRecordDetailActivity extends BaseActivity {

    private Calendar calendar = Calendar.getInstance(); //获取当前时间
    private String _id = "";
    private String time = "";
    private String center_name = "";
    private String site_name = "";
    private String site_person = "";
    private String install_person = "";
    private String install_pro = "";
    private String device = "";
    private String install_state = "";
    private String install_complete = "";
    private int install_cost = 0;

    @OnClick(R.id.rl_back)
    void back() {
        onBackPressed();
    }

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.iv_edit)
    ImageView ivEdit;
    @BindView(R.id.bt_submit)
    Button btSubmit;

    @BindView(R.id.tv_time) //时间（选择）
            TextView timeView;
    @BindView(R.id.tv_site_name) //网点名称（单选）
            TextView siteNameView;
    @BindView(R.id.tv_install_pro) //维修项目（多选）
            TextView installProView;
    @BindView(R.id.tv_install_person) //维修人员（多选）
            TextView installPersonView;
    @BindView(R.id.tv_center_name) //联社名称（单选）
            TextView centerNameView;
    @BindView(R.id.et_install_state) //维修详情（填写）
            EditText installStateView;
    @BindView(R.id.tv_install_device) //设备明细（多选）
            TextView deviceView;
    @BindView(R.id.et_site_person) //网点人员（填写）
            EditText sitePersonView;
    @BindView(R.id.tv_install_complete) //是否完成（选择）
            TextView returnFixView;
    @BindView(R.id.et_install_cost) //花费（管理员填写）
            EditText costView;

    //需要选择的一些选项
    @BindView(R.id.ll_time) //维修时间
            LinearLayout llTime;
    @BindView(R.id.rl_center_name) //联社中心
            RelativeLayout rlCenterName;
    @BindView(R.id.rl_site_name) //网点中心
            RelativeLayout rlSiteName;
    @BindView(R.id.rl_install_pro) // 维修项目
            RelativeLayout rlInstallPro;
    @BindView(R.id.rl_install_person) // 维修人员
            RelativeLayout rlInstallPerson;
    @BindView(R.id.rl_install_complete) //是否返厂维修
            RelativeLayout rlInstallComplete;
    @BindView(R.id.rl_install_device) //选择设备
            RelativeLayout rlInstallDevice;

    @OnClick(R.id.ll_time)
    void showTimeDialog() {
        DialogUtil.showDateDialog(this, calendar, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                timeView.setText(TimeUtil.getShowTime(calendar));
                time = TimeUtil.getUploadTime(calendar); //时间
            }
        });
    }

    @OnClick(R.id.rl_center_name)
    void chooseCenterName() {
        if (centerItem == null) {
            getCenterList();
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
                            center_name = centerItem[which];
                            centerNameView.setText(centerItem[which]);
                            getSiteList(center_name);
                        }
                    }
            );
        }
    }

    @OnClick(R.id.rl_site_name)
    void chooseSiteName() {
        if (siteItem == null) {
            ToastUtil.showToastShort(InstallRecordDetailActivity.this, "请先选择联社");
        } else {
            DialogUtil.showSingleChooseDialog(this, "选择网点", siteItem,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    },
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            site_name = siteItem[which];
                            siteNameView.setText(siteItem[which]);
                        }
                    }
            );
        }
    }

    @OnClick(R.id.rl_install_pro)
    void chooseInstallPro() {
        // 维修项目——多选
        if (projectItem == null) {
            getProject();
        } else {
            DialogUtil.showMultiChooseDialog(this, "选择安装项目", projectItem, projectItemIsChecked,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            install_pro = StringFormatUtil.ListToString(chooseProjectResult);
                            installProView.setText(install_pro.equals("") ? "请选择安装项目" : install_pro);
                            dialog.dismiss();
                        }
                    }, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            projectItemIsChecked[which] = isChecked;
                            if (isChecked) {
                                chooseProjectResult.add(projectItem[which]);
                            } else {
                                chooseProjectResult.remove(projectItem[which]);
                            }
                        }
                    });
        }
    }

    @OnClick(R.id.rl_install_person)
    void chooseInstallPerson() {
        //安装人员——多选
        if (repairManItem == null) {
            getRepairMan();
        } else {
            DialogUtil.showMultiChooseDialog(this, "选择安装人员", repairManItem, repairManItemIsChecked,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            install_person = StringFormatUtil.ListToString(chooseRepairManResult);
                            installPersonView.setText(install_person.equals("") ? "请选择安装人员" : install_person);
                            dialog.dismiss();
                        }
                    }, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            repairManItemIsChecked[which] = isChecked;
                            if (isChecked) {
                                chooseRepairManResult.add(repairManItem[which]);
                            } else {
                                chooseRepairManResult.remove(repairManItem[which]);
                            }
                        }
                    });
        }
    }

    @OnClick(R.id.rl_install_complete)
    void chooseReturnFix() {
        final String[] item = {"完工", "未完工"};
        DialogUtil.showSingleChooseDialog(this, "选择是否完工", item,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                },
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        install_complete = item[which];
                        returnFixView.setText(install_complete);
                    }
                }
        );
    }

    @OnClick(R.id.rl_install_device)
    void chooseDevice() {
        // 设备——多选
        DialogUtil.showMultiChooseDialog(InstallRecordDetailActivity.this, "选择设备", deviceItem, deviceItemIsChecked,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        device = StringFormatUtil.ListToString(chooseDeviceResult);
                        deviceView.setText(device.equals("") ? "请选择设备" : device);
                        dialog.dismiss();
                    }
                }, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        deviceItemIsChecked[which] = isChecked;
                        if (isChecked) {
                            inputDeviceNum(which);
                        } else {
                            chooseDeviceResult.remove(deviceItem[which] + deviceNum[which]);
                        }
                    }
                });
    }

    //选择个数
    private void inputDeviceNum(final int position) {
        View view = View.inflate(InstallRecordDetailActivity.this, R.layout.layout_dialog_edit, null);
        final EditText editText = view.findViewById(R.id.et_add);
        DialogUtil.showEditTextDialog(this, "请填写数量", view, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deviceNum[position] = editText.getText().toString();
                if (deviceNum[position].equals("")) {
                    ToastUtil.showToastShort(InstallRecordDetailActivity.this, "请正确填写");
                    deviceItemIsChecked[position] = false;
                } else {
                    deviceItemIsChecked[position] = true;
                    chooseDeviceResult.add(deviceItem[position] + deviceNum[position]);
                    dialog.dismiss();
                }
            }
        }, null);
    }

    @OnClick(R.id.iv_delete)
    void delete() {
        if (UserInfoUtil.isSuperManager(getApplicationContext())) {
            DialogUtil.showAlertDialog(InstallRecordDetailActivity.this, "确定删除吗", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteRecord();
                }
            }, null);
        } else {
            ToastUtil.showToastShort(this, "只能由总管理员操作");
        }
    }

    @OnClick(R.id.iv_edit)
    void editRecord() {
        if (UserInfoUtil.isSuperManager(getApplicationContext())) {
            setViewTouchable();
            ToastUtil.showToastShort(this, "编辑模式");
        } else {
            ToastUtil.showToastShort(this, "只能由总管理员操作");
        }

    }

    // 上传
    @OnClick(R.id.bt_submit)
    void submit() {
        if (isTextNull()) {
            //TODO 上传操作
            JSONObject jsonObject = new JSONObject();
            String url = URLConstant.INSTALL_ADD;
            try {
                if (!_id.equals("")) {
                    jsonObject.put("_id", _id);
                    url = URLConstant.INSTALL_CHANGE;
                }
                jsonObject.put("time", time);
                jsonObject.put("site_name", site_name);
                jsonObject.put("install_pro", install_pro);
                jsonObject.put("install_person", install_person);
                jsonObject.put("site_person", site_person);
                jsonObject.put("center_name", center_name);
                jsonObject.put("device", device);
                jsonObject.put("install_state", install_state);
                jsonObject.put("install_complete", install_complete);
                jsonObject.put("install_cost", install_cost);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
            ApiBuilder builder = new ApiBuilder()
                    .Url(url)
                    .Body(requestBody);
            Log.i("上传测试", jsonObject.toString());
            ApiClient.getInstance().doPost(builder, new CallBack<GetResultBean>() {
                @Override
                public void onResponse(GetResultBean data) {
                    if (data.getCode() == URLConstant.SUCCUSS_CODE) {
                        openActivity(SuccessActivity.class);
                    } else {
                        ToastUtil.showToastShort(InstallRecordDetailActivity.this, "请重试");
                    }
                }

                @Override
                public void onFail(String msg) {
                    ToastUtil.showToastShort(InstallRecordDetailActivity.this, "请重试");
                }
            }, GetResultBean.class);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install_record_detail);
        ButterKnife.bind(this);
        initDate();
        initView();
    }

    @Override
    public void initView() {

        String keyMode = getIntent().getStringExtra(StringConstant.KEY_MODE);
        switch (keyMode) {
            case StringConstant.KEY_ADD_MODE:
                tvTitle.setText("添加记录");
                ivDelete.setVisibility(View.GONE);
                ivEdit.setVisibility(View.GONE);
                btSubmit.setVisibility(View.VISIBLE);
                addRecordView();
                break;
            case StringConstant.KEY_FIX_MODE:
                tvTitle.setText("修改记录");
                btSubmit.setVisibility(View.VISIBLE);
                break;
            case StringConstant.KEY_LOOK_MODE:
                tvTitle.setText("记录详情");
                btSubmit.setVisibility(View.GONE);
                putDataToView();
                break;
        }

    }

    @Override
    public void initDate() {
        getCenterList();
        getDeviceList();
        getProject();
        getRepairMan();
    }

    private boolean isTextNull() {
        if (time.equals("")) {
            ToastUtil.showToastShort(this, "请选择时间");
            return false;
        }
        if (center_name.equals("")) {
            ToastUtil.showToastShort(this, "请选择联社名称");
        }
        if (site_name.equals("")) {
            ToastUtil.showToastShort(this, "请选择网点名称");
            return false;
        }
        site_person = sitePersonView.getText().toString();
        if (site_person.equals("")) {
            ToastUtil.showToastShort(this, "请填写网点人员");
            return false;
        }
        if (install_person.equals("")) {
            ToastUtil.showToastShort(this, "请选择安装人员");
            return false;
        }
        if (install_pro.equals("")) {
            ToastUtil.showToastShort(this, "请选择安装项目");
            return false;
        }
        if (device.equals("")) {
            ToastUtil.showToastShort(this, "请选择设备明细");
            return false;
        }
        install_state = installStateView.getText().toString();
        if (install_state.equals("")) {
            ToastUtil.showToastShort(this, "请填写安装详情");
            return false;
        }
        if (install_complete.equals("")) {
            ToastUtil.showToastShort(this, "请选择是否完工");
            return false;
        }
        install_cost = costView.getText().toString().equals("") ? 0 : Integer.valueOf(costView.getText().toString());

        return true;
    }

    /**
     * 添加记录，把当前时间更新到对应时间
     */
    public void addRecordView() {
        time = TimeUtil.getUploadTime(calendar);
        timeView.setText(TimeUtil.getShowTime(calendar));
    }

    /**
     * 查看记录，把传过来的数据更新到View
     */
    public void putDataToView() {
        InstallRecordBean.DataBean bean = (InstallRecordBean.DataBean) getIntent().getSerializableExtra(StringConstant.KEY_TRANSFER_RECORD);

        _id = bean.get_id();

        time = bean.getTime();
        timeView.setText(TimeUtil.transferTimeToShow(time));

        site_name = bean.getSite_name();
        siteNameView.setText(site_name);

        center_name = bean.getCenter_name();
        centerNameView.setText(center_name);

        install_pro = bean.getInstall_pro();
        installProView.setText(install_pro);

        install_person = bean.getInstall_person();
        installPersonView.setText(install_person);

        site_person = bean.getSite_person();
        sitePersonView.setText(site_person);

        device = bean.getDevice();
        deviceView.setText(device);

        install_state = bean.getInstall_state();
        installStateView.setText(install_state);

        install_cost = bean.getInstall_cost();
        costView.setText(String.valueOf(install_cost));

        install_complete = bean.getInstall_complete();
        returnFixView.setText(install_complete);

        setViewUntouchable();
    }

    /**
     * 设置View不可编辑
     */
    public void setViewUntouchable() {
        llTime.setEnabled(false);
        rlCenterName.setEnabled(false);
        rlSiteName.setEnabled(false);
        rlInstallPro.setEnabled(false);
        rlInstallPerson.setEnabled(false);
        rlInstallComplete.setEnabled(false);
        rlInstallDevice.setEnabled(false);

        sitePersonView.setFocusable(false);
        installStateView.setFocusable(false);
        costView.setFocusable(false);
    }

    /**
     * 设置View可以编辑
     */
    public void setViewTouchable() {

        llTime.setEnabled(true);
        rlCenterName.setEnabled(true);
        rlSiteName.setEnabled(true);
        rlInstallPro.setEnabled(true);
        rlInstallPerson.setEnabled(true);
        rlInstallDevice.setEnabled(true);
        rlInstallComplete.setEnabled(true);

        costView.setFocusable(true);
        costView.setFocusableInTouchMode(true);
        sitePersonView.setFocusable(true);
        sitePersonView.setFocusableInTouchMode(true);
        installStateView.setFocusable(true);
        installStateView.setFocusableInTouchMode(true);

        btSubmit.setVisibility(View.VISIBLE);
    }

    private void deleteRecord() {
        ApiBuilder builder = new ApiBuilder().Url(URLConstant.INSTALL_DELETE)
                .Params("_id", _id);
        ApiClient.getInstance().doGet(builder, new CallBack<GetResultBean>() {
            @Override
            public void onResponse(GetResultBean data) {
                if (data.getCode() == URLConstant.SUCCUSS_CODE) {
                    openActivityAndCleanUp(SuccessActivity.class);
                } else {
                    ToastUtil.showToastShort(InstallRecordDetailActivity.this, "请重试");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(InstallRecordDetailActivity.this, msg + "请重试");
            }
        }, GetResultBean.class);
    }

    /**
     * 获取联社列表
     */
    private String[] centerItem;

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
                    ToastUtil.showToastShort(InstallRecordDetailActivity.this, "获取联社列表失败");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(InstallRecordDetailActivity.this, "获取联社列表失败");
            }
        }, CenterBean.class);
    }

    /**
     * 获取网点列表
     *
     * @param siteProperty
     */
    private String[] siteItem;

    private void getSiteList(String siteProperty) {
        ApiBuilder builder = new ApiBuilder().Url(URLConstant.SITE_GET_LIST)
                .Params("site_property", siteProperty);
        ApiClient.getInstance().doGet(builder, new CallBack<SiteBean>() {
            @Override
            public void onResponse(SiteBean data) {
                if (data.getCode() == URLConstant.SUCCUSS_CODE) {
                    if (data.getData() != null) {
                        siteItem = new String[data.getData().size()];
                        for (int i = 0; i < data.getData().size(); i++) {
                            siteItem[i] = data.getData().get(i).getSite_name();
                        }
                    }
                } else {
                    ToastUtil.showToastShort(InstallRecordDetailActivity.this, "查询失败请重试");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(InstallRecordDetailActivity.this, "查询失败请重试");
            }
        }, SiteBean.class);
    }

    /**
     * 获取设备列表
     */
    private String[] deviceItem;
    private boolean[] deviceItemIsChecked;
    private String[] deviceNum; //单位
    final List<String> chooseDeviceResult = new ArrayList<>();

    private void getDeviceList() {
        ApiBuilder builder = new ApiBuilder().Url(URLConstant.DEVICE_GET_LIST);
        ApiClient.getInstance().doGet(builder, new CallBack<DeviceBean>() {
            @Override
            public void onResponse(DeviceBean data) {
                if (data.getCode() == URLConstant.SUCCUSS_CODE) {
                    if (data.getData() != null) {
                        deviceItem = new String[data.getData().size()];
                        deviceItemIsChecked = new boolean[data.getData().size()];
                        deviceNum = new String[data.getData().size()];
                        for (int i = 0; i < data.getData().size(); i++) {
                            deviceItem[i] = data.getData().get(i).getDevice_name();
                            deviceItemIsChecked[i] = false;
                            deviceNum[i] = "";
                        }
                    }
                } else {
                    ToastUtil.showToastShort(InstallRecordDetailActivity.this, "获取设备列表失败");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(InstallRecordDetailActivity.this, "获取设备列表失败");
            }
        }, DeviceBean.class);
    }

    /**
     * 获取维修人员列表
     */
    private String[] repairManItem;
    private boolean[] repairManItemIsChecked;
    final List<String> chooseRepairManResult = new ArrayList<>();

    private void getRepairMan() {
        ApiBuilder builder = new ApiBuilder().Url(URLConstant.PERSON_GET_LIST)
                .Params("limit", UserInfoUtil.LIMIT_REPAIR_MAN);
        ApiClient.getInstance().doGet(builder, new CallBack<PeopleBean>() {
            @Override
            public void onResponse(PeopleBean data) {
                if (data.getCode() == URLConstant.SUCCUSS_CODE) {
                    if (data.getData() != null) {
                        repairManItem = new String[data.getData().size()];
                        repairManItemIsChecked = new boolean[data.getData().size()];
                        for (int i = 0; i < data.getData().size(); i++) {
                            repairManItem[i] = data.getData().get(i).getNick_name();
                            repairManItemIsChecked[i] = false;
                        }
                    }
                } else {
                    ToastUtil.showToastShort(InstallRecordDetailActivity.this, "获取安装人员失败");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(InstallRecordDetailActivity.this, "获取安装人员失败");
            }
        }, PeopleBean.class);
    }

    /**
     * 获取维修项目列表
     */
    private String[] projectItem;
    private boolean[] projectItemIsChecked;
    final List<String> chooseProjectResult = new ArrayList<>();

    private void getProject() {
        ApiBuilder builder = new ApiBuilder().Url(URLConstant.PROJECT_GET_LIST);
        ApiClient.getInstance().doGet(builder, new CallBack<ProjectBean>() {
            @Override
            public void onResponse(ProjectBean data) {
                if (data.getCode() == URLConstant.SUCCUSS_CODE) {
                    if (data.getData() != null) {
                        projectItem = new String[data.getData().size()];
                        projectItemIsChecked = new boolean[data.getData().size()];
                        for (int i = 0; i < data.getData().size(); i++) {
                            projectItem[i] = data.getData().get(i).getProject_name();
                            projectItemIsChecked[i] = false;
                        }
                    }
                } else {
                    ToastUtil.showToastShort(InstallRecordDetailActivity.this, "获取安装项目失败");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(InstallRecordDetailActivity.this, "获取安装项目失败");
            }
        }, ProjectBean.class);
    }
}
