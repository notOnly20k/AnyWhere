package com.jzdtl.anywhere.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.bean.UserActivitiesResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by cz on 2017/1/3.
 */


public class UserPageAdapter extends RecyclerView.Adapter<UserPageAdapter.MyUserPageViewHolder> {

    private Context context;
    private List<UserActivitiesResult.DataBean> list;
    private ArrayList<String> url;
    private FragmentManager manager;
    private Activity ac;

    public UserPageAdapter(Context context, List<UserActivitiesResult.DataBean> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.ac = activity;
    }

    @Override
    public MyUserPageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_activities, parent, false);
        return new MyUserPageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyUserPageViewHolder holder, int position) {
        UserActivitiesResult.DataBean dataBean=list.get(position);
        holder.layout.setVisibility(View.GONE);
        holder.tvActivitiesInfo.setText(dataBean.getDescription());
        holder.tvActitiesTitle.setText(dataBean.getTopic());
        holder.tvActitiesLike.setText(dataBean.getLikes_count()+"");
        holder.tvActitiesComment.setText(dataBean.getComments_count()+"");
        holder.tvActitiesCollect.setText(dataBean.getFavorites_count()+"");
        url=new ArrayList<>();
        for (int i = 0; i < dataBean.getContents().size(); i++) {
            url.add(dataBean.getContents().get(i).getPhoto_url());
            if (i==0) {
                final String path=dataBean.getContents().get(i).getPhoto_url();
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyUserPageViewHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.tv_actities_comment)
        TextView tvActitiesComment;
        @BindView(R.id.img_actities_collect)
        ImageView imgActitiesCollect;
        @BindView(R.id.tv_actities_collect)
        TextView tvActitiesCollect;
        @BindView(R.id.layout_actities_head)
        RelativeLayout layout;
        public MyUserPageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
