package com.minhpvn.restaurantsapp.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minhpvn.restaurantsapp.R;
import com.minhpvn.restaurantsapp.model.googleMapModel.QueryAddressResponse;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * Created by smkko on 3/23/2018.
 */

public class GoogleMapPlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>   {

    private Context context;
    private LayoutInflater inflater;
    List<QueryAddressResponse.Predictions> data = new ArrayList<>();
    private List<QueryAddressResponse.Predictions> save = new ArrayList<>();
    private List<QueryAddressResponse.Predictions> wtf = new ArrayList<>();
    private OnClickEvent onClickEvent;




    public GoogleMapPlaceAdapter(Context context, List<QueryAddressResponse.Predictions> data, OnClickEvent onClickEvent) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.onClickEvent = onClickEvent;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_map_place, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        final MyHolder myHolder = (MyHolder) holder;
        myHolder.infoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onClickEvent.onClick(data.get(position));
            }
        });
        myHolder.title.setText(data.get(position).getDescription());
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

//    @Override
//    public long getItemId(int position) {
//        return position;
//    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    class MyHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView subTitle;
        LinearLayout infoLayout;
        ImageView icon;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);

            subTitle = (TextView) itemView.findViewById(R.id.sub_title);
            title = (TextView) itemView.findViewById(R.id.title);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            infoLayout = (LinearLayout) itemView.findViewById(R.id.info_layout);
        }

    }

    public interface OnClickEvent {
        void onClick(QueryAddressResponse.Predictions predictions);
    }

    public String removeAccent(String s) {
        while (s.indexOf("  ") != -1) {
            s = s.replaceAll("  ", " ");
        }
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replace('đ', 'd').replace('Đ', 'D');

    }
}