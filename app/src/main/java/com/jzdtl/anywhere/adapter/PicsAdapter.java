package com.jzdtl.anywhere.adapter;


import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.fragment.PicDiaLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cz on 2016/12/29.
 */

public class PicsAdapter extends RecyclerView.Adapter<PicsAdapter.MyPicsViewHolder> {

    private List<String> list;
    private Context context;
    private Activity activity;

    public PicsAdapter(List<String> list, Context context,Activity a) {
        this.list = list;
        this.context = context;
        this.activity=a;
    }

    @Override
    public MyPicsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_activitiew_pics, parent, false );

        return new MyPicsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyPicsViewHolder holder, final int position) {
            Glide.with(context).load(list.get(position)).into(holder.imgActitiesPics);
            holder.imgActitiesPics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment d=new PicDiaLog();
                    Bundle b=new Bundle();
                    b.putString("url",list.get(position));
                    d.setArguments(b);
                    d.show(activity.getFragmentManager(),"pics");
                }
            });
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    class MyPicsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_actities_pics)
        ImageView imgActitiesPics;
        public MyPicsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
