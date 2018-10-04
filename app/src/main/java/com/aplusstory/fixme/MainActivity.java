package com.aplusstory.fixme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private ToggleButton noti_togg;
    private Button on;
    private Button off;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.on = (Button) findViewById(R.id.onbutton);
        this.off = (Button) findViewById(R.id.offbutton);

        this.on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"Service 시작",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,MyService.class);
                startService(intent);
            }
        });
        this.off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"Service 끝",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,MyService.class);
                stopService(intent);
            }
        });

        this.noti_togg = (ToggleButton) findViewById(R.id.noti_togg);
        this.noti_togg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent it = new Intent(MainActivity.this, MyService.class);
                if(isChecked){
                    startService(it);
                    Toast.makeText(MainActivity.this,"notification on", Toast.LENGTH_SHORT).show();
                } else{
                    stopService(it);
                    Toast.makeText(MainActivity.this,"notification off", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void add(View v){
        EditText opd1_txt = (EditText) findViewById(R.id.opd1);
        EditText opd2_txt = (EditText) findViewById(R.id.opd2);
        TextView res_txt = (TextView) findViewById(R.id.res);

        int opd1 = Integer.parseInt(opd1_txt.getText().toString());
        int opd2 = Integer.parseInt(opd2_txt.getText().toString());

        res_txt.setText(Integer.toString(opd1 + opd2));
    }

    public void sub(View v){ //hyueikeudabyeongsindeula
        EditText opd1_txt = (EditText) findViewById(R.id.opd1);
        EditText opd2_txt = (EditText) findViewById(R.id.opd2);
        TextView res_txt = (TextView) findViewById(R.id.res);

        int opd1 = Integer.parseInt(opd1_txt.getText().toString());
        int opd2 = Integer.parseInt(opd2_txt.getText().toString());

        res_txt.setText(Integer.toString(opd1 + opd2));
    }
    public void mul(View v){ //hyueikeudabyeongsindeula
        EditText opd1_txt = (EditText) findViewById(R.id.opd1);
        EditText opd2_txt = (EditText) findViewById(R.id.opd2);
        TextView res_txt = (TextView) findViewById(R.id.res);

        int opd1 = Integer.parseInt(opd1_txt.getText().toString());
        int opd2 = Integer.parseInt(opd2_txt.getText().toString());

        res_txt.setText(Integer.toString(opd1 + opd2));
    }
    public void div(View v){
        EditText opd1_txt = (EditText) findViewById(R.id.opd1);
        EditText opd2_txt = (EditText) findViewById(R.id.opd2);
        TextView res_txt = (TextView) findViewById(R.id.res);

        int opd1 = Integer.parseInt(opd1_txt.getText().toString());
        int opd2 = Integer.parseInt(opd2_txt.getText().toString());

        res_txt.setText(Integer.toString(opd1 / opd2));
    }
}
