package com.jzdtl.anywhere.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jzdtl.anywhere.R;

/**
 * Created by gcy on 2016/12/28.
 */

public class ItemView extends LinearLayout {

    private View mView;
    private ImageView mImage;
    private TextView mName;
    private TextView mTip;
    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemView);
        int id = typedArray.getResourceId(R.styleable.ItemView_item_src, 0);
        String name = typedArray.getString(R.styleable.ItemView_item_name);
        String tip = typedArray.getString(R.styleable.ItemView_item_tip);
        typedArray.recycle();
        mImage.setImageResource(id);
        mName.setText(name);
        mTip.setText(tip);
        addView(mView);
    }
    private void initViews(Context context) {
        mView = LayoutInflater.from(context).inflate(R.layout.view_combination_item,null);
        mImage = (ImageView) mView.findViewById(R.id.image_view_image);
        mName = (TextView) mView.findViewById(R.id.text_view_name);
        mTip = (TextView) mView.findViewById(R.id.text_view_tip);
    }
}
