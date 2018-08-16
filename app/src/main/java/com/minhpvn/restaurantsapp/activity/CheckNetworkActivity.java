package com.minhpvn.restaurantsapp.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.minhpvn.restaurantsapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckNetworkActivity extends AppCompatActivity {
    @BindView(R.id.tvNetwork) TextView tvNetwork;
    @BindView(R.id.pbNetwork) ProgressBar pbNetwork;
    @BindView(R.id.tvTryAgain) TextView tvTryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_check_network);
        ButterKnife.bind(this);

        if (isOnline()) {
            tvNetwork.setText("Mạng sẵn sàng");
            pbNetwork.setVisibility(View.GONE);
            tvTryAgain.setVisibility(View.GONE);
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        } else {
            tvNetwork.setText("Không có kết nối mạng. Vui lòng thử lại sau");
            pbNetwork.setVisibility(View.GONE);
            tvTryAgain.setVisibility(View.VISIBLE);
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @OnClick(R.id.tvTryAgain)
    public void onViewClicked() {
        Intent i = new Intent(this, CheckNetworkActivity.class);
        startActivity(i);
        finish();
    }
}
