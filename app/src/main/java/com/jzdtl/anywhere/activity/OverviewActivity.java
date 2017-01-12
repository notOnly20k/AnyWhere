package com.jzdtl.anywhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.adapter.OverviewExpandListAdapter;
import com.jzdtl.anywhere.bean.OverviewBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OverviewActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_image)
    ImageView toolbarImage;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.elv_overview)
    ExpandableListView elv_overview;
//    @BindView(R.id.map_header)
    MapView mapHeader;
    private String url_overview;
    private List<OverviewBean.DataBean.DestinationsBean> data;
    private OverviewExpandListAdapter mAdapter;
    private BaiduMap baiduMap;

    @Override
    public void onResume() {
        super.onResume();
        mapHeader.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapHeader.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapHeader.onPause();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null){
            url_overview = intent.getStringExtra("url");
        }

        ButterKnife.bind(this);
        toolbarInit();
        getDataFromNet(url_overview);
//        baiduMap = mapHeader.getMap();
        data = new ArrayList<>();
        mAdapter = new OverviewExpandListAdapter(OverviewActivity.this,data);
        elv_overview.setAdapter(mAdapter);


        View view = LayoutInflater.from(this).inflate(R.layout.item_header_expandlist,null);
        mapHeader = (MapView)view.findViewById(R.id.map_header);
        baiduMap = mapHeader.getMap();
        mapHeader.showZoomControls(false);
        mapHeader.showScaleControl(false);

        //view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,500));
        view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,500));
        elv_overview.addHeaderView(view);
        elv_overview.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0; i < mAdapter.getGroupCount(); i++) {
                    if (groupPosition != i)
                        elv_overview.collapseGroup(i);
                    else
                        elv_overview.setSelectedGroup(i);
                }
            }
        });


    }

    @OnClick ({R.id.toolbar_image})
    public void onClick(View view){
        if (view.getId() == R.id.toolbar_image){
            finish();
        }
    }

    private void getDataFromNet(String url_overview) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url_overview).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(OverviewActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                final OverviewBean bean = gson.fromJson(response.body().string(), OverviewBean.class);
                data.addAll(bean.getData().getDestinations());
                final OverviewBean.DataBean.DestinationBean destination = bean.getData().getDestination();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();

                        MapStatusUpdate u = MapStatusUpdateFactory
                                .newLatLng(new LatLng(destination.getLat(),destination.getLng()));
//                        baiduMap.animateMapStatus(u);
                        baiduMap.setMapStatus(u);
                        //定义Maker坐标点
//                        LatLng point = new LatLng(destination.getLat(), destination.getLng());
//                        BitmapDescriptor bitmap = BitmapDescriptorFactory
//                                .fromResource(R.mipmap.icon_gcoding);
//                        OverlayOptions options = new MarkerOptions()
//                                .position(point).icon(bitmap);
//                        baiduMap.addOverlay(options);



//                        baiduMap.clear();
//                        PoiOverlay poiOverlay = new PoiOverlay(baiduMap);
//                        poiOverlay.
//                        poiOverlay.setData(poiResult);// 设置POI数据
//                        baiduMap.setOnMarkerClickListener(poiOverlay);
//                        poiOverlay.addToMap();// 将所有的overlay添加到地图上
//                        poiOverlay.zoomToSpan();
                        toolbarTitle.setText(destination.getName()+"目的地");

                    }
                });

            }
        });

    }


    private void toolbarInit() {
        toolbarImage.setImageResource(R.mipmap.back_icon);
        toolbarSubtitle.setText("");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_overview;
    }



}
