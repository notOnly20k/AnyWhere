package com.jzdtl.anywhere.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.LinearLayout;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.adapter.PagerStrategyAdapter;
import com.jzdtl.anywhere.bean.ExpandListBean;
import com.jzdtl.anywhere.bean.StrategyBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StrategyDetailActivity extends BaseActivity {
    private static final String TAG = "StrategyDetailActivity";
    @BindView(R.id.pager_strategy_content)
    ViewPager pagerStrategyContent;
    @BindView(R.id.activity_strategy_detail)
    LinearLayout activityStrategyDetail;
    private Bundle bundle;
    private int group;
    private int child;
    private ArrayList<StrategyBean> datas;
    private PagerStrategyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            group = bundle.getInt("group");
            child = bundle.getInt("child");
            datas = bundle.getParcelableArrayList("data");
            adapter = new PagerStrategyAdapter(this);
            pagerStrategyContent.setAdapter(adapter);
            adapter.setData(datas);
            Log.d(TAG, "onCreate: "+datas.toString());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_strategy_detail;
    }
}
