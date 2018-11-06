package com.aplusstory.fixme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity implements SettingsUIManager{

    Toolbar toolbar2;
    private SettingsDataManager sm = null;

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

        final String[] setlistV = {
                 this.getString(R.string.setting_item_alert_vibration)
        };
        ListView listV = (ListView) findViewById(R.id.settingListV);

        final String[] setlist2 = {
                 this.getString(R.string.setting_item_footprint_data_duration)
                ,this.getString(R.string.setting_item_footprint_location_bookmark)
        };
        ListView list2 = (ListView) findViewById(R.id.settingList2);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, setlist1);
        list1.setAdapter(arrayAdapter);

        list1.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(), setlist1[position],Toast.LENGTH_SHORT).show();
                }
            }
        );

        ArrayAdapter<String> arrayAdapterV = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, setlistV);
        listV.setAdapter(arrayAdapterV);

        listV.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(), setlistV[position],Toast.LENGTH_SHORT).show();
                }
            }
        );

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, setlist2);
        list2.setAdapter(arrayAdapter2);

        list2.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(), setlist2[position],Toast.LENGTH_SHORT).show();
                }
            }
        );

        if(this.sm != null){
            this.sm = new UserSettingsManager(this);
        }
    }

    @Override
    public void setDataManager(UserDataManager m) {
        if(m instanceof SettingsUIManager){
            this.setDataManager((SettingsDataManager)m);
        }
    }

    @Override
    public void setDataManager(SettingsDataManager sm) {
        this.sm = sm;
    }
}
