package com.jzdtl.anywhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
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
import com.jzdtl.anywhere.adapter.GoodsPagerAdapter;
import com.jzdtl.anywhere.bean.DestBean;
import com.jzdtl.anywhere.bean.DestinationsResult;
import com.jzdtl.anywhere.callback.ApiService;
import com.jzdtl.anywhere.callback.OnDestClickListener;
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

public class DestinationActivity extends BaseActivity implements OnDestClickListener {
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
    @BindView(R.id.text_dest_city_name)
    TextView textDestCityName;
    @BindView(R.id.text_dest_activity_name)
    TextView textDestActivityName;
    @BindView(R.id.text_dest_all_name)
    TextView textDestAllName;
    @BindView(R.id.image_dest_activity_pic)
    ImageView imageDestActivityPic;
    @BindView(R.id.layout_dest_gride)
    LinearLayout layoutDestGride;
    @BindView(R.id.text_dest_author)
    TextView textDestAuthor;
    @BindView(R.id.layout_dest_activity)
    LinearLayout layoutDestActivity;
    @BindView(R.id.pager_dest_goods)
    ViewPager pagerDestGoods;
    private Retrofit retrofit;
    private ApiService apiService;
    private String city;
    private List<DestBean> modelData;
    private DestAdapter destAdapter;
    private ArrayList<String> photoPaths;
    private String userId;
    private String userPhoto;
    private GoodsPagerAdapter pagerAdapter;
    private List<DestinationsResult.DataBean.GoodsBean> goodsData;
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
        modelData = new ArrayList<>();
        recyclerDestGride.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        destAdapter = new DestAdapter(this);
        recyclerDestGride.setAdapter(destAdapter);
        layoutDestGride.setVisibility(View.GONE);
        layoutDestActivity.setVisibility(View.GONE);
        goodsData = new ArrayList<>();
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

                    toolbarTitle.setText(nameCN);
                    /**
                     *解析goods
                     **/
                    //导航栏
                    String goods = dataJson.getString("goods");
                    JSONArray goodsJson = new JSONArray(goods);
                    int goodsLen = goodsJson.length();
                    for (int i = 0; i < goodsLen; i++) {
                        DestinationsResult.DataBean.GoodsBean goodsBean = new DestinationsResult.DataBean.GoodsBean();
                        String gonglveJson = goodsJson.getString(i);
                        Log.d(TAG, "onResponse: =============正常执行0==========="+gonglveJson);
                        JSONObject gonglveObject = new JSONObject(gonglveJson);
                        String goodsTitle = gonglveObject.getString("title");
                        String goodsPhotoUrl = gonglveObject.getString("photo_url");
                        String goodsUrl= gonglveObject.getString("url");
                        String goodsType = gonglveObject.getString("type");
                        Log.d(TAG, "onResponse: =============正常执行1===========");
                        //添加wiki的id
                        if (gonglveJson.contains("wiki_destination")){
                            String wikiDestination = gonglveObject.getString("wiki_destination");
                            if (wikiDestination !=null && !wikiDestination.equals("")){
                                JSONObject wikiObject = new JSONObject(wikiDestination);
                                String id = wikiObject.getString("id");
                                Log.d(TAG, "onResponse: ============="+id);
                                DestinationsResult.DataBean.GoodsBean.WikiDestinationBeanX wikiDestinationBeanX = new DestinationsResult.DataBean.GoodsBean.WikiDestinationBeanX();
                                wikiDestinationBeanX.setId(Integer.parseInt(id));
                                goodsBean.setWiki_destination(wikiDestinationBeanX);
                            }
                        }
                        Log.d(TAG, "onResponse: =============正常执行2===========");
                        goodsBean.setTitle(goodsTitle);
                        goodsBean.setPhoto_url(goodsPhotoUrl);
                        goodsBean.setUrl(goodsUrl);
                        goodsBean.setType(goodsType);
                        goodsData.add(goodsBean);
                    }
                    pagerAdapter = new GoodsPagerAdapter(DestinationActivity.this);
                    pagerAdapter.setData(goodsData,DestinationActivity.this);
                    pagerDestGoods.setAdapter(pagerAdapter);

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
                                    String path = tempObject.getString("path");
                                    String city = getDestinationsPath(path);
                                    DestBean destBean = new DestBean(city, imgUrl, name, nameEn);
                                    modelData.add(destBean);
                                    destAdapter.setData(modelData);
                                }
                                destAdapter.setOnDestClickListener(DestinationActivity.this);
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
                                userId = userObject.getString("id");
                                userPhoto = userObject.getString("photo_url");
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

    @OnClick({R.id.toolbar_image, R.id.image_dest_head, R.id.text_dest_map, R.id.image_dest_activity_pic, R.id.text_dest_author, R.id.text_dest_all, R.id.text_dest_all_name})
    public void onClick(View view) {
        Intent intentBrowse = new Intent();
        intentBrowse.setAction("android.intent.action.VIEW");
        switch (view.getId()) {
            case R.id.toolbar_image:
                ActivityManager.finishActivity(this);
                break;
            case R.id.image_dest_head:
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
                Bundle bundle = new Bundle();
                bundle.putInt("user_id", Integer.parseInt(userId));
                bundle.putString("user_headpic", userPhoto);
                ActivityManager.startActivity(this, new Intent(this, UserPageActivity.class).putExtras(bundle));
                break;
            case R.id.text_dest_all:
                textDestContent.setMaxLines(Integer.MAX_VALUE);
                textDestAll.setVisibility(View.GONE);
                break;
            case R.id.text_dest_all_name:
                break;
        }
    }

    public String getDestinationsPath(String path) {
        //.1.5.166.111.
        String[] strings = path.split("\\.");
        String realPath = strings[strings.length - 1];
        return realPath;
    }

    @Override
    public void onDestClickListener(String city) {
        ActivityManager.startActivity(this, new Intent(this, DestinationActivity.class).putExtra("city", city));
    }
}
