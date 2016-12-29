package com.jzdtl.anywhere.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.utils.ActivityManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.toolbar_image)
    ImageView toolbarImage;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_feedback_content)
    EditText editFeedbackContent;
    @BindView(R.id.edit_feedback_contact)
    EditText editFeedbackContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        toolbarImage.setImageResource(R.mipmap.back_icon);
        toolbarTitle.setText("意见反馈");
        toolbarSubtitle.setVisibility(View.VISIBLE);
        toolbarSubtitle.setText("发送");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @OnClick({R.id.toolbar_image, R.id.toolbar_subtitle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_image:
                ActivityManager.finishActivity(this);
                break;
            case R.id.toolbar_subtitle:
                if (editFeedbackContent.getText().toString().trim()!=null){
                    Snackbar.make(editFeedbackContact,"提交成功",Snackbar.LENGTH_SHORT).show();
                    ActivityManager.finishActivity(this);
                }
                break;
        }
    }
}
