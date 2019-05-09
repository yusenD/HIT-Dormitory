package com.icephone.yuhao.repairerecord.view;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icephone.yuhao.repairerecord.R;
import com.icephone.yuhao.repairerecord.Util.DialogUtil;
import com.icephone.yuhao.repairerecord.Util.StringConstant;
import com.icephone.yuhao.repairerecord.Util.ToastUtil;
import com.icephone.yuhao.repairerecord.Util.UserInfoUtil;
import com.icephone.yuhao.repairerecord.bean.CenterBean;
import com.icephone.yuhao.repairerecord.bean.GetResultBean;
import com.icephone.yuhao.repairerecord.bean.PeopleBean;
import com.icephone.yuhao.repairerecord.net.ApiBuilder;
import com.icephone.yuhao.repairerecord.net.ApiClient;
import com.icephone.yuhao.repairerecord.net.CallBack;
import com.icephone.yuhao.repairerecord.net.URLConstant;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PeopleDetailActivity extends BaseActivity {

    //选择人员权限身份
    @OnClick(R.id.rl_person_limit)
    void chooseLimit() {
        final String[] item = {"维修人员", "联社管理员", "总管理员"};
        DialogUtil.showSingleChooseDialog(this, "选择权限", item,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                },
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        personLimitView.setText(item[which]);
                        limit = item[which];
                        if(limit.equals(UserInfoUtil.LIMIT_SUPER_MANAGER)){
                            centerNameView.setText("全部");
                            manage_center = "全部";
                        }
                        if(limit.equals(UserInfoUtil.LIMIT_REPAIR_MAN)) {
                            centerNameView.setText("无");
                            manage_center = "无";
                        }
                    }
                }

        );
    }

    //选择联社名称
    @OnClick(R.id.rl_center_name)
    void chooseCenterName() {
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
                        centerNameView.setText(centerItem[which]);
                        manage_center = centerItem[which];
                    }
                }

        );
    }

    @OnClick(R.id.bt_submit)
    void submit() {
        if (isTextNull()) {
            //TODO 上传操作
            String url = URLConstant.PERSON_ADD;
            JSONObject jsonObject = new JSONObject();
            try {
                if (!_id.equals("")) {
                    jsonObject.put("_id", _id);
                    url = URLConstant.PERSON_CHANGE;
                }
                jsonObject.put("nick_name", nick_name);
                jsonObject.put("account", account);
                jsonObject.put("password", password);
                jsonObject.put("limit", limit);
                jsonObject.put("manage_center", manage_center);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("上传测试", "submit: " + jsonObject.toString());
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
            ApiBuilder builder = new ApiBuilder()
                    .Url(url)
                    .Body(requestBody);
            ApiClient.getInstance().doPost(builder, new CallBack<GetResultBean>() {
                @Override
                public void onResponse(GetResultBean data) {
                    if (data.getCode()==URLConstant.SUCCUSS_CODE){
                        openActivityAndCleanUp(SuccessActivity.class);
                        finish();
                    }else{
                        ToastUtil.showToastShort(PeopleDetailActivity.this,data.getMsg());
                    }
                }

                @Override
                public void onFail(String msg) {
                    ToastUtil.showToastShort(PeopleDetailActivity.this,"失败，请重试");
                }
            },GetResultBean.class);
        }
    }

    @OnClick(R.id.iv_edit)
    void editMode() {
        setViewTouchable();
    }

    @BindView(R.id.iv_edit)
    ImageView ivEdit;
    //提交按钮
    @BindView(R.id.bt_submit)
    Button btSubmit;
    //用户昵称
    @BindView(R.id.et_person_nickname)
    EditText nickNameView;
    //用户账号
    @BindView(R.id.et_person_account)
    EditText accountView;
    //用户密码
    @BindView(R.id.et_person_password)
    EditText passowordView;
    //联社名称
    @BindView(R.id.tv_center_name)
    TextView centerNameView;
    //用户权限
    @BindView(R.id.tv_person_limit)
    TextView personLimitView;
    //权限框
    @BindView(R.id.rl_person_limit)
    RelativeLayout rlPersonLimit;
    //联社名称框
    @BindView(R.id.rl_center_name)
    RelativeLayout rlCenterName;


    @OnClick(R.id.rl_back)
    void back() {
        onBackPressed();
    }

    private String _id = "";
    private String manage_center = "无";
    private String nick_name = "";
    private String account = "";
    private String limit = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_detail);
        ButterKnife.bind(this);
        initDate();
        initView();
    }

    @Override
    public void initView() {
        //如果是查看模式，那么数据预先填好，点击修改按钮之后可以进行修改。
        String mode = getIntent().getStringExtra(StringConstant.KEY_MODE);
        if (mode.equals(StringConstant.KEY_LOOK_PEOPLE)) {
            PeopleBean.DataBean bean = (PeopleBean.DataBean) getIntent().getSerializableExtra(StringConstant.KEY_TRANSFER_PEOPLE);
            //填充数据
            _id = bean.get_id();
            account = bean.getAccount();
            accountView.setText(account);
            password = bean.getPassword();
            passowordView.setText(password);
            nick_name = bean.getNick_name();
            nickNameView.setText(nick_name);
            limit = bean.getLimit();
            personLimitView.setText(limit);
            manage_center = bean.getManage_center();
            centerNameView.setText(manage_center);
            btSubmit.setVisibility(View.INVISIBLE);
            setViewUntouchable();

            //如果是添加模式，那么都可以填,隐藏编辑按钮
        } else if (mode.equals(StringConstant.KEY_ADD_PEOPLE)) {
            ivEdit.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void initDate() {
        getCenterList();
    }

    //检查数据是否符合
    private boolean isTextNull() {
        nick_name = nickNameView.getText() == null ? "" : nickNameView.getText().toString();
        if (nick_name.equals("")) {
            ToastUtil.showToastShort(this, "请填写用户昵称");
            return false;
        }
        account = accountView.getText() == null ? "" : accountView.getText().toString();
        if (account.equals("")) {
            ToastUtil.showToastShort(this, "请填写用户账号");
            return false;
        }
        password = passowordView.getText() == null ? "" : passowordView.getText().toString();
        if (password.equals("")) {
            ToastUtil.showToastShort(this, "请填写网点人员");
            return false;
        }
        if (limit.equals("")) {
            ToastUtil.showToastShort(this, "请选择用户权限");
            return false;
        } else {
            if (limit.equals(UserInfoUtil.LIMIT_MANAGER) && manage_center.equals("")) {
                ToastUtil.showToastShort(this, "请选择管理联社");
                return false;
            }
            if(limit.equals(UserInfoUtil.LIMIT_SUPER_MANAGER)) manage_center = "全部";
            if(limit.equals(UserInfoUtil.LIMIT_REPAIR_MAN)) manage_center = "无";
         }

        return true;
    }

    /**
     * 设置View不可编辑
     */
    public void setViewUntouchable() {
        rlPersonLimit.setEnabled(false);
        rlCenterName.setEnabled(false);
//        rlSiteName.setFocusable(false);
//        rlRepairPro.setFocusable(false);
//        rlRepairPerson.setFocusable(false);

        accountView.setFocusable(false);
        passowordView.setFocusable(false);
        nickNameView.setFocusable(false);
    }

    /**
     * 设置View可以编辑
     */
    public void setViewTouchable() {

        rlCenterName.setEnabled(true);
        rlPersonLimit.setEnabled(true);
//        rlRepairPro.setFocusable(true);
//        rlRepairPerson.setFocusable(true);

        accountView.setFocusable(true);
        accountView.setFocusableInTouchMode(true);
        passowordView.setFocusable(true);
        passowordView.setFocusableInTouchMode(true);
        nickNameView.setFocusable(true);
        nickNameView.setFocusableInTouchMode(true);

        btSubmit.setVisibility(View.VISIBLE);
    }

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
                    ToastUtil.showToastShort(PeopleDetailActivity.this, "获取联社列表失败");
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToastShort(PeopleDetailActivity.this, "获取联社列表失败");
            }
        }, CenterBean.class);
    }
}
