package com.aplusstory.fixme;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    Toolbar toolbar2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_full_menu);

        final String[] setlist1 = {
                 this.getString(R.string.setting_item_alert_transperancy)
                ,this.getString(R.string.setting_item_alert_light_sensivity)
                ,this.getString(R.string.setting_item_alert_period)
        };

        ListView list1 = (ListView) findViewById(R.id.settingList1);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, setlist1);
        list1.setAdapter(arrayAdapter);
        list1.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch(position) {
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), AlarmOpacityActivity.class);

                                startActivity(intent);
                                break;
                            case 1:
                                Intent intent2 = new Intent(getApplicationContext(), LightSensActivity.class);
                                startActivity(intent2);
                                break;
                            case 2:
                                Intent intent3 = new Intent(getApplicationContext(), AlarmPeriodActivity.class);
                                startActivity(intent3);
                                break;
                        }
                    }
                }
        );

        final ArrayList<String> setlistV = new ArrayList<String>();
        setlistV.add(this.getString(R.string.setting_item_alert_vibration));
        setlistV.add(this.getString(R.string.setting_item_alert_activate));
        ListView listV = (ListView) findViewById(R.id.settingListV);
        SwitchListviewAdapter listviewAdapter = new SwitchListviewAdapter(this, R.layout.switch_list, setlistV);
        listV.setAdapter(listviewAdapter);

        final String[] setlist2 = {
                 this.getString(R.string.setting_item_footprint_data_duration)
                ,this.getString(R.string.setting_item_footprint_location_bookmark)
        };
        ListView list2 = (ListView) findViewById(R.id.settingList2);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, setlist2);
        list2.setAdapter(arrayAdapter2);
        list2.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch(position) {
                        case 0:
                            Intent intent = new Intent(getApplicationContext(), FootDataSettingsActivity.class);
                            startActivity(intent);
                            break;
                        case 1:
                            Intent intent2 = new Intent(getApplicationContext(), FavoritesActivity.class);
                            startActivity(intent2);
                            break;
                    }
                }
            }
        );
    }
}
