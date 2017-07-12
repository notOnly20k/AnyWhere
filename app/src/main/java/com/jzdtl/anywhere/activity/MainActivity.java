package com.jzdtl.anywhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.db.UserEntity;
import com.jzdtl.anywhere.db.UserEntityDao;
import com.jzdtl.anywhere.fragment.ActivitiesFragment;
import com.jzdtl.anywhere.fragment.IndexFragment;
import com.jzdtl.anywhere.fragment.NearbyFragment;
import com.jzdtl.anywhere.utils.ActivityManager;
import com.jzdtl.anywhere.views.ItemView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, SlidingPaneLayout.PanelSlideListener, DrawerLayout.DrawerListener {
    private static final int LOGIN_CODE = 0;
    private static final int MODIFY_CODE = 1;
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
    private static final String TAG = "MainActivity";
    @BindView(R.id.slide_main_menu)
    DrawerLayout slideMainMenu;
//    SlidingPaneLayout slideMainMenu;
    private boolean login;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private IndexFragment mIndexFragment;
    private ActivitiesFragment mActivitiesFragment;
    private NearbyFragment mNearbyFragment;
    private UserEntityDao userEntityDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initDatas();
        checkLogin();
        initEvents();
        radioButtonHome.setChecked(true);
        updateUI();
    }

    private void initDatas() {
        userEntityDao = mDaoSession.getUserEntityDao();
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
        slideMainMenu.addDrawerListener(this);
//        slideMainMenu.setPanelSlideListener(this);
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

    @OnClick({R.id.image_view_head, R.id.relative_main_register, R.id.layout_user_login_true, R.id.layout_user_login_false,
            R.id.image_main_activity, R.id.image_main_like, R.id.image_main_feedback, R.id.image_main_set, R.id.toolbar_subtitle, R.id.toolbar_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_view_head:
            case R.id.layout_user_login_true:
                if (login) {
                    ActivityManager.startActivity(this, new Intent(this, ModifyInfoActivity.class));
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
                ActivityManager.startActivity(this, new Intent(this, FeedBackActivity.class));
                break;
            case R.id.image_main_set:
                ActivityManager.startActivity(this, new Intent(this, SettingActivity.class));
                break;
            case R.id.toolbar_subtitle:
                ActivityManager.startActivity(this, new Intent(this, WriteActivity.class));
                break;
            case R.id.toolbar_image:
//                if (slideMainMenu.isOpen()){
//                    slideMainMenu.closePane();
//                }else {
//                    slideMainMenu.openPane();
//                }

                if(slideMainMenu.isDrawerOpen(GravityCompat.START)){
                    slideMainMenu.closeDrawer(GravityCompat.START);
                }else{
                    slideMainMenu.openDrawer(GravityCompat.START);
                }

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
        Log.i(TAG, "onResume: " + userEntity.toString());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetLoginInfo(String loginOut) {
        updateUI();
    }

    private void updateUI() {
        login = spUtils.getBoolean("login", false);
        if (login) {
            layoutUserLoginFalse.setVisibility(View.GONE);
            layoutUserLoginTrue.setVisibility(View.VISIBLE);
            String username = spUtils.getString("username", "");
            List<UserEntity> entityList = userEntityDao.queryBuilder().where(UserEntityDao.Properties.UserName.eq(username)).list();
            if (entityList != null && entityList.size() > 0) {
                UserEntity userEntity = entityList.get(0);
                String imageUrl = userEntity.getHead();
                if (imageUrl.equals("")) {
                    Glide.with(this).load(R.mipmap.yuntu_logo).into(imageViewHead);
                } else {
                    Glide.with(this).load(imageUrl).into(imageViewHead);
                }
                String nickName = userEntity.getNickName();
                textViewNameTrue.setText(nickName);
                //TODO 添加关注数量  粉丝数量
                textViewFollowerTrue.setText("800万粉丝");
                textViewFollowingTrue.setText("1关注");
                Log.i(TAG, "onResume: " + userEntity.toString());
            }
        } else {
            layoutUserLoginFalse.setVisibility(View.VISIBLE);
            layoutUserLoginTrue.setVisibility(View.GONE);
            Glide.with(this).load(R.mipmap.yuntu_logo).into(imageViewHead);
            //TODO 设置收藏数和游记数等
        }
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        panel.setAlpha(1-slideOffset*0.2f);
        toolbarImage.setRotation(360f*slideOffset);
    }

    @Override
    public void onPanelOpened(View panel) {

    }

    @Override
    public void onPanelClosed(View panel) {

    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
//        drawerView.setAlpha(slideOffset*0.2f);
        toolbarImage.setRotation(360f*slideOffset);
    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
