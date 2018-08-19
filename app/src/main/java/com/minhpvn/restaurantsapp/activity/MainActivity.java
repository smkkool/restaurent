package com.minhpvn.restaurantsapp.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window w = getWindow();
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }



        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
                setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
            }
            if (Build.VERSION.SDK_INT >= 19) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
            //make fully Android Transparent Status bar
            if (Build.VERSION.SDK_INT >= 21) {
                setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
        }


        containerFragment = new ContainerFragment();
        strName = getIntent().getExtras().getString("name");
        strId = getIntent().getExtras().getString("id");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left, R.anim.pull_in_left, R.anim.push_out_right);
        if (containerFragment != null) {

            ft.replace(R.id.fl_container, containerFragment, "containerFragment").commitAllowingStateLoss();
        }


    }
    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    public void addFragmentWithTag(Fragment targetFragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left, R.anim.pull_in_left, R.anim.push_out_right);
        if (targetFragment != null) {
            ft.addToBackStack(null);
            ft.add(R.id.fl_container, targetFragment, tag).commitAllowingStateLoss();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
