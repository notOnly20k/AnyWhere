package com.jzdtl.anywhere.utils;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gcy on 2016/12/27.
 */

public class ActivityManager {
    private static List<Activity> activityList = new ArrayList<>();
    public static void startActivity(Activity activity,Intent intent){
        activity.startActivity(intent);
        activityList.add(activity);
    }

    public static void startActivityForResult(Activity activity,Intent intent,int requestCode){
        activity.startActivityForResult(intent,requestCode);
        activityList.add(activity);
    }

    public static void finishActivity(Activity activity){
        activity.finish();
        activityList.remove(activity);
    }
    public static void exitApp(){
        for (int i = 0; i < activityList.size(); i++) {
            activityList.get(i).finish();
        }
    }
}
