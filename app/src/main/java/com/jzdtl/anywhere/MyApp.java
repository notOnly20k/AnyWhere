package com.jzdtl.anywhere;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by chenzd on 2016/12/23.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
    }
}
