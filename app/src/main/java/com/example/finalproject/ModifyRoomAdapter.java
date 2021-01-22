package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class ModifyRoomAdapter extends RecyclerView.Adapter<ModifyRoomAdapter.RoomHolder> {

    private List<ModifyRoom> roomList;
    private Context context;

    public ModifyRoomAdapter(Context context, List<ModifyRoom> roomList) {
        this.roomList = roomList;
        this.context = context;
    }

    @NonNull
    @Override
    public RoomHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.modify_cell, viewGroup, false);
        return new RoomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RoomHolder holder, final int i) {

        holder.roomName.setText(roomList.get(i).name);
        holder.roomPrice.setText("$ "+roomList.get(i).price);
        holder.roomDes.setText(roomList.get(i).des);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.roomPrice.setEnabled(true);
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

        public ImageButton btnEdit;
        public TextView roomName, roomDes;
        public EditText roomPrice;

        public RoomHolder(@NonNull View itemView) {
            super(itemView);

            roomDes = itemView.findViewById(R.id.room_description);
            roomName = itemView.findViewById(R.id.room_name);
            roomPrice = itemView.findViewById(R.id.room_price);
            btnEdit = itemView.findViewById(R.id.edit_button);

        }
    }
}

