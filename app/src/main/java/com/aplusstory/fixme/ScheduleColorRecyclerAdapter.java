package com.aplusstory.fixme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ScheduleColorRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context = null;

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

    ScheduleColorRecyclerAdapter(ArrayList<SettingsColorInfo> settingsColorInfoArrayList) {
        this.settingsColorInfoArrayList = settingsColorInfoArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_row, viewGroup, false);
        this.context = viewGroup.getContext();
        return new ColorRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ColorRecyclerViewHolder colorRecyclerViewHolder = (ColorRecyclerViewHolder) viewHolder;

        colorRecyclerViewHolder.colorImageView.setImageResource(settingsColorInfoArrayList.get(i).colorId);
        colorRecyclerViewHolder.colorTextView.setText(settingsColorInfoArrayList.get(i).colorName);
        colorRecyclerViewHolder.colorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleColorRecyclerAdapter that = ScheduleColorRecyclerAdapter.this;
                SettingsColorInfo colorInfo = that.settingsColorInfoArrayList.get(i);
                Log.d(ScheduleColorRecyclerAdapter.class.getName()
                        , "color selected, code : "
                    + colorInfo.colorName);
                if(that.context != null && that.context instanceof ScheduleColorActivity){
                    ScheduleColorActivity activity = (ScheduleColorActivity)that.context;
                    Intent it = new Intent();
                    it.putExtra(ScheduleColorActivity.EXTRA_NAME_ARGUMENT, colorInfo.colorCode);
                    activity.setResult(Activity.RESULT_OK, it);
                    activity.finish();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return settingsColorInfoArrayList.size();
    }
}
