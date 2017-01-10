package com.jzdtl.anywhere.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jzdtl.anywhere.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPicker;

/**
 * Created by cz on 2017/1/5.
 */

public class WriteAdapter extends RecyclerView.Adapter<WriteAdapter.MyWriterViewHolder> {

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
        View view = LayoutInflater.from(context).inflate(R.layout.item_activitiew_pics, parent, false);
        return new MyWriterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyWriterViewHolder holder, int position) {

        holder.imgActitiesPics.setLayoutParams(new LinearLayout.LayoutParams(300,300));
        if ((position==0&&list.size()==0)||position==(list.size())) {
            holder.imgActitiesPics.setScaleType(ImageView.ScaleType.CENTER);
            holder.imgActitiesPics.setBackgroundResource(R.color.gray_bfbfbf);
            holder.imgActitiesPics.setImageResource(R.mipmap.add);
            holder.imgActitiesPics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PhotoPicker.builder()
                            .setPhotoCount(9)
                            .setShowCamera(true)
                            .setPreviewEnabled(false)
                            .start(activity);
                }
            });
        }else if (list.size()!=0){

            holder.imgActitiesPics.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context).load(list.get(position)).into(holder.imgActitiesPics);
        }
    }

    @Override
    public int getItemCount() {
        return (list.size() + 1);
    }

    class MyWriterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_actities_pics)
        ImageView imgActitiesPics;
        public MyWriterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
