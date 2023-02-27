package com.example.daba;

public class Details {

    String location;
    String postal;
    String landmark;
    String phone;

    public Details(String location, String postal, String landmark, String phone) {
        this.location = location;
        this.postal = postal;
        this.landmark = landmark;
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
