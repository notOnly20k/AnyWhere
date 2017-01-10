package com.jzdtl.anywhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.utils.ActivityManager;

import de.hdodenhof.circleimageview.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartActivity extends BaseActivity {

    @BindView(R.id.text_start_name)
    TextView textStartName;
    @BindView(R.id.image_start_pic)
    CircleImageView imageStartPic;

    private TranslateAnimation ta ;
    private TranslateAnimation ta1 ;
    private AlphaAnimation al;
    private AnimationSet as1;
    private AnimationSet as2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        al = new AlphaAnimation(0.5f,1);
        al.setDuration(1500);
        ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,
                Animation.RELATIVE_TO_SELF,-3f,Animation.RELATIVE_TO_SELF,0.1f);
        ta.setDuration(1500);
        ta1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,-5f,Animation.RELATIVE_TO_SELF,0,
                Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0);
        ta1.setDuration(1500);
        as1 = new AnimationSet(false);
        as2 = new AnimationSet(false);
        as1.addAnimation(ta);
        as1.addAnimation(al);

        as2.addAnimation(ta1);
        as2.addAnimation(al);

        imageStartPic.setAnimation(as1);
        textStartName.setAnimation(as2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ActivityManager.startActivity(StartActivity.this, new Intent(StartActivity.this, MainActivity.class));
                ActivityManager.finishActivity(StartActivity.this);
            }
        }).start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start;
    }
}
