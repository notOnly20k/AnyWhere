package com.jzdtl.anywhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.db.UserEntity;
import com.jzdtl.anywhere.db.UserEntityDao;
import com.jzdtl.anywhere.utils.ActivityManager;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.weibo.TencentWeibo;

public class LoginActivity extends BaseActivity implements PlatformActionListener {

    private static final String TAG = "LoginActivity";
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_login_user)
    TextInputEditText textLoginUser;
    @BindView(R.id.text_login_password)
    TextInputEditText textLoginPassword;
    @BindView(R.id.button_login_submit)
    Button buttonLoginSubmit;
    @BindView(R.id.toolbar_image)
    ImageView toolbarImage;
    @BindView(R.id.image_login_qq)
    RoundedImageView imageLoginQq;
    @BindView(R.id.image_login_sina)
    RoundedImageView imageLoginSina;
    @BindView(R.id.image_login_tencent)
    RoundedImageView imageLoginTencent;
    private UserEntityDao userEntityDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        toolbarImage.setImageResource(R.mipmap.back_icon);
        toolbarTitle.setText("登录");
        toolbarSubtitle.setText("注册");
        userEntityDao = mDaoSession.getUserEntityDao();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.toolbar_image, R.id.toolbar_subtitle, R.id.button_login_submit,R.id.image_login_qq,R.id.image_login_sina,R.id.image_login_tencent})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                ActivityManager.startActivity(this, new Intent(this, RegisterActivity.class));
                break;
            case R.id.button_login_submit:
                hideKeyboard();
                if (isRight()) {
                    List<UserEntity> listByUser = userEntityDao.queryBuilder().where(UserEntityDao.Properties.UserName.eq(textLoginUser.getText().toString()),
                            UserEntityDao.Properties.Password.eq(textLoginPassword.getText().toString())).list();
                    List<UserEntity> listByPhone = userEntityDao.queryBuilder().where(UserEntityDao.Properties.PhoneNumber.eq(textLoginUser.getText().toString()),
                            UserEntityDao.Properties.Password.eq(textLoginPassword.getText().toString())).list();
                    if ((listByUser == null || listByUser.size() == 0) && (listByPhone == null || listByPhone.size() == 0)) {
                        Snackbar.make(buttonLoginSubmit, "用户名或密码错误！", Snackbar.LENGTH_SHORT).show();
                    } else {
                        UserEntity userEntity;
                        if (listByUser == null || listByUser.size() == 0)
                            userEntity = listByPhone.get(0);
                        else
                            userEntity = listByUser.get(0);
                        spUtils.putBoolean("login", true);
                        spUtils.putString("username", userEntity.getUserName());
                        ActivityManager.finishActivity(this);
                        EventBus.getDefault().post(userEntity);
                    }
                } else {
                    Snackbar.make(buttonLoginSubmit, "用户名或密码错误！", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.toolbar_image:
                ActivityManager.finishActivity(this);
                break;
            case R.id.image_login_qq:
                thirdLogin(QQ.NAME);
                break;
            case R.id.image_login_tencent:
                thirdLogin(TencentWeibo.NAME);
                break;
            case R.id.image_login_sina:
                thirdLogin(SinaWeibo.NAME);
                break;
        }
    }

    private void thirdLogin(String name) {
        ShareSDK.initSDK(this);
        Platform p = ShareSDK.getPlatform(this, name);
        p.SSOSetting(false);
        p.showUser(null);
        p.setPlatformActionListener(this);
    }

    private boolean isRight() {
        boolean userError = (textLoginUser.getError() == null)
                && (textLoginUser.getText().toString().trim().length() <= 16)
                && (textLoginUser.getText().toString().trim().length() != 0);
        boolean passwordError = (textLoginPassword.getError() == null)
                && (textLoginPassword.getText().toString().trim().length() <= 16)
                && (textLoginPassword.getText().toString().trim().length() != 0);
        return userError && passwordError;
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        String icon = platform.getDb().getUserIcon();
        String name = platform.getDb().getUserName();
        String id = platform.getDb().getUserId();

        UserEntity userEntity = new UserEntity(name,"","","",name,"",icon,id);
        userEntityDao.insertOrReplace(userEntity);
        spUtils.putBoolean("login", true);
        spUtils.putString("username", userEntity.getUserName());
        ActivityManager.finishActivity(this);
        EventBus.getDefault().post(userEntity);
        Log.d(TAG, "onComplete: "+name+"============"+icon);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}
