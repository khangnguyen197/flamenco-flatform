package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class HotelManageAdapter extends RecyclerView.Adapter<HotelManageAdapter.HotelHolder> {

    private List<Hotel> hotelList;
    private Context context;

    public HotelManageAdapter(Context context, List<Hotel> hotelList) {
        this.hotelList = hotelList;
        this.context = context;
    }

    @NonNull
    @Override
    public HotelHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hotel_manage_cell, viewGroup, false);
        return new HotelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelHolder holder, final int i) {

        holder.hotelName.setText(hotelList.get(i).hotelName);
        holder.hotelAdd.setText(hotelList.get(i).numberAdd + ", " + hotelList.get(i).district + " " + "District");
        holder.hotelPhone.setText(hotelList.get(i).phone);
        holder.hotelSpecial.setText(hotelList.get(i).special);

        Picasso.with(context).setLoggingEnabled(true);
        Picasso.with(context).load(hotelList.get(i).imageUrl)
                .fit()
                .centerCrop()
                .into(holder.hotelImage);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (hotelList == null)
            return 0;
        else
            return hotelList.size();
    }

    public void removeItem(int i) {
        hotelList.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, hotelList.size());
    }


    public static class HotelHolder extends RecyclerView.ViewHolder {

        public ImageButton btnEdit;
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
            btnEdit = itemView.findViewById(R.id.edit_button);

        }
    }
}