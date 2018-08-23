package com.minhpvn.restaurantsapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minhpvn.restaurantsapp.R;
import com.minhpvn.restaurantsapp.fragment.adapter.HistoryAdapter;
import com.minhpvn.restaurantsapp.model.realmObject.SaveMap;
import com.minhpvn.restaurantsapp.ultil.RealmController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HistoryFragment extends Fragment {
    List<SaveMap> saveMapList = new ArrayList<>();
    @BindView(R.id.rcvHistory) RecyclerView rcvHistory;
    HistoryAdapter historyAdapter;

    private SaveMap saveMap;

    public void refreshData() {
        if (historyAdapter != null && saveMapList.size() > 0) {
            historyAdapter.notifyDataSetChanged();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        ButterKnife.bind(this, view);
//        setHasOptionsMenu(true);
        saveMapList = RealmController.with(this).getMapItemSortByDate();
        initViews();
        return view;
    }

    private void initViews() {
        if (saveMapList != null) {


            historyAdapter = new HistoryAdapter(getApplicationContext(), saveMapList, new HistoryAdapter.OnItemClick() {
                @Override
                public void onClick(SaveMap item) {

                }
            });
            rcvHistory.setAdapter(historyAdapter);
            rcvHistory.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
