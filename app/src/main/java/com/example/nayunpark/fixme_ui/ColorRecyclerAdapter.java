package com.example.nayunpark.fixme_ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ColorRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class ColorRecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView colorImageView;
        TextView colorTextView;

        public ColorRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            colorImageView = itemView.findViewById(R.id.colorImage);
            colorTextView = itemView.findViewById(R.id.colorText);
        }
    }

    private ArrayList<SettingsColorInfo> settingsColorInfoArrayList;
    ColorRecyclerAdapter(ArrayList<SettingsColorInfo> settingsColorInfoArrayList) {
        this.settingsColorInfoArrayList = settingsColorInfoArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_row, viewGroup, false);
        return new ColorRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ColorRecyclerViewHolder colorRecyclerViewHolder = (ColorRecyclerViewHolder) viewHolder;

        colorRecyclerViewHolder.colorImageView.setImageResource(settingsColorInfoArrayList.get(i).colorId);
        colorRecyclerViewHolder.colorTextView.setText(settingsColorInfoArrayList.get(i).colorName);
    }

    @Override
    public int getItemCount() {
        return settingsColorInfoArrayList.size();
    }
}
