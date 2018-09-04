
package com.minhpvn.restaurantsapp.model.googleMapNearby;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.materialize.util.UIUtils;
import com.minhpvn.restaurantsapp.R;

import java.util.List;

import butterknife.ButterKnife;

public class Result extends AbstractItem<Result, Result.ViewHolder> {

    @SerializedName("geometry")
    @Expose
    private Geometry geometry;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("opening_hours")
    @Expose
    private OpeningHours openingHours;
    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;
    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("scope")
    @Expose
    private String scope;
    @SerializedName("types")
    @Expose
    private List<String> types = null;
    @SerializedName("vicinity")
    @Expose
    private String vicinity;
    @SerializedName("price_level")
    @Expose
    private Integer priceLevel;

    private Double km;

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public Integer getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(Integer priceLevel) {
        this.priceLevel = priceLevel;
    }


    @Override
    public int getType() {
        return 0;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.reward;
    }


//    @Override
//    public void bindView(ViewHolder holder, List<Object> payloads) {
//        super.bindView(holder, payloads);
//    }

    @SuppressLint("NewApi")
    @Override
    public void bindView(ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);

        Context ctx = holder.itemView.getContext();

        //set the background for the item
        int color = UIUtils.getThemeColor(ctx, R.attr.colorPrimary);

        holder.view.clearAnimation();
        holder.view.setForeground(UIUtils.getSelectablePressedBackground(ctx, UIUtils.adjustAlpha(color, 100), 50, true));


        holder.tvName.setText(name);
        holder.tvAddress.setText(vicinity);
        if (openingHours != null) {
            if (openingHours.getOpenNow() != null) {
                if (openingHours.getOpenNow()) {
                    holder.tvActive.setText("Đang mở cửa");
                    holder.tvActive.setTextColor(ctx.getColor(R.color.color_active));
                } else {
                    holder.tvActive.setText("Đóng cửa");
                    holder.tvActive.setTextColor(ctx.getColor(R.color.color_deactive));
                }
            }
        } else {
            holder.tvActive.setText("Đóng cửa");
            holder.tvActive.setTextColor(ctx.getColor(R.color.color_deactive));
        }
        if (rating != null) {
            holder.rbRatingBar.setRating(Float.parseFloat(rating.toString()));
        }

        holder.tvKm.setText(km.intValue() + " m");
    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected FrameLayout view;
        protected ImageView imageView;
        protected TextView tvName;
        protected TextView tvAddress;
        protected TextView tvActive;
        protected RatingBar rbRatingBar;
        protected TextView tvKm;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = (FrameLayout) itemView;
            this.imageView = itemView.findViewById(R.id.img);
            this.tvName = itemView.findViewById(R.id.tvName);
            this.tvAddress = itemView.findViewById(R.id.tvAddress);
            this.tvActive = itemView.findViewById(R.id.tvActive);
            this.rbRatingBar = itemView.findViewById(R.id.rbRatingBar);
            this.tvKm = itemView.findViewById(R.id.tvKm);
        }
    }
}
