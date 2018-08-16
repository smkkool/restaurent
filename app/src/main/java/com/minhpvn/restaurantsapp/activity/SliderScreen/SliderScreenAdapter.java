package com.minhpvn.restaurantsapp.activity.SliderScreen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minhpvn.restaurantsapp.R;

public class SliderScreenAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public int[] bgColor = {R.color.colorAccent, R.color.colorPrimary, R.color.colorGreen};
    public int[] img = {R.drawable.a, R.drawable.b, R.drawable.c};
    public String[] header = {"FAST", "MOVE", "BEST"};
    public String[] strContent = {
            "Loreting a It ypesetting, remaining essentially unchanged.Loreting a It ypesetting, remaining essentially unchanged.Loreting a It ypesetting, remaining essentially unchanged.",
            "Loreting a It ypesetting, remaining essentially unchanged.",
            "Loreting a It ypesetting, remaining essentially unchanged.Loreting a It ypesetting, remaining essentially unchanged.Loreting a It ypesetting, remaining essentially unchanged.",
    };

    public SliderScreenAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return header.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.screen_slider_item, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imgItem);
        TextView tvHeader = (TextView) view.findViewById(R.id.tvHeader);
        TextView content = (TextView) view.findViewById(R.id.tvContent);
        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.llBg);
        linearLayout.setBackgroundResource(bgColor[position]);
        imageView.setImageResource(img[position]);
        tvHeader.setText(header[position]);
        content.setText(strContent[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
