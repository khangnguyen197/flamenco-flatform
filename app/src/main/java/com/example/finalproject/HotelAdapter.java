package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.net.CookieHandler;
import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelHolder> {

    private List<Hotel> hotelList;
    private Context context;
    private String isAdmin;

    public HotelAdapter(Context context, List<Hotel> hotelList, String isAdmin) {
        this.hotelList = hotelList;
        this.context = context;
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public HotelHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell, viewGroup, false);
        return new HotelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelHolder holder, final int i) {
        holder.hotelName.setText(hotelList.get(i).hotelName);
        holder.hotelAdd.setText(hotelList.get(i).numberAdd + ", " + hotelList.get(i).district +" "+ "District" );
        holder.hotelPhone.setText(hotelList.get(i).phone);
        holder.hotelSpecial.setText(hotelList.get(i).special);
        holder.priceRange.setText(hotelList.get(i).price);

        Picasso.with(context).setLoggingEnabled(true);
        Picasso.with(context).load(hotelList.get(i).imageUrl)
            .fit()
            .centerCrop()
            .into(holder.hotelImage);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                    Intent intent = new Intent(context,HotelDetail.class);
                    intent.putExtra("hotelID", hotelList.get(i).hotelID);
                    intent.putExtra("isAdmin", isAdmin);
                    context.startActivity(intent);
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

    public static class HotelHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView hotelImage;
        public TextView hotelName, hotelAdd, hotelPhone, hotelSpecial, priceRange;

        private ItemClickListener itemClickListener;

        public HotelHolder(@NonNull View itemView) {
            super(itemView);
            hotelImage = itemView.findViewById(R.id.hotel_img);
            hotelName = itemView.findViewById(R.id.hotel_name);
            hotelAdd = itemView.findViewById(R.id.hotel_address);
            hotelPhone = itemView.findViewById(R.id.hotel_phone);
            hotelSpecial = itemView.findViewById(R.id.hotel_special);
            priceRange = itemView.findViewById(R.id.price_range);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition());
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
        }

        public interface ItemClickListener {
            void onClick(View view, int position);
        }
}
