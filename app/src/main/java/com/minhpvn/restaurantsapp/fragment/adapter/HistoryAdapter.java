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
import com.minhpvn.restaurantsapp.model.realmObject.SaveMap;
import com.minhpvn.restaurantsapp.ultil.APIInterface;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<SaveMap> data;
    private SaveMap current;
    APIInterface apiInterface;
    private OnItemClick onItemClick;

    public HistoryAdapter(Context context, List<SaveMap> data, OnItemClick onItemClick) {
        this.context = context;
        this.data = data;
        this.onItemClick = onItemClick;
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final MyHolder myHolder = (MyHolder) holder;
        current = data.get(position);
//                Glide.with(context).load(current.avatar).into(myHolder.imageView);


        myHolder.tvName.setText(current.getName());
        myHolder.tvAddress.setText(current.getDescription());
        if (current.isOpen()) {
            myHolder.tvActive.setText("Đang mở cửa");
            myHolder.tvActive.setTextColor(context.getColor(R.color.color_active));
        } else {
            myHolder.tvActive.setText("Đóng cửa");
            myHolder.tvActive.setTextColor(context.getColor(R.color.color_deactive));
        }
        myHolder.rbRatingBar.setRating((float) current.getStar());
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClick(data.get(position));
            }
        });
        myHolder.tvKm.setText(current.getKm() + " m");

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
        TextView tvKm;

        public MyHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvActive = itemView.findViewById(R.id.tvActive);
            rbRatingBar = itemView.findViewById(R.id.rbRatingBar);
            tvKm = itemView.findViewById(R.id.tvKm);

        }
    }

    public interface OnItemClick {
        void onClick(SaveMap item);
    }

}
