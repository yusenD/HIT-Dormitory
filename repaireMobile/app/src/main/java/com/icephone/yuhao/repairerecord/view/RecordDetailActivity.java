package com.icephone.yuhao.repairerecord.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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
    private String person_phone = ""; // 申请人电话
    private String site_person = ""; //申请人
    private String fix_state = ""; //维修详情
    private String dormitory_name = ""; //公寓
    private String site_name = ""; //宿舍号
    private String start_time = ""; //申请维修时间
    private String end_time = ""; //维修完成时间
    private String repair_state = ""; //未维修
    private String repair_reverse = ""; //学生评价
    private String repair_person = ""; //维修人员
    private String device = "";

    private String[] stateItem = {"未维修", "已完成"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);
        ButterKnife.bind(this);
        initDate();
        initView();
    }

    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.iv_edit)
    ImageView ivEdit;
    @OnClick(R.id.iv_delete)
    void delete(){
        deleteRecord();
    }
    @OnClick(R.id.iv_edit)
    void edit(){
        setViewTouchable();
    }

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bt_submit)
    Button btSubmit;

    @BindView(R.id.tv_time) //时间（选择）
    TextView timeView;
    @BindView(R.id.tv_repair_time)
    TextView repairTimeView;
    @BindView(R.id.tv_repair_pro) //维修项目（多选）
    TextView repairProView;
    @BindView(R.id.tv_center_name) //公寓名称（单选）
    TextView centerNameView;
    @BindView(R.id.tv_state)
    TextView stateView;

    @BindView(R.id.et_fix_state) //维修详情（学生填写）
    EditText fixStateView;
    @BindView(R.id.et_site_name) //寝室号
    EditText siteNameView;
    @BindView(R.id.et_site_person) //申请人（填写）
    EditText sitePersonView;
    @BindView(R.id.et_repair_person)
    EditText repairPersonView;
    @BindView(R.id.et_person_phone)
    EditText personPhoneView;
    @BindView(R.id.et_repair_state)
    EditText repairStateView;
    @BindView(R.id.cv_repair_man)
    CardView repairCard;


    //需要选择的一些选项
    @BindView(R.id.ll_time) //开始时间
            LinearLayout llTime;
    @BindView(R.id.rl_center_name) //公寓
            RelativeLayout rlCenterName;
    @BindView(R.id.rl_repair_pro) // 维修项目
            RelativeLayout rlRepairPro;
    @BindView(R.id.rl_state) //维修状态
            RelativeLayout rlState;
    @BindView(R.id.rl_repair_time) //维修时间
            RelativeLayout rlRepairTime;

    @OnClick(R.id.ll_time)
    void showTimeDialog() {
        DialogUtil.showDateDialog(this, calendar, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                timeView.setText(TimeUtil.getShowTime(calendar));
                start_time = TimeUtil.getUploadTime(calendar); //时间
            }
        });
    }

    @OnClick(R.id.rl_repair_time)
    void repairTime() {
        DialogUtil.showDateDialog(this, calendar, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                repairTimeView.setText(TimeUtil.getShowTime(calendar));
                end_time = TimeUtil.getUploadTime(calendar); //时间
            }
        });
    }

    @OnClick(R.id.rl_center_name)
    void chooseCenterName() {
        if (centerItem == null) {
            getCenterList();
        } else {
            DialogUtil.showSingleChooseDialog(this, "选择公寓", centerItem,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    },
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dormitory_name = centerItem[which];
                            centerNameView.setText(centerItem[which]);
                        }
                    }
            );
        }
    }

    @OnClick(R.id.rl_cur_state)
    void chooseCurState() {
        DialogUtil.showSingleChooseDialog(this, "选择维修状态", stateItem,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                },
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stateView.setText(stateItem[which]);
                        repair_state = stateItem[which];
                    }
                }

        );
    }

