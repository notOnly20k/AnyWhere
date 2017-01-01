package com.jzdtl.anywhere.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.db.DaoMaster;
import com.jzdtl.anywhere.db.DaoSession;
import com.jzdtl.anywhere.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

/**
 * Created by gcy on 2016/12/27.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    private ImageView mToolbarImage;
    private TextView mToolbarTitle;
    private TextView mToolbarSubTitle;
    private Toolbar mToolbar;
    public SPUtils spUtils;
    public DaoSession mDaoSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        spUtils = new SPUtils(this,"config_file");
        initViews();
        initDatabase();
        if (mToolbar != null) {
            //将Toolbar显示到界面
            setSupportActionBar(mToolbar);
        }
        if (mToolbarTitle != null) {
            //getTitle()的值是activity的android:lable属性值
            mToolbarTitle.setText(getTitle());
            //设置默认的标题不显示
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        EventBus.getDefault().register(this);
    }

    protected void initViews(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarImage = (ImageView) findViewById(R.id.toolbar_image);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarSubTitle = (TextView) findViewById(R.id.toolbar_subtitle);
    }

    protected void initDatabase(){
        mDaoSession = DaoMaster.newDevSession(this,"yuntu.db");
    }
    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
    @Override
    protected void onStart() {
        super.onStart();
        /**
         * 判断是否有Toolbar,并默认显示返回按钮
         */
        if(null != getToolbar() && isShowMenu()){
            showMenu();
        }
    }

    /**
     * 获取头部标题的TextView
     * @return
     */
    public TextView getToolbarTitle(){
        return mToolbarTitle;
    }
    /**
     * 获取头部标题的TextView
     * @return
     */
    public TextView getSubTitle(){
        return mToolbarSubTitle;
    }

    /**
     * 设置头部标题
     * @param title
     */
    public void setToolBarTitle(CharSequence title) {
        if(mToolbarTitle != null){
            mToolbarTitle.setText(title);
        }else{
            getToolbar().setTitle(title);
            setSupportActionBar(getToolbar());
        }
    }
    /**
     * this Activity of tool bar.
     * 获取头部.
     * @return support.v7.widget.Toolbar.
     */
    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    /**
     * 版本号小于21的后退按钮图片
     */
    private void showMenu(){
//        //setNavigationIcon必须在setSupportActionBar(toolbar);方法后面加入
//        getToolbar().setNavigationIcon(R.mipmap.icon_back);
//        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
    }

    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     * @return
     */
    protected boolean isShowMenu(){
        return true;
    }

    /**
     * this activity layout res
     * 设置layout布局,在子类重写该方法.
     * @return res layout xml id
     */
    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy...");
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetLoginInfo(Object object) {

    }
    //创建存储图片的文件
    public File getStorageFile() {
        File imgPath = null;
        //判断sd卡存在
        File root = getExternalFilesDir(null);
        //创建目录
        File directory = new File(root, "camera");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        //创建文件
        imgPath = new File(directory, System.currentTimeMillis() + ".jpg");
        Log.i(TAG, "getStorageFile: "+imgPath);
        return imgPath;
    }
}
