package com.jzdtl.anywhere.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.bean.StrategyTypeBean;
import com.jzdtl.anywhere.callback.OnStrategyItemClickListener;
import com.jzdtl.anywhere.views.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gcy on 2017/1/5.
 */
public class StrategyAdapter extends RecyclerView.Adapter<StrategyAdapter.StrategyViewHolder> {
    private Context context;
    private List<StrategyTypeBean> data = new ArrayList<>();
    private StrategyItemAdapter adapter;
    private OnStrategyItemClickListener mOnStrategyItemClickListener;
    public StrategyAdapter(Context context) {
        this.context = context;
    }

    public void setmOnStrategyItemClickListener(OnStrategyItemClickListener mOnStrategyItemClickListener) {
        this.mOnStrategyItemClickListener = mOnStrategyItemClickListener;
    }

    public void setData(List<StrategyTypeBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public StrategyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_strategy_index, parent, false);
        return new StrategyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StrategyViewHolder holder, int position) {
        StrategyTypeBean strategyTypeBean = data.get(position);
        Glide.with(context).load(strategyTypeBean.getRes()).into(holder.imageStrategyPic);
        holder.textStrategyNameCn.setText(strategyTypeBean.getNameCN());
        holder.textStrategyNameEn.setText(strategyTypeBean.getNameEN());
        adapter = new StrategyItemAdapter(context);
        holder.gridStrategyIndex.setAdapter(adapter);
        adapter.setData(strategyTypeBean.getTitle());
        holder.gridStrategyIndex.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int group = holder.getLayoutPosition();
                mOnStrategyItemClickListener.onStrategyItemClickListener(group,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class StrategyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_strategy_pic)
        ImageView imageStrategyPic;
        @BindView(R.id.text_strategy_name_cn)
        TextView textStrategyNameCn;
        @BindView(R.id.text_strategy_name_en)
        TextView textStrategyNameEn;
        @BindView(R.id.grid_strategy_index)
        MyGridView gridStrategyIndex;
        public StrategyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
