package com.jzdtl.anywhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.bean.LoginOutEvent;
import com.jzdtl.anywhere.db.UserEntity;
import com.jzdtl.anywhere.fragment.ActivitiesFragment;
import com.jzdtl.anywhere.fragment.IndexFragment;
import com.jzdtl.anywhere.fragment.NearbyFragment;
import com.jzdtl.anywhere.utils.ActivityManager;
import com.jzdtl.anywhere.views.ItemView;

import de.hdodenhof.circleimageview.CircleImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private static final int REQUEST_LOGIN = 1;
    @BindView(R.id.group_view_guide)
    RadioGroup groupViewGuide;
    @BindView(R.id.radio_button_home)
    RadioButton radioButtonHome;
    @BindView(R.id.relative_main_register)
    RelativeLayout relativeMainRegister;
    @BindView(R.id.layout_user_login_true)
    RelativeLayout layoutUserLoginTrue;
    @BindView(R.id.layout_user_login_false)
    RelativeLayout layoutUserLoginFalse;
    @BindView(R.id.toolbar_image)
    ImageView toolbarImage;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragment_laout_for_replace)
    FrameLayout fragmentLaoutForReplace;
    @BindView(R.id.image_main_activity)
    ItemView imageMainActivity;
    @BindView(R.id.image_main_like)
    ItemView imageMainLike;
    @BindView(R.id.image_main_feedback)
    ItemView imageMainFeedback;
    @BindView(R.id.image_main_set)
    ItemView imageMainSet;
    @BindView(R.id.image_view_head)
    CircleImageView imageViewHead;
    @BindView(R.id.text_view_name_true)
    TextView textViewNameTrue;
    @BindView(R.id.text_view_following_true)
    TextView textViewFollowingTrue;
    @BindView(R.id.text_view_follower_true)
    TextView textViewFollowerTrue;
    @BindView(R.id.text_view_name_false)
    TextView textViewNameFalse;
    private boolean login;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private IndexFragment mIndexFragment;
    private ActivitiesFragment mActivitiesFragment;
    private NearbyFragment mNearbyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initDatas();
        checkLogin();
        initEvents();
        radioButtonHome.setChecked(true);
    }

    private void initDatas() {
        mIndexFragment = new IndexFragment();
        mActivitiesFragment = new ActivitiesFragment();
        mNearbyFragment = new NearbyFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.fragment_laout_for_replace, mIndexFragment);
        mFragmentTransaction.add(R.id.fragment_laout_for_replace, mActivitiesFragment);
        mFragmentTransaction.add(R.id.fragment_laout_for_replace, mNearbyFragment);
        mFragmentTransaction.show(mIndexFragment);
        mFragmentTransaction.hide(mActivitiesFragment);
        mFragmentTransaction.hide(mNearbyFragment);
        mFragmentTransaction.commit();


        toolbarImage.setImageResource(R.mipmap.menu);
        toolbarTitle.setText("首页");
        toolbarSubtitle.setVisibility(View.GONE);
        login = spUtils.getBoolean("login", false);
    }

    private void checkLogin() {
        if (login) {
            layoutUserLoginFalse.setVisibility(View.GONE);
            layoutUserLoginTrue.setVisibility(View.VISIBLE);
        } else {
            layoutUserLoginFalse.setVisibility(View.VISIBLE);
            layoutUserLoginTrue.setVisibility(View.GONE);
        }
    }

    private void initEvents() {
        groupViewGuide.setOnCheckedChangeListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        for (int j = 0; j < radioGroup.getChildCount(); j++) {
            RadioButton rb = (RadioButton) radioGroup.getChildAt(j);
            if (rb.isChecked()) {
                rb.setTextColor(getResources().getColor(R.color.colorPrimary));

                mFragmentTransaction = mFragmentManager.beginTransaction();
                switch (j) {
                    case 0:
                        mFragmentTransaction.show(mIndexFragment);
                        mFragmentTransaction.hide(mActivitiesFragment);
                        mFragmentTransaction.hide(mNearbyFragment);
                        toolbarTitle.setText("首页");
                        toolbarSubtitle.setVisibility(View.GONE);
                        break;
                    case 1:
                        mFragmentTransaction.show(mActivitiesFragment);
                        mFragmentTransaction.hide(mIndexFragment);
                        mFragmentTransaction.hide(mNearbyFragment);
                        toolbarTitle.setText("游记");
                        toolbarSubtitle.setVisibility(View.VISIBLE);
                        toolbarSubtitle.setText("写游记");
                        break;
                    case 2:
                        mFragmentTransaction.show(mNearbyFragment);
                        mFragmentTransaction.hide(mActivitiesFragment);
                        mFragmentTransaction.hide(mIndexFragment);
                        toolbarTitle.setText("发现");
                        toolbarSubtitle.setVisibility(View.GONE);
                        break;
                }
                mFragmentTransaction.commit();
            } else {
                rb.setTextColor(getResources().getColor(R.color.gray_bfbfbf));
            }
        }
    }

    @OnClick({R.id.image_view_head,R.id.relative_main_register, R.id.layout_user_login_true, R.id.layout_user_login_false,
            R.id.image_main_activity, R.id.image_main_like, R.id.image_main_feedback, R.id.image_main_set})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_view_head:
            case R.id.layout_user_login_true:
                if (login) {
                    ActivityManager.startActivity(this,new Intent(this,ModifyInfoActivity.class));
                } else {
                    goLogin();
                }
                break;
            case R.id.layout_user_login_false:
                goLogin();
                break;
            case R.id.image_main_activity:
                if (login) {

                } else {
                    goLogin();
                }
                break;
            case R.id.image_main_like:
                if (login) {

                } else {
                    goLogin();
                }
                break;
            case R.id.image_main_feedback:
                ActivityManager.startActivity(this,new Intent(this,FeedBackActivity.class));
                break;
            case R.id.image_main_set:
                ActivityManager.startActivity(this,new Intent(this,SettingActivity.class));

                break;
        }
    }

    /**
     * 进入登录界面
     */
    private void goLogin() {
        ActivityManager.startActivity(this, new Intent(this, LoginActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetLoginInfo(UserEntity userEntity) {
        spUtils.putBoolean("login",true);
        login = true;
        layoutUserLoginFalse.setVisibility(View.GONE);
        layoutUserLoginTrue.setVisibility(View.VISIBLE);
        String imageUrl = userEntity.getHead();
        if (imageUrl.equals(""))
            Glide.with(this).load(R.mipmap.yuntu_logo).into(imageViewHead);
        else
            Glide.with(this).load(imageUrl).into(imageViewHead);
        String nickName = userEntity.getNickName();
        textViewNameTrue.setText(nickName);
        //TODO 添加关注数量  粉丝数量
        textViewFollowerTrue.setText("800万粉丝");
        textViewFollowingTrue.setText("1关注");
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetOutInfo(LoginOutEvent event) {
        spUtils.putBoolean("login",false);
        login = false;
        layoutUserLoginFalse.setVisibility(View.VISIBLE);
        layoutUserLoginTrue.setVisibility(View.GONE);
        Glide.with(this).load(R.mipmap.yuntu_logo).into(imageViewHead);
    }
}
