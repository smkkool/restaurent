package com.minhpvn.restaurantsapp.model;

public class MapPoint {
    private String pointName;
    private String pointDescription;
    private String pointPhoneNumber;
    private double pointLat;
    private double pointLong;

    public MapPoint(String pointName, String pointDescription, String pointPhoneNumber, double pointLat, double pointLong) {
        this.pointName = pointName;
        this.pointDescription = pointDescription;
        this.pointPhoneNumber = pointPhoneNumber;
        this.pointLat = pointLat;
        this.pointLong = pointLong;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getPointDescription() {
        return pointDescription;
    }

    public void setPointDescription(String pointDescription) {
        this.pointDescription = pointDescription;
    }

    public String getPointPhoneNumber() {
        return pointPhoneNumber;
    }

    public void setPointPhoneNumber(String pointPhoneNumber) {
        this.pointPhoneNumber = pointPhoneNumber;
    }

    public double getPointLat() {
        return pointLat;
    }

    public void setPointLat(double pointLat) {
        this.pointLat = pointLat;
    }

    public double getPointLong() {
        return pointLong;
    }

    public void setPointLong(double pointLong) {
        this.pointLong = pointLong;
    }
}
