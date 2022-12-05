package com.lq.helloworld;

public class ImageExif {
    private String createTime;
    private double latitude;
    private double longitude;

    public ImageExif() {
    }

    public ImageExif(String createTime, double latitude, double longitude) {
        this.createTime = createTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "ImageExif{" +
                "createTime='" + createTime + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
