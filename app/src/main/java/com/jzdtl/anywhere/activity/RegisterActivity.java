package com.jzdtl.anywhere.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.jzdtl.anywhere.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.et_register_email)
    TextInputEditText etRegisterEmail;
    @BindView(R.id.et_register_psw)
    TextInputEditText etRegisterPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

}
