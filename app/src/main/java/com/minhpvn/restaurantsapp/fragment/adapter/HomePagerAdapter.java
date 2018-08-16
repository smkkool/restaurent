package com.minhpvn.restaurantsapp.fragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.minhpvn.restaurantsapp.fragment.AccountFragment;
import com.minhpvn.restaurantsapp.fragment.DeliveryFragment;
import com.minhpvn.restaurantsapp.fragment.HomeFragment;
import com.minhpvn.restaurantsapp.fragment.RestaurentFragment;

public class HomePagerAdapter extends FragmentStatePagerAdapter {
    private HomeFragment homeFragment;
    private DeliveryFragment deliveryFragment;
    private RestaurentFragment restaurentFragment;
    private AccountFragment accountFragment;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
        homeFragment = new HomeFragment();
        deliveryFragment = new DeliveryFragment();
        restaurentFragment = new RestaurentFragment();
        accountFragment = new AccountFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return homeFragment;
            case 1:
                return deliveryFragment;
            case 2:
                return restaurentFragment;
            case 3:
                return accountFragment;
            default:
                return new Fragment();
        }
    }
    @Override
    public Fragment instantiateItem(ViewGroup container, int position) {
        Fragment f = (Fragment) super.instantiateItem(container, position);
        switch (position) {
            case 0:
                homeFragment = (HomeFragment) f;
                break;
            case 1:
                deliveryFragment = (DeliveryFragment) f;
                break;
            case 2:
                restaurentFragment = (RestaurentFragment) f;
                break;
            case 3:
                accountFragment = (AccountFragment) f;
                break;
        }
        return f;
    }
    @Override
    public int getCount() {
        return 4;
    }
}
