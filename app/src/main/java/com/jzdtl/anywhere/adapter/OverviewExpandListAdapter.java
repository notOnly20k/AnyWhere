package com.jzdtl.anywhere.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.bean.OverviewBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/8 0008.
 */

public class OverviewExpandListAdapter extends BaseExpandableListAdapter {
//    private OverviewBean overviewBean;
    private Context mContext;
    private List<OverviewBean.DataBean.DestinationsBean> mDestinations;

    public OverviewExpandListAdapter(Context context, List<OverviewBean.DataBean.DestinationsBean> destinations) {
        mContext = context;
        mDestinations = destinations;
    }

    @Override
    public int getGroupCount() {
        return mDestinations == null ?0:mDestinations.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mDestinations.get(groupPosition);


    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDestinations.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_poiresult,parent,false);
        }
        convertView.setTag(R.layout.item_poiresult,groupPosition);
        convertView.setTag(R.layout.item_expandlist_child,-1);

        ((TextView) convertView.findViewById(R.id.tv_name))
                .setText(mDestinations.get(groupPosition).getName()
                +","+mDestinations.get(groupPosition).getTitle());
        ((TextView) convertView.findViewById(R.id.tv_addr))
                .setText(mDestinations.get(groupPosition).getVisit_tip());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_expandlist_child, parent, false);
        }
        convertView.setTag(R.layout.item_poiresult,groupPosition);
        convertView.setTag(R.layout.item_expandlist_child,childPosition);
        final ImageView img = (ImageView)convertView.findViewById(R.id.img_child_photo);

        Glide.with(mContext).load(mDestinations.get(groupPosition).getPhoto_url()).into(img);
        ((TextView) convertView.findViewById(R.id.tv_description))
                .setText(mDestinations.get(groupPosition).getDescription());
        convertView.findViewById(R.id.tv_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"点到了地图跳转",Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
