package com.aplusstory.fixme;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nayunpark.fixme_ui.R;

import java.util.ArrayList;

public class ColorRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

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
        context = viewGroup.getContext();
        return new ColorRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ColorRecyclerViewHolder colorRecyclerViewHolder = (ColorRecyclerViewHolder) viewHolder;

        LayerDrawable imageShape = (LayerDrawable) context.getResources().getDrawable(R.drawable.image_color);
        GradientDrawable imageColor = (GradientDrawable) imageShape.findDrawableByLayerId(R.id.gradient_drawable);

        colorRecyclerViewHolder.colorImageView.setImageResource(settingsColorInfoArrayList.get(i).colorId);
        switch (i) {
            default:
                imageColor.setColor(context.getResources().getColor(R.color.scheduleColor1));
                break;
            case 0:
                imageColor.setColor(context.getResources().getColor(R.color.scheduleColor2));
                break;
            case 1:
                imageColor.setColor(context.getResources().getColor(R.color.scheduleColor3));
                break;
            case 2:
                imageColor.setColor(context.getResources().getColor(R.color.scheduleColor4));
                break;
            case 3:
                imageColor.setColor(context.getResources().getColor(R.color.scheduleColor5));
                break;
            case 4:
                imageColor.setColor(context.getResources().getColor(R.color.scheduleColor6));
                break;
            case 5:
                imageColor.setColor(context.getResources().getColor(R.color.scheduleColor7));
                break;
            case 6:
                imageColor.setColor(context.getResources().getColor(R.color.scheduleColor8));
                break;
            case 7:
                imageColor.setColor(context.getResources().getColor(R.color.scheduleColor9));
                break;
        }

        colorRecyclerViewHolder.colorTextView.setText(settingsColorInfoArrayList.get(i).colorName);
    }

    @Override
    public int getItemCount() {
        return settingsColorInfoArrayList.size();
    }
}
