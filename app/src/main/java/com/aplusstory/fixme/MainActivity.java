package com.aplusstory.fixme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
