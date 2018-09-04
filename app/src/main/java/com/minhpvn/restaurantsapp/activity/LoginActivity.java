package com.minhpvn.restaurantsapp.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.minhpvn.restaurantsapp.R;
import com.minhpvn.restaurantsapp.ultil.MyBounceInterpolator;
import com.minhpvn.restaurantsapp.ultil.PreferenceUtils;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "eeeeeeeeeeeeeer";
    LoginButton loginButton;
    CallbackManager callbackManager;
    String email, birthday;
    @BindView(R.id.tv_radio_setting_login) TextView tvRadioSettingLogin;
    Animation pulseTop;
    Handler handler = new Handler();
    @BindView(R.id.tvErr) TextView tvErr;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (!isOnline()) {
            tvErr.setVisibility(View.VISIBLE);
            return;
        }
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 30);

        pulseTop = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        pulseTop.setInterpolator(interpolator);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn) {
            Bundle bundle = new Bundle();
            bundle.putString("name", PreferenceUtils.getFBData(this).get(0));
            bundle.putString("id", PreferenceUtils.getFBData(this).get(1));
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
            return;
        }
        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();


    }


    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.tv_radio_setting_login)
    public void onViewClicked() {
        tvRadioSettingLogin.setClickable(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvRadioSettingLogin.startAnimation(pulseTop);

            }
        }, 100);
        fbLogin(getCurrentFocus());
    }

    public void fbLogin(View view) {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
//        LoginManager.getInstance().logInWithPublishPermissions(this, Arrays.asList("publish_actions"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, "err", Toast.LENGTH_SHORT).show();
                        // App code
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        // [START_EXCLUDE silent]
//        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            tvRadioSettingLogin.clearAnimation();
                            tvRadioSettingLogin.setBackground(getResources().getDrawable(R.drawable.bg_button_new_gradient_three));
                            tvRadioSettingLogin.setText("Login Success");

                            FirebaseUser user = mAuth.getCurrentUser();

                            String name = user.getDisplayName();
                            String id = user.getProviderId();


                            PreferenceUtils.saveFBData(LoginActivity.this, name, id);

                            Bundle bundle = new Bundle();
                            bundle.putString("name", name);
                            bundle.putString("id", id);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                            return;
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                    }
                });
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
