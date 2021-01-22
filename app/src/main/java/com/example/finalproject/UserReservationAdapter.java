package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserReservationAdapter extends RecyclerView.Adapter<UserReservationAdapter.HotelHolder>{
    private List<UserReservation> reservationList;
    private Context context;

    public UserReservationAdapter(Context context, List<UserReservation> reservationList) {
        this.reservationList = reservationList;
        this.context = context;
    }

    @NonNull
    @Override
    public HotelHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_booking_cell, viewGroup, false);
        return new HotelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelHolder holder, int i) {

        holder.userName.setText("CUSTOMER: "+reservationList.get(i).name.toUpperCase());
        holder.tvLine1.setText(reservationList.get(i).hotelName);

        holder.tvLine2.setText(reservationList.get(i).roomType.toUpperCase()+" ");
        holder.tvLine3.setText(reservationList.get(i).dateTime);
        holder.priceTotal.setText(reservationList.get(i).priceTotal);
    }

    @Override
    public int getItemCount() {
        if (reservationList == null)
            return 0;
        else
            return reservationList.size();
    }

    public static class HotelHolder extends RecyclerView.ViewHolder {

        public TextView userName, tvLine1, tvLine2, tvLine3, priceTotal;

        public HotelHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_name);
            tvLine1 = itemView.findViewById(R.id.tv_line1);
            tvLine2 = itemView.findViewById(R.id.tv_line2);
            tvLine3 = itemView.findViewById(R.id.tv_line3);
            priceTotal = itemView.findViewById(R.id.room_price);

        }
    }
}
