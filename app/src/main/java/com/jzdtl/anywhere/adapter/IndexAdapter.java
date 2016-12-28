package com.jzdtl.anywhere.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzdtl.anywhere.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cz on 2016/12/28.
 */

public class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.MyIndexAdapter> {

    private Context context;
    private List<String> list;

    public IndexAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyIndexAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_index, parent, false);
        return new MyIndexAdapter(view);
    }

    @Override
    public void onBindViewHolder(MyIndexAdapter holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class MyIndexAdapter extends RecyclerView.ViewHolder {
        @BindView(R.id.img_index_headpic)
        CircleImageView imgIndexHeadpic;
        @BindView(R.id.tv_index_username)
        TextView tvIndexUsername;
        @BindView(R.id.img_index_fristpic)
        ImageView imgIndexFristpic;
        @BindView(R.id.grid_index_pics)
        GridView gridIndexPics;
        @BindView(R.id.img_index_like)
        ImageView imgIndexLike;
        @BindView(R.id.tv_index_like)
        TextView tvIndexLike;
        @BindView(R.id.img_index_comment)
        ImageView imgIndexComment;
        @BindView(R.id.tv_index_comment)
        TextView tvIndexComment;
        @BindView(R.id.img_index_collect)
        ImageView imgIndexCollect;
        @BindView(R.id.tv_index_collect)
        TextView tvIndexCollect;

        public MyIndexAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
