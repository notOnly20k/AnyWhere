package com.jzdtl.anywhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.adapter.ViewPageAdapter;
import com.jzdtl.anywhere.bean.UserProfilesResult;
import com.jzdtl.anywhere.callback.ApiService;
import com.jzdtl.anywhere.constants.Constant;
import com.jzdtl.anywhere.fragment.UserPageFragment1;
import com.jzdtl.anywhere.fragment.UserPageFragment2;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserPageActivity extends BaseActivity {
    @BindView(R.id.img_userpage_background)
    ImageView imguserpagebacground;
    @BindView(R.id.toolbar_image)
    ImageView toolbarImage;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_userpage_headpic)
    CircleImageView imgUserpageHeadpic;
    @BindView(R.id.tv_userpage_name)
    TextView tvUserpageName;
    @BindView(R.id.tv_userpage_follow)
    TextView tvUserpageFollow;
    @BindView(R.id.tab_userpage)
    TabLayout tabUserpage;
    @BindView(R.id.vp_userpage)
    ViewPager vpUserpage;
    private List<UserProfilesResult> list;
    private ViewPageAdapter adapter;
    private int user_id;
    private List<Fragment> fragmentlist;
    private List<Integer> icon;
    private UserPageFragment1 userPage1;
    private UserPageFragment2 userPage2;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initHead();
        initList();
        initViewPage();
        initTab();
        download(user_id + "");
        setToolBarTitle("个人主页");
    }


    private void initTab() {
        tabUserpage.setupWithViewPager(vpUserpage);
        for (int i = 0; i <fragmentlist.size(); i++) {
            tabUserpage.getTabAt(i).setIcon(icon.get(i));
}
}

    private void initViewPage() {
        UserPageFragment1 userPageFragment1=new UserPageFragment1();
        adapter=new ViewPageAdapter(getSupportFragmentManager(),fragmentlist,icon);
        vpUserpage.setAdapter(adapter);
        vpUserpage.setCurrentItem(0);
    }

    private void initHead() {
        Intent intent = getIntent();
        String user_name = intent.getStringExtra("user_name");
        String user_headpic = intent.getStringExtra("user_headpic");
        Glide.with(UserPageActivity.this).load(user_headpic).into(imgUserpageHeadpic);
        Glide.with(UserPageActivity.this).load(user_headpic).into(imguserpagebacground);
        tvUserpageName.setText(user_name);
        user_id = intent.getIntExtra("user_id", 1);
        bundle = new Bundle();
        bundle.putString("id",user_id+"");
        toolbarImage.setImageResource(R.mipmap.back_icon);
        toolbarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initList() {
        fragmentlist=new ArrayList<>();
        userPage1 = new UserPageFragment1();
        userPage1.setArguments(bundle);
        userPage2 = new UserPageFragment2();
        userPage2.setArguments(bundle);
        fragmentlist.add(userPage1);
        fragmentlist.add(userPage2);
        icon = new ArrayList<>();
        icon.add(R.drawable.selector_userpage_one);
        icon.add(R.drawable.selector_userpage_two);
    }


    private void download(String id) {
        list = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.YUNYOU_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getUserProfileResult(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserProfilesResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(UserPageActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(UserProfilesResult userProfilesResult) {
                        tvUserpageFollow.setText(userProfilesResult.getData().getFollowings_count()+"关注|"+userProfilesResult.getData().getFollowers_count()+"粉丝");
                       if (userProfilesResult.getData().isIs_follow()==false) {
                           toolbarSubtitle.setText("关注");
                       }else {
                           toolbarSubtitle.setText("已关注");
                       }
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_page;
    }


}
