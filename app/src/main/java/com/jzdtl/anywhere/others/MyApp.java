package com.jzdtl.anywhere.others;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;
import cn.smssdk.SMSSDK;

/**
 * Created by chenzd on 2016/12/23.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        SMSSDK.initSDK(this, "1a563f659fd8f", "4ba95726495b8009827d93eb53af8a58");
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        ShareSDK.initSDK(this);
    }
}
