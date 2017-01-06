package com.jzdtl.anywhere.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.bean.StrategyBean;
import com.jzdtl.anywhere.callback.OnStrategyPhotoClickListener;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPreview;

/**
 * Created by gcy on 2017/1/5.
 */
public class ExpandListAdapter implements ExpandableListAdapter{
    private Context context;
    private Activity mActivity;
    private ArrayList<StrategyBean.Page.Children> data = new ArrayList<>();
    public ExpandListAdapter(Context context,Activity mActivity) {
        this.context = context;
        this.mActivity = mActivity;
    }

    public void setData(ArrayList<StrategyBean.Page.Children> data) {
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
        final ChildHolder childHolder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_strategy_child,viewGroup,false);
            childHolder = new ChildHolder(view);
            view.setTag(childHolder);
        }else {
            childHolder = (ChildHolder) view.getTag();
        }
        String title = data.get(i).getSections().get(i1).getTitle();
        if (title == null || title.equals("")){
            childHolder.childName.setVisibility(View.GONE);
        }else {
            childHolder.childName.setText(title);
        }

        String description = data.get(i).getSections().get(i1).getDescription();
        if (description == null || description.equals("")){
            childHolder.childValue.setVisibility(View.GONE);
        }else {
            childHolder.childValue.setText(description);
        }
        ArrayList<String> photos = data.get(i).getSections().get(i1).getPhotos();
        if (photos == null || photos.size() == 0){
            childHolder.childPhoto.setVisibility(View.GONE);
        }else {
            childHolder.childPhoto.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            ExpandPhotoAdapter photoAdapter = new ExpandPhotoAdapter(context);
            childHolder.childPhoto.setAdapter(photoAdapter);
            photoAdapter.setData(photos);
            photoAdapter.setmOnStrategyPhotoClickListener(new OnStrategyPhotoClickListener() {
                @Override
                public void onStrategyPhotoClickListener(int pos, ArrayList<String> photo) {

                    PhotoPreview.builder()
                            .setPhotos(photo)
                            .setCurrentItem(pos)
                            .setShowDeleteButton(false)
                            .start(mActivity);
                }
            });
        }
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
        private RecyclerView childPhoto;

        public ChildHolder(View itemView) {
            childName = (TextView) itemView.findViewById(R.id.text_strategy_child_title);
            childValue = (TextView) itemView.findViewById(R.id.text_strategy_child_content);
            childPhoto = (RecyclerView) itemView.findViewById(R.id.recycler_strategy_photo);
        }
    }
}
