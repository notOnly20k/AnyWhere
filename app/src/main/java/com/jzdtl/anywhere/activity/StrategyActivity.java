package com.jzdtl.anywhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.adapter.StrategyAdapter;
import com.jzdtl.anywhere.bean.StrategyBean;
import com.jzdtl.anywhere.bean.StrategyTypeBean;
import com.jzdtl.anywhere.callback.ApiService;
import com.jzdtl.anywhere.callback.OnStrategyItemClickListener;
import com.jzdtl.anywhere.constants.Constant;
import com.jzdtl.anywhere.utils.ActivityManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class StrategyActivity extends BaseActivity implements OnStrategyItemClickListener {
    private static final String TAG = "StrategyActivity";
    @BindView(R.id.toolbar_image)
    ImageView toolbarImage;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_strategy)
    LinearLayout activityStrategy;
    @BindView(R.id.recycler_strategy_content)
    RecyclerView recyclerStrategyContent;
    private String id;
    private Retrofit retrofit;
    private ApiService apiService;
    private ArrayList<StrategyTypeBean> strateData;
    private StrategyAdapter adapter;
    private ArrayList<StrategyBean> expandData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initData();
        requestData(id);
    }

    private void requestData(String id) {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.YUNYOU_BASE_URL)
                .build();
        apiService = retrofit.create(ApiService.class);
        apiService.getStrategyResult(id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String result = response.body().string();
                            JSONArray jsonArray = new JSONArray(result);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //得到每一个组
                                String jsonArrayString = jsonArray.getString(i);
                                JSONObject jsonObject = new JSONObject(jsonArrayString);
                                String categoryType = jsonObject.getString("category_type");
                                switch (categoryType) {
                                    case "0"://概览
                                        addData(jsonObject, R.mipmap.tips_overview, "概览", "Overview");
                                        expandData.add(getStrategyDetailData(jsonObject, categoryType));
                                        break;
                                    case "5"://娱乐
                                        addData(jsonObject, R.mipmap.tips_entertainment, "娱乐", "Entertainment");
                                        expandData.add(getStrategyDetailData(jsonObject, categoryType));
                                        break;
                                    case "7"://美食
                                        addData(jsonObject, R.mipmap.tips_food, "美食", "Food");
                                        expandData.add(getStrategyDetailData(jsonObject, categoryType));
                                        break;
                                    case "8"://购物
                                        addData(jsonObject, R.mipmap.tips_shopping, "购物", "Shopping");
                                        expandData.add(getStrategyDetailData(jsonObject, categoryType));
                                        break;
                                    case "11"://其他
                                        addData(jsonObject, R.mipmap.tips_more, "其他", "Other");
                                        expandData.add(getStrategyDetailData(jsonObject, categoryType));
                                        break;
                                }
                                adapter.setData(strateData);
                                Log.d(TAG, "onResponse: " + expandData.toString());
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

    private StrategyBean getStrategyDetailData(JSONObject jsonObject, String type) throws JSONException {
        StrategyBean strategyBean = new StrategyBean();
        strategyBean.setType(type);
        String pages = jsonObject.getString("pages");
        JSONArray titleArray = new JSONArray(pages);
        //多个项目，游玩指南、旅行行程规划、节假日出行……
        ArrayList<StrategyBean.Page> strategyPages = new ArrayList<>();
        //解析pages
        for (int j = 0; j < titleArray.length(); j++) {
            StrategyBean.Page strategyPage = new StrategyBean.Page();
            String titleString = titleArray.getString(j);
            JSONObject titleObject = new JSONObject(titleString);
            String children = titleObject.getString("children");
            String pageName = titleObject.getString("title");
            strategyPage.setTitle(pageName);
            JSONArray childArray = new JSONArray(children);
            //多个类型，如：四川游玩概览、适宜气候、当地节日……
            //解析children
            ArrayList<StrategyBean.Page.Children> strategyChildrens = new ArrayList<>();
            for (int k = 0; k < childArray.length(); k++) {
                StrategyBean.Page.Children strategyChildren = new StrategyBean.Page.Children();
                String tempChild = childArray.getString(k);
                JSONObject tempObject = new JSONObject(tempChild);
                //添加groupname
                String groupTitle = tempObject.getString("title");
                strategyChildren.setTitle(groupTitle);
                String sections = tempObject.getString("sections");
                JSONArray sectionsArray = new JSONArray(sections);
                ArrayList<StrategyBean.Page.Children.Section> strategySections = new ArrayList<>();
                //多个条目，标题、内容、图片
                for (int l = 0; l < sectionsArray.length(); l++) {
                    StrategyBean.Page.Children.Section strategySection = new StrategyBean.Page.Children.Section();
                    String string = sectionsArray.getString(l);
                    JSONObject object = new JSONObject(string);
                    //添加标题
                    String contentTitle = object.getString("title");
                    strategySection.setTitle(contentTitle);
                    Log.d(TAG, "===========contentTitle===========: " + contentTitle);
                    //添加描述
                    String contentDescription = object.getString("description");
                    strategySection.setDescription(contentDescription);
                    String contentPhotos = object.getString("photos");
                    JSONArray photoArray = new JSONArray(contentPhotos);
                    ArrayList<String> photoData = new ArrayList<>();
                    //添加图片数组
                    for (int m = 0; m < photoArray.length(); m++) {
                        String photo = photoArray.getString(m);
                        JSONObject photoObject = new JSONObject(photo);
                        String imageUrl = photoObject.getString("image_url");
                        photoData.add(imageUrl);
                    }
                    strategySection.setPhotos(photoData);
                    strategySections.add(strategySection);
                }
                strategyChildren.setSections(strategySections);
                strategyChildrens.add(strategyChildren);
            }
            strategyPage.setChildrens(strategyChildrens);
            strategyPages.add(strategyPage);
        }
        strategyBean.setPages(strategyPages);
        return strategyBean;
    }

    private void addData(JSONObject jsonObject, int res, String cn, String en) throws JSONException {
        StrategyTypeBean bean = new StrategyTypeBean();
        bean.setRes(res);
        bean.setNameCN(cn);
        bean.setNameEN(en);
        bean.setTitle(getStrategyTitle(jsonObject));
        strateData.add(bean);
    }

    private List<String> getStrategyTitle(JSONObject jsonObject) throws JSONException {
        List<String> titles = new ArrayList<>();
        String pages = jsonObject.getString("pages");
        JSONArray titleArray = new JSONArray(pages);
        for (int j = 0; j < titleArray.length(); j++) {
            String titleString = titleArray.getString(j);
            JSONObject titleObject = new JSONObject(titleString);
            String title = titleObject.getString("title");
            titles.add(title);
        }
        return titles;
    }

    private void initData() {
        id = getIntent().getStringExtra("id");
        strateData = new ArrayList<>();
        expandData = new ArrayList<>();
        recyclerStrategyContent.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StrategyAdapter(this);
        recyclerStrategyContent.setAdapter(adapter);
        adapter.setmOnStrategyItemClickListener(this);

        toolbarImage.setImageResource(R.mipmap.back_icon);
        toolbarTitle.setText("攻略");
        toolbarSubtitle.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_strategy;
    }

    @Override
    public void onStrategyItemClickListener(int group, int child) {
        Bundle bundle = new Bundle();
        bundle.putInt("group", group);
        bundle.putInt("child", child);
        bundle.putParcelableArrayList("data", expandData);
        ActivityManager.startActivity(this, new Intent(this, StrategyDetailActivity.class).putExtras(bundle));
    }

    @OnClick(R.id.toolbar_image)
    public void onClick() {
        ActivityManager.finishActivity(this);
    }
}
