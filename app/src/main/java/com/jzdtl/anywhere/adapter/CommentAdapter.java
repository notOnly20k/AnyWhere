package com.jzdtl.anywhere.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.activity.UserPageActivity;
import com.jzdtl.anywhere.bean.CommentsResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cz on 2017/1/6.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyCommentViewHolder> {

    private List<CommentsResult.DataBean> list;
    private Context context;

    public CommentAdapter(List<CommentsResult.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new MyCommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyCommentViewHolder holder, int position) {
        Log.e("comment", "onAdapter: "+list.toString() );
        final CommentsResult.DataBean dataBean=list.get(position);
        holder.tvCommentName.setText(dataBean.getUser().getName());
        holder.tvCommentInfo.setText(dataBean.getComment());
        Glide.with(context).load(dataBean.getUser().getPhoto_url()).into(holder.imgCommentHeadpic);
        holder.imgCommentHeadpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(context,UserPageActivity.class);
                intent.putExtra("user_id",dataBean.getUser().getId());
                intent.putExtra("user_name",dataBean.getUser().getName());
                intent.putExtra("user_headpic",dataBean.getUser().getPhoto_url());
                context.startActivity(intent);
            }
        });
        String time=dataBean.getCreated_at();
        String s=time.substring(0,time.indexOf("T"));
        holder.tvCommentTime.setText(s);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyCommentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_comment_headpic)
        CircleImageView imgCommentHeadpic;
        @BindView(R.id.tv_comment_name)
        TextView tvCommentName;
        @BindView(R.id.tv_comment_info)
        TextView tvCommentInfo;
        @BindView(R.id.tv_comment_time)
        TextView tvCommentTime;
        public MyCommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
