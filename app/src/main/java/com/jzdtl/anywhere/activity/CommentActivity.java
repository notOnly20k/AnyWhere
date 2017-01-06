package com.jzdtl.anywhere.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.adapter.CommentAdapter;
import com.jzdtl.anywhere.bean.CommentsResult;
import com.jzdtl.anywhere.callback.ApiService;
import com.jzdtl.anywhere.constants.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CommentActivity extends BaseActivity {

    @BindView(R.id.toolbar_image)
    ImageView toolbarImage;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rec_comment)
    RecyclerView recComment;
    @BindView(R.id.tv_comment)
    EditText tvComment;
    @BindView(R.id.btn_comment)
    ImageButton btnComment;
    @BindView(R.id.activity_comment)
    LinearLayout activityComment;
    private String mUserid;
    private List<CommentsResult.DataBean>list=new ArrayList<>();
    private CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTitle();
        Log.e("comment", "onCreate: "+mUserid);
        initRec();
        download(mUserid);
    }

    private void initTitle() {
        setToolBarTitle("评论");
        toolbarSubtitle.setVisibility(View.GONE);
        toolbarImage.setImageResource(R.mipmap.back_icon);
        toolbarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        int id=getIntent().getIntExtra("id",1);
        mUserid=id+"";
    }

    private void download(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.YUNYOU_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getCommentResult("UserActivity",id,"1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommentsResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CommentsResult commentsResult) {
                        for (int i = 0; i < commentsResult.getData().size(); i++) {
                            list.add(commentsResult.getData().get(i));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void initRec() {
        adapter = new CommentAdapter(list,this);
        recComment.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
        recComment.setAdapter(adapter);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }
}
