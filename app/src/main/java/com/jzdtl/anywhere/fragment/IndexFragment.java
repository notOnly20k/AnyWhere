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
import com.jzdtl.anywhere.adapter.IndexAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends Fragment {

    @BindView(R.id.recycler_index)
    RecyclerView recyclerView;
    private Context context;
    private View view;


    public IndexFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_index, container, false);
        ButterKnife.bind(this,view);
        context = getContext();
        initAdapter();
        downLoad();
        return view;
    }

    private void initAdapter() {
        List<String>list=new ArrayList<>();
        IndexAdapter adapter=new IndexAdapter(context,list);
        LinearLayoutManager manager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void downLoad() {
    }

}
