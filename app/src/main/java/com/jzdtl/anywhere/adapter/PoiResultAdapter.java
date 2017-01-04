package com.jzdtl.anywhere.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.jzdtl.anywhere.R;

import java.util.List;

/**
 * Created by Administrator on 2017/1/4 0004.
 */

public class PoiResultAdapter extends RecyclerView.Adapter<PoiResultAdapter.ViewHolder> {

    private List<PoiInfo> mList;

    public PoiResultAdapter(List<PoiInfo> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_poiresult,null,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_name.setText(mList.get(position).name);
        holder.tv_addr.setText(mList.get(position).address);
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name;
        TextView tv_addr;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_addr = (TextView) itemView.findViewById(R.id.tv_addr);
        }
    }
}
