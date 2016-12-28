package com.jzdtl.anywhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.utils.ActivityManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.group_view_guide)
    RadioGroup groupViewGuide;
    @BindView(R.id.radio_button_home)
    RadioButton radioButtonHome;
    @BindView(R.id.relative_main_register)
    RelativeLayout relativeMainRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        groupViewGuide.setOnCheckedChangeListener(this);
        radioButtonHome.setChecked(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        for (int j = 0; j < radioGroup.getChildCount(); j++) {
            RadioButton rb = (RadioButton) radioGroup.getChildAt(j);
            if (rb.isChecked()) {
                rb.setTextColor(getResources().getColor(R.color.colorPrimary));
            } else {
                rb.setTextColor(getResources().getColor(R.color.gray_bfbfbf));
            }
        }
    }

    @OnClick(R.id.relative_main_register)
    public void onClick() {
        ActivityManager.startActivity(this,new Intent(this,RegisterActivity.class));
////打开注册页面
//        RegisterPage registerPage = new RegisterPage();
//        registerPage.setRegisterCallback(new EventHandler() {
//            public void afterEvent(int event, int result, Object data) {
//// 解析注册结果
//                if (result == SMSSDK.RESULT_COMPLETE) {
//                    @SuppressWarnings("unchecked")
//                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
//                    String country = (String) phoneMap.get("country");
//                    String phone = (String) phoneMap.get("phone");
//
//// 提交用户信息（此方法可以不调用）
////                    registerUser(country, phone);
//                }
//            }
//        });
//        registerPage.show(this);
    }
}
