package com.jzdtl.anywhere.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.activity.StrategyActivity;
import com.jzdtl.anywhere.utils.ActivityManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gcy on 2017/1/4.
 */

public class GoodsView extends LinearLayout implements View.OnClickListener {
    @BindView(R.id.image_pager_pic)
    ImageView imagePagerPic;
    @BindView(R.id.text_pager_name)
    TextView textPagerName;
    private View mView;
    private String url = null;
    private Context context;
    private Activity activity;
    private String strategy;
    public GoodsView(Context context) {
        super(context);
        initViews(context);
    }

    public GoodsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    private void initViews(Context context) {
        this.context = context;
        mView = LayoutInflater.from(context).inflate(R.layout.view_pager_item, null);
        ButterKnife.bind(this, mView);
        addView(mView);
        mView.setOnClickListener(this);
    }

    public void setImagePic(String pic) {
        Glide.with(context).load(pic).into(imagePagerPic);
    }

    public void setTextName(String name) {
        textPagerName.setText(name);
    }

    public void setUrl(String url, Activity activity){
        this.url = url;
        this.activity = activity;
    }
    public void setStrategy(String strategy){
        this.strategy = strategy;
    }
    @Override
    public void onClick(View view) {
        if (!textPagerName.getText().equals("攻略")){
            Intent intentBrowse = new Intent();
            intentBrowse.setAction("android.intent.action.VIEW");
            Uri ticketUri = Uri.parse(url);
            intentBrowse.setData(ticketUri);
            ActivityManager.startActivity(activity, intentBrowse);
        }else {
            Intent intent = new Intent(activity, StrategyActivity.class);
            ActivityManager.startActivity(activity, intent.putExtra("id",strategy));
        }
    }
}
