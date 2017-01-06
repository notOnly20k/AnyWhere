package com.jzdtl.anywhere.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.activity.ShakeActivity;
import com.jzdtl.anywhere.adapter.PoiResultAdapter;
import com.jzdtl.anywhere.overlayutil.PoiOverlay;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyFragment extends Fragment implements BDLocationListener, View.OnClickListener{


    private MapView map_nearby;
    private BaiduMap baiduMap;
    private LocationClient locationClient;
    private FloatingActionButton float_action_button;

    private boolean isLocation = false;
    private LatLng position;

    private Context mContext;

    public NearbyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
        map_nearby.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        map_nearby.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        map_nearby.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getActivity().getApplicationContext();
        SDKInitializer.initialize(mContext);
        View view = inflater.inflate(R.layout.fragment_nearby, container, false);
        map_nearby = ((MapView) view.findViewById(R.id.map_nearby));
        float_action_button = ((FloatingActionButton) view.findViewById(R.id.float_action_button));

        float_action_button.setOnClickListener(this);
        baiduMap = map_nearby.getMap();
        //1.初始化定位对象
        locationClient = new LocationClient(getActivity().getApplicationContext());
        //2.设置定位初始化信息
        initLocation();
        //3.绑定定位的结果监听器
        locationClient.registerLocationListener(this);
        //4.开启定位
        locationClient.start();
        return view;
    }

    //初始化定位参数
    private  void initLocation(){
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度(Hight_Accuracy)，低功耗(Battery_Saving)，仅设备(Device_Sensors)
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系

        int span=0;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        //定位的设置信息，要设置到定位对象locationClient中
        locationClient.setLocOption(option);

    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        int typeCode = bdLocation.getLocType();
        if (typeCode == 61 || typeCode == 161) {

            isLocation = true;
            String p = bdLocation.getProvince();
            String c = bdLocation.getCity();
            String s = bdLocation.getStreet();
            String address = bdLocation.getAddrStr();
            Log.i("info", "====定位成功=======" + p + c + s + address);

            //获取当前定位的经纬度
            double lat = bdLocation.getLatitude();
            double lng = bdLocation.getLongitude();
            position = new LatLng(lat,lng);
            BitmapDescriptor descriptor =
                    BitmapDescriptorFactory.fromResource(R.mipmap.location);

            OverlayOptions options = new MarkerOptions()
                    .position(position)
                    .icon(descriptor)
                    .title(address);
            baiduMap.addOverlay(options);
            baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(position));

        }else{
            isLocation = false;
            Toast.makeText(getContext(),"定位失败",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.float_action_button) {

            Bundle bundle = new Bundle();
            bundle.putBoolean("isLocation",isLocation);
            bundle.putParcelable("position",position);
            Intent intent = new Intent(getActivity(),ShakeActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);


        }

    }


//    public abstract class EndLessOnScrollListener extends RecyclerView.OnScrollListener{
//        //声明一个LinearLayoutManager
//        private LinearLayoutManager mLinearLayoutManager;
//
//        //当前页，从0开始    private int currentPage = 0;
//        //已经加载出来的Item的数量
//        private int totalItemCount;
//
//        //主要用来存储上一个totalItemCount
//        private int previousTotal = 0;
//
//        //在屏幕上可见的item数量
//        private int visibleItemCount;
//
//        //在屏幕可见的Item中的第一个
//        private int firstVisibleItem;
//
//        //是否正在上拉数据
//        private boolean loading = true;
//
//        public EndLessOnScrollListener(LinearLayoutManager linearLayoutManager) {
//            this.mLinearLayoutManager = linearLayoutManager;
//        }
//
//        @Override
//        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            super.onScrolled(recyclerView, dx, dy);
//            visibleItemCount = recyclerView.getChildCount();
//            totalItemCount = mLinearLayoutManager.getItemCount();
//            firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
//            if(loading){
//                //Log.d("wnwn","firstVisibleItem: " +firstVisibleItem);
//                //Log.d("wnwn","totalPageCount:" +totalItemCount);
//                //Log.d("wnwn", "visibleItemCount:" + visibleItemCount)；
//
//                if(totalItemCount > previousTotal){
//                    //说明数据已经加载结束
//                    loading = false;
//                    previousTotal = totalItemCount;
//                }
//            }
//            //这里需要好好理解
//            if (!loading && totalItemCount-visibleItemCount <= firstVisibleItem){
//                onLoadMore(1);
//                loading = true;
//            }
//        }
//        public abstract void onLoadMore(int currentPage);
//    }
}
