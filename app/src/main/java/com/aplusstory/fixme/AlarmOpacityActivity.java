package com.aplusstory.fixme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.nayunpark.fixme_ui.R;

public class AlarmOpacityActivity extends AppCompatActivity {
    Toolbar toolbar;
    SeekBar seekBar;
    int percentage = 0;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_opacity);

        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_full_menu);

        seekBar = (SeekBar) findViewById(R.id.seekBar3);
        textView = (TextView) findViewById(R.id.textView2);
        textView.setText(seekBar.getProgress()+"%");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                percentage = seekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                percentage = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                percentage = seekBar.getProgress();
                update();
            }
        });
    }

    public void update() {
        textView.setText(new StringBuilder().append(percentage)+"%");
    }


}
