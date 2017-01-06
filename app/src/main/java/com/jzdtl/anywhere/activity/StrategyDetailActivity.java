package com.jzdtl.anywhere.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.adapter.PagerStrategyAdapter;
import com.jzdtl.anywhere.bean.StrategyBean;
import com.jzdtl.anywhere.utils.ActivityManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StrategyDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private static final String TAG = "StrategyDetailActivity";
    @BindView(R.id.pager_strategy_content)
    ViewPager pagerStrategyContent;
    @BindView(R.id.activity_strategy_detail)
    LinearLayout activityStrategyDetail;
    @BindView(R.id.toolbar_image)
    ImageView toolbarImage;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Bundle bundle;
    private int group;
    private int child;
    private ArrayList<StrategyBean> datas;
    private PagerStrategyAdapter adapter;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        bundle = getIntent().getExtras();
        if (bundle != null) {
            group = bundle.getInt("group");
            child = bundle.getInt("child");
            datas = bundle.getParcelableArrayList("data");
            adapter = new PagerStrategyAdapter(this, this);
            pagerStrategyContent.setAdapter(adapter);
            adapter.setData(datas);
            for (int i = 0; i < group; i++) {
                count += datas.get(i).getPages().size();
            }
            pagerStrategyContent.setCurrentItem(count + child);
            pagerStrategyContent.addOnPageChangeListener(this);
            toolbarImage.setImageResource(R.mipmap.back_icon);
            toolbarTitle.setText(namesList().get(count+child));
            toolbarSubtitle.setVisibility(View.GONE);

        }
    }

    private List<String> namesList() {
        List<String> lists =new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            for (int j = 0; j < datas.get(i).getPages().size(); j++) {
                StrategyBean.Page children = datas.get(i).getPages().get(j);
                lists.add(children.getTitle()+" | "+getTotalName(datas.get(i).getType()));
            }
        }
        return lists;
    }

    private String getTotalName(String type) {
        String totalTitle = "";
        switch (type){
            case "0"://概览
                totalTitle = "概览";
                break;
            case "5"://娱乐
                totalTitle = "娱乐";
                break;
            case "7"://美食
                totalTitle = "美食";
                break;
            case "8"://购物
                totalTitle = "购物";
                break;
            case "11"://其他
                totalTitle = "其他";
                break;
        }
        return totalTitle;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_strategy_detail;
    }

    @OnClick(R.id.toolbar_image)
    public void onClick() {
        ActivityManager.finishActivity(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        toolbarTitle.setText(namesList().get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
