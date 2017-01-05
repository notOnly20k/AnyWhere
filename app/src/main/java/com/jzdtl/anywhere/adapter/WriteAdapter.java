package com.jzdtl.anywhere.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzdtl.anywhere.R;

import java.util.ArrayList;

/**
 * Created by cz on 2017/1/5.
 */

public class WriteAdapter extends RecyclerView.Adapter<WriteAdapter.MyWriterViewHolder>{
    private ArrayList<String> list;
    private Context context;
    private Activity activity;

    public WriteAdapter(ArrayList<String> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public MyWriterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.__picker_item_photo,parent,false);
        return new MyWriterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyWriterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyWriterViewHolder extends RecyclerView.ViewHolder{

        public MyWriterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
