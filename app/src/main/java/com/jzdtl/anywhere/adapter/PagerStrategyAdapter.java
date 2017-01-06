package com.jzdtl.anywhere.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.bean.StrategyBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gcy on 2017/1/5.
 */
public class PagerStrategyAdapter extends PagerAdapter {
    private Context context;
    private List<StrategyBean> data = new ArrayList<>();
    private ExpandListAdapter adapter;
    private int count;
    private Activity mActivity;
    public PagerStrategyAdapter(Context context,Activity mActivity) {
        this.context = context;
        this.mActivity = mActivity;
    }

    public void setData(List<StrategyBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        count = 0;
        for (int i = 0; i < data.size(); i++) {
                count += data.get(i).getPages().size();
        }
        return count;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ExpandableListView expandableListView = (ExpandableListView) LayoutInflater.from(context).inflate(R.layout.view_expand_listview, null);
        adapter = new ExpandListAdapter(context,mActivity);
        expandableListView.setAdapter(adapter);
        List<StrategyBean.Page> lists =new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).getPages().size(); j++) {
                    StrategyBean.Page children = data.get(i).getPages().get(j);
                    lists.add(children);
            }
        }
        adapter.setData(lists.get(position).getChildrens());
        expandableListView.expandGroup(0);
        container.addView(expandableListView);
        return expandableListView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
