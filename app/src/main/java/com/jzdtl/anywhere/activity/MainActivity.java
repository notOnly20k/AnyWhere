package com.jzdtl.anywhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.utils.ActivityManager;
import com.jzdtl.anywhere.views.ItemView;

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
    @BindView(R.id.layout_user_login_true)
    RelativeLayout layoutUserLoginTrue;
    @BindView(R.id.layout_user_login_false)
    RelativeLayout layoutUserLoginFalse;
    @BindView(R.id.toolbar_image)
    ImageView toolbarImage;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragment_laout_for_replace)
    FrameLayout fragmentLaoutForReplace;
    @BindView(R.id.image_main_activity)
    ItemView imageMainActivity;
    @BindView(R.id.image_main_like)
    ItemView imageMainLike;
    @BindView(R.id.image_main_feedback)
    ItemView imageMainFeedback;
    @BindView(R.id.image_main_set)
    ItemView imageMainSet;
    private boolean login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initDatas();
        checkLogin();
        initEvents();
        radioButtonHome.setChecked(true);
    }

    private void initDatas() {
        toolbarImage.setImageResource(R.mipmap.menu);
        toolbarTitle.setText("首页");
        toolbarSubtitle.setVisibility(View.GONE);
        login = spUtils.getBoolean("login", false);
    }

    private void checkLogin() {
        if (login) {
            layoutUserLoginFalse.setVisibility(View.GONE);
            layoutUserLoginTrue.setVisibility(View.VISIBLE);
        } else {
            layoutUserLoginFalse.setVisibility(View.VISIBLE);
            layoutUserLoginTrue.setVisibility(View.GONE);
        }
    }

    private void initEvents() {
        groupViewGuide.setOnCheckedChangeListener(this);
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

    @OnClick({R.id.relative_main_register, R.id.layout_user_login_true, R.id.layout_user_login_false,
    R.id.image_main_activity,R.id.image_main_like,R.id.image_main_feedback,R.id.image_main_set})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_user_login_true:
                //TODO 个人中心
                break;
            case R.id.layout_user_login_false:
                goLogin();
                break;
            case R.id.image_main_activity:
                if (login){

                }else {
                    goLogin();
                }
                break;
            case R.id.image_main_like:
                if (login){

                }else {
                    goLogin();
                }
                break;
            case R.id.image_main_feedback:
                if (login){

                }else {
                    goLogin();
                }
                break;
            case R.id.image_main_set:
                break;
        }
    }

    private void goLogin() {
        ActivityManager.startActivity(this,new Intent(this,LoginActivity.class));
    }
}
