package com.jzdtl.anywhere.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.db.UserEntity;
import com.jzdtl.anywhere.db.UserEntityDao;
import com.jzdtl.anywhere.utils.ActivityManager;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;

public class ModifyInfoActivity extends BaseActivity implements OptionsPickerView.OnOptionsSelectListener, OnOperItemClickL {
    private static final String TAG = "ModifyInfoActivity";
    private static final int CAMERA_CODE = 5;
    @BindView(R.id.toolbar_image)
    ImageView toolbarImage;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image_modify_go)
    ImageView imageModifyGo;
    @BindView(R.id.edit_modify_nickname)
    EditText editModifyNickname;
    @BindView(R.id.text_modify_sex)
    TextView textModifySex;
    @BindView(R.id.edit_modify_email)
    EditText editModifyEmail;
    @BindView(R.id.layout_modify_head)
    RelativeLayout layoutModifyHead;
    @BindView(R.id.image_modify_head)
    ImageView imageModifyHead;
    private OptionsPickerView mOptionsPickerView;
    private ArrayList<String> options1Items = new ArrayList<>();
    private UserEntityDao userEntityDao;
    private ActionSheetDialog dialog;
    private String headUrl = "";
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initData();
        initPickView();
    }

    private void initData() {
        userEntityDao = mDaoSession.getUserEntityDao();
        List<UserEntity> listByUser = userEntityDao.queryBuilder().where(UserEntityDao.Properties.UserName.eq(spUtils.getString("username"))).list();
        if (listByUser != null && listByUser.size() > 0){
            UserEntity userEntity = listByUser.get(0);
            headUrl = userEntity.getHead();
            if (headUrl.equals("")) {
                Glide.with(this).load(R.mipmap.yuntu_logo).into(imageModifyHead);
            } else {
                Glide.with(this).load(headUrl).into(imageModifyHead);
            }
            editModifyNickname.setText(userEntity.getNickName());
            textModifySex.setText(userEntity.getSex());
            editModifyEmail.setText(userEntity.getEmail());
        }
        toolbarImage.setImageResource(R.mipmap.back_icon);
        toolbarTitle.setText("修改信息");
        toolbarSubtitle.setVisibility(View.VISIBLE);
        toolbarSubtitle.setText("确认");
    }

    private void initPickView() {
        //选项选择器
        mOptionsPickerView = new OptionsPickerView(this);
        //选项
        options1Items.add("男");
        options1Items.add("女");
        mOptionsPickerView.setPicker(options1Items);
        mOptionsPickerView.setCyclic(false);
        mOptionsPickerView.setOnoptionsSelectListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modifyinfo;
    }

    @Override
    public void onOptionsSelect(int options1, int option2, int options3) {
        textModifySex.setText(options1Items.get(options1));
    }

    @OnClick({R.id.layout_modify_head, R.id.toolbar_image, R.id.toolbar_subtitle, R.id.image_modify_go, R.id.text_modify_sex})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_modify_head:
                dialog = new ActionSheetDialog(this, new String[]{"拍摄图片", "从相册选择"}, null);
                dialog.isTitleShow(false).show();
                dialog.setOnOperItemClickL(this);
                break;
            case R.id.toolbar_image:
                ActivityManager.finishActivity(this);
                break;
            case R.id.toolbar_subtitle:
                List<UserEntity> result = userEntityDao.queryBuilder().where(UserEntityDao.Properties.UserName.eq(spUtils.getString("username", ""))).list();
                UserEntity entity = result.get(0);
                entity.setHead(headUrl);
                entity.setNickName(editModifyNickname.getText().toString());
                entity.setSex(textModifySex.getText().toString());
                entity.setEmail(editModifyEmail.getText().toString());
                userEntityDao.update(entity);
                ActivityManager.finishActivity(this);
                EventBus.getDefault().post(entity);
                break;
            case R.id.text_modify_sex:
                mOptionsPickerView.show();
                break;
        }
    }

    @Override
    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                //设置拍照的行为
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = getStorageFile();
                Log.i(TAG, "onOperItemClick: "+file.getPath().toString());
                //设置多媒体的输出路径 ，参数2:MediaStore是Provider类提供，给出的地址必须是Uri,自己存储的是文件，所以文件和Uri转化
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(intent, CAMERA_CODE);
                break;
            case 1:
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(false)
                        .setShowGif(true)
                        .setPreviewEnabled(true)
                        .start(this, PhotoPicker.REQUEST_CODE);
                break;
        }
        dialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                headUrl = photos.get(0);
                Glide.with(this).load(headUrl).into(imageModifyHead);
            }
        }else if (resultCode == RESULT_OK && requestCode == CAMERA_CODE){
                headUrl = file.getAbsolutePath();
                Glide.with(this).load(headUrl).into(imageModifyHead);
        }
    }
}
