package com.example.finalproject;

public class Room {
    String hotelID, hotelName, numberAdd, district, ward, phone, special, price, imageUrl, deal;

    public Room() {
    }

    public Room(String hotelID, String hotelName, String numberAdd, String district, String ward, String phone, String special, String price, String imageUrl, String deal) {
        this.hotelID = hotelID;
        this.hotelName = hotelName;
        this.numberAdd = numberAdd;
        this.district = district;
        this.ward = ward;
        this.phone = phone;
        this.special = special;
        this.price = price;
        this.imageUrl = imageUrl;
        this.deal = deal;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDistrict() {
        return district;
    }
}
