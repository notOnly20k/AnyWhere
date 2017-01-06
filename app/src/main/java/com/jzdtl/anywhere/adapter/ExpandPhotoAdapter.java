package com.jzdtl.anywhere.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.callback.OnStrategyPhotoClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gcy on 2017/1/6.
 */
public class ExpandPhotoAdapter extends RecyclerView.Adapter<ExpandPhotoAdapter.PhotoViewHolder> {
    private ArrayList<String> data = new ArrayList<>();
    private Context context;
    private OnStrategyPhotoClickListener mOnStrategyPhotoClickListener;
    public ExpandPhotoAdapter(Context context) {
        this.context = context;
    }

    public void setmOnStrategyPhotoClickListener(OnStrategyPhotoClickListener mOnStrategyPhotoClickListener) {
        this.mOnStrategyPhotoClickListener = mOnStrategyPhotoClickListener;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_strategy_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, int position) {
        Glide.with(context).load(data.get(position)).into(holder.imageStrategyPhoto);
        holder.imageStrategyPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getLayoutPosition();
                mOnStrategyPhotoClickListener.onStrategyPhotoClickListener(pos,data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_strategy_photo)
        ImageView imageStrategyPhoto;
        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
