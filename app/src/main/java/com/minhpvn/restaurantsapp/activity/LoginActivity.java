package com.minhpvn.restaurantsapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.minhpvn.restaurantsapp.R;
import com.minhpvn.restaurantsapp.ultil.MyBounceInterpolator;
import com.minhpvn.restaurantsapp.ultil.PreferenceUtils;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    LoginButton loginButton;
    CallbackManager callbackManager;
    String email, birthday;
    @BindView(R.id.tv_radio_setting_login) TextView tvRadioSettingLogin;
    Animation pulseTop;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
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
        }

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        // If using in a fragment
//        loginButton.setFragment(this);


        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d("onSuccess", loginResult + "");
                GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {
                                if (response.getError() != null) {
                                    // handle error
                                } else {
                                    loginButton.setVisibility(View.GONE);
                                    // get email and id of the user
                                    String name = me.optString("name");
                                    String id = me.optString("id");
                                    PreferenceUtils.saveFBData(LoginActivity.this, name, id);

                                    Bundle bundle = new Bundle();
                                    bundle.putString("name", name);
                                    bundle.putString("id", id);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }).executeAsync();


            }

            @Override
            public void onCancel() {
                // App code
                Log.d("onCancel", "");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("onError", exception + "");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
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
                        tvRadioSettingLogin.clearAnimation();
                        tvRadioSettingLogin.setBackground(getResources().getDrawable(R.drawable.bg_button_new_gradient_three));
                        tvRadioSettingLogin.setText("Login Success");
                        Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                        // App code
                        // App code
                        GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject me, GraphResponse response) {
                                        if (response.getError() != null) {
                                            // handle error
                                        } else {
                                            loginButton.setVisibility(View.GONE);
                                            // get email and id of the user
                                            String name = me.optString("name");
                                            String id = me.optString("id");
                                            PreferenceUtils.saveFBData(LoginActivity.this, name, id);

                                            Bundle bundle = new Bundle();
                                            bundle.putString("name", name);
                                            bundle.putString("id", id);
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }).executeAsync();
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
}
