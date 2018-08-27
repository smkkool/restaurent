package com.minhpvn.restaurantsapp.activity.SliderScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minhpvn.restaurantsapp.R;
import com.minhpvn.restaurantsapp.activity.CheckNetworkActivity;
import com.minhpvn.restaurantsapp.ultil.PreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class SliderScreenActivity extends AppCompatActivity {
    @BindView(R.id.viewPagerSlider) ViewPager viewPagerSlider;
    @BindView(R.id.circleIndicator) CircleIndicator circleIndicator;
    @BindView(R.id.tvNext) TextView tvNext;
    @BindView(R.id.llFinish) LinearLayout llFinish;
    private SliderScreenAdapter sliderScreenAdapter;
    private Handler handler = new Handler();
    private Animation animationbbb, fadeIn, fadeOut;
    ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.screen_slider);

        ButterKnife.bind(this);
        fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        if (PreferenceUtils.isStartApp(this)) {
//            PreferenceUtils.saveStartApp(this);
            sliderScreenAdapter = new SliderScreenAdapter(this);
            viewPagerSlider.setAdapter(sliderScreenAdapter);
            circleIndicator.setViewPager(viewPagerSlider);
            viewPagerSlider.setPageTransformer(false, new ViewPager.PageTransformer() {
                @Override
                public void transformPage(View page, float position) {
                    Log.d("poss", position + "");
                    // Get the page index from the tag. This makes
                    // it possible to know which page index you're
                    // currently transforming - and that can be used
                    // to make some important performance improvements.
                    // int pagePosition = (int) page.getTag();

                    // Here you can do all kinds of stuff, like get the
                    // width of the page and perform calculations based
                    // on how far the user has swiped the page.
                    int pageWidth = page.getWidth();
                    float pageWidthTimesPosition = pageWidth * position;
                    float absPosition = Math.abs(position);
                    Log.d("absPosition", absPosition + "");
                    View title = page.findViewById(R.id.tvHeader);
                    View llBg = page.findViewById(R.id.llBg);
                    View description = page.findViewById(R.id.tvContent);
                    View computer = page.findViewById(R.id.imgItem);

                    // Now it's time for the effects
                    if (position <= -1.0f || position >= 1.0f) {
                        // The page is not visible. This is a good place to stop
                        // any potential work / animations you may have running.
                        llBg.setAlpha(0.0F);
                        llBg.setTranslationX(pageWidth * position);
                    } else if (position == 0.0f) {
                        llBg.setAlpha(1.0F);
                        llBg.setTranslationX(pageWidth * position);
                        // The page is selected. This is a good time to reset Views
                        // after animations as you can't always count on the PageTransformer
                        // callbacks to match up perfectly.
                    } else {

                        // The page is currently being scrolled / swiped. This is
                        // a good place to show animations that react to the user's
                        // swiping as it provides a good user experience.

                        // Let's start by animating the title.
                        // We want it to fade as it scrolls out
                        title.setAlpha(1f - absPosition);
                        title.setScaleX(1f - absPosition);
                        title.setScaleY(1f - absPosition);
                        llBg.setAlpha(1f - absPosition);
                        llBg.setTranslationX(pageWidth * -position);
                        // Now the description. We also want this one to
                        // fade, but the animation should also slowly move
                        // down and out of the screen
//            description.setTranslationX(-pageWidthTimesPosition / 2f);
                        description.setAlpha(1.0f - absPosition);
                        description.setScaleX(1f - absPosition);
                        description.setScaleY(1f - absPosition);
                        // Now, we want the image to move to the right,
                        // i.e. in the opposite direction of the rest of the
                        // content while fading out

                        // We're attempting to create an effect for a View
                        // specific to one of the pages in our ViewPager.
                        // In other words, we need to check that we're on
                        // the correct page and that the View in question
                        // isn't null.
                        if (computer != null) {
                            computer.setAlpha(1.0f - absPosition);
//                            computer.setTranslationX(pageWidthTimesPosition * 1.5f);
                            computer.setScaleX(1f - absPosition);
                            computer.setScaleY(1f - absPosition);
                        }

                        // Finally, it can be useful to know the direction
                        // of the user's swipe - if we're entering or exiting.
                        // This is quite simple:
                        if (position < 0) {
                            // Create your out animation here

                        } else {
                            // Create your in animation here
                        }
                    }
                }
            });

            viewPagerSlider.addOnPageChangeListener(onPageChangeListener);
        } else {

            Intent i = new Intent(SliderScreenActivity.this, CheckNetworkActivity.class);
            startActivity(i);
            finish();
            return;
        }

    }


    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 2:
//                    circleIndicator.setVisibility(View.GONE);
                    llFinish.setVisibility(View.VISIBLE);

                    break;
                default:
//                    circleIndicator.setVisibility(View.VISIBLE);
                    llFinish.setVisibility(View.GONE);


                    break;
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @OnClick(R.id.tvNext)
    public void onViewClicked() {
        PreferenceUtils.saveStartApp(this);
        Intent i = new Intent(this, CheckNetworkActivity.class);
        startActivity(i);
        finish();
    }
}
