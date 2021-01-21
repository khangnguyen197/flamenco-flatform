package com.example.finalproject;

public class Hotel {
    String hotelID, hotelName, numberAdd, district, ward, phone, special, price, imageUrl;

    public Hotel() {
    }

    public Hotel(String hotelID, String hotelName, String numberAdd, String district, String ward, String phone, String special, String price, String imageUrl) {
        this.hotelID = hotelID;
        this.hotelName = hotelName;
        this.numberAdd = numberAdd;
        this.district = district;
        this.ward = ward;
        this.phone = phone;
        this.special = special;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDistrict() {
        return district;
    }
}