//    private String[] deviceItem;//设备列表
//    private boolean[] deviceItemIsChecked;//设备是否被选中
//    private String[] deviceNum;
//    final List<String> chooseDeviceResult = new ArrayList<>();


    @OnClick(R.id.rl_repair_pro)
    void chooseRepairPro() {
        // 维修项目——多选
        if (deviceItem == null) {
            getDeviceList();
        } else {
            DialogUtil.showMultiChooseDialog(this, "选择维修项目", deviceItem, deviceItemIsChecked,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            device = StringFormatUtil.ListToString(chooseDeviceResult);
                            repairProView.setText(device.equals("") ? "请选择维修项目" : device);
                            dialog.dismiss();
                        }
                    }, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            deviceItemIsChecked[which] = isChecked;
                            if (isChecked) {
                                chooseDeviceResult.add(deviceItem[which]);
                            } else {
                                chooseDeviceResult.remove(deviceItem[which]);
                            }
                        }
                    });
        }
    }

    // 上传
    @OnClick(R.id.bt_submit)
    void submit() {
        if(keyMode.equals(StringConstant.KEY_ADD_MODE)){
            if(isTextNullStu()){
                uploadRecord();
            }
        }else{
            if(!UserInfoUtil.isStudent(this) && isTextNullRepair()){
                uploadRecord();
            }
        }
    }

    void uploadRecord(){
        //TODO 上传操作
        JSONObject jsonObject = new JSONObject();
        String url = URLConstant.REPAIR_ADD_RECORD;
        try {
            if (!_id.equals("")) {
                jsonObject.put("_id", _id);
                url = URLConstant.REPAIR_CHANGE_RECORD;
            }
            jsonObject.put("start_time", start_time);
            jsonObject.put("person_phone", person_phone);
            jsonObject.put("site_person", site_person);
            jsonObject.put("fix_stae", fix_state);
            jsonObject.put("dormitory_name", dormitory_name);
            jsonObject.put("site_name", site_name);
            jsonObject.put("end_time", end_time);
            jsonObject.put("repair_state", repair_state);
            jsonObject.put("repair_person", repair_person);
            jsonObject.put("repair_reverse", repair_reverse);
            jsonObject.put("device", device);

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

    @OnClick(R.id.rl_back)
    void back() {
        onBackPressed();
    }

    String keyMode;
    @Override
    public void initView() {
        //更新时间
        addRecordView();
        keyMode = getIntent().getStringExtra(StringConstant.KEY_MODE);
        switch (keyMode) {
            case StringConstant.KEY_ADD_MODE:
                tvTitle.setText("申请维修");
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
    }

    private boolean isTextNullStu() {
        if (start_time.equals("")) {
            ToastUtil.showToastShort(this, "请选择时间");
            return false;
        }
        if (dormitory_name.equals("")) {
            ToastUtil.showToastShort(this, "请选择公寓");
        }
        site_name = siteNameView.getText().toString();
        if (site_name.equals("")) {
            ToastUtil.showToastShort(this, "请填写寝室号");
            return false;
        }
        site_person = sitePersonView.getText().toString();
        if (site_person.equals("")) {
            ToastUtil.showToastShort(this, "请填写申请人");
            return false;
        }
        person_phone = personPhoneView.getText().toString();
        if(person_phone.equals("")){
            ToastUtil.showToastShort(this, "请填写申请人电话");
            return false;
        }
        if (device.equals("")) {
            ToastUtil.showToastShort(this, "请选择维修项目");
            return false;
        }
        repair_reverse = fixStateView.getText().toString();
        if(repair_reverse.equals("")){
            ToastUtil.showToastShort(this, "请填写报修详情");
            return false;
        }

        return true;
    }

    private boolean isTextNullRepair() {
        if (repair_state.equals("")) {
            ToastUtil.showToastShort(this, "请选择维修状态");
            return false;
        }
        if (end_time.equals("")) {
            ToastUtil.showToastShort(this, "请选择维修项目");
            return false;
        }
        repair_person = repairPersonView.getText().toString();
        if(repair_person.equals("")){
            ToastUtil.showToastShort(this, "请填写维修人");
            return false;
        }
        fix_state = repairStateView.getText().toString();
        if(repair_person.equals("")){
            ToastUtil.showToastShort(this, "请填写维修人");
            return false;
        }

        return true;
    }

    /**
     * 添加记录，把当前时间更新到对应时间
     */
    public void addRecordView() {
        start_time = TimeUtil.getUploadTime(calendar);
        timeView.setText(TimeUtil.getShowTime(calendar));
    }

    /**
     * 查看记录，把传过来的数据更新到View
     */
    public void putDataToView() {
        RepairRecordBean.DataBean bean = (RepairRecordBean.DataBean) getIntent().getSerializableExtra(StringConstant.KEY_TRANSFER_RECORD);

        _id = bean.get_id();

        start_time = bean.getStart_time();
        timeView.setText(TimeUtil.transferTimeToShow(start_time));

        site_name = bean.getSite_name();
        siteNameView.setText(site_name);

        dormitory_name = bean.getDormitory_name();
        centerNameView.setText(dormitory_name);

        site_person = bean.getSite_person();
        sitePersonView.setText(site_person);

        person_phone = bean.getPreson_phone();
        personPhoneView.setText(person_phone);

        device = bean.getDevice();
        repairProView.setText(device);

        repair_reverse = bean.getRepair_reverse();
        fixStateView.setText(repair_reverse);

        site_person = bean.getSite_person();
        sitePersonView.setText(site_person);

        fix_state = bean.getFix_state();
        fixStateView.setText(fix_state);

        repair_state = bean.getRepair_state();
        stateView.setText(repair_state);

        end_time = bean.getEnd_time();
        if(!end_time.equals("")){
            repairTimeView.setText(TimeUtil.transferTimeToShow(start_time));
        }
        repair_person = bean.getRepair_person();
        if(!repair_person.equals("")){
            repairPersonView.setText(repair_person);
        }
        fix_state = bean.getRepair_state();
        if(!fix_state.equals("")){
            repairStateView.setText(fix_state);
        }

        setViewUntouchable();
    }

    /**
     * 设置View不可编辑
     */
    public void setViewUntouchable() {
        llTime.setEnabled(false);
        rlCenterName.setEnabled(false);
        rlRepairPro.setEnabled(false);
        rlState.setEnabled(false);
        rlRepairTime.setEnabled(false);

        fixStateView.setFocusable(false);
        siteNameView.setFocusable(false);
        sitePersonView.setFocusable(false);
        repairPersonView.setFocusable(false);
        personPhoneView.setFocusable(false);
        repairStateView.setFocusable(false);
    }

    /**
     * 设置View可以编辑
     */
    public void setViewTouchable() {

        llTime.setEnabled(true);
        rlCenterName.setEnabled(true);
        rlState.setEnabled(true);
        rlRepairPro.setEnabled(true);
        rlRepairTime.setEnabled(true);

        sitePersonView.setFocusable(true);
        sitePersonView.setFocusableInTouchMode(true);
        fixStateView.setFocusable(true);
        fixStateView.setFocusableInTouchMode(true);

        siteNameView.setFocusable(true);
        siteNameView.setFocusableInTouchMode(true);

        repairPersonView.setFocusable(true);
        repairPersonView.setFocusableInTouchMode(true);

        personPhoneView.setFocusable(true);
        personPhoneView.setFocusableInTouchMode(true);

        repairStateView.setFocusable(true);
        repairStateView.setFocusableInTouchMode(true);

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
                            centerItem[i] = data.getData().get(i).getDormitory_name();
                        }
                    }
                } else {
                    ToastUtil.showToastShort(RecordDetailActivity.this, "获取公寓列表失败");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(RecordDetailActivity.this, "获取公寓列表失败");
            }
        }, CenterBean.class);
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
                    ToastUtil.showToastShort(RecordDetailActivity.this, "获取维修项目列表失败");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(RecordDetailActivity.this, "获取维修项目列表失败");
            }
        }, DeviceBean.class);
    }

}
