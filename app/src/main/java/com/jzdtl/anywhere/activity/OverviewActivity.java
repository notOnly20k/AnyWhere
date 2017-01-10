package com.jzdtl.anywhere.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.MapView;
import com.google.gson.Gson;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.adapter.OverviewExpandListAdapter;
import com.jzdtl.anywhere.bean.OverviewBean;
import com.jzdtl.anywhere.callback.ApiService;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OverviewActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_image)
    ImageView toolbarImage;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.elv_overview)
    ExpandableListView elv_overview;
    private String url_overview;
    private OverviewBean data;
    private OverviewExpandListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null){
            url_overview = intent.getStringExtra("url");
        }

        ButterKnife.bind(this);
        toolbarInit();
        getDataFromNet(url_overview);
        initData();

//        View view = LayoutInflater.from(this).inflate(R.layout.item_header_expandlist,)
//        elv_overview.addHeaderView();
        elv_overview.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0; i < mAdapter.getGroupCount(); i++) {
                    if (groupPosition != i)
                        elv_overview.collapseGroup(i);
                    else
                        elv_overview.setSelectedGroup(i);
                }
            }
        });


    }

    @OnClick ({R.id.toolbar_image})
    public void onClick(View view){
        if (view.getId() == R.id.toolbar_image){
            finish();
        }
    }


    private void initData() {
        data = new OverviewBean();
    }

    private void getDataFromNet(String url_overview) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url_overview).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(OverviewActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                OverviewBean bean = gson.fromJson(response.body().string(), OverviewBean.class);
                data = bean;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new OverviewExpandListAdapter(data,OverviewActivity.this);
                        elv_overview.setAdapter(mAdapter);
                    }
                });

            }
        });

    }


    private void toolbarInit() {
        toolbarImage.setImageResource(R.mipmap.back_icon);
        toolbarSubtitle.setText("");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_overview;
    }



}
