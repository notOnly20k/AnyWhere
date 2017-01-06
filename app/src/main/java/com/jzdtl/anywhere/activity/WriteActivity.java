package com.jzdtl.anywhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzdtl.anywhere.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.iwf.photopicker.PhotoPicker;

/**
 * Created by cz on 2017/1/5.
 */

public class WriteActivity extends BaseActivity {
    @BindView(R.id.toolbar_image)
    ImageView toolbarImage;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_write_title)
    EditText tvWriteTitle;
    @BindView(R.id.tv_write_info)
    EditText tvWriteInfo;
    @BindView(R.id.img_write_add)
    ImageButton imgWriteAdd;
    @BindView(R.id.rec_write_showpics)
    RecyclerView recWriteShowpics;
    private List<String>photoPath=new ArrayList<>();
    private List<File>list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        choosePics();
    }

    private void choosePics() {
        imgWriteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoPicker.builder()
                        .setPhotoCount(9)
                        .setShowCamera(true)
                        .setPreviewEnabled(false)
                        .start(WriteActivity.this);
                imgWriteAdd.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            ArrayList<String> photos =
                    data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            Log.i("照片 路径", "onActivityResult: " + photos);
            photoPath.addAll(photos);
            if (photoPath.size() != 0) {
                for (int i = 0; i < photoPath.size(); i++) {
                    File f = new File(photoPath.get(i));
                    list.add(f);
                }
            }
        }
    }

    private void initToolbar() {
        setToolBarTitle("写游记");
        toolbarSubtitle.setText("提交");
        toolbarImage.setImageResource(R.mipmap.back_icon);
        toolbarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write;
    }
}
