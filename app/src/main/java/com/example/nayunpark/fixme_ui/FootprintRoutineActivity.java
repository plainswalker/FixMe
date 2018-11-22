package com.example.nayunpark.fixme_ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FootprintRoutineActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footprint_routine);

        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        final TextView departureTextview = (TextView) findViewById(R.id.departureText);
        departureTextview.setText("중앙대학교");

        final TextView arrivalTextview = (TextView) findViewById(R.id.arrivalText);
        arrivalTextview.setText("스타시티 건대");

        TextView whenToWhen = (TextView) findViewById(R.id.when_to_when);
        whenToWhen.setText("17:30 출발 ~ 18:30 도착");

        TextView totalLapse = (TextView) findViewById(R.id.totalLapse);
        totalLapse.setText("소요시간: 1시간 00분");

        Button departButton = (Button) findViewById(R.id.departButton);
        departButton.setText(String.valueOf(departureTextview.getText()));
        departButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                intent.putExtra("location", departureTextview.getText());
                startActivity(intent);
            }
        });

        Button arriveButton = (Button) findViewById(R.id.arrivalButton);
        arriveButton.setText(String.valueOf(arrivalTextview.getText()));
        arriveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                intent.putExtra("location", arrivalTextview.getText());
                startActivity(intent);
            }
        });

    }
}
