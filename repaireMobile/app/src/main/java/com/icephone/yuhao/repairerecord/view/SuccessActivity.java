package com.icephone.yuhao.repairerecord.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.icephone.yuhao.repairerecord.MainActivity;
import com.icephone.yuhao.repairerecord.R;
import com.icephone.yuhao.repairerecord.Util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuccessActivity extends BaseActivity {

    @OnClick(R.id.rl_back)
    void back() {
        openActivityAndCleanUp(MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucess);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initDate() {

    }
}
