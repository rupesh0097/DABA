package com.example.daba.Model;

public class Users {

    String email;
    String password;
    String phone;
    String location;
    String postal;
    String landmark;
    String district;

    public Users(String email, String password, String phone, String location, String postal, String landmark, String district) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.location = location;
        this.postal = postal;
        this.landmark = landmark;
        this.district = district;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
