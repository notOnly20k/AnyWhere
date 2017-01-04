package com.jzdtl.anywhere.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.bean.RegionResult;
import com.jzdtl.anywhere.callback.OnDestListItemClickListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gcy on 2017/1/4.
 */
public class DestListAdapter extends RecyclerView.Adapter<DestListAdapter.ListViewHolder> {
    private Context context;
    private List<RegionResult.DataBean> data = new ArrayList<>();
    private OnDestListItemClickListener onDestListItemClickListener;

    public void setOnDestListItemClickListener(OnDestListItemClickListener onDestListItemClickListener) {
        this.onDestListItemClickListener = onDestListItemClickListener;
    }

    public DestListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<RegionResult.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_list_dest, parent, false);
        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, int position) {
        RegionResult.DataBean dataBean = data.get(position);
        Glide.with(context).load(dataBean.getPhoto_url()).into(holder.imageListPic);
        holder.textListNameCn.setText(dataBean.getName());
        holder.textListNameEn.setText(dataBean.getName_en());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getLayoutPosition();
                String path = data.get(pos).getPath();
                String city = getDestinationsPath(path);
                onDestListItemClickListener.onDestListItemClickListener(city);
            }
        });
    }
    public String getDestinationsPath(String path) {
        //.1.5.166.111.
        String[] strings = path.split("\\.");
        String realPath = strings[strings.length-1];
        return realPath;
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_list_pic)
        RoundedImageView imageListPic;
        @BindView(R.id.text_list_name_cn)
        TextView textListNameCn;
        @BindView(R.id.text_list_name_en)
        TextView textListNameEn;
        public ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
