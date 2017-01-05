package com.jzdtl.anywhere.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jzdtl.anywhere.bean.DestinationsResult;
import com.jzdtl.anywhere.views.GoodsView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gcy on 2017/1/4.
 */
public class GoodsPagerAdapter extends PagerAdapter {
    private Context context;
    private List<DestinationsResult.DataBean.GoodsBean> data = new ArrayList<>();
    private Activity activity;
    public GoodsPagerAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DestinationsResult.DataBean.GoodsBean> data,Activity activity) {
        this.data = data;
        this.activity = activity;
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,4));
        if (position==0){
            for (int i = 0; i < 4; i++) {
                GoodsView goodsView = new GoodsView(context);
                goodsView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
                goodsView.setImagePic(data.get(i).getPhoto_url());
                goodsView.setTextName(data.get(i).getTitle());
                goodsView.setUrl(data.get(i).getUrl(),activity);
                goodsView.setGravity(Gravity.CENTER);
                linearLayout.addView(goodsView);
            }
        }else {
            for (int i = 4; i < data.size(); i++) {
                GoodsView goodsView = new GoodsView(context);
                goodsView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
                goodsView.setImagePic(data.get(i).getPhoto_url());
                goodsView.setTextName(data.get(i).getTitle());
                goodsView.setUrl(data.get(i).getUrl(),activity);
                goodsView.setGravity(Gravity.CENTER);
                linearLayout.addView(goodsView);
            }
        }
        container.addView(linearLayout);
        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return data.size()==0?0:(data.size()>4?2:1);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
