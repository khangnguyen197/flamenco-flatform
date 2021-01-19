package com.example.finalproject;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.net.CookieHandler;
import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelHolder> {

    private List<Hotel> hotelList;
    private Context context;

    public HotelAdapter(Context context, List<Hotel> hotelList) {
        this.hotelList = hotelList;
        this.context = context;
    }

    @NonNull
    @Override
    public HotelHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell, viewGroup, false);

        return new HotelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelHolder holder, int i) {

        holder.hotelName.setText(hotelList.get(i).hotelName);
        holder.hotelAdd.setText("Address: "+hotelList.get(i).numberAdd+", Dist. "+hotelList.get(i).district);
        holder.hotelPhone.setText("Phone: +"+hotelList.get(i).phone);
        holder.hotelSpecial.setText("SPECIAL: "+hotelList.get(i).special);
        holder.priceRange.setText("Price: "+hotelList.get(i).price);

        Picasso.with(context).setLoggingEnabled(true);
        Picasso.with(context).load(hotelList.get(i).imageUrl)
                .fit()
                .centerCrop()
                .into(holder.hotelImage);
    }

    @Override
    public int getItemCount() {
        if(hotelList == null)
            return 0;
        else
            return hotelList.size();
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
