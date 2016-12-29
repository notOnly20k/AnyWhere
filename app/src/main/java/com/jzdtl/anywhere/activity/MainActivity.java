package com.jzdtl.anywhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.fragment.ActivitiesFragment;
import com.jzdtl.anywhere.fragment.IndexFragment;
import com.jzdtl.anywhere.fragment.NearbyFragment;
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
    private FragmentTransaction transation;
    private FragmentManager manager;
    private ActivitiesFragment activitiesFragment;
    private IndexFragment indexFragment;
    private NearbyFragment nearbyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        groupViewGuide.setOnCheckedChangeListener(this);
        initFragMent();
        radioButtonHome.setChecked(true);

    }

    private void initFragMent() {
        manager = getSupportFragmentManager();
        activitiesFragment = new ActivitiesFragment();
        indexFragment = new IndexFragment();
        nearbyFragment = new NearbyFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        transation = manager.beginTransaction();
        for (int j = 0; j < radioGroup.getChildCount(); j++) {
            RadioButton rb = (RadioButton) radioGroup.getChildAt(j);
            if (rb.isChecked()) {
                rb.setTextColor(getResources().getColor(R.color.colorPrimary));
            } else {
                rb.setTextColor(getResources().getColor(R.color.gray_bfbfbf));
            }
        }
        switch (i){
            case R.id.radio_button_home:
                transation.replace(R.id.fragment_laout_for_replace,indexFragment);
                transation.commit();
                break;
            case R.id.radio_button_index:

                transation.replace(R.id.fragment_laout_for_replace,activitiesFragment);
                transation.commit();
                break;
            case R.id.radio_button_nearby:
                transation.replace(R.id.fragment_laout_for_replace,nearbyFragment);
                transation.commit();
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
