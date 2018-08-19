package com.minhpvn.restaurantsapp;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.minhpvn.restaurantsapp.fragment.ContainerFragment;

public class BaseFragment extends Fragment {
    /**
     * Could handle back press.
     *
     * @return true if back press was handled
     */
    public boolean onBackPressed() {
        return false;
    }





}
