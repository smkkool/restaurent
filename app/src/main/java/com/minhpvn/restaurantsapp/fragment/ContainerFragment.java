package com.minhpvn.restaurantsapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.minhpvn.restaurantsapp.R;
import com.minhpvn.restaurantsapp.ultil.MyViewPager;
import com.minhpvn.restaurantsapp.ultil.Toolbox;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContainerFragment extends Fragment {
    @BindView(R.id.bottomNavigation) BottomNavigationView bottomNavigation;
    @BindView(R.id.viewPager) MyViewPager viewPager;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private HomeFragment homeFragment;
    private HistoryFragment historyFragment;
    //    private RestaurentFragment restaurentFragment;
    private AccountFragment accountFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_container, container, false);
        ButterKnife.bind(this, view);
//        setupToolbar();
        setupViewPager();
        setupBottomBar();
        return view;
    }

    private void setupToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Trang chủ");
        setHasOptionsMenu(true);
    }

    public MyViewPager getViewPager() {
        return viewPager;
    }

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigation;
    }

    private void setupViewPager() {
        final FragmentStatePagerAdapter pagerAdapter = new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return getItemFragmentViewPager(position);
            }

            @Override
            public int getCount() {
                return 3;
            }

        };
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(pagerAdapter);
        bottomNavigation.setSelectedItemId(R.id.menu_newfeed);
        bottomNavigation.setItemIconTintList(null);
        viewPager.setCurrentItem(0, false);
        viewPager.setPagingEnabled(false);
        Toolbox.disableShiftMode(bottomNavigation);

    }

    private Fragment getItemFragmentViewPager(int position) {
        Fragment f;
        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
//                        homeFragment.setArguments(data);
                }
                f = homeFragment;
                break;
            case 1:
                if (historyFragment == null)
                    historyFragment = new HistoryFragment();
                f = historyFragment;
                break;
//            case 2:
//                if (restaurentFragment == null)
//                    restaurentFragment = new RestaurentFragment();
//                f = restaurentFragment;
//                break;
            case 2:
                if (accountFragment == null)
                    accountFragment = new AccountFragment();
                f = accountFragment;
                break;
            default:
                if (homeFragment == null)
                    homeFragment = new HomeFragment();
                f = homeFragment;
                break;
        }
        return f;
    }

    private void setupBottomBar() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_newfeed:
                        viewPager.setCurrentItem(0, false);
                        break;
                    case R.id.menu_delivery:
                        viewPager.setCurrentItem(1, false);
                        historyFragment.refreshData();
                        break;
//                    case R.id.menu_create_order:
//                        viewPager.setCurrentItem(2, false);
//                        break;
                    case R.id.menu_setting:
                        viewPager.setCurrentItem(2, false);
                        break;
                    default:
                        viewPager.setCurrentItem(0, false);
                        break;
                }
                return true;
            }
        });
    }

    public void startView(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container, fragment).commitAllowingStateLoss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
