package com.jzdtl.anywhere.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzdtl.anywhere.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShakeActivity extends BaseActivity {

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

    Sensor sensor;
    SensorEventListener sensorEventListener;
    SensorManager sensorManager;

    //震动器
    Vibrator vibrator;
    //播放音频
    SoundPool soundPool;
    int soundId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        toolbarImage.setImageResource(R.mipmap.back_icon);
        toolbarTitle.setText("摇一摇搜周边");
        toolbarSubtitle.setText("");

        /**
         * 参数1： 通过的最大流的数量
         * 参数2：流的类型
         * 参数3：无效，默认0
         */
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

        soundId =  soundPool.load(this,R.raw.shake,0);
        //获取震动器类的对象
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        //获取传感器的管理类
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //获取手机中的所有传感器
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (int i = 0; i < sensorList.size(); i++) {
            Sensor s = sensorList.get(i);
            Log.e("======", "====供应商===" + s.getVendor());
            Log.e("======", "====传感器的名字===" + s.getName());
            Log.e("======", "====功率===" + s.getPower());
            Log.e("======", "====类型===" + s.getType());
            Log.e("======", "====版本===" + s.getVersion());
        }

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
                    soundPool.play(soundId,1,1,0,0,1);

                    TranslateAnimation animationUp = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF,0,
                            Animation.RELATIVE_TO_SELF,0,
                            Animation.RELATIVE_TO_SELF,-1f,
                            Animation.RELATIVE_TO_SELF,0);
                    TranslateAnimation animationDown = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF,0,
                            Animation.RELATIVE_TO_SELF,0,
                            Animation.RELATIVE_TO_SELF,1f,
                            Animation.RELATIVE_TO_SELF,0);
                    animationUp.setDuration(1000);
                    animationDown.setDuration(1000);
                    imgUp.startAnimation(animationUp);
                    imgDown.startAnimation(animationDown);


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
}
