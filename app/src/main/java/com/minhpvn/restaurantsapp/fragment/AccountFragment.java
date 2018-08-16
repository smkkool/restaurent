package com.minhpvn.restaurantsapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.login.LoginManager;
import com.minhpvn.restaurantsapp.R;
import com.minhpvn.restaurantsapp.activity.CheckNetworkActivity;
import com.minhpvn.restaurantsapp.activity.SliderScreen.SliderScreenActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountFragment extends Fragment {
    @BindView(R.id.btnLogOut) Button btnLogOut;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.btnLogOut)
    public void onViewClicked() {
        LoginManager.getInstance().logOut();
        Intent i = new Intent(getActivity(), SliderScreenActivity.class);
        startActivity(i);
        if(getActivity()!=null)
        getActivity().finish();
    }
}
