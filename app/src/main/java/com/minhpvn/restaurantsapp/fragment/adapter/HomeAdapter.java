package com.minhpvn.restaurantsapp.fragment.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.minhpvn.restaurantsapp.R;
import com.minhpvn.restaurantsapp.model.googleMapNearby.Result;
import com.minhpvn.restaurantsapp.ultil.APIInterface;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Result> data;
    private Result current;
    APIInterface apiInterface;

    public HomeAdapter(Context context, List<Result> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.reward, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final MyHolder myHolder = (MyHolder) holder;
        current = data.get(position);
//                Glide.with(context).load(current.avatar).into(myHolder.imageView);


        myHolder.tvName.setText(current.getName());
        myHolder.tvAddress.setText(current.getVicinity());
        if (current.getOpeningHours() != null) {
            if (current.getOpeningHours().getOpenNow() != null) {
                if (current.getOpeningHours().getOpenNow()) {
                    myHolder.tvActive.setText("Đang mở cửa");
                    myHolder.tvActive.setTextColor(context.getColor(R.color.color_active));
                } else {
                    myHolder.tvActive.setText("Đóng cửa");
                    myHolder.tvActive.setTextColor(context.getColor(R.color.color_deactive));
                }
            }
        } else {
            myHolder.tvActive.setText("Đóng cửa");
            myHolder.tvActive.setTextColor(context.getColor(R.color.color_deactive));
        }
        if (current.getRating() != null) {
            myHolder.rbRatingBar.setRating(Float.parseFloat(current.getRating().toString()));
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvName;
        TextView tvAddress;
        TextView tvActive;
        RatingBar rbRatingBar;

        public MyHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvActive = itemView.findViewById(R.id.tvActive);
            rbRatingBar = itemView.findViewById(R.id.rbRatingBar);

        }
    }

}
