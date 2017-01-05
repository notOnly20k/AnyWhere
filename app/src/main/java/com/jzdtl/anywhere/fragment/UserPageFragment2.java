package com.jzdtl.anywhere.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.adapter.UserPageFragment2Adapter;
import com.jzdtl.anywhere.bean.UserGroupNoActivitiesResult;
import com.jzdtl.anywhere.callback.ApiService;
import com.jzdtl.anywhere.constants.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.baidu.location.d.a.i;

/**
 * Created by cz on 2017/1/3.
 */

public class UserPageFragment2 extends Fragment {
    @BindView(R.id.rec_userpage2)
    RecyclerView recUserpage2;
    private ArrayList<String>list;
    private String id;
    private UserPageFragment2Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
//            Log.e("userpage1", "获取idonCreateView: "+id);
            download(id);
        }
        View view = inflater.inflate(R.layout.fragment_useroage2, container, false);
        ButterKnife.bind(this, view);
        initRec();
        return view;
    }

    private void initRec() {
        adapter = new UserPageFragment2Adapter(list,getContext(),getActivity());
        recUserpage2.setLayoutManager(new GridLayoutManager(getContext(),3));
        recUserpage2.setAdapter(adapter);
    }

    private void download(String id) {
        list = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.YUNYOU_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getUserGroupNoActivitiesResult("19772")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Subscriber<UserGroupNoActivitiesResult>() {
                   @Override
                   public void onCompleted() {

                   }

                   @Override
                   public void onError(Throwable e) {
                       Log.e("log", "失败: "+e);
                   }

                   @Override
                   public void onNext(UserGroupNoActivitiesResult userGroupNoActivitiesResult) {
//                      Log.e("log", "bean: "+userGroupNoActivitiesResult.toString());
//                       Log.e("log", "on: "+userGroupNoActivitiesResult.getData().get(1).toString() );
                       list.add(userGroupNoActivitiesResult.getData().get(1).getContents().get(i).getPhoto_url());
                               Log.e("log", "onNext: "+list );

                        adapter.notifyDataSetChanged();
                   }
               });
    }
}
