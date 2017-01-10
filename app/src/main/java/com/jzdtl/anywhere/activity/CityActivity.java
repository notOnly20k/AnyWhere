package com.jzdtl.anywhere.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.bean.CityActivityResult;
import com.jzdtl.anywhere.callback.ApiService;
import com.jzdtl.anywhere.constants.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CityActivity extends BaseActivity {

    @BindView(R.id.toolbar_image)
    ImageView toolbarImage;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sp_city_1)
    Spinner spCity1;
    @BindView(R.id.sp_city_2)
    Spinner spCity2;
    @BindView(R.id.sp_city_3)
    Spinner spCity3;
    @BindView(R.id.rec_city)
    RecyclerView recCity;
    @BindView(R.id.swip_city)
    SwipeRefreshLayout swipCity;
    @BindView(R.id.activity_city)
    LinearLayout activityCity;
    private String id;
    private String sort;
    private String month;
    private List<String> list3;
    private List<String> list2;
    private List<String> list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSpanner();
        setSpanner();
        download(id, month, sort);
    }

    private void setSpanner() {
        // spCity1.setAdapter(new ArrayAdapter<String>(this,R.layout.it));
    }

    private void initSpanner() {
        list1 = new ArrayList<>();
        list1.add("全部");
        for (int i = 0; i < 11; i++) {
            list1.add((i+1)+"月");
        }
        list2 = new ArrayList<>();
        list2.add("全部主题");
        list2.add("美食");
        list2.add("酒店");
        list2.add("购物");
        list3 = new ArrayList<>();
        list3.add("默认  排序");
        list3.add("最近出行");
        list3.add("最新发布");
        list3.add("最多点赞");

    }

    private void download(String id, String month, String sort) {
        swipCity.isRefreshing();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.YUNYOU_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getCityActivityResult(id,month,sort,id,"1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CityActivityResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CityActivityResult cityActivityResult) {

                    }
                });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_city;
    }
}
