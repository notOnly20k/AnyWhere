package com.jzdtl.anywhere.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.baidu.mapapi.search.core.PoiInfo;
import com.jzdtl.anywhere.R;

import java.util.List;

/**
 * Created by Administrator on 2017/1/4 0004.
 */

public class PoiResultAdapter extends BaseAdapter {

    private List<PoiInfo> data;
    private Context mContext;

    public PoiResultAdapter(List<PoiInfo> data, Context context) {
        this.data = data;
        mContext = context;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_poiresult,parent,false);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_addr = (TextView) convertView.findViewById(R.id.tv_addr);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.tv_name.setText(data.get(position).name);
        viewHolder.tv_addr.setText(data.get(position).address);

        return convertView;
    }

    class ViewHolder{
        TextView tv_name;
        TextView tv_addr;
    }
}
