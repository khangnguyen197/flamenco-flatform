package com.example.finalproject;

public class HotelReservation {
    String name, hotelName, dateTime, priceTotal, roomType;

    public HotelReservation() {
    }

    public HotelReservation(String name, String hotelName, String dateTime, String priceTotal, String roomType) {
        this.name = name;
        this.hotelName = hotelName;
        this.dateTime = dateTime;
        this.priceTotal = priceTotal;
        this.roomType = roomType;
    }
}
