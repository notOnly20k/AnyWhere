package com.jzdtl.anywhere.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.adapter.MyAdaoter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.activity_main)
    CoordinatorLayout activityMain;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ctl)
    CollapsingToolbarLayout ctl;
    @BindView(R.id.abl)
    AppBarLayout abl;
    @BindView(R.id.group_view_guide)
    RadioGroup groupViewGuide;
    @BindView(R.id.radio_button_home)
    RadioButton radioButtonHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        ctl.setTitle("标题");
        ctl.setCollapsedTitleTextColor(Color.WHITE);
        ctl.setCollapsedTitleGravity(Gravity.CENTER);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("数据" + i);
        }
        recyclerView.setAdapter(new MyAdaoter(this, data));
        groupViewGuide.setOnCheckedChangeListener(this);
        radioButtonHome.setChecked(true);
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
}
