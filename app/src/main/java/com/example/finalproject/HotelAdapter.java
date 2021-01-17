package com.example.finalproject;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelHolder> {

    private List<Hotel> hotelList;

    public HotelAdapter(List<Hotel> hotelList) {
        this.hotelList = hotelList;
    }

    @NonNull
    @Override
    public HotelHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell, viewGroup, false);

        return new HotelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class HotelHolder extends RecyclerView.ViewHolder {

        public ConstraintLayout container;
        public ImageView hotelImage;
        public TextView hotelName, hotelAdd, hotelPhone, hotelSpecial, priceRange;

        public HotelHolder(@NonNull View itemView) {
            super(itemView);

            hotelImage = itemView.findViewById(R.id.hotel_img);
            hotelName = itemView.findViewById(R.id.hotel_name);
            hotelAdd = itemView.findViewById(R.id.hotel_address);
            hotelPhone = itemView.findViewById(R.id.hotel_phone);
            hotelSpecial = itemView.findViewById(R.id.hotel_special);
            priceRange = itemView.findViewById(R.id.price_range);
        }

    }
}
