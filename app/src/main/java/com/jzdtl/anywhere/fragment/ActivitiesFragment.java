package com.jzdtl.anywhere.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzdtl.anywhere.R;
import com.jzdtl.anywhere.adapter.ActivitiesAdapter;
import com.jzdtl.anywhere.bean.TimeLinesResult;
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
 * A simple { @link Fragment} subclass.
 */
public class ActivitiesFragment extends Fragment {

    @BindView(R.id.recycler_activities)
    RecyclerView recyclerView;
    private Context context;
    private View view;
    private List<TimeLinesResult.DataBean.ActivityBean> list;
    private ActivitiesAdapter adapter;


    public ActivitiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_activities, container, false);
        ButterKnife.bind(this,view);
        context = getContext();
        initAdapter();
        downLoad();
        return view;
    }

    private void initAdapter() {
        list = new ArrayList<>();
        adapter = new ActivitiesAdapter(context, list,getActivity());
        LinearLayoutManager manager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void downLoad() {
        Retrofit retrofit=new Retrofit.Builder()
                            .baseUrl(Constant.YUNYOU_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
        ApiService apiService=retrofit.create(ApiService.class);
               apiService .getTimeLinesResult("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TimeLinesResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TimeLinesResult timeLinesResult) {
                        for (int i = 0; i < timeLinesResult.getData().size(); i++) {
                            list.add(timeLinesResult.getData().get(i).getActivity());
                        }
                        adapter.notifyDataSetChanged();
                    }
                });


    }

}
