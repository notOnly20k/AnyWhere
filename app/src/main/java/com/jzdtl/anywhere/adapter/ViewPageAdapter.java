package com.jzdtl.anywhere.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by cz on 2017/1/3.
 */

public class ViewPageAdapter extends FragmentPagerAdapter{
    private List<Fragment>list;
    private List<Integer>list_title;

    public ViewPageAdapter(FragmentManager fm, List<Fragment> list, List<Integer> list_title) {
        super(fm);
        this.list = list;
        this.list_title = list_title;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
