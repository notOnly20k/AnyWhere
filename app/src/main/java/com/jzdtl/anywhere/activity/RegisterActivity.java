package com.jzdtl.anywhere.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.constants.Constant;

import de.hdodenhof.circleimageview.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends BaseActivity {
    private static final String TAG = "RegisterActivity";
    @BindView(R.id.button_register_reg)
    Button btnRegister;
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
    @BindView(R.id.radio_register_sex)
    RadioGroup radioRegisterSex;
    @BindView(R.id.text_register_phone)
    TextInputEditText textRegisterPhone;
    @BindView(R.id.button_register_code)
    Button buttonRegisterCode;
    private EventHandler eh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initEvents();
        smsRegister();
    }

    private void initEvents() {
        textRegisterNick.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().matches(Constant.YUNYOU_LENGTH_FORMAT)){
                    textRegisterNick.setError("请输入3-16个字符");
                }
            }
        });
        textRegisterUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().matches(Constant.YUNYOU_LENGTH_FORMAT)){
                    textRegisterUser.setError("请输入3-16个字符");
                }
            }
        });
        textRegisterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().matches(Constant.YUNYOU_LENGTH_FORMAT)){
                    textRegisterPassword.setError("请输入3-16个字符");
                }
            }
        });
        textRegisterEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().matches(Constant.YUNYOU_EMAIL_FORMAT)){
                    textRegisterEmail.setError("非法邮箱");
                }
            }
        });
        textRegisterPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().matches(Constant.YUNYOU_PHONE_FORMAT)){
                    textRegisterPhone.setError("非法手机号码");
                }
            }
        });
        buttonRegisterCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CountDownTimer(60000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        buttonRegisterCode.setText("剩余" + (millisUntilFinished / 1000) + "S");
                        buttonRegisterCode.setTextColor(getResources().getColor(R.color.gray_bfbfbf));
                        buttonRegisterCode.setEnabled(false);
                    }
                    @Override
                    public void onFinish() {
                        buttonRegisterCode.setText("获取验证码");
                        buttonRegisterCode.setTextColor(getResources().getColor(R.color.white_fffffff));
                        buttonRegisterCode.setEnabled(true);
                    }
                }.start();
            }
        });
    }

    private void smsRegister() {
        eh = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表

                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @OnClick(R.id.button_register_reg)
    public void onClick() {
        SMSSDK.getVerificationCode("86", "18382470226");
        Log.i(TAG, "onClick: 点击了");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }
}
