package com.minhpvn.restaurantsapp;


import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.minhpvn.restaurantsapp.fragment.ContainerFragment;

import java.util.List;

public class BaseActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;


    @Override
    public void onBackPressed() {

        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();

        boolean isBackPressFragment = false;
        for (Fragment f : fragmentList) {
            if (f instanceof BaseFragment) {
                isBackPressFragment = ((BaseFragment) f).onBackPressed();

                if (isBackPressFragment) {
                    break;
                }
            }
        }
        if (!isBackPressFragment) {
            if (getContainerFragment() != null && getContainerFragment().getViewPager().getCurrentItem() != 0) {
                getContainerFragment().getViewPager().setCurrentItem(0);
                getContainerFragment().getBottomNavigationView().setSelectedItemId(R.id.menu_newfeed);
                return;
            }
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                getSupportFragmentManager().popBackStackImmediate(getSupportFragmentManager().getBackStackEntryCount() - 1, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Nhấn Back một lần nữa để thoát ứng dụng", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }

    }

    public ContainerFragment getContainerFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("containerFragment");
        if (fragment != null && fragment instanceof ContainerFragment) {
            return (ContainerFragment) fragment;
        }
        return null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "abc" +
                "", Toast.LENGTH_SHORT).show();
    }
}
