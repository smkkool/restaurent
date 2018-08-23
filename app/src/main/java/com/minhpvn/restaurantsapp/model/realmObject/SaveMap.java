package com.minhpvn.restaurantsapp.model.realmObject;

import com.google.android.gms.maps.model.LatLng;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class SaveMap extends RealmObject {
    private String name;
//    private LatLng latLng;
    private String description;
    private double star;
    private double km;
    private boolean isOpen;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public SaveMap() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public LatLng getLatLng() {
//        return latLng;
//    }

//    public void setLatLng(LatLng latLng) {
//        this.latLng = latLng;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public double getKm() {
        return km;
    }

    public void setKm(double km) {
        this.km = km;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
