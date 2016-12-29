package com.jzdtl.anywhere.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jzdtl.anywhere.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cz on 2016/12/29.
 */

public class PicDiaLog extends DialogFragment {


    @BindView(R.id.img_dialog_pic)
    ImageView imgDialogPic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_pic, container);
        ButterKnife.bind(this, view);
//        bannerDialogPic.setImageLoader(new ImageLoader() {
//            @Override
//            public void displayImage(Context context, Object path, ImageView imageView) {
//                Glide.with(context).load(path).into(imageView);
//            }
//        }).setImages().isAutoPlay(false).start();

        Bundle b=getArguments();
        Log.e("log", "onCreateView: "+b.toString() );
        Glide.with(this).load(b.getString("url")).crossFade(2000).into(imgDialogPic);
        imgDialogPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }

}
