package com.example.nayunpark.fixme_ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class SwitchListviewAdapter extends ArrayAdapter<String> {
    public ArrayList<String> items;

    public SwitchListviewAdapter(Context context, int resourceId, ArrayList<String> objects) {
        super(context, resourceId, objects);
        this.items = objects;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;


        if(v == null) {
            LayoutInflater viewInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = viewInflater.inflate(R.layout.switch_list, null);
        }

        TextView textView = (TextView) v.findViewById(R.id.switchText);
        textView.setText(items.get(position));

        final String text = items.get(position);
        Switch switchButton = (Switch)v.findViewById(R.id.switch1);

        return v;
    }
}
