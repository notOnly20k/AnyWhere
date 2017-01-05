package com.jzdtl.anywhere.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.baidu.mapapi.search.core.PoiInfo;
import com.jzdtl.anywhere.R;

import java.util.List;

/**
 * Created by Administrator on 2017/1/4 0004.
 */

public class PoiResultAdapter extends BaseRecyclerAdapter<PoiResultAdapter.ViewHolder> {

    private List<PoiInfo> data;

    public PoiResultAdapter(List<PoiInfo> data) {
        this.data = data;
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_poiresult,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {
        holder.tv_name.setText(data.get(position).name);
        holder.tv_addr.setText(data.get(position).address);

    }

    @Override
    public int getAdapterItemCount() {
        return data == null?0:data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name;
        TextView tv_addr;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_addr = (TextView)itemView.findViewById(R.id.tv_addr);
        }
    }
}
