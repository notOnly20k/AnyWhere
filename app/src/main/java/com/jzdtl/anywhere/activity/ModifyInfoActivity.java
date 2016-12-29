package com.jzdtl.anywhere.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.db.UserEntity;
import com.jzdtl.anywhere.db.UserEntityDao;
import com.jzdtl.anywhere.utils.ActivityManager;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyInfoActivity extends BaseActivity implements OptionsPickerView.OnOptionsSelectListener {
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
    @BindView(R.id.image_modify_head)
    CircleImageView imageModifyHead;
    private OptionsPickerView mOptionsPickerView;
    private ArrayList<String> options1Items = new ArrayList<>();
    private UserEntityDao userEntityDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initData();
        initPickView();
    }

    private void initData() {
        userEntityDao = mDaoSession.getUserEntityDao();
        toolbarImage.setImageResource(R.mipmap.back_icon);
        toolbarTitle.setText("修改信息");
        toolbarSubtitle.setVisibility(View.VISIBLE);
        toolbarSubtitle.setText("确认");
        String imageUrl = spUtils.getString("head","");
        if (imageUrl.equals("")) {
            Glide.with(this).load(R.mipmap.yuntu_logo).into(imageModifyHead);
        }else {
            Glide.with(this).load(imageUrl).into(imageModifyHead);
        }
        editModifyNickname.setText(spUtils.getString("nickname"));
        textModifySex.setText(spUtils.getString("sex"));
        editModifyEmail.setText(spUtils.getString("email"));
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

    @OnClick({R.id.image_modify_head,R.id.toolbar_image, R.id.toolbar_subtitle, R.id.image_modify_go, R.id.text_modify_sex})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_modify_head:
                break;
            case R.id.toolbar_image:
                ActivityManager.finishActivity(this);
                break;
            case R.id.toolbar_subtitle:
//                spUtils.putString("head",userEntity.getHead());
                spUtils.putString("nickname", editModifyNickname.getText().toString());
                spUtils.putString("sex",textModifySex.getText().toString());
                spUtils.putString("email",editModifyEmail.getText().toString());
                List<UserEntity> result = userEntityDao.queryBuilder().where(UserEntityDao.Properties.UserName.eq(spUtils.getString("username",""))).list();
                UserEntity entity = result.get(0);
                entity.setNickName(editModifyNickname.getText().toString());
                entity.setSex(textModifySex.getText().toString());
                entity.setEmail(editModifyEmail.getText().toString());
                userEntityDao.update(entity);
                ActivityManager.finishActivity(this);
                break;
            case R.id.image_modify_go:
                break;
            case R.id.text_modify_sex:
                mOptionsPickerView.show();
                break;
        }
    }
}
