package com.jzdtl.anywhere.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.activity.CommentActivity;
import com.jzdtl.anywhere.activity.UserPageActivity;
import com.jzdtl.anywhere.bean.CityActivityResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by cz on 2017/1/7.
 */

public class CityActivityAdapter extends RecyclerView.Adapter<CityActivityAdapter.MyCityViewHolder>{
    private Context context;
    private List<CityActivityResult.DataBean.UserActivitiesBean> list;
    private ArrayList<String> url;
    private Activity ac;

    public CityActivityAdapter(Context context, List<CityActivityResult.DataBean.UserActivitiesBean> list, ArrayList<String> url, Activity ac) {
        this.context = context;
        this.list = list;
        this.url = url;
        this.ac = ac;
    }

    @Override
    public MyCityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_activities, parent, false);
        return new MyCityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyCityViewHolder holder, final int position) {
        final CityActivityResult.DataBean.UserActivitiesBean activityBean=list.get(position);
        holder.tvActivitiesUsername.setText(activityBean.getUser().getName());
        Glide.with(context).load(activityBean.getUser().getPhoto_url()).into(holder.imgActivitiesHeadpic);
        holder.imgActivitiesHeadpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(context, UserPageActivity.class);
                intent.putExtra("user_name",activityBean.getUser().getName());
                intent.putExtra("user_headpic",activityBean.getUser().getPhoto_url());
                intent.putExtra("user_id",activityBean.getUser().getId());
                context.startActivity(intent);
            }
        });
        holder.tvActivitiesInfo.setText(activityBean.getDescription());
        holder.tvActitiesTitle.setText(activityBean.getTopic());
        holder.tvActitiesLike.setText(activityBean.getLikes_count()+"");
        holder.tvActitiesComment.setText(activityBean.getComments_count()+"");
        holder.tvActitiesCollect.setText(activityBean.getFavorites_count()+"");

        url=new ArrayList<>();
        for (int i = 0; i < activityBean.getContents().size(); i++) {
            url.add(activityBean.getContents().get(i).getPhoto_url());
            if (i==0) {
                final String path=activityBean.getContents().get(i).getPhoto_url();
                Glide.with(context).load(path).into(holder.imgActitiesFristpic);
                holder.imgActitiesFristpic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PhotoPreview.builder()
                                .setPhotos(url)
                                .setCurrentItem(0)
                                .setShowDeleteButton(false)
                                .start(ac);
                    }
                });
            }
        }
        if (url!=null){
            PicsAdapter adapter=new PicsAdapter(url,context,ac);
            holder.recActitiesPics.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            holder.recActitiesPics.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else {
            holder.recActitiesPics.setVisibility(View.GONE);
        }
        holder.imgActitiesComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(context, CommentActivity.class);
                intent.putExtra("id",list.get(position).getId());
                context.startActivity(intent);
            }
        });
        holder.imgActitiesLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(String.valueOf(holder.imgActitiesLike.getTag()))==1){
                    holder.imgActitiesLike.setImageResource(R.mipmap.icon_like_normal);
                    holder.tvActitiesLike.setText((activityBean.getLikes_count()) + "");
                    holder.imgActitiesLike.setTag(0);
                }else {
                    holder.imgActitiesLike.setImageResource(R.mipmap.icon_like_highlight);
                    holder.tvActitiesLike.setText((activityBean.getLikes_count() + 1) + "");
                    holder.imgActitiesLike.setTag(1);
                }
            }
        });
        holder.imgActitiesCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(String.valueOf(holder.imgActitiesCollect.getTag()))==1){
                    holder.imgActitiesCollect.setImageResource(R.mipmap.icon_fav_normal);
                    holder.tvActitiesCollect.setText((activityBean.getFavorites_count()) + "");
                    holder.imgActitiesCollect.setTag(0);
                }else {
                    holder.imgActitiesCollect.setImageResource(R.mipmap.icon_fav_highlight);
                    holder.tvActitiesCollect.setText((activityBean.getFavorites_count() + 1) + "");
                    holder.imgActitiesCollect.setTag(1);
                }
            }
        });
        holder.imgActitiesShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //showShare();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyCityViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_activities_headpic)
        CircleImageView imgActivitiesHeadpic;
        @BindView(R.id.tv_activities_username)
        TextView tvActivitiesUsername;
        @BindView(R.id.tv_actities_fllow)
        TextView tvActitiesFllow;
        @BindView(R.id.img_actities_fristpic)
        ImageView imgActitiesFristpic;
        @BindView(R.id.rec_actities_pics)
        RecyclerView recActitiesPics;
        @BindView(R.id.tv_actities_title)
        TextView tvActitiesTitle;
        @BindView(R.id.tv_activities_info)
        TextView tvActivitiesInfo;
        @BindView(R.id.tv_actities_more)
        TextView tvActitiesMore;
        @BindView(R.id.img_actities_like)
        ImageView imgActitiesLike;
        @BindView(R.id.tv_actities_like)
        TextView tvActitiesLike;
        @BindView(R.id.img_actities_comment)
        ImageView imgActitiesComment;
        @BindView(R.id.img_actities_share)
        ImageView imgActitiesShare;
        @BindView(R.id.tv_actities_comment)
        TextView tvActitiesComment;
        @BindView(R.id.img_actities_collect)
        ImageView imgActitiesCollect;
        @BindView(R.id.tv_actities_collect)
        TextView tvActitiesCollect;
        public MyCityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
