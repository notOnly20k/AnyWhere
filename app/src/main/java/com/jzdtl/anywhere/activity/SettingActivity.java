package com.jzdtl.anywhere.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.bean.LoginOutEvent;
import com.jzdtl.anywhere.utils.ActivityManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar_image)
    ImageView toolbarImage;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_setting_check)
    CheckBox layoutSettingCheck;
    @BindView(R.id.layout_setting_push)
    RelativeLayout layoutSettingPush;
    @BindView(R.id.layout_setting_cache)
    TextView layoutSettingCache;
    @BindView(R.id.layout_setting_clear)
    RelativeLayout layoutSettingClear;
    @BindView(R.id.layout_setting_update)
    RelativeLayout layoutSettingUpdate;
    @BindView(R.id.layout_setting_out)
    RelativeLayout layoutSettingOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        toolbarImage.setImageResource(R.mipmap.back_icon);
        toolbarTitle.setText("设置");
        toolbarSubtitle.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @OnClick({R.id.toolbar_image, R.id.layout_setting_push, R.id.layout_setting_clear, R.id.layout_setting_update, R.id.layout_setting_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_image:
                ActivityManager.finishActivity(this);
                break;
            case R.id.layout_setting_push:
                layoutSettingCheck.setChecked(!layoutSettingCheck.isChecked());
                break;
            case R.id.layout_setting_clear:
                layoutSettingCache.setText("0M");
                break;
            case R.id.layout_setting_update:
                Snackbar.make(layoutSettingOut,"当前已是最新版本",Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.layout_setting_out:
                ActivityManager.finishActivity(this);
                EventBus.getDefault().post(new LoginOutEvent());
                break;
        }
    }
}
