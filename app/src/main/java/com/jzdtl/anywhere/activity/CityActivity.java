package com.jzdtl.anywhere.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.adapter.CityActivityAdapter;
import com.jzdtl.anywhere.bean.CityActivityResult;
import com.jzdtl.anywhere.callback.ApiService;
import com.jzdtl.anywhere.constants.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    private List<CityActivityResult.DataBean.UserActivitiesBean>info=new ArrayList<>();
    private ArrayList<String>path=new ArrayList<>();
    private List<String> list3;
    private List<String> list2;
    private List<String> list1;
    private CityActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        id=getIntent().getStringExtra("id");
        initSpanner();
        setSpanner();
        download(id,"", "", "");
        initRec();
    }

    private void initRec() {
        adapter = new CityActivityAdapter(this,info,path,this);
        recCity.setLayoutManager(new LinearLayoutManager(this));
        recCity.setAdapter(adapter);
        swipCity.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                download(id,"","","");
            }
        });
    }

    private void setSpanner() {
        swipCity.isRefreshing();
        ArrayAdapter<String>cityAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list1);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCity1.setAdapter(cityAdapter);
        spCity2.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list2));
        spCity3.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list3));
        spCity1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Log.e("item", "onItemSelected: "+i );
                month="month_"+i;
                download(id,month,"","");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spCity2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                download(id,"","",i+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
         spCity3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 1:
                        download(id, "", "made_desc", "");
                        break;
                    case 2:
                        download(id, "", "created_desc", "");
                        break;
                    case 3:
                        download(id, "", "like_desc", "");
                        break;
                    default:
                        download(id, "", "", "");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
        list3.add("默认排序");
        list3.add("最近出行");
        list3.add("最新发布");
        list3.add("最多点赞");

    }

    private void download(String id, String month, String sort, String cate_id) {
        swipCity.isRefreshing();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.YUNYOU_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getCityActivityResult(id,month,sort,cate_id,"1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CityActivityResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("msg", "onError: "+e );
                    }

                    @Override
                    public void onNext(CityActivityResult cityActivityResult) {
                        Log.e("msg", "onNex t: "+cityActivityResult.getData().getUser_activities());
                        Log.e("msg", "onNe xt: "+cityActivityResult.toString());
                        info.addAll(cityActivityResult.getData().getUser_activities());
                        for (int i = 0; i <  cityActivityResult.getData().getUser_activities().size(); i++) {
                            for (int j = 0; j <cityActivityResult.getData().getUser_activities().get(i).getContents().size(); j++) {
                                path.add( cityActivityResult.getData().getUser_activities().get(i).getContents().get(j).getPhoto_url());
                            }
                        }
                        adapter.notifyDataSetChanged();
                        swipCity.setRefreshing(false);
                    }
                });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_city;
    }
}
