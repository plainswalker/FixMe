package com.example.nayunpark.fixme_ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
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

        final String[] setlist1 = {"알림창 투명도", "밝기 민감도", "알림 주기"};
        ListView list1 = (ListView) findViewById(R.id.settingList1);

        final String[] setlistV = {"진동"};
        ListView listV = (ListView) findViewById(R.id.settingListV);

        final String[] setlist2 = {"데이터 유지 기간", "즐겨찾기 목록"};
        ListView list2 = (ListView) findViewById(R.id.settingList2);



/*
        final AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;

                SimpleAdapter adapter = (SimpleAdapter) listView.getAdapter();

                HashMap<String, Object> hashMap = (HashMap) adapter.getItem(position);

                RelativeLayout relativeLayout = (RelativeLayout) view;

                Switch switchButton = (Switch) relativeLayout.getChildAt(1);

                String status = "";

                if(switchButton.isChecked()) {
                    switchButton.setChecked(false);
                    status = "Off";
                } else {
                    switchButton.setChecked(true);
                    status = "On";
                }
                Toast.makeText(getBaseContext(), (String) hashMap.get("txt")+":"+status, Toast.LENGTH_SHORT).show();
            }
        };

        list.setOnItemClickListener(itemClickListener);

        List<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();


        HashMap<String,Object> hashMap = new HashMap<String, Object>();
        hashMap.put("txt", setlist[3]);
        arrayList.add(hashMap);


        String[] from = {"txt", "txt"};

        int[] to = {R.id.st_button, R.id.st_button};

        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), arrayList, R.layout.switchlist_custom, from, to);

        list.setAdapter(adapter);
*/



        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, setlist1);
        list1.setAdapter(arrayAdapter);

        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), setlist1[position],Toast.LENGTH_SHORT).show();
            }
        });

        ArrayAdapter<String> arrayAdapterV = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, setlistV);
        listV.setAdapter(arrayAdapterV);

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), setlistV[position],Toast.LENGTH_SHORT).show();
            }
        });



        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, setlist2);
        list2.setAdapter(arrayAdapter2);

        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), setlist2[position],Toast.LENGTH_SHORT).show();
            }
        });


    }
}
