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
import com.jzdtl.anywhere.bean.UserActivitiesResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cz on 2017/1/3.
 */

public class UserPageFragment2 extends Fragment {
    @BindView(R.id.rec_userpage2)
    RecyclerView recUserpage2;
    private ArrayList<String>path=new ArrayList<>();
    private String id;
    private UserPageFragment2Adapter adapter;
    private List<UserActivitiesResult.DataBean.ContentsBean>mlist=new ArrayList<>();
    private UserActivitiesResult.DataBean.ContentsBean bean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_useroage2, container, false);
        ButterKnife.bind(this, view);
        initRec();
        EventBus.getDefault().register(this);
        return view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvenMain(List<UserActivitiesResult.DataBean>list){
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getContents().size(); j++) {
//                Log.e("log", "onEv enMain: "+bean.getPhoto_url() );
                path.add(list.get(i).getContents().get(j).getPhoto_url());
            }
        }
        Log.e("log", "onEv enMain: "+path.toString());
        adapter.notifyDataSetChanged();
    }



    private void initRec() {
        adapter = new UserPageFragment2Adapter(path,getContext(),getActivity());
        recUserpage2.setLayoutManager(new GridLayoutManager(getContext(),3));
        recUserpage2.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
