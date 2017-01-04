package com.jzdtl.anywhere.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.activity.DestinationActivity;
import com.jzdtl.anywhere.activity.DestinationListActivity;
import com.jzdtl.anywhere.adapter.IndexAdapter;
import com.jzdtl.anywhere.bean.BannerResult;
import com.jzdtl.anywhere.bean.IndexResult;
import com.jzdtl.anywhere.callback.OnIndexItemButtonClickListener;
import com.jzdtl.anywhere.callback.OnIndexItemClickListener;
import com.jzdtl.anywhere.utils.ActivityManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class IndexFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,OnIndexItemClickListener,OnIndexItemButtonClickListener {

    @BindView(R.id.recycler_index_content)
    RecyclerView recyclerIndexContent;
    @BindView(R.id.swipe_index_refresh)
    SwipeRefreshLayout swipeIndexRefresh;
    private IndexAdapter indexAdapter;
    private boolean isBannerOk = false;
    private boolean isContentOk = false;
    public IndexFragment() {
        // Required empty public constructor
    }

    private static final String TAG = "IndexFragment";
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_index, container, false);
        ButterKnife.bind(this, view);
        recyclerIndexContent.setLayoutManager(new LinearLayoutManager(mActivity));
        indexAdapter = new IndexAdapter(mActivity);
        recyclerIndexContent.setAdapter(indexAdapter);
        indexAdapter.setOnIndexItemClickListener(this);
        indexAdapter.setmOnIndexItemButtonClickListener(this);
        setSwipeRefresh();
        requestData();
        return view;
    }

    private void setSwipeRefresh() {
        swipeIndexRefresh.setColorSchemeColors(Color.BLUE,Color.CYAN,Color.YELLOW,Color.GRAY,Color.RED);
        swipeIndexRefresh.setOnRefreshListener(this);
        swipeIndexRefresh.setRefreshing(true);
    }

    private void requestData() {
        apiService
                .getIndexResult()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<IndexResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(IndexResult indexResult) {
                        indexAdapter.setDataBeen(indexResult.getData());
                        isContentOk = true;
                        if (isBannerOk && isContentOk)
                            swipeIndexRefresh.setRefreshing(false);
                    }
                });
        apiService
                .getBannerResult("general", "false")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BannerResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BannerResult bannerResult) {
                        List<String> imgUrlList = new ArrayList<String>();
                        Log.i(TAG, "onNext: " + bannerResult.toString());
                        Log.i(TAG, "onNext: " + bannerResult.getData().size());
                        for (int i = 0; i < bannerResult.getData().size(); i++) {
                            String imgUrl = bannerResult.getData().get(i).getPhoto().getPhoto_url();
                            imgUrlList.add(imgUrl);
                        }
                        Log.i(TAG, "onNext: " + imgUrlList.toString());
                        indexAdapter.setBanner(imgUrlList);
                        isBannerOk = true;
                        if (isBannerOk && isContentOk)
                            swipeIndexRefresh.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onRefresh() {
        swipeIndexRefresh.setRefreshing(true);
        requestData();
        isContentOk = false;
        isBannerOk = false;
    }

    @Override
    public void onIndexItemClick(String path) {
        ActivityManager.startActivity(mActivity,new Intent(mActivity, DestinationActivity.class).putExtra("city",path));
    }

    @Override
    public void onIndexItemClickListener(View view, String resion) {
        ActivityManager.startActivity(mActivity,new Intent(mActivity, DestinationListActivity.class).putExtra("resion",resion));
    }
}
