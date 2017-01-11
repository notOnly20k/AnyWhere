package com.jzdtl.anywhere.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.bean.IndexResult;
import com.jzdtl.anywhere.callback.OnIndexItemButtonClickListener;
import com.jzdtl.anywhere.callback.OnIndexItemClickListener;
import com.jzdtl.anywhere.views.MyGridView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by gc y on 2016/12/30.
 */
public class IndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_LIST = 1;
    private Context context;
    private List<IndexResult.DataBean> dataBeen = new ArrayList<>();
    private List<String> headUrl = new ArrayList<>();
    private OnIndexItemClickListener mOnIndexItemClickListener;
    private OnIndexItemButtonClickListener mOnIndexItemButtonClickListener;
    public void setOnIndexItemClickListener(OnIndexItemClickListener mOnIndexItemClickListener) {
        this.mOnIndexItemClickListener = mOnIndexItemClickListener;
    }

    public void setmOnIndexItemButtonClickListener(OnIndexItemButtonClickListener mOnIndexItemButtonClickListener) {
        this.mOnIndexItemButtonClickListener = mOnIndexItemButtonClickListener;
    }

    public IndexAdapter(Context context) {
        this.context = context;
    }

    public void setDataBeen(List<IndexResult.DataBean> dataBeen) {
        this.dataBeen = dataBeen;
        notifyDataSetChanged();
    }
    public void setBanner(List<String> headUrl){
        this.headUrl = headUrl;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LIST){
            View view = LayoutInflater.from(context).inflate(R.layout.item_index_content, parent, false);
            return new IndexViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_index_banner, parent, false);
            return new HeadViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int realPosition = holder.getLayoutPosition()-1;
        if (getItemViewType(position) == TYPE_LIST){
            IndexViewHolder indexHolder = (IndexViewHolder) holder;
            indexHolder.textItemTitle.setText(dataBeen.get(realPosition).getName());
            IndexGrideAdapter indexGrideAdapter = new IndexGrideAdapter(context);
            indexGrideAdapter.setData(dataBeen.get(realPosition).getDestinations());
            indexHolder.gridItemDestination.setAdapter(indexGrideAdapter);
            String button_text = dataBeen.get(realPosition).getButton_text();
            if (button_text == null){
                indexHolder.layouItemMore.setVisibility(View.GONE);
            }else {
                indexHolder.layouItemMore.setVisibility(View.VISIBLE);
                indexHolder.textItemMore.setText(button_text);
                indexHolder.layouItemMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String resion = dataBeen.get(realPosition).getRegion();
                        String name = dataBeen.get(realPosition).getName();
                        mOnIndexItemButtonClickListener.onIndexItemClickListener(view,name,resion);
                    }
                });
            }
            indexHolder.gridItemDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String path = dataBeen.get(realPosition).getDestinations().get(i).getPath();
                    Log.d(TAG, "onItemClick: "+path);
                    String realPath = getDestinationsPath(path);
                    mOnIndexItemClickListener.onIndexItemClick(realPath);
                }
            });
        } else {
            HeadViewHolder headHolder =   (HeadViewHolder) holder;
            //设置广告条
            headHolder.layoutItemBanner
                    .setImageLoader(new GlideImageLoader())
                    .setImages(headUrl)
                    .start();
        }
    }

    public String getDestinationsPath(String path) {
        //.1.5.166.111.
        String[] strings = path.split("\\.");
        String realPath = strings[strings.length-1];
        return realPath;
    }

    @Override
    public int getItemCount() {
        return dataBeen.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return position==0?TYPE_HEAD:TYPE_LIST;
    }

    class IndexViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_item_title)
        TextView textItemTitle;
        @BindView(R.id.grid_item_destination)
        MyGridView gridItemDestination;
        @BindView(R.id.text_item_more)
        TextView textItemMore;
        @BindView(R.id.layout_item_more)
        LinearLayout layouItemMore;
        public IndexViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    class HeadViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_item_banner)
        Banner layoutItemBanner;
        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);
        }
    }
}
