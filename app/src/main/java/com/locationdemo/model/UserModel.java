package com.locationdemo.model;

public class UserModel {
    public String uId;
    public String name;
    public String email;
    public String phoneNumber;
    public String password;
    public LocationModel location;

    public  String latitude;
    public  String longtitute;
    public   String timestamp;




    public UserModel(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;

    }

    public UserModel(String latitude, String longtitute, String timestamp) {
        this.latitude = latitude;
        this.longtitute = longtitute;
        this.timestamp = timestamp;
    }


    public UserModel(LocationModel locationModel) {
        location = locationModel;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
