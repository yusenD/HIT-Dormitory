package com.icephone.yuhao.repairerecord.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringJoiner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RecordDetailActivity extends BaseActivity {

    private Calendar calendar = Calendar.getInstance(); //获取当前时间
    private String _id = "";
    private String time = ""; //选择的时间，默认是当前时间
    private String site_name = "";
    private String repair_pro = "";
    private String repair_person = "";
    private String site_person = "";
    private String center_name = "";
    private String device = "";
    private String fix_state = "";
    private int fix_cost = 0;
    private String return_fix = "";
    private String return_time = "未填写";
    private String warranty = ""; //选择是否在保修期内

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);
        ButterKnife.bind(this);
        initDate();
        initView();
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
    @BindView(R.id.tv_repair_pro) //维修项目（多选）
            TextView repairProView;
    @BindView(R.id.tv_repair_person) //维修人员（多选）
            TextView repairPersonView;
    @BindView(R.id.tv_center_name) //联社名称（单选）
            TextView centerNameView;
    @BindView(R.id.et_fix_state) //维修详情（填写）
            TextView fixStateView;
    @BindView(R.id.tv_device) //设备明细（多选）
            TextView deviceView;
    @BindView(R.id.et_site_person) //网点人员（填写）
            EditText sitePersonView;
    @BindView(R.id.et_return_time) //返厂时间（管理员填写）
            EditText returnTimeView;
    @BindView(R.id.tv_return_fix) //是否返厂维修（选择）
            TextView returnFixView;
    @BindView(R.id.et_cost) //花费（管理员填写）
            EditText costView;
    @BindView(R.id.tv_repair_warranty)
    TextView warrantyView;

    //需要选择的一些选项
    @BindView(R.id.ll_time) //维修时间
            LinearLayout llTime;
    @BindView(R.id.rl_center_name) //联社中心
            RelativeLayout rlCenterName;
    @BindView(R.id.rl_site_name) //网点中心
            RelativeLayout rlSiteName;
    @BindView(R.id.rl_repair_pro) // 维修项目
            RelativeLayout rlRepairPro;
    @BindView(R.id.rl_repair_person) // 维修人员
            RelativeLayout rlRepairPerson;
    @BindView(R.id.rl_return_fix) //是否返厂维修
            RelativeLayout rlReturnFix;
    @BindView(R.id.rl_device) //选择设备
            RelativeLayout rlDevice;
    @BindView(R.id.rl_repair_warranty)
    RelativeLayout rlRepairWarranty;

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
            ToastUtil.showToastShort(RecordDetailActivity.this, "请先选择联社");
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

    @OnClick(R.id.rl_repair_pro)
    void chooseRepairPro() {
        // 维修项目——多选
        if (projectItem == null) {
            getProject();
        } else {
            DialogUtil.showMultiChooseDialog(this, "选择维修项目", projectItem, projectItemIsChecked,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            repair_pro = StringFormatUtil.ListToString(chooseProjectResult);
                            repairProView.setText(repair_pro.equals("") ? "请选择维修项目" : repair_pro);
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

    @OnClick(R.id.rl_repair_person)
    void chooseRepairPerson() {
        //维修人员——多选
        if (repairManItem == null) {
            getRepairMan();
        } else {
            DialogUtil.showMultiChooseDialog(this, "选择维修人员", repairManItem, repairManItemIsChecked,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            repair_person = StringFormatUtil.ListToString(chooseRepairManResult);
                            repairPersonView.setText(repair_person.equals("") ? "请选择维修人员" : repair_person);
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

    @OnClick(R.id.rl_return_fix)
    void chooseReturnFix() {
        final String[] item = {"未返厂维修", "返厂维修"};
        DialogUtil.showSingleChooseDialog(this, "选择是否返厂", item,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                },
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return_fix = item[which];
                        returnFixView.setText(return_fix);
                    }
                }
        );
    }

    //是否在保修期内
    @OnClick(R.id.rl_repair_warranty)
    void chooseIsWarranty() {
        final String[] item = {"是", "否"};
        DialogUtil.showSingleChooseDialog(this, "选择是否在保修期内", item,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                },
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        warranty = item[which];
                        warrantyView.setText(warranty);
                    }
                }
        );
    }

    @OnClick(R.id.rl_device)
    void chooseDevice() {
        // 设备——多选
        DialogUtil.showMultiChooseDialog(RecordDetailActivity.this, "选择设备", deviceItem, deviceItemIsChecked,
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
        View view = View.inflate(RecordDetailActivity.this, R.layout.layout_dialog_edit, null);
        final EditText editText = view.findViewById(R.id.et_add);
        DialogUtil.showEditTextDialog(this, "请填写数量", view, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deviceNum[position] = editText.getText().toString();
                if (deviceNum[position].equals("")) {
                    ToastUtil.showToastShort(RecordDetailActivity.this, "请正确填写");
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
            DialogUtil.showAlertDialog(RecordDetailActivity.this, "确定删除吗", new DialogInterface.OnClickListener() {
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
            String url = URLConstant.REPAIR_ADD_RECORD;
            try {
                if (!_id.equals("")) {
                    jsonObject.put("_id", _id);
                    url = URLConstant.REPAIR_CHANGE_RECORD;
                }
                jsonObject.put("time", time);
                jsonObject.put("site_name", site_name);
                jsonObject.put("repair_pro", repair_pro);
                jsonObject.put("repair_person", repair_person);
                jsonObject.put("site_person", site_person);
                jsonObject.put("center_name", center_name);
                jsonObject.put("device", device);
                jsonObject.put("fix_state", fix_state);
                jsonObject.put("return_fix", return_fix);
                jsonObject.put("fix_cost", fix_cost);
                jsonObject.put("return_time", return_time);
                jsonObject.put("warranty", warranty);
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
                        ToastUtil.showToastShort(RecordDetailActivity.this, "请重试");
                    }
                }

                @Override
                public void onFail(String msg) {
                    ToastUtil.showToastShort(RecordDetailActivity.this, "请重试");
                }
            }, GetResultBean.class);
            finish();
        }
    }

    @OnClick(R.id.rl_back)
    void back() {
        onBackPressed();
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
                if (!UserInfoUtil.isSuperManager(getApplicationContext())) {
                    returnTimeView.setFocusable(false);
                }
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
        if (repair_person.equals("")) {
            ToastUtil.showToastShort(this, "请选择维修人员");
            return false;
        }
        if (repair_pro.equals("")) {
            ToastUtil.showToastShort(this, "请选择维修项目");
            return false;
        }
        if (warranty.equals("")) {
            ToastUtil.showToastShort(this, "请选择是否在保修期内");
            return false;
        }
        if (device.equals("")) {
            ToastUtil.showToastShort(this, "请选择设备明细");
            return false;
        }
        fix_state = fixStateView.getText().toString();
        if (fix_state.equals("")) {
            ToastUtil.showToastShort(this, "请填写维修详情");
            return false;
        }
        if (return_fix.equals("")) {
            ToastUtil.showToastShort(this, "请选择是否返厂维修");
            return false;
        }
        fix_cost = costView.getText().toString().equals("") ? 0 : Integer.valueOf(costView.getText().toString());
        return_time = returnTimeView.getText().toString().equals("") ? "未填写" : returnTimeView.getText().toString();
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
        RepairRecordBean.DataBean bean = (RepairRecordBean.DataBean) getIntent().getSerializableExtra(StringConstant.KEY_TRANSFER_RECORD);

        _id = bean.get_id();

        time = bean.getTime();
        timeView.setText(TimeUtil.transferTimeToShow(time));

        site_name = bean.getSite_name();
        siteNameView.setText(site_name);

        center_name = bean.getCenter_name();
        centerNameView.setText(center_name);

        repair_pro = bean.getRepair_pro();
        repairProView.setText(repair_pro);

        repair_person = bean.getRepair_person();
        repairPersonView.setText(repair_person);

        site_person = bean.getSite_person();
        sitePersonView.setText(site_person);

        device = bean.getDevice();
        deviceView.setText(device);

        fix_state = bean.getFix_state();
        fixStateView.setText(fix_state);

        fix_cost = bean.getFix_cost();
        costView.setText(String.valueOf(fix_cost));

        return_fix = bean.getReturn_fix();
        returnFixView.setText(return_fix);

        return_time = bean.getReturn_time();
        returnTimeView.setText(return_time);

        warranty = bean.getWarranty();
        warrantyView.setText(warranty);

        setViewUntouchable();
    }

    /**
     * 设置View不可编辑
     */
    public void setViewUntouchable() {
        llTime.setEnabled(false);
        rlCenterName.setEnabled(false);
        rlSiteName.setEnabled(false);
        rlRepairPro.setEnabled(false);
        rlRepairPerson.setEnabled(false);
        rlReturnFix.setEnabled(false);
        rlDevice.setEnabled(false);
        rlRepairWarranty.setEnabled(false);

        returnTimeView.setFocusable(false);
        sitePersonView.setFocusable(false);
        fixStateView.setFocusable(false);
        costView.setFocusable(false);
    }

    /**
     * 设置View可以编辑
     */
    public void setViewTouchable() {

        llTime.setEnabled(true);
        rlCenterName.setEnabled(true);
        rlSiteName.setEnabled(true);
        rlRepairPro.setEnabled(true);
        rlRepairPerson.setEnabled(true);
        rlReturnFix.setEnabled(true);
        rlDevice.setEnabled(true);
        rlRepairWarranty.setEnabled(true);

        returnTimeView.setFocusable(true);
        returnTimeView.setFocusableInTouchMode(true);
        costView.setFocusable(true);
        costView.setFocusableInTouchMode(true);
        sitePersonView.setFocusable(true);
        sitePersonView.setFocusableInTouchMode(true);
        fixStateView.setFocusable(true);
        fixStateView.setFocusableInTouchMode(true);

        btSubmit.setVisibility(View.VISIBLE);
    }

    private void deleteRecord() {
        ApiBuilder builder = new ApiBuilder().Url(URLConstant.REPAIR_DELETE_RECORD)
                .Params("_id", _id);
        ApiClient.getInstance().doGet(builder, new CallBack<GetResultBean>() {
            @Override
            public void onResponse(GetResultBean data) {
                if (data.getCode() == URLConstant.SUCCUSS_CODE) {
                    openActivityAndCleanUp(SuccessActivity.class);
                } else {
                    ToastUtil.showToastShort(RecordDetailActivity.this, "请重试");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(RecordDetailActivity.this, msg + "请重试");
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
                    ToastUtil.showToastShort(RecordDetailActivity.this, "获取联社列表失败");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(RecordDetailActivity.this, "获取联社列表失败");
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
                    ToastUtil.showToastShort(RecordDetailActivity.this, "查询失败请重试");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(RecordDetailActivity.this, "查询失败请重试");
            }
        }, SiteBean.class);
    }

    /**
     * 获取设备列表
     */
    private String[] deviceItem;//设备列表
    private boolean[] deviceItemIsChecked;//设备是否被选中
    private String[] deviceNum;
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
                    ToastUtil.showToastShort(RecordDetailActivity.this, "获取设备列表失败");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(RecordDetailActivity.this, "获取设备列表失败");
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
                    ToastUtil.showToastShort(RecordDetailActivity.this, "获取维修人员失败");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(RecordDetailActivity.this, "获取维修人员失败");
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
                    ToastUtil.showToastShort(RecordDetailActivity.this, "获取维修项目失败");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(RecordDetailActivity.this, "获取维修项目失败");
            }
        }, ProjectBean.class);
    }

}
