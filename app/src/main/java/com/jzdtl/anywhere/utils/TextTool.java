package com.jzdtl.anywhere.utils;

import android.text.TextUtils;

/**
 * Created by gcy on 2016/11/22.
 */

public class TextTool {
    public static final String removeBOM(String data) {
        if (TextUtils.isEmpty(data)) {
            return data;
        }

        if (data.startsWith("\ufeff")) {
            return data.substring(1);
        } else {
            return data;
        }
    }

    public static String getImageName(String path){
        String[] strings = path.split("/");
        String name = strings[strings.length-1];
        return name;
    }
}
