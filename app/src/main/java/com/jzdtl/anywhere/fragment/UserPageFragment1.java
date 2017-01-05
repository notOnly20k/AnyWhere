package com.jzdtl.anywhere.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.adapter.UserPageAdapter;
import com.jzdtl.anywhere.bean.UserActivitiesResult;
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

/**
 * Created by cz on 2017/1/3.
 */

public class UserPageFragment1 extends Fragment {
    @BindView(R.id.rec_userpage1)
    RecyclerView recUserpage1;
    private List<UserActivitiesResult.DataBean> list;
    private String id;
    private UserPageAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
            download(id);
        }
        View view = inflater.inflate(R.layout.fragment_useroage1, container, false);
        ButterKnife.bind(this, view);
        initRec();
        return view;
    }

    private void initRec() {
        adapter = new UserPageAdapter(getContext(),list,getActivity());
        recUserpage1.setLayoutManager(new LinearLayoutManager(getContext()));
        recUserpage1.setAdapter(adapter);
    }

    private void download(String id) {
        list = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.YUNYOU_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getUserActivitiesResult(id, "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserActivitiesResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "请检查网络连接", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(UserActivitiesResult userActivitiesResult) {
                        for (int i = 0; i < userActivitiesResult.getData().size(); i++) {
                            list.add(userActivitiesResult.getData().get(i));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

}
