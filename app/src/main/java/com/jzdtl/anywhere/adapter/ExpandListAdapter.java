package com.jzdtl.anywhere.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.bean.StrategyBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gcy on 2017/1/5.
 */
public class ExpandListAdapter implements ExpandableListAdapter{
    private Context context;
    private List<StrategyBean.Page.Children> data = new ArrayList<>();
    public ExpandListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<StrategyBean.Page.Children> data) {
        this.data = data;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return data.get(i).getSections().size();
    }

    @Override
    public Object getGroup(int i) {
        return data.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return data.get(i).getSections().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupHolder groupHolder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_strategy_group,viewGroup,false);
            groupHolder = new GroupHolder(view);
            view.setTag(groupHolder);
        }else {
            groupHolder = (GroupHolder) view.getTag();
        }
        String groupName = data.get(i).getTitle();
        groupHolder.groupName.setText(groupName);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildHolder childHolder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_strategy_child,viewGroup,false);
            childHolder = new ChildHolder(view);
            view.setTag(childHolder);
        }else {
            childHolder = (ChildHolder) view.getTag();
        }
        childHolder.childName.setText(data.get(i).getSections().get(i1).getTitle());
        childHolder.childValue.setText(data.get(i).getSections().get(i1).getDescription());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int i) {

    }

    @Override
    public void onGroupCollapsed(int i) {

    }

    @Override
    public long getCombinedChildId(long l, long l1) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long l) {
        return 0;
    }

    class GroupHolder {
        private TextView groupName;

        public GroupHolder(View itemView) {
            groupName = (TextView) itemView.findViewById(R.id.text_strategy_groupname);
        }
    }

    class ChildHolder {
        private TextView childName;
        private TextView childValue;

        public ChildHolder(View itemView) {
            childName = (TextView) itemView.findViewById(R.id.text_strategy_child_title);
            childValue = (TextView) itemView.findViewById(R.id.text_strategy_child_content);
        }
    }
}
