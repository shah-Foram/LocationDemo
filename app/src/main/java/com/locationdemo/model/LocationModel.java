package com.locationdemo.model;

public class LocationModel {


    public  String latitude;
    public  String longtitute;
    public   String timestamp;
    public String location;


    public LocationModel(String latitude, String longtitute, String timestamp) {
        this.latitude = latitude;
        this.longtitute = longtitute;
        this.timestamp = timestamp;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitute() {
        return longtitute;
    }

    public void setLongtitute(String longtitute) {
        this.longtitute = longtitute;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
