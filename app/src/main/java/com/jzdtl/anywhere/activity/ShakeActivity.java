package com.jzdtl.anywhere.activity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
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
import com.jzdtl.anywhere.adapter.PoiResultAdapter;
import com.jzdtl.anywhere.overlayutil.PoiOverlay;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShakeActivity extends BaseActivity implements XRefreshView.XRefreshViewListener, OnGetPoiSearchResultListener {

    @BindView(R.id.toolbar_image)
    ImageView toolbarImage;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.img_up)
    ImageView imgUp;
    @BindView(R.id.img_down)
    ImageView imgDown;
    @BindView(R.id.lv_poi)
    ListView lv_poi;
    @BindView(R.id.xrfv)
    XRefreshView xrfv;

    Sensor sensor;
    SensorEventListener sensorEventListener;
    SensorManager sensorManager;
    Vibrator vibrator;
    SoundPool soundPool;
    int soundId=0;
    private PoiSearch mPoiSearch;
    private Bundle mBundle;
    private LatLng position;
    private boolean mIsLocation;
    private boolean shakeValid = true;
    private boolean isSleeping = false;
    private PoiResultAdapter mAdapter;
    private List<PoiInfo> data = new ArrayList<>();
    private int page = 0;
    private int totalPage;
    private boolean isClear = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent !=null){
            mBundle = intent.getExtras();
            mIsLocation = mBundle.getBoolean("isLocation",false);
            position =  mBundle.getParcelable("position");
        }

        ButterKnife.bind(this);

        toolbarImage.setImageResource(R.mipmap.back_icon);
        toolbarTitle.setText("摇一摇搜周边");
        toolbarSubtitle.setText("");
        xrfv.setAutoLoadMore(false);
        xrfv.setPullLoadEnable(true);
        xrfv.enableReleaseToLoadMore(true);
        xrfv.enablePullUpWhenLoadCompleted(true);
        xrfv.setXRefreshViewListener(this);


        mAdapter = new PoiResultAdapter(data,this);
        lv_poi.setAdapter(mAdapter);

        poiSearchInit();
        senserConfig();


    }

    private void poiSearchInit() {
        //创建POI检索实例
        mPoiSearch = PoiSearch.newInstance();
        //设置POI检索监听者
        mPoiSearch.setOnGetPoiSearchResultListener(this);
    }

    private void senserConfig() {
        /**
         * 参数1： 通过的最大流的数量
         * 参数2：流的类型
         * 参数3：无效，默认0
         */
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

        soundId =  soundPool.load(this, R.raw.shake,0);
        //获取震动器类的对象
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        //获取传感器的管理类
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //获取手机中的所有传感器
//        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
//        for (int i = 0; i < sensorList.size(); i++) {
//            Sensor s = sensorList.get(i);
//            Log.e("======", "====供应商===" + s.getVendor());
//            Log.e("======", "====传感器的名字===" + s.getName());
//            Log.e("======", "====功率===" + s.getPower());
//            Log.e("======", "====类型===" + s.getType());
//            Log.e("======", "====版本===" + s.getVersion());
//        }

        //仿微信摇一摇效果(加速度传感器X，Y,Z)

        //取出加速度传感器
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                //传感器发生变化的时候调用
                //取出传感器三个方向变化的值
                float[] values = event.values;
                float x = values[0];
                float y = values[1];
                float z = values[2];

                if (Math.abs(x) > 19 || Math.abs(y) > 19 || Math.abs(z) > 19) {
                    Log.e("=====", "===表示手机晃动===");
                    if (shakeValid) {
                        shakeValid = false;
                        //设置震动时间
                        vibrator.vibrate(1000);
                        /**
                         * 参数1:soundId，通过load()返回
                         * 参数2:左音量0-1
                         * 参数3:右音量0-1
                         * 参数4:优先级，最低为0
                         * 参数5:循环次数，－1无限循环，0不循环，
                         * 参数6:播放速率0.5-2,1表示正常速率
                         */
                        soundPool.play(soundId, 1, 1, 0, 0, 1);

                        TranslateAnimation animationUp = new TranslateAnimation(
                                Animation.RELATIVE_TO_SELF, 0,
                                Animation.RELATIVE_TO_SELF, 0,
                                Animation.RELATIVE_TO_SELF, -1f,
                                Animation.RELATIVE_TO_SELF, 0);
                        TranslateAnimation animationDown = new TranslateAnimation(
                                Animation.RELATIVE_TO_SELF, 0,
                                Animation.RELATIVE_TO_SELF, 0,
                                Animation.RELATIVE_TO_SELF, 1f,
                                Animation.RELATIVE_TO_SELF, 0);
                        animationUp.setDuration(1000);
                        animationDown.setDuration(1000);
                        imgUp.setVisibility(View.VISIBLE);
                        imgDown.setVisibility(View.VISIBLE);
                        imgUp.startAnimation(animationUp);
                        imgDown.startAnimation(animationDown);

                        if (mIsLocation) {
                            page = 0;
                            poiSearch();
                        } else {
                            Toast.makeText(ShakeActivity.this, "未获取到定位信息", Toast.LENGTH_SHORT).show();
                        }
                        isClear = true;
                        poiSearch();

                    }else {
                        if (isSleeping == false){
                            isSleeping = true;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(5000);
                                        shakeValid = true;
                                        isSleeping = false;
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }).start();
                        }else {
                            Toast.makeText(ShakeActivity.this,"客官太频繁了，休息哈哦",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                //传感器精度发生变化的时候调用
            }
        };
        //注册加速度传感器
        /**
         * 参数1:传感器事件监听器
         * 参数2:指定传感器
         * 参数3:sensor事件传递的速率
         */
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void poiSearch() {

        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
        nearbySearchOption.location(position);
        nearbySearchOption.keyword("景点");
        nearbySearchOption.radius(1000);
        nearbySearchOption.pageCapacity(10);
        nearbySearchOption.pageNum(page);
        mPoiSearch.searchNearby(nearbySearchOption);
    }

    @OnClick(R.id.toolbar_image)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.toolbar_image:
                finish();
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shake;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销加速度传感器
        sensorManager.unregisterListener(sensorEventListener, sensor);
    }

    @Override
    public void onRefresh() {

        xrfv.stopRefresh();
    }

    @Override
    public void onLoadMore(boolean isSilence) {
        if (++page >= totalPage){
            Toast.makeText(this,"最后一页了",Toast.LENGTH_SHORT).show();
        }else {
            isClear = false;
            poiSearch();
        }
        xrfv.stopLoadMore();

    }

    @Override
    public void onRelease(float direction) {

    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY) {

    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
            Toast.makeText(ShakeActivity.this, "未找到结果", Toast.LENGTH_LONG).show();
            return;
        }
        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
