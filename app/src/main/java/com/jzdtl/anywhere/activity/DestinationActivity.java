package com.jzdtl.anywhere.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.adapter.DestAdapter;
import com.jzdtl.anywhere.bean.DestBean;
import com.jzdtl.anywhere.callback.ApiService;
import com.jzdtl.anywhere.constants.Constant;
import com.jzdtl.anywhere.utils.ActivityManager;
import com.jzdtl.anywhere.utils.TextTool;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPreview;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DestinationActivity extends BaseActivity {
    private static final String TAG = "DestinationActivity";
    @BindView(R.id.toolbar_image)
    ImageView toolbarImage;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image_dest_head)
    ImageView imageDestHead;
    @BindView(R.id.text_dest_name_cn)
    TextView textDestNameCn;
    @BindView(R.id.text_dest_name_en)
    TextView textDestNameEn;
    @BindView(R.id.image_dest_gonglve)
    ImageView imageDestGonglve;
    @BindView(R.id.image_dest_ticket)
    ImageView imageDestTicket;
    @BindView(R.id.image_dest_free)
    ImageView imageDestFree;
    @BindView(R.id.image_dest_follow)
    ImageView imageDestFollow;
    @BindView(R.id.recycler_dest_gride)
    RecyclerView recyclerDestGride;
    @BindView(R.id.text_dest_map)
    TextView textDestMap;
    @BindView(R.id.text_dest_title)
    TextView textDestTitle;
    @BindView(R.id.text_dest_content)
    TextView textDestContent;
    @BindView(R.id.text_dest_all)
    TextView textDestAll;
    @BindView(R.id.activity_destination)
    CoordinatorLayout activityDestination;
    @BindView(R.id.text_dest_city_name)
    TextView textDestCityName;
    @BindView(R.id.text_dest_activity_name)
    TextView textDestActivityName;
    @BindView(R.id.text_dest_all_name)
    TextView textDestAllName;
    @BindView(R.id.image_dest_activity_pic)
    ImageView imageDestActivityPic;
    @BindView(R.id.text_dest_gonglve)
    TextView textDestGonglve;
    @BindView(R.id.test_dest_ticket)
    TextView testDestTicket;
    @BindView(R.id.test_dest_free)
    TextView testDestFree;
    @BindView(R.id.test_dest_follow)
    TextView testDestFollow;
    @BindView(R.id.layout_dest_gride)
    LinearLayout layoutDestGride;
    @BindView(R.id.text_dest_author)
    TextView textDestAuthor;
    @BindView(R.id.layout_dest_activity)
    LinearLayout layoutDestActivity;
    private Retrofit retrofit;
    private ApiService apiService;
    private String city;
    private List<DestBean> modelData;
    private DestAdapter destAdapter;
    private String ticketUrl = "";
    private String freeUrl = "";
    private String followUrl = "";
    private Uri ticketUri;
    private ArrayList<String> photoPaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initData();
        city = getIntent().getStringExtra("city");
        requestData(city);
    }

    private void initData() {
        toolbarImage.setImageResource(R.mipmap.back_icon);
        toolbarTitle.setText("动态");
        toolbarSubtitle.setVisibility(View.GONE);
        toolbar.getBackground().setAlpha(0);
        modelData = new ArrayList<>();
        recyclerDestGride.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        destAdapter = new DestAdapter(this);
        recyclerDestGride.setAdapter(destAdapter);
        layoutDestGride.setVisibility(View.GONE);
        layoutDestActivity.setVisibility(View.GONE);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_destination;
    }

    private void requestData(String id) {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.YUNYOU_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        apiService.getDestinationResult(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    String json = TextTool.removeBOM(response.body().string());
                    Log.d(TAG, "onResponse: " + json);
                    /**
                     *解析destination
                     **/
                    JSONObject rootJson = new JSONObject(json);
                    String data = rootJson.optString("data");
                    JSONObject dataJson = new JSONObject(data);
                    String destination = dataJson.optString("destination");
                    JSONObject infoJson = new JSONObject(destination);
                    //背景图
                    String headUrl = infoJson.getString("photo_url");
                    Glide.with(DestinationActivity.this).load(headUrl).into(imageDestHead);
                    //名称
                    String nameCN = infoJson.getString("name");
                    String nameEN = infoJson.getString("name_en");
                    textDestNameCn.setText(nameCN);
                    textDestNameEn.setText(nameEN);

                    /**
                     *解析goods
                     **/
                    //导航栏
                    String goods = dataJson.getString("goods");
                    JSONArray goodsJson = new JSONArray(goods);
                    //TODO 解析额外信息

                    String gonglveJson = goodsJson.getString(0);
                    JSONObject gonglveObject = new JSONObject(gonglveJson);
                    String gonglveTitle = gonglveObject.getString("title");
                    String gonglvePhotoUrl = gonglveObject.getString("photo_url");

                    textDestGonglve.setText(gonglveTitle);
                    Glide.with(DestinationActivity.this).load(gonglvePhotoUrl).into(imageDestGonglve);

                    String ticketsJson = goodsJson.getString(1);
                    JSONObject ticketsObject = new JSONObject(ticketsJson);
                    String ticketsTitle = ticketsObject.getString("title");
                    String ticketsPhotoUr1 = ticketsObject.getString("photo_url");
                    ticketUrl = ticketsObject.getString("url");
                    testDestTicket.setText(ticketsTitle);
                    Glide.with(DestinationActivity.this).load(ticketsPhotoUr1).into(imageDestTicket);

                    String freeJson = goodsJson.getString(2);
                    JSONObject freeObject = new JSONObject(freeJson);
                    String freeTitle = freeObject.getString("title");
                    String freePhotoUrl = freeObject.getString("photo_url");
                    freeUrl = freeObject.getString("url");
                    testDestFree.setText(freeTitle);
                    Glide.with(DestinationActivity.this).load(freePhotoUrl).into(imageDestFree);

                    String followJson = goodsJson.getString(3);
                    JSONObject followObject = new JSONObject(followJson);
                    String followTitle = followObject.getString("title");
                    String followPhotoUrl = followObject.getString("photo_url");
                    followUrl = followObject.getString("url");
                    testDestFollow.setText(followTitle);
                    Glide.with(DestinationActivity.this).load(followPhotoUrl).into(imageDestFollow);

                    /**
                     *解析sections
                     **/
                    String sections = dataJson.getString("sections");
                    JSONArray sectionsJson = new JSONArray(sections);
                    for (int i = 0; i < sectionsJson.length(); i++) {
                        String destSection = sectionsJson.getString(i);
                        //城市数据
                        JSONObject destObject = new JSONObject(destSection);
                        String count = destObject.getString("count");
                        String title = destObject.getString("title");
                        String type = destObject.getString("type");
                        switch (type) {
                            case "Destination":
                                layoutDestGride.setVisibility(View.VISIBLE);
                                //城市数据
                                layoutDestGride.setVisibility(View.VISIBLE);
                                String buttonText = destObject.getString("button_text");
                                //城市列表
                                String models = destObject.getString("models");
                                JSONArray destArray = new JSONArray(models);
                                for (int j = 0; j < destArray.length(); j++) {
                                    String tempJson = destArray.getString(j);
                                    JSONObject tempObject = new JSONObject(tempJson);
                                    String imgUrl = tempObject.getString("photo_url");
                                    String name = tempObject.getString("name");
                                    String nameEn = tempObject.getString("name_en");
                                    DestBean destBean = new DestBean(imgUrl, name, nameEn);
                                    modelData.add(destBean);
                                    destAdapter.setData(modelData);
                                }
                                textDestCityName.setText(title);
                                textDestMap.setText(buttonText);
                                break;
                            case "Plan":
                                break;
                            case "UserActivity":
                                layoutDestActivity.setVisibility(View.VISIBLE);
                                //游记数据数据
                                String activityButton = destObject.getString("button_text");
                                String activityModels = destObject.getString("models");
                                JSONArray activityArray = new JSONArray(activityModels);
                                //具体游记
                                String tempString = activityArray.getString(0);
                                JSONObject tempActivity = new JSONObject(tempString);
                                String topic = tempActivity.getString("topic");
                                String description = tempActivity.getString("description");
                                String user = tempActivity.getString("user");
                                JSONObject userObject = new JSONObject(user);
                                String userName = userObject.getString("name");
                                //游记图片
                                photoPaths = new ArrayList<>();
                                String activityContents = tempActivity.getString("contents");
                                JSONArray activityPics = new JSONArray(activityContents);
                                for (int j = 0; j < activityPics.length(); j++) {
                                    String stringPic = activityPics.getString(j);
                                    JSONObject picObject = new JSONObject(stringPic);
                                    String picUrl = picObject.getString("photo_url");
                                    photoPaths.add(picUrl);
                                }
                                Glide.with(DestinationActivity.this).load(photoPaths.get(0)).into(imageDestActivityPic);

                                textDestAllName.setText(activityButton);
                                textDestTitle.setText(topic);
                                textDestContent.setText(description);
                                textDestAuthor.setText(userName);
                                break;
                            case "SearchActivityCollectionDestinationEntity":
                                break;
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.toolbar_image, R.id.image_dest_head, R.id.image_dest_gonglve, R.id.image_dest_ticket, R.id.image_dest_free, R.id.image_dest_follow, R.id.text_dest_map, R.id.image_dest_activity_pic, R.id.text_dest_author, R.id.text_dest_all, R.id.text_dest_all_name})
    public void onClick(View view) {
        Intent intentBrowse = new Intent();
        intentBrowse.setAction("android.intent.action.VIEW");
        switch (view.getId()) {
            case R.id.toolbar_image:
                ActivityManager.finishActivity(this);
                break;
            case R.id.image_dest_head:
                break;
            case R.id.image_dest_gonglve:

                break;
            case R.id.image_dest_ticket:
                ticketUri = Uri.parse(ticketUrl);
                intentBrowse.setData(ticketUri);
                ActivityManager.startActivity(this, intentBrowse);
                break;
            case R.id.image_dest_free:
                Uri freeUri = Uri.parse(freeUrl);
                intentBrowse.setData(freeUri);
                ActivityManager.startActivity(this, intentBrowse);
                break;
            case R.id.image_dest_follow:
                Uri followUri = Uri.parse(followUrl);
                intentBrowse.setData(followUri);
                ActivityManager.startActivity(this, intentBrowse);
                break;
            case R.id.text_dest_map:
                break;
            case R.id.image_dest_activity_pic:
                PhotoPreview.builder()
                        .setPhotos(photoPaths)
                        .setCurrentItem(0)
                        .setShowDeleteButton(false)
                        .start(DestinationActivity.this);
                break;
            case R.id.text_dest_author:
                break;
            case R.id.text_dest_all:
                textDestContent.setMaxLines(Integer.MAX_VALUE);
                textDestAll.setVisibility(View.GONE);
                break;
            case R.id.text_dest_all_name:
                break;
        }
    }
}