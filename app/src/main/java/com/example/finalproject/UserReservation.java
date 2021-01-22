package com.example.finalproject;

public class UserReservation {
    String name, hotelName, dateTime, priceTotal, roomType;

    public UserReservation() {
    }

    public UserReservation(String name, String hotelName, String dateTime, String priceTotal, String roomType) {
        this.name = name;
        this.hotelName = hotelName;
        this.dateTime = dateTime;
        this.priceTotal = priceTotal;
        this.roomType = roomType;
    }

    public String getName() {
        return name;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getPriceTotal() {
        return priceTotal;
    }

    public String getRoomType() {
        return roomType;
    }
}
