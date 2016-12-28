package com.jzdtl.anywhere.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.jzdtl.anywhere.R;

public class MapTest extends AppCompatActivity implements BDLocationListener{
    private MapView mapView;
    private LocationClient locationClient;
    private BaiduMap baiduMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_test);

        mapView = (MapView) findViewById(R.id.map_view);
        baiduMap = mapView.getMap();

        //1.初始化定位对象
        locationClient = new LocationClient(getApplicationContext());
        //2.设置定位初始化信息
        initLocation();
        //3.绑定定位的结果监听器
        locationClient.registerLocationListener(this);
        //4.开启定位
        locationClient.start();
    }

    //初始化定位参数
    private  void initLocation(){
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度(Hight_Accuracy)，低功耗(Battery_Saving)，仅设备(Device_Sensors)
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系

        int span=2000;
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
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    //定位结束之后调用的方法
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        int typeCode = bdLocation.getLocType();
        if (typeCode == 61 || typeCode == 161) {

            String p = bdLocation.getProvince();
            String c = bdLocation.getCity();
            String s = bdLocation.getStreet();
            String address = bdLocation.getAddrStr();
            Log.i("info", "====定位成功=======" + p + c + s + address);

            //获取当前定位的经纬度
            double lat = bdLocation.getLatitude();
            double lng = bdLocation.getLongitude();
            LatLng point = new LatLng(lat,lng);
            BitmapDescriptor descriptor =
                    BitmapDescriptorFactory.fromResource(R.mipmap.location);

            OverlayOptions options = new MarkerOptions()
                    .position(point)
                    .icon(descriptor)
                    .title(address);
            baiduMap.addOverlay(options);
            baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(point));

        }else{
            Toast.makeText(this,"定位失败",Toast.LENGTH_LONG).show();
        }
    }
}
