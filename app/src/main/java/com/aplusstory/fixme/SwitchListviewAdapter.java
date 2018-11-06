package com.aplusstory.fixme;

import android.content.Context;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.aplusstory.fixme.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Set;

public class SwitchListviewAdapter extends ArrayAdapter<String> implements SettingsUIManager{
    private ArrayList<String> items;
    private Context context;
    private int id;
    private UserSettingsManager dm;

    public SwitchListviewAdapter(Context context, int resourceId, ArrayList<String> objects) {
        super(context, resourceId, objects);
        this.context = context;
        this.id = resourceId;
        this.items = objects;
        this.dm = new UserSettingsManager(this.context);
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {
        View v = convertView;
        SwitchListviewAdapter that = SwitchListviewAdapter.this;

        if(v == null) {
            LayoutInflater viewInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = viewInflater.inflate(R.layout.switch_list, null);
        }

        TextView textView = (TextView) v.findViewById(R.id.switchText);
        textView.setText(items.get(position));

        final String text = items.get(position);
        final Switch switchButton = (Switch)v.findViewById(R.id.switch1);
        if(position == 0){
            switchButton.setChecked(that.dm.getVibation());
        }
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SwitchListviewAdapter that = SwitchListviewAdapter.this;
                switch(position){
                    case 0:
                        that.dm.setViberation(isChecked);
                        break;
                    case 1:
                        Intent it = new Intent(that.context.getApplicationContext(), NotificationUIService.class);
                        if(isChecked){
                            that.context.startForegroundService(it);
                        } else {
                            that.context.stopService(it);
                        }
                        break;
                }
            }
        });

        return v;
    }

    public ArrayList<String> getItems() {
        return this.items;
    }

    @Override
    public void setDataManager(UserDataManager m) {
        if(m instanceof SettingsDataManager){
            this.setDataManager((SettingsDataManager)m);
        }
    }

    @Override
    public void setDataManager(SettingsDataManager m) {
        if(m instanceof UserSettingsManager){
            this.dm = (UserSettingsManager)m;
        }
    }
}
