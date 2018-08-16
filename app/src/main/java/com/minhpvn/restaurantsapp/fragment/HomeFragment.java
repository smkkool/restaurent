package com.minhpvn.restaurantsapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.login.widget.ProfilePictureView;
import com.minhpvn.restaurantsapp.R;
import com.minhpvn.restaurantsapp.fragment.adapter.HomeAdapter;
import com.minhpvn.restaurantsapp.model.MultipleResources;
import com.minhpvn.restaurantsapp.model.UserList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HomeFragment extends Fragment implements HomeContract.View {
    @BindView(R.id.rcv1) RecyclerView rcv1;
    @BindView(R.id.imgProfile) ProfilePictureView imgProfile;
    private HomeContract.Presenter mPresenter;
    private HomeAdapter homeAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        mPresenter = new HomePresenter(this);
//        mPresenter.doGetListResources();
//        mPresenter.doCreateUserWithField("minhpvn","leader");
        mPresenter.doGetUserList(2);
        imgProfile.setProfileId("2076095055735083");
        return view;
    }

    @Override
    public void doGetListResourcesSuccess(MultipleResources response) {
        Toast.makeText(getContext(), "thành công", Toast.LENGTH_SHORT).show();
        String displayResponse = "";

        MultipleResources resource = response;
        Integer text = resource.page;
        Integer total = resource.total;
        Integer totalPages = resource.totalPages;
        List<MultipleResources.Datum> datumList = resource.data;

        displayResponse += text + " Page\n" + total + " Total\n" + totalPages + " Total Pages\n";

        for (MultipleResources.Datum datum : datumList) {
            displayResponse += datum.id + " " + datum.name + " " + datum.pantoneValue + " " + datum.year + "\n";
        }
    }

    @Override
    public void doGetListResourcesError() {
        Toast.makeText(getContext(), "có lỗi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doCreateUserWithFieldSuccess(UserList response) {
        Integer text = response.page;
        Integer total = response.total;
        Integer totalPages = response.totalPages;
        List<UserList.Datum> datumList = response.data;
        Toast.makeText(getApplicationContext(), text + " page\n" + total + " total\n" + totalPages + " totalPages\n", Toast.LENGTH_SHORT).show();

        for (UserList.Datum datum : datumList) {
            Toast.makeText(getApplicationContext(), "id : " + datum.id + " name: " + datum.first_name + " " + datum.last_name + " avatar: " + datum.avatar, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void doCreateUserWithFieldError() {

    }

    @Override
    public void doGetUserListSuccess(List<UserList.Datum> response) {
        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
        homeAdapter = new HomeAdapter(getApplicationContext(), response);
        rcv1.setAdapter(homeAdapter);
        rcv1.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
    }

    @Override
    public void doGetUserListError() {
        Toast.makeText(getApplicationContext(), "errr", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.doGetUserList(2);
    }
}
