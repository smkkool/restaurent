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

public class HomeHighlightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Result> data;
    private Result current;
    APIInterface apiInterface;
    private OnItemClick onItemClick;
    public HomeHighlightAdapter(Context context, List<Result> data,OnItemClick onItemClick) {
        this.context = context;
        this.data = data;
        this.onItemClick = onItemClick;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.highlight, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final MyHolder myHolder = (MyHolder) holder;
        current = data.get(position);
//                Glide.with(context).load(current.avatar).into(myHolder.imageView);

        if(data.get(position).getRating()!=null){
            myHolder.tvRate.setText(data.get(position).getRating().toString()+ " ");
        }
        myHolder.ratingBar.setRating(1);
        myHolder.tvName.setText(current.getName());
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClick(data.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvName;
        TextView tvRate;
        RatingBar ratingBar;

        public MyHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            tvName = itemView.findViewById(R.id.tvName);
            tvRate = itemView.findViewById(R.id.tvRate);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
    public  interface OnItemClick {
        void onClick(Result item);
    }
}
