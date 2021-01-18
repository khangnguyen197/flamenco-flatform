package com.example.finalproject;

public class hotelImages {
    String imageUrl, name;

    public hotelImages() {
    }

    public hotelImages(String imageUrl, String name) {
        if(name.trim().equals("")){
            name = "No name";
        }
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }
}
