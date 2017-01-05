package com.jzdtl.anywhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.adapter.DestListAdapter;
import com.jzdtl.anywhere.bean.RegionResult;
import com.jzdtl.anywhere.callback.ApiService;
import com.jzdtl.anywhere.callback.OnDestListItemClickListener;
import com.jzdtl.anywhere.constants.Constant;
import com.jzdtl.anywhere.utils.ActivityManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DestinationListActivity extends BaseActivity implements OnDestListItemClickListener {

    @BindView(R.id.toolbar_image)
    ImageView toolbarImage;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_list_all)
    RecyclerView recyclerListAll;
    @BindView(R.id.activity_destinationlist)
    LinearLayout activityDestinationlist;
    List<RegionResult.DataBean> regionData;
    private Retrofit retrofit;
    private ApiService apiService;
    private DestListAdapter destListAdapter;
    private String resion = null;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initData();
        requestData(resion);
    }

    private void requestData(String resion) {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.YUNYOU_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        apiService
                .getResionResult(resion)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegionResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RegionResult regionResult) {
                        regionData.addAll(regionResult.getData());
                        destListAdapter.setData(regionData);
                    }
                });
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        resion = bundle.getString("resion");
        name = bundle.getString("name");
        regionData = new ArrayList<>();
        toolbarImage.setImageResource(R.mipmap.back_icon);
        toolbarTitle.setText(name);
        toolbarSubtitle.setVisibility(View.GONE);
        recyclerListAll.setLayoutManager(new LinearLayoutManager(this));
        destListAdapter = new DestListAdapter(this);
        recyclerListAll.setAdapter(destListAdapter);
        destListAdapter.setOnDestListItemClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_destinationlist;
    }

    @OnClick(R.id.toolbar_image)
    public void onClick() {
        ActivityManager.finishActivity(this);
    }

    @Override
    public void onDestListItemClickListener(String city) {
        ActivityManager.startActivity(this,new Intent(this,DestinationActivity.class).putExtra("city",city));
    }
}
