package com.jzdtl.anywhere.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.bean.DestBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by gcy on 2017/1/3.
 */
public class DestAdapter extends RecyclerView.Adapter<DestAdapter.DestViewHolder> {
    private Context context;

    public void setData(List<DestBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    private List<DestBean> data = new ArrayList<>();

    public DestAdapter(Context context) {
        this.context = context;
    }

    @Override
    public DestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_index_gride, parent, false);
        return new DestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DestViewHolder holder, int position) {
        DestBean modelsBean = data.get(position);
        String photo_url = modelsBean.getImgUrl();
        Glide.with(context).load(photo_url).into(holder.imageGridePic);
        String nameCN = modelsBean.getName();
        holder.textGrideName.setText(nameCN);
        String nameEN = modelsBean.getNameEN();
        holder.textGrideNameEn.setText(nameEN);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class DestViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_gride_pic)
        RoundedImageView imageGridePic;
        @BindView(R.id.text_gride_name)
        TextView textGrideName;
        @BindView(R.id.text_gride_name_en)
        TextView textGrideNameEn;
        public DestViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}