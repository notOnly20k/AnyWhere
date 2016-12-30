package com.jzdtl.anywhere.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.constants.Constant;
import com.jzdtl.anywhere.db.UserEntity;
import com.jzdtl.anywhere.db.UserEntityDao;
import com.jzdtl.anywhere.utils.ActivityManager;

import de.hdodenhof.circleimageview.CircleImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import me.iwf.photopicker.PhotoPicker;

import static butterknife.OnTextChanged.Callback.AFTER_TEXT_CHANGED;

public class RegisterActivity extends BaseActivity implements OptionsPickerView.OnOptionsSelectListener, OnOperItemClickL {
    private static final String TAG = "RegisterActivity";
    private static final int CAMERA_CODE = 5;
    @BindView(R.id.image_register_head)
    CircleImageView imageRegisterHead;
    @BindView(R.id.text_register_nick)
    TextInputEditText textRegisterNick;
    @BindView(R.id.text_register_user)
    TextInputEditText textRegisterUser;
    @BindView(R.id.text_register_password)
    TextInputEditText textRegisterPassword;
    @BindView(R.id.text_register_email)
    TextInputEditText textRegisterEmail;
    @BindView(R.id.text_register_phone)
    TextInputEditText textRegisterPhone;
    @BindView(R.id.text_register_code)
    TextView textRegisterCode;
    @BindView(R.id.text_register_check)
    EditText textRegisterCheck;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_image)
    ImageView toolbarImage;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_register_sex)
    TextView textRegisterSex;
    private EventHandler eh;
    private UserEntityDao userEntityDao;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                if (msg.arg1 == 0) {
                    Snackbar.make(toolbarSubtitle, "用户名或者手机号已经存在！", Snackbar.LENGTH_SHORT).show();
                } else if (msg.arg1 == 1) {
                    Snackbar.make(toolbarSubtitle, "注册成功！", Snackbar.LENGTH_SHORT).show();
                } else if (msg.arg1 == 2) {
                    Snackbar.make(toolbarSubtitle, "验证失败！", Snackbar.LENGTH_SHORT).show();
                }
            }
        }
    };
    private OptionsPickerView mOptionsPickerView;
    private ArrayList<String> options1Items = new ArrayList<>();
    private ActionSheetDialog dialog;
    private File file;
    private String headUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initDatas();
        smsRegister();
        initSexPicker();
    }

    private void initSexPicker() {
        //选项选择器
        mOptionsPickerView = new OptionsPickerView(this);
        //选项
        options1Items.add("男");
        options1Items.add("女");
        mOptionsPickerView.setPicker(options1Items);
        mOptionsPickerView.setCyclic(false);
        mOptionsPickerView.setOnoptionsSelectListener(this);
    }

    private void initDatas() {
        userEntityDao = mDaoSession.getUserEntityDao();
        toolbarImage.setImageResource(R.mipmap.back_icon);
        toolbarTitle.setText("注册");
        toolbarSubtitle.setText("确认");
    }

    private void smsRegister() {
        eh = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {
                Log.i(TAG, "afterEvent: ==============" + event);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        insertDatabase();
                        sendMessage(0, 1);
                        ActivityManager.finishActivity(RegisterActivity.this);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        sendMessage(0, 2);
                    }
                }
            }

        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    private void sendMessage(int what, int arg) {
        Message message = Message.obtain();
        message.what = what;
        message.arg1 = arg;
        handler.sendMessage(message);
    }

    private void insertDatabase() {
        UserEntity userEntity = new UserEntity();
        userEntity.setHead(headUrl);
        userEntity.setUserName(textRegisterUser.getText().toString());
        userEntity.setNickName(textRegisterNick.getText().toString());
        userEntity.setPassword(textRegisterPassword.getText().toString());
        userEntity.setEmail(textRegisterEmail.getText().toString());
        userEntity.setSex(textRegisterSex.getText().toString());
        userEntity.setPhoneNumber(textRegisterPhone.getText().toString());
        userEntity.setUserId(textRegisterPhone.getText().toString());
        userEntityDao.insert(userEntity);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    private boolean isDatabaseExist() {
        List<UserEntity> userList = userEntityDao.queryBuilder().where(UserEntityDao.Properties.UserName.eq(textRegisterUser.getText().toString())).list();
        if (userList != null && userList.size() > 0) {
            return false;
        }
        List<UserEntity> phoneList = userEntityDao.queryBuilder().where(UserEntityDao.Properties.PhoneNumber.eq(textRegisterPhone.getText().toString())).list();
        if (phoneList != null && phoneList.size() > 0) {
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }

    private boolean isRight() {
        boolean nickError = (textRegisterNick.getError() == null)
                && (textRegisterNick.getText().toString().trim().length() <= 16)
                && (textRegisterNick.getText().toString().trim().length() != 0);
        boolean userError = (textRegisterUser.getError() == null)
                && (textRegisterUser.getText().toString().trim().length() <= 16)
                && (textRegisterUser.getText().toString().trim().length() != 0);
        boolean passwordError = (textRegisterPassword.getError() == null)
                && (textRegisterPassword.getText().toString().trim().length() <= 16)
                && (textRegisterPassword.getText().toString().trim().length() != 0);
        boolean emailError = (textRegisterEmail.getError() == null);
        boolean phoneError = (textRegisterPhone.getError() == null)
                && (textRegisterNick.getText().toString().trim().length() != 0);
        return nickError && userError && passwordError && emailError && phoneError;
    }

    @Override
    public void onOptionsSelect(int options1, int option2, int options3) {
        textRegisterSex.setText(options1Items.get(options1));
    }

    private void setCodeButton(String string, int color, boolean enabled) {

        textRegisterCode.setText(string);
        textRegisterCode.setTextColor(color);
        textRegisterCode.setEnabled(enabled);
    }

    @OnClick({R.id.text_register_sex, R.id.image_register_head, R.id.toolbar_image, R.id.toolbar_subtitle, R.id.text_register_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_register_sex:
                mOptionsPickerView.show();
                break;
            case R.id.image_register_head:
                dialog = new ActionSheetDialog(this, new String[]{"拍摄图片", "从相册选择"}, null);
                dialog.isTitleShow(false).show();
                dialog.setOnOperItemClickL(this);
                break;
            case R.id.text_register_code:
                boolean phoneError = (textRegisterPhone.getError() == null)
                        && (textRegisterNick.getText().toString().trim().length() != 0);
                if (phoneError) {
                    SMSSDK.getVerificationCode("86", textRegisterPhone.getText().toString());
                    new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            setCodeButton("剩余" + (millisUntilFinished / 1000) + "S", getResources().getColor(R.color.gray_bfbfbf), false);
                        }

                        @Override
                        public void onFinish() {
                            setCodeButton("获取验证码", getResources().getColor(R.color.white_fffffff), true);
                        }
                    }.start();
                } else {
                    Snackbar.make(toolbarSubtitle, "手机号码有误，请检查", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.toolbar_subtitle:
                hideKeyboard();
                if (isRight()) {
                    if (isDatabaseExist()) {
                        SMSSDK.submitVerificationCode("86", textRegisterPhone.getText().toString(), textRegisterCheck.getText().toString().trim());
                    } else {
                        sendMessage(0, 0);
                    }
                } else {
                    Snackbar.make(toolbarSubtitle, "信息有误，请检查", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.toolbar_image:
                ActivityManager.finishActivity(RegisterActivity.this);
                break;
        }
    }

    @OnTextChanged(value = R.id.text_register_nick, callback = AFTER_TEXT_CHANGED)
    void onNickNameChanged(CharSequence editable) {
        if (!editable.toString().matches(Constant.YUNYOU_INVALID_FORMAT)) {
            textRegisterNick.setError("非法字符");
        }
    }

    @OnTextChanged(value = R.id.text_register_user, callback = AFTER_TEXT_CHANGED)
    void onUserChanged(CharSequence editable) {
        if (!editable.toString().matches(Constant.YUNYOU_INVALID_FORMAT)) {
            textRegisterUser.setError("非法字符");
        }
    }

    @OnTextChanged(value = R.id.text_register_email, callback = AFTER_TEXT_CHANGED)
    void onEmailChanged(CharSequence editable) {
        if (!editable.toString().equals("") && !editable.toString().matches(Constant.YUNYOU_EMAIL_FORMAT)) {
            textRegisterEmail.setError("非法邮箱");
        }
    }

    @OnTextChanged(value = R.id.text_register_phone, callback = AFTER_TEXT_CHANGED)
    void onPhoneChanged(CharSequence editable) {
        if (!editable.toString().matches(Constant.YUNYOU_PHONE_FORMAT)) {
            textRegisterPhone.setError("非法手机号码");
        }
    }

    @Override
    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                //设置拍照的行为
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = getStorageFile();
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
                Glide.with(this).load(headUrl).into(imageRegisterHead);
            }
        } else if (RESULT_OK == resultCode && requestCode == CAMERA_CODE) {
            headUrl = file.getAbsolutePath();
            Glide.with(this).load(headUrl).into(imageRegisterHead);
        }
    }
}
