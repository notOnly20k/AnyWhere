package com.jzdtl.anywhere.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.bean.IndexResult;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gcy on 2016/12/30.
 */
public class IndexGrideAdapter extends BaseAdapter {
    private Context context;
    private List<IndexResult.DataBean.DestinationsBean> data = new ArrayList<>();
    private LayoutInflater inflater;

    public IndexGrideAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<IndexResult.DataBean.DestinationsBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public IndexResult.DataBean.DestinationsBean getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        IndexGrideViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_index_gride, viewGroup, false);
            holder = new IndexGrideViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (IndexGrideViewHolder) view.getTag();
        }
        IndexResult.DataBean.DestinationsBean item = getItem(i);
        Glide.with(context).load(item.getPhoto_url()).into(holder.imageGridePic);
        holder.textGrideName.setText(item.getName());
        holder.textGrideNameEn.setText(item.getName_en());
        return view;
    }

    static class IndexGrideViewHolder {
        @BindView(R.id.image_gride_pic)
        RoundedImageView imageGridePic;
        @BindView(R.id.text_gride_name)
        TextView textGrideName;
        @BindView(R.id.text_gride_name_en)
        TextView textGrideNameEn;

        public IndexGrideViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