//                            baiduMap.clear();
//                            PoiOverlay poiOverlay = new PoiOverlay(baiduMap);
//                            poiOverlay.setData(poiResult);// 设置POI数据
//                            baiduMap.setOnMarkerClickListener(poiOverlay);
//                            poiOverlay.addToMap();// 将所有的overlay添加到地图上
//                            poiOverlay.zoomToSpan();

            //检索数据加载到RecyclerView里
            if (isClear){
                data.clear();
            }
            data.addAll(poiResult.getAllPoi());
            imgUp.setVisibility(View.INVISIBLE);
            imgDown.setVisibility(View.INVISIBLE);
            mAdapter.notifyDataSetChanged();
//                            rcv_poi.setVisibility(View.VISIBLE);
//                            rcv_poi.setBackgroundColor(Color.WHITE);
//                            rcv_poi.addItemDecoration(
//                                    new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
//                            rcv_poi.setLayoutManager(new LinearLayoutManager(mContext));
//                            mAdapter = new PoiResultAdapter(infos);
//                            rcv_poi.setAdapter(mAdapter);


            totalPage = poiResult.getTotalPageNum();// 获取总分页数
            Toast.makeText(ShakeActivity.this,
                    "总共查到" + poiResult.getTotalPoiNum() + "个兴趣点, 分为"
                            + totalPage + "页", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }
}
