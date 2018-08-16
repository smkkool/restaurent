package com.minhpvn.restaurantsapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.minhpvn.restaurantsapp.BaseActivity;
import com.minhpvn.restaurantsapp.R;
import com.minhpvn.restaurantsapp.fragment.ContainerFragment;
import com.minhpvn.restaurantsapp.ultil.APIInterface;
import com.minhpvn.restaurantsapp.ultil.ApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    String strId, strName;
    ContainerFragment containerFragment;
    @BindView(R.id.fl_container) FrameLayout flContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        containerFragment = new ContainerFragment();
        strName = getIntent().getExtras().getString("name");
        strId = getIntent().getExtras().getString("id");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left, R.anim.pull_in_left, R.anim.push_out_right);
        if (containerFragment != null) {

            ft.replace(R.id.fl_container, containerFragment, "containerFragment").commitAllowingStateLoss();
        }


    }

    public void addFragmentWithTag(Fragment targetFragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left, R.anim.pull_in_left, R.anim.push_out_right);
        if (targetFragment != null) {
            ft.addToBackStack(null);
            ft.add(R.id.fl_container, targetFragment, tag).commitAllowingStateLoss();
        }
    }
}
