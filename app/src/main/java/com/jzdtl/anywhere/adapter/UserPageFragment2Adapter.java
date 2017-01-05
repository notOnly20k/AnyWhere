package com.jzdtl.anywhere.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jzdtl.anywhere.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.baidu.location.d.a.i;

/**
 * Created by cz on 2017/1/4.
 */

public class UserPageFragment2Adapter extends RecyclerView.Adapter<UserPageFragment2Adapter.MyUserPageFragment2ViewHolder> {

    private List<String> list;
    private Context context;
    private Activity activity;
    private List<String>pics;

    public UserPageFragment2Adapter(List<String> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public MyUserPageFragment2ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_activitiew_pics, parent, false);
        return new MyUserPageFragment2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyUserPageFragment2ViewHolder holder, final int position) {
        LinearLayoutCompat.LayoutParams params= (LinearLayoutCompat.LayoutParams) holder.imgActitiesPics.getLayoutParams();
        params.height=300;
        params.width=300;
       holder.imgActitiesPics.setLayoutParams(params);
        Glide.with(context).load(list.get(i)).into(holder.imgActitiesPics);
        pics= new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            pics.add(list.get(i));
        }
        holder.imgActitiesPics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                PhotoPreview.builder()
//                        .setPhotos(list)
//                        .setCurrentItem(position)
//                        .setShowDeleteButton(false)
//                        .start(activity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    class MyUserPageFragment2ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_actities_pics)
        ImageView imgActitiesPics;
        public MyUserPageFragment2ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
