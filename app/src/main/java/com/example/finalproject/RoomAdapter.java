package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomHolder> {

    private List<Room> roomList;
    private Context context;
    private TextView roomTotal;
    private TextView[] roomType;
    public int count = 0;
    public double total;

    public RoomAdapter(Context context, List<Room> roomList, TextView roomTotal, TextView[] roomType) {
        this.roomList = roomList;
        this.context = context;
        this.roomTotal = roomTotal;
        this.roomType = roomType;
    }

    @NonNull
    @Override
    public RoomHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.room_cell, viewGroup, false);
        return new RoomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomHolder holder, final int i) {

        holder.roomName.setText(roomList.get(i).name);
        holder.roomDescription.setText(roomList.get(i).description);
        holder.roomPrice.setText("$ "+roomList.get(i).price);
        holder.roomDesLine1.setText(roomList.get(i).desline1);
        holder.roomDesLine2.setText(roomList.get(i).desline2);
        holder.roomDesLine3.setText(roomList.get(i).desline3);
        holder.btnSubmit.setText("Select");
        
        holder.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (roomList.get(i).name){
                    case "DELUXE":
                        count++;
                        roomType[0].setText("deluxe: "+ count +"x"+roomList.get(i).price+ "$");
                        break;
                    case "DOUBLE":
                        count++;
                        roomType[1].setText("double: "+ count +"x"+roomList.get(i).price+ "$");
                        break;
                    case "SINGLE":
                        count++;
                        roomType[2].setText("single: "+ count +"x"+roomList.get(i).price+ "$");
                        break;
                    case "FAMILY":
                        count++;
                        roomType[3].setText("family: "+ count +"x"+roomList.get(i).price+ "$");
                        break;
                    case "PRESIDENT":
                        count++;
                        roomType[4].setText("president: "+ count +"x"+roomList.get(i).price+ "$");
                        break;
                    default:
                        break;
                }

                final double dPrice = Double.parseDouble(roomList.get(i).price);
                total =  dPrice + total;

                DecimalFormat df = new DecimalFormat("#.##");
                String sTotal = df.format(total);
                roomTotal.setText("$ "+sTotal);

            }
        });


    }

    @Override
    public int getItemCount() {
        if (roomList == null)
            return 0;
        else
            return roomList.size();
    }

    public static class RoomHolder extends RecyclerView.ViewHolder {

        public Button btnSubmit;
        public TextView roomName, roomPrice, roomDescription, roomDesLine1, roomDesLine2, roomDesLine3;

        public RoomHolder(@NonNull View itemView) {
            super(itemView);

            roomName = itemView.findViewById(R.id.room_name);
            roomPrice = itemView.findViewById(R.id.room_price);
            roomDescription = itemView.findViewById(R.id.room_description);
            roomDesLine1 = itemView.findViewById(R.id.tv_line1);
            roomDesLine2 = itemView.findViewById(R.id.tv_line2);
            roomDesLine3 = itemView.findViewById(R.id.tv_line3);
            btnSubmit = itemView.findViewById(R.id.submit_btn);

        }
    }
}

