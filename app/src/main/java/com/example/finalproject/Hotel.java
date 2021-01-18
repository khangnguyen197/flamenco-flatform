package com.example.finalproject;

public class Hotel {
    String hotelName, numberAdd, district, ward, phone, special, price;
    hotelImages images;

    public Hotel() {
    }

    public Hotel(String hotelName, String numberAdd, String district, String ward, String phone, String special, String price, hotelImages images) {
        this.hotelName = hotelName;
        this.numberAdd = numberAdd;
        this.district = district;
        this.ward = ward;
        this.phone = phone;
        this.special = special;
        this.price = price;
        this.images = images;
    }

    public hotelImages getImages() {
        return images;
    }
}
